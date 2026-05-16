package org.ood.presentation;

import org.ood.application.CRUDServiceInterface;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputHandler {
    private final Scanner scanner;
    private final OutputFormatter outputFormatter;

    // A map linking a primitive type to its wrapper class
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = Map.of(
            byte.class,    Byte.class,
            short.class,   Short.class,
            int.class,     Integer.class,
            long.class,    Long.class,
            float.class,   Float.class,
            double.class,  Double.class,
            boolean.class, Boolean.class,
            char.class,    Character.class
    );

    public InputHandler(Scanner scanner, OutputFormatter outputFormatter) {
        this.scanner = scanner;
        this.outputFormatter = outputFormatter;
    }

    public boolean AskYesNo() {
        outputFormatter.DisplayMessage("Are you sure? (N/y)");
        return switch (GetInput(String.class).toLowerCase()) {
            case "y" -> true;
            case "", "n" -> false;
            default -> {
                outputFormatter.DisplayWarningMessage("Unrecognized command. Defaulting to No");
                yield false;
            }
        };
    }

    public boolean AskYesNo(String message) {
        outputFormatter.DisplayMessage(message + " (N/y)");
        return switch (GetInput(String.class).toLowerCase()) {
            case "y" -> true;
            case "", "n" -> false;
            default -> {
                outputFormatter.DisplayWarningMessage("Unrecognized command. Defaulting to No");
                yield false;
            }
        };
    }

    public int SelectfromRange(List<String> range) {
        if (range.isEmpty()) {outputFormatter.DisplayWarningMessage("List has no options."); return 0;}
            outputFormatter.DisplayMessage("Choose from one of the following:");
            for (int i = 1; i <= range.size(); i++) {
                outputFormatter.DisplayMessage(i + ". " + range.get(i - 1));
            }
            while (true) {
                int choice = GetInput(Integer.class);
                if (choice > 0 && choice <= range.size()) {
                    return choice - 1;
                } else {
                    outputFormatter.DisplayWarningMessage("Invalid number, Please try again.");
                }
            }
    }

    public <T> T GetInput(Class<T> clazz) {
        if (clazz == null) {return null;}
        while (true) {
            // Get the input with the right class
            T input = GetInputLogic(clazz);

            // If an error occurred, skip to the next iteration and try again
            if(input == null) {continue;}

            return input;
        }
    }

    public <T> T GetInput(Class<T> clazz, String prompt) {
        if(clazz == null) {return null;}
        while (true) {
            // Display the prompt
            outputFormatter.DisplayMessage(prompt);

            // Get the input with the right class
            T input = GetInputLogic(clazz);

            // If an error occurred, skip to the next iteration and try again
            if (input == null) {continue;}

            return input;
        }
    }

    private <T> T GetInputLogic(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        Class<T> resolvedClazz = (Class<T>) PrimitiveToWrapper(clazz);



        try {
            // Get input
            String line = scanner.nextLine().trim();

            // If the input is not a text, replace ',' with '.' so it is parseable
            if (clazz != Character.class && clazz != String.class) {
                line = line.replaceAll(",", ".");
            }

            // Try constructor with String
            try {
                Constructor<T> ctor = resolvedClazz.getConstructor(String.class);
                return ctor.newInstance(line);

            } catch (NoSuchMethodException e) {
                // Fallback: try valueOf / parse static method, etc. (more code)
                throw new IllegalArgumentException("Class " + resolvedClazz.getName() +
                        " does not have a public String constructor");
            }
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage("Failed to create " + resolvedClazz.getSimpleName() + ": " + e.getMessage(), e.hashCode());
            return null;
        }
    }

    public int GetId(String prompt, CRUDServiceInterface<?,?,?> service) {
        int id;
        while(true) {
            id = GetInput(Integer.class, prompt);
            if((id >= 0 && service.IdExists(id)) || id == -1) {
                return id;
            }
            outputFormatter.DisplayMessage("That is not a valid id. Please try again or enter -1 to exit.");
        }
    }

    public <T extends Enum<T>> T categoryPicker(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        this.outputFormatter.DisplayMessage("What is the category?");
        int categoryIndex = SelectfromRange(
                Arrays.stream(values)
                        .map(Enum::name)
                        .collect(Collectors.toList()));
        return values[categoryIndex];
    }

    // Converts a primitive class to its wrapper class, if no wrapper is found return clazz
    private Class<?> PrimitiveToWrapper(Class<?> clazz) {
        return PRIMITIVE_TO_WRAPPER.getOrDefault(clazz, clazz);
    }
}