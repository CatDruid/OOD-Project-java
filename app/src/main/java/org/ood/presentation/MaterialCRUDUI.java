package org.ood.presentation;

import org.ood.application.CRUDServiceInterface;
import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.RecyclingCategory;
import org.ood.presentation.records.Results.MaterialCUDSuccessfully;
import org.ood.presentation.records.requests.MaterialRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private final List<String> menuOptions = Arrays.asList("Get all materials", "Get a material by ID", "Create New Material", "Update Material", "Delete Material", "Exit");
    private boolean looping = true;


    protected void RetrieveAll() {
        this.outputFormatter.DisplayMessage("Displaying all materials");
        try {
            List<MaterialEntity> materials = this.materialService.RetrieveAll();
            outputFormatter.DisplayMaterials(materials);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected void RetrieveByID() {
        this.outputFormatter.DisplayMessage("Which ID does the material have?");
        int id = inputHandler.GetInput(Integer.class);
        try {
            MaterialEntity material = this.materialService.RetrieveByID(id);
            outputFormatter.DisplayMaterial(material);
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());
        }
    }
    protected void Create() {
        this.outputFormatter.DisplayMessage("What is the material's name?");
        String name = inputHandler.GetInput(String.class);
        this.outputFormatter.DisplayMessage("What is the material's mass?");
        Float mass = inputHandler.GetInput(Float.class);
        this.outputFormatter.DisplayMessage("What is the material's emission factor?");
        Float emissionFactor = inputHandler.GetInput(Float.class);
        this.outputFormatter.DisplayMessage("What is the material's category?");
        int categoryIndex = this.inputHandler.SelectfromRange(
                Arrays.stream(RecyclingCategory.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()));
        RecyclingCategory category = RecyclingCategory.values()[categoryIndex];
        try {
            MaterialCUDSuccessfully successMessage = this.materialService.Create(new MaterialRequest(name, category, mass, emissionFactor));
            outputFormatter.DisplayMessage("[" + successMessage.id() + "] " + successMessage.name());
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());
        }
    }
    protected void Update() {
        outputFormatter.DisplayMessage("Do you want to print the IDs before choosing?");
        if(inputHandler.AskYesNo())
            RetrieveAll();

        this.outputFormatter.DisplayMessage("Which ID do you want to edit?");
        int id = inputHandler.GetInput(Integer.class);
        try{
            MaterialEntity material = this.materialService.RetrieveByID(id);
            String name = material.GetName();
            RecyclingCategory category = material.GetRecyclingCategory();
            Float mass = material.GetMass();
            Float emissionFactor = material.GetEmissionFactor();
            List<String> choices = Arrays.asList("Name", "Category", "Mass", "Emission Factor", "Finish");
            boolean loop = true;
            while(loop){
                switch (inputHandler.SelectfromRange(choices)){
                    case 0:
                        this.outputFormatter.DisplayMessage("What is the material's new name?");
                        name = inputHandler.GetInput(String.class);
                        break;
                    case 1:
                        this.outputFormatter.DisplayMessage("What is the material's new category?");
                        int categoryIndex = this.inputHandler.SelectfromRange(
                                Arrays.stream(RecyclingCategory.values())
                                        .map(Enum::name)
                                        .collect(Collectors.toList()));
                        category = RecyclingCategory.values()[categoryIndex];
                        break;
                    case 2:
                        this.outputFormatter.DisplayMessage("What is the material's new mass?");
                        mass = inputHandler.GetInput(Float.class);
                        break;
                    case 3:
                        this.outputFormatter.DisplayMessage("What is the material's new emission factor?");
                        emissionFactor = inputHandler.GetInput(Float.class);
                        break;
                    case 4: {
                        if(inputHandler.AskYesNo()){
                            loop = false;
                            MaterialCUDSuccessfully successMessage = materialService.Update(new MaterialRequest(name, category, mass, emissionFactor), id);
                            outputFormatter.DisplayMessage("[" + successMessage.id() + "] " + successMessage.name());
                        }
                    }
                }
            }
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());
        }
    }
    protected void Delete() {
        outputFormatter.DisplayMessage("Do you want to print the IDs before choosing?");
        if(inputHandler.AskYesNo())
            RetrieveAll();
        this.outputFormatter.DisplayMessage("Which material do you want to delete?");
        int id = inputHandler.GetInput(Integer.class);
        outputFormatter.DisplayMessage("You are about to delete " + materialService.RetrieveByID(id).GetName());
        outputFormatter.DisplayWarningMessage("This action is irreversible!");
        if(inputHandler.AskYesNo()) {
            try {
                MaterialCUDSuccessfully successMessage = materialService.Delete(id);
                outputFormatter.DisplayMessage("[" + successMessage.id() + "] " + successMessage.name());
            } catch (Exception e) {
                outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());
            }
        }
    }
    public void MenuLoop() {
        looping = true;
        while(looping){
            int selectedOption = inputHandler.SelectfromRange(menuOptions);
            OptionsHandler(selectedOption);
        }
    }
    /**Handles the user choice depending on the option chosen. Each option redirects to a different menu.
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
