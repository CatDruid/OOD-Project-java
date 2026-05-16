package org.ood.presentation;

import org.ood.application.CRUDServiceInterface;
import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.RecyclingCategory;
import org.ood.presentation.records.Results.MaterialCUDSuccessfully;
import org.ood.presentation.records.EntityRecords.MaterialRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MaterialCRUDUI extends UICRUDAbstract<MaterialEntity> {
    /**Dependency injections for initialization.
     * @param inputHandler Handler for Input operations.
     * @param outputFormatter Formatter for Output operations.
     * @param materialService Service for operations.
     * */
    public MaterialCRUDUI(InputHandler inputHandler,
                          OutputFormatter outputFormatter,
                          CRUDServiceInterface<MaterialEntity, MaterialRecord, MaterialCUDSuccessfully> materialService,
                          RequestBuilder requestBuilder
        ){
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.materialService = materialService;
        this.requestBuilder = requestBuilder;
        this.fields = materialService.GetFields();

    }

    private final InputHandler inputHandler;
    private final OutputFormatter outputFormatter;
    private final RequestBuilder requestBuilder;
    private final CRUDServiceInterface<MaterialEntity, MaterialRecord, MaterialCUDSuccessfully> materialService;
    private final List<String> menuOptions = Arrays.asList("Get all materials", "Get a material by ID", "Create New Material", "Update Material", "Delete Material", "Exit");
    private final Map<String, Class<?>> fields;
    private boolean looping = true;


    protected void RetrieveAll() {
        this.outputFormatter.DisplayMessage("Displaying all materials");
        try {
            List<MaterialRecord> materials = this.materialService.RetrieveAll();
            outputFormatter.PrintMaterials(materials);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected void RetrieveByID() {
        this.outputFormatter.DisplayMessage("Which ID does the material have?");
        int id = inputHandler.GetInput(Integer.class);
        try {
            MaterialRecord material = this.materialService.RetrieveByID(id);
            outputFormatter.PrintMaterial(material);
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());
        }
    }
    protected void Create() {
        MaterialCUDSuccessfully successfully;
        try {
            successfully = materialService.Create(requestBuilder.CreateRecord());
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage("Couldn't create the material : ", e.hashCode());
            return;
        }
        outputFormatter.DisplayMessage("Successfully created the material:");
        outputFormatter.PrintMaterial(materialService.RetrieveByID(successfully.id()));

    }
    protected void Update() {
        outputFormatter.DisplayMessage("Do you want to print the IDs before choosing?");
        if(inputHandler.AskYesNo())
            RetrieveAll();

        this.outputFormatter.DisplayMessage("Which ID do you want to edit?");
        int id = inputHandler.GetInput(Integer.class);
        try{
            MaterialRecord material = this.materialService.RetrieveByID(id);
            String name = material.name();
            RecyclingCategory category = material.category();
            Float mass = material.mass();
            Float emissionFactor = material.emissionFactor();
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
                            MaterialCUDSuccessfully successMessage = materialService.Update(new MaterialRecord(id, name, category, mass, emissionFactor));
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
        outputFormatter.DisplayMessage("You are about to delete " + materialService.RetrieveByID(id).name());
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
