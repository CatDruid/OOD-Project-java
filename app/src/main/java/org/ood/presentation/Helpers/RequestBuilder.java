package org.ood.presentation.Helpers;

import org.ood.application.MaterialService;
import org.ood.domain.ProductCategory;
import org.ood.domain.RecyclingCategory;
import org.ood.presentation.records.EntityRecords.MaterialRecord;
import org.ood.presentation.records.Introspectable;

import java.util.*;
import java.util.stream.Collectors;

public class RequestBuilder<T extends Introspectable> {
    private final InputHandler inputHandler;
    private final Map<String, Class<?>> fields;
    private final RecordMapper<T> mapper;
    private final OutputFormatter outputFormatter;
    private MaterialService materialService = null;

    /**
     * Creates the Request Builder for a given record.
     * @param inputHandler      The Input Handler, used throughout the construction of a craete/update request.
     */
    public RequestBuilder(InputHandler inputHandler,
                          Class<T> clazz,
                          RecordMapper<T> mapper,
                          OutputFormatter outputFormatter,
                          MaterialService materialService) {
        this.inputHandler = inputHandler;
        this.fields = Introspectable.GetFields(clazz);
        this.mapper = mapper;
        this.outputFormatter = outputFormatter;
        this.materialService = materialService;
    }

    public RequestBuilder(InputHandler inputHandler,
                          Class<T> clazz,
                          RecordMapper<T> mapper,
                          OutputFormatter outputFormatter) {
        this.inputHandler = inputHandler;
        this.fields = Introspectable.GetFields(clazz);
        this.mapper = mapper;
        this.outputFormatter = outputFormatter;
    }

    /**
     * Makes use of CreateRecordLogic to update a record
     * @param toUpdate The record to update
     * @return the updated record
     */
    public T UpdateRecord(T toUpdate) {
        // Get the initial values from the record and run the creation logic
        Map<String, Object> newValues = CreateRecordLogic(toUpdate.GetValues()).GetValues();
        // Add the id again
        newValues.put("id", toUpdate.id());
        // Return the updated record
        try {
            return mapper.map(newValues);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T CreateRecord() {
        return CreateRecordLogic(null);
    }

    /**
     * The logic for the generic creation and update of a record, thus centralizing the logic for Create and Update requests here.
     * Currently, it only implements MaterialRecord, but generic types are used throughout in the ambition of expanding further from it if the project were larger.
     * @param initialValues         The initial values to seed the record with,
     * @return                      The finalized record.
     */
    private T CreateRecordLogic(Map<String, Object> initialValues) {
        Map<String, Object> valuesMap;

        // If there are no initial values; Create a new list with the fields and null for the values
        if (initialValues == null || initialValues.isEmpty()) {
            valuesMap = new HashMap<>();
            for (String field : fields.keySet()) {valuesMap.put(field, null);}
        } else {
            valuesMap = initialValues;
        }
        // Create the labels for the fields
        Map<String, String> labels = CreateLabels(fields.keySet());

        while(true) {
            //Create the options with updated value display
            ArrayList<String> options = CreateOptions(labels, valuesMap);
            // Get the input from the user
            String chosenOption = options.get(inputHandler.SelectfromRange(options));
            // If the choice is to quit; Ask one more time
            if(chosenOption.equals("Finish")) {
                if (inputHandler.AskYesNo("Have you chosen a value for all the attributes?")) {
                    break;
                }
                continue;
            }

            // Trim the option from the value, if there is one
            String trimmedOption = chosenOption;
            if(chosenOption.contains("[")) {
                trimmedOption = chosenOption.substring(0, chosenOption.indexOf("[") ).trim();
            }
            // Get the fields name from the label
            String field = labels.get(trimmedOption);

            // Value gathering:
            Object value;
            // If the field is a productCategory, use the categoryPicker
            if(field.equals("recyclingCategory")) {
                value = inputHandler.categoryPicker(RecyclingCategory.class);
            }
            else if (field.equals("productCategory")) {
                value = inputHandler.categoryPicker(ProductCategory.class);
            }
            // If the field is the list of materials
            else if(field.equals("materials") && fields.get(field) == List.class) {
                @SuppressWarnings("unchecked")
                List<MaterialRecord> currentMaterials = (List<MaterialRecord>) (valuesMap.get(field) != null ? valuesMap.get(field) : new ArrayList<MaterialRecord>());
                value = CreateMaterialList(currentMaterials);
            }
            // Else get the input from the user and
            else {
                System.out.print(">>> ");
                value = inputHandler.GetInput(fields.get(field));
            }
            // Store the value in a map
            valuesMap.put(field, value);
        }
        // Return the initialized record with the values
        try {
            return mapper.map(valuesMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<MaterialRecord> CreateMaterialList(List<MaterialRecord> materialSelection) {
        if (materialService == null) {throw new NullPointerException("Couldn't access materials");}

        List<MaterialRecord> allMaterials = materialService.RetrieveAll();
        outputFormatter.DisplayMessage("Choose the ID of the desired products materials. Choose again to remove. Type -1 to exit");

        while (true) {
            outputFormatter.PrintMaterialSelection(allMaterials, materialSelection);
            int selectedID = inputHandler.GetInput(Integer.class, "Choose the id to toggle material(-1 to exit)");
            if(selectedID == -1) {break;}
            try {
                MaterialRecord toggledMaterial = materialService.RetrieveByID(selectedID);
                if(materialSelection.contains(toggledMaterial)) {
                    materialSelection.remove(toggledMaterial);
                } else {
                    materialSelection.add(toggledMaterial);
                }
            } catch (Exception e) {
                outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());
            }
        }
        return materialSelection;
    }

    /**
     * Generates the list of options, alongside the visualized of its current values.
     * @param labels            The labels of each type, as created per CreateLabels
     * @param valuesMap         A map of the currently-chosen values of the object being created.
     * @return                  A list of options.
     */
    private ArrayList<String> CreateOptions(Map<String, String> labels, Map<String, Object> valuesMap) {
        ArrayList<String> options = new ArrayList<>(labels.keySet().stream()
                .map(option ->
                        option + (valuesMap.getOrDefault(labels.get(option), null) == null ?
                                ""
                                : String.format(" [%s]", valuesMap.getOrDefault(labels.get(option), null).toString())))
                .toList());
        options.addLast("Finish"); return options;
    }

    /**
     * Takes a list of fields and returns a map with labels and fields
     * @param fields A list of the fields of MaterialRecord
     * @return Map with the label as keys and the corresponding fields as values
     */
    private Map<String, String> CreateLabels(Collection<String> fields) {
        // This delimer is used to separate a string by uppercase letters, allowing consecutive uppercase letters
        String delimiter = "(?<=\\p{Ll})(?=\\p{Lu})|(?=\\p{Lu}\\p{Ll})";

        return fields.stream()
                // Filters out the id
                .filter(field -> !field.equals("id"))
                // Creates the map
                .collect(Collectors.toMap(
                        // Keys: Splits the field into several words if there are uppercase letters
                        field -> Arrays.stream(field.split(delimiter))
                                // Makes the first letter of each word uppercase
                                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                                // join the words to one string
                                .collect(Collectors.joining(" ")),
                        // Values: Takes the field as the value
                        field -> field,
                        // Overwrites duplicates that can occur; Though they shouldn't as that would be bad naming convention
                        (existing, _) -> existing
                ));
    }
}
