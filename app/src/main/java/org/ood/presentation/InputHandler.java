package org.ood.presentation;

import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private Scanner scanner;
    private OutputFormatter outputFormatter;

    public InputHandler(Scanner scanner, OutputFormatter outputFormatter) {
        this.scanner = scanner;
        this.outputFormatter = outputFormatter;
    }

    public boolean AskYesNo() {
        outputFormatter.DisplayMessage("Are you sure? N/y");
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
        outputFormatter.DisplayMessage("Choose from one of the following:");
        for (int i = 1; i <= range.size(); i++) {
            outputFormatter.DisplayMessage(i + ". " + range.get(i - 1));
        }
        while(true) {
            int choice = GetInput(int.class);
            if (choice > 0 && choice <= range.size()) {
                return choice - 1;
            } else {
                outputFormatter.DisplayWarningMessage("Invalid number, Please try again.");
            }
        }
    }

    public <T> T GetInput(Class<T> t){
        while(true) {
            try {
                String line = scanner.nextLine();
                return t.getConstructor(String.class).newInstance(line);
            } catch (Exception e) {
                outputFormatter.DisplayErrorMessage(e.getMessage(), e.hashCode());
            }
        }
    }

}
