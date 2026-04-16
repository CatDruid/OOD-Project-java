package org.ood.presentation;

public class OutputFormatter {
    public void DisplayMessage(String message) {
        System.out.println(message);
    }

    public void DisplayWarningMessage(String message) {
        System.out.println("\\u001B[33m" + "Warning: " + message + "\\u001B[37m");
    }

    public void DisplayErrorMessage(String message, int errorcode) {
        System.out.println("\\u001B[31m" + "ERROR " + errorcode + ": " + message + "\\u001B[37m");
    }
}
