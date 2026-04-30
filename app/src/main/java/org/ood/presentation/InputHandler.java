package org.ood.presentation;

import org.ood.domain.RecyclingCategory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputHandler {
    private final Scanner scanner;
    private final OutputFormatter outputFormatter;

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

    public <T> T GetInput(Class<T> t) {
        while (true) {
            try {
                String line = scanner.nextLine().trim();

                // Try constructor with String
                try {
                    Constructor<T> ctor = t.getConstructor(String.class);
                    return ctor.newInstance(line);
                } catch (NoSuchMethodException e) {
                    // Fallback: try valueOf / parse static method, etc. (more code)
                    throw new IllegalArgumentException("Class " + t.getName() +
                            " does not have a public String constructor");
                }
            } catch (Exception e) {
                outputFormatter.DisplayErrorMessage("Failed to create " + t.getSimpleName() + ": " + e.getMessage(), e.hashCode());
            }
        }
    }

    public <T> T GetInput(Class<T> t, String question) {
        while (true) {
            outputFormatter.DisplayMessage(question);
            try {
                String line = scanner.nextLine().trim();

                // Try constructor with String
                try {
                    Constructor<T> ctor = t.getConstructor(String.class);
                    return ctor.newInstance(line);
                } catch (NoSuchMethodException e) {
                    // Fallback: try valueOf / parse static method, etc. (more code)
                    throw new IllegalArgumentException("Class " + t.getName() +
                            " does not have a public String constructor");
                }
            } catch (Exception e) {
                outputFormatter.DisplayErrorMessage("Failed to create " + t.getSimpleName() + ": " + e.getMessage(), e.hashCode());
            }
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
}