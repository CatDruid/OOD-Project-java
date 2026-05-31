package org.ood.presentation.Helpers;

import com.google.common.primitives.Primitives;
import org.ood.application.CRUDServiceInterface;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The class centralizing the Input operations and functionality.
 */
public class InputHandler {
    private final Scanner scanner;
    private final OutputFormatter outputFormatter;

    /**
     * Constructs the class with what it requires to function.
     * @param scanner               The scanner, of which it has a monopoly: none others have access to it.
     * @param outputFormatter       The output formatter, for the user messages.
     */
    public InputHandler(Scanner scanner, OutputFormatter outputFormatter) {
        this.scanner = scanner;
        this.outputFormatter = outputFormatter;
    }

    /**
     * Simple Y/n question handler.
     * @return              A boolean value, as per chosen by the user.
     */
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


    /**
     * An overload of the y/n handler, to utilize in tandem with custom messages.
     * @return              A boolean value, as per chosen by the user.
     */
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

    /**
     * This is utilized for the user to select from an array of options.
     * @param range         A list of strings, each of them representing an option.
     * @return              the index of which option was chosen.
     */
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

    /**
     * Retrieves a particular input of the user of a generic class.
     * @param clazz         The class(zz) that is intended to be inputted. Integrer, float, etc.
     * @return              An object of the type of said class.
     */
    public <T> T GetInput(Class<T> clazz) {
        if (clazz == null) {return null;}
        while (true) {
            // Get the input with the right class
            T input;
            try {
                input = GetInputLogic(clazz);
            } catch (IllegalArgumentException e) {
                outputFormatter.DisplayErrorMessage("Couldn't read the input", e.hashCode());
                return null;
            } catch (Exception e) {
                outputFormatter.DisplayErrorMessage("Failed to create " + clazz.getSimpleName() + ": " + e.getMessage(), e.hashCode());
                continue;
            }

            return input;
        }
    }

    /**
     * Retrieves a particular input of the user of a generic class, this time with a custom prompt.
     * @param prompt        The message to be displayed to the user.
     * @param clazz         The class(zz) that is intended to be inputted. Integer, float, etc.
     * @return              An object of the type of said class.
     */
    public <T> T GetInput(Class<T> clazz, String prompt) {
        if(clazz == null) {return null;}
        while (true) {
            // Display the prompt
            outputFormatter.DisplayMessage(prompt);

            // Get the input with the right class
            T input;
            try {
                input = GetInputLogic(clazz);
            } catch (IllegalArgumentException e) {
                outputFormatter.DisplayErrorMessage("Couldn't read the input", e.hashCode());
                return null;
            } catch (Exception e) {
                outputFormatter.DisplayErrorMessage("Failed to create " + clazz.getSimpleName() + ": " + e.getMessage(), e.hashCode());
                continue;
            }

            return input;
        }
    }

    private <T> T GetInputLogic(Class<T> clazz) throws Exception{
        Class<T> resolvedClazz = Primitives.wrap(clazz);


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

    }

    /**
     * Custom logic to select IDs, based on the premise that an ID must be above and equal to zero and that a service stores entities.
     * @param prompt            The prompting message.
     * @param service           The interface of a CRUDService, which by contract has an IDExists.
     * @return                  An ID, if successful. The loop shall continue until a valid and existing one has been inputted.
     */
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

    /**
     * Custom logic for the inputting of an option, specifically in this project, categories.
     * @param enumClass         The enum class of the productCategory being chosen.
     * @return                  The chosen value of the enum.
     */
    public <T extends Enum<T>> T categoryPicker(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        this.outputFormatter.DisplayMessage("What is the productCategory?");
        int categoryIndex = SelectfromRange(
                Arrays.stream(values)
                        .map(Enum::name)
                        .collect(Collectors.toList()));
        return values[categoryIndex];
    }
}