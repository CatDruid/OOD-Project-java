package org.ood.presentation;

import java.util.Arrays;
import java.util.List;


public class UI implements UIInterface {
    /**Dependency injections for the main menu.
     * @param inputHandler Handler for Input operations.
     * @param outputFormatter Formatter for Output operations.
     * @param environmentalUI UI submenu for the environmental methods.
     * @param materialCRUDUI UI submenu for the material methods.
     * @param productCRUDUI UI submenu for the product methods.
     * */
    public UI (InputHandler inputHandler, OutputFormatter outputFormatter, UIInterface environmentalUI, UIInterface materialCRUDUI, UIInterface productCRUDUI ){
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.materialCRUDUI = materialCRUDUI;
        this.productCRUDUI = productCRUDUI;
        this.environmentalUI = environmentalUI;
    }

    private final InputHandler inputHandler;
    private final OutputFormatter outputFormatter;
    private final UIInterface environmentalUI;
    private final UIInterface materialCRUDUI;
    private final UIInterface productCRUDUI;
    private final List<String> menuOptions = Arrays.asList(new String[]{"Material", "Product", "Environmental and Recycling", "Exit"});
    private boolean looping = true;

    /**Runs main menu loop**/
    public void MenuLoop(){
        outputFormatter.DisplayMessage("Welcome.");
        while(looping){
            int selectedOption = inputHandler.SelectfromRange(menuOptions);
            OptionsHandler(selectedOption);
        }
    }

    /**Handles the user choice depending on the option chosen. Each option redirects to a differnetm enu.
     * @param option The user-selected option.
     * */
    private void OptionsHandler(int option){
        switch(option){
            case 0:
                this.materialCRUDUI.MenuLoop();
                break;
            case 1:
                this.productCRUDUI.MenuLoop();
                break;
            case 2:
                this.environmentalUI.MenuLoop();
                break;
            case 3:
                this.looping = false;
                break;
            default:
                outputFormatter.DisplayErrorMessage("This shouldn't have happened", 422);
        }
    }
}
