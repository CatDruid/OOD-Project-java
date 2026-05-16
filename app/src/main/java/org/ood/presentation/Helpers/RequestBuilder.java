package org.ood.presentation.Helpers;

import org.ood.domain.RecyclingCategory;
import org.ood.presentation.records.EntityRecords.MaterialRecord;

import java.util.*;
import java.util.stream.Collectors;

public class RequestBuilder {
    private final InputHandler inputHandler;
    private final Map<String, Class<?>> fields;

    public RequestBuilder(InputHandler inputHandler, Map<String, Class<?>> fields) {
        this.inputHandler = inputHandler;
        this.fields = fields;
    }

    public MaterialRecord CreateRecord() {
        Map<String, Object> valuesMap = new HashMap<>(); for (String field : fields.keySet()) {valuesMap.put(field, null);}
        Map<String, String> labels = CreateLabels(fields.keySet());

        while(true) {
            ArrayList<String> options = CreateOptions(labels, valuesMap);
            String option = options.get(inputHandler.SelectfromRange(options));
            if(option.equals("Create")) {
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
        return ValuesToRecord(valuesMap);
    }


    private MaterialRecord ValuesToRecord(Map<String, Object> valuesMap) {
        try {
            return new MaterialRecord(
                    (Integer) valuesMap.get("id"),
                    (String) valuesMap.get("name"),
                    (RecyclingCategory) valuesMap.get("category"),
                    (float) valuesMap.get("mass"),
                    (float) valuesMap.get("emissionFactor")
            );
        } catch (Exception e) {
            return null;
        }
    }

    private ArrayList<String> CreateOptions(Map<String, String> labels, Map<String, Object> valuesMap) {
        ArrayList<String> options = new ArrayList<>(labels.keySet().stream()
                .map(option ->
                        option += (valuesMap.getOrDefault(labels.get(option), null) == null ?
                                ""
                                : String.format(" [%s]", valuesMap.getOrDefault(labels.get(option), null).toString())))
                .toList());
        options.addLast("Create"); return options;
    }

    /**
     * Takes a list of fields and returns a map with labels and fields
     * @param fields A list of the fields of MaterialRecord
     * @return Map with the label as keys and the corresponding fields as values
     */
    private Map<String, String> CreateLabels(Collection<String> fields) {
        // This delimer is used to separate a string by uppercase letters, allowing consecutive uppercase letters
        String delimiter = "(?<=\\p{Ll})(?=\\p{Lu})|(?=\\p{Lu}\\p{Ll})";

        // Add a finish option
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
