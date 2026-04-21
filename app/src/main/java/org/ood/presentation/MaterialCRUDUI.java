package org.ood.presentation;

import org.ood.application.CRUDServiceInterface;
import org.ood.domain.MaterialEntity;
import org.ood.domain.RecyclingCategory;
import org.ood.presentation.records.Results.MaterialCUDSuccessfully;
import org.ood.presentation.records.requests.MaterialRequest;

import java.util.Arrays;
import java.util.List;

public class MaterialCRUDUI extends UICRUDAbstract<MaterialEntity> {
    /**Dependency injections for initialization.
     * @param inputHandler Handler for Input operations.
     * @param outputFormatter Formatter for Output operations.
     * @param materialService Service for operations.
     * */
    public MaterialCRUDUI(InputHandler inputHandler, OutputFormatter outputFormatter, CRUDServiceInterface<MaterialEntity, MaterialRequest, MaterialCUDSuccessfully> materialService){
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.materialService = materialService;
    }

    private final InputHandler inputHandler;
    private final OutputFormatter outputFormatter;
    private final CRUDServiceInterface<MaterialEntity, MaterialRequest, MaterialCUDSuccessfully> materialService;
    private final List<String> menuOptions = Arrays.asList(new String[]{"Get all products", "Get a product by ID", "Create New Product", "Update Product", "Delete Product", "Exit"});
    private boolean looping = true;


    public void RetrieveAll() {
        try {
            this.outputFormatter.DisplayMessage("Displaying all materials");
            List<MaterialEntity> materials = this.materialService.RetrieveAll();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void RetrieveByID() {
        try {
            int id = inputHandler.GetInput(Integer.class);
            this.materialService.RetrieveByID(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void Create() {
        try {

            String name = "";
            Float value = (float) 0;
            RecyclingCategory category = RecyclingCategory.Test;
            this.materialService.Create(new MaterialRequest(name, value, category));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void Update() {
        try {
            String name = "";
            Float value = (float) 0;
            RecyclingCategory category = RecyclingCategory.Test;
            int id = 0;
            this.materialService.Update(new MaterialRequest(name, value, category), 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void Delete() {
        try {
            int id = 0;
            this.materialService.Delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void MenuLoop() {
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
                this.RetrieveAll();
                break;
            case 1:
                this.RetrieveByID();
                break;
            case 2:
                this.Create();
                break;
            case 3:
                this.Update();
                break;
            case 4:
                this.Delete();
                break;
            case 5:
                this.looping = false;
                break;
            default:
                outputFormatter.DisplayErrorMessage("This shouldn't have happened", 422);
        }
    }
}
