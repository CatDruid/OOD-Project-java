package org.ood.presentation.Helpers;

import org.ood.domain.RecyclingCategory;
import org.ood.presentation.records.Introspectable;

import java.util.*;
import java.util.stream.Collectors;

public class RequestBuilder<T extends Introspectable> {
    private final InputHandler inputHandler;
    private final Map<String, Class<?>> fields;
    private final RecordMapper<T> mapper;

    /**
     * Creates the Request Builder for a given record.
     * @param inputHandler      The Input Handler, used throughout the construction of a craete/update request.
     */
    public RequestBuilder(InputHandler inputHandler, Class<T> clazz, RecordMapper<T> mapper) {
        this.inputHandler = inputHandler;
        this.fields = Introspectable.GetFields(clazz);
        this.mapper = mapper;
    }

    public T UpdateRecord(T toUpdate) {
        Map<String, Object> newValues = CreateRecordLogic(toUpdate.GetValues()).GetValues();
        newValues.put("id", toUpdate.id());
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
        if (initialValues == null || initialValues.isEmpty()) {
            valuesMap = new HashMap<>();
            for (String field : fields.keySet()) {valuesMap.put(field, null);}
        } else {
            valuesMap = initialValues;
        }
        Map<String, String> labels = CreateLabels(fields.keySet());

        while(true) {
            ArrayList<String> options = CreateOptions(labels, valuesMap);
            String option = options.get(inputHandler.SelectfromRange(options));
            if(option.equals("Finish")) {
                if (inputHandler.AskYesNo("Have you chosen a value for all the attributes?")) {
                    break;
                }
                continue;
            }
            String field = !option.contains("[") ?
                    labels.get(option)
                    : labels.get(option.substring(0, option.indexOf("[") ).trim());

            Object value;
            if(field.toLowerCase().contains("category")) {
                value = inputHandler.categoryPicker(RecyclingCategory.class);
            } else {
                System.out.print(">>> ");
                value = inputHandler.GetInput(fields.get(field));
            }

            valuesMap.put(field, value);
        }
        try {
            return mapper.map(valuesMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
