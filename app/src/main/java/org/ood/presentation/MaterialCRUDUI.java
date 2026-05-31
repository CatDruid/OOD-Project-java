package org.ood.presentation;

import org.ood.application.CRUDServiceInterface;
import org.ood.domain.entities.MaterialEntity;
import org.ood.presentation.Helpers.InputHandler;
import org.ood.presentation.Helpers.OutputFormatter;
import org.ood.presentation.Helpers.RequestBuilder;
import org.ood.presentation.Helpers.RequestFactory;
import org.ood.presentation.records.Results.MaterialCUDSuccessfully;
import org.ood.presentation.records.EntityRecords.MaterialRecord;

import java.util.Arrays;
import java.util.List;

public class MaterialCRUDUI extends UICRUDAbstract<MaterialEntity> {
    /**Dependency injections for initialization.
     * @param inputHandler Handler for Input operations.
     * @param outputFormatter Formatter for Output operations.
     * @param materialService Service for operations.
     * */
    public MaterialCRUDUI(InputHandler inputHandler,
                          OutputFormatter outputFormatter,
                          CRUDServiceInterface<MaterialEntity, MaterialRecord, MaterialCUDSuccessfully> materialService,
                          RequestFactory requestFactory
        ) {
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.materialService = materialService;
        this.requestFactory = requestFactory;
    }

    private final InputHandler inputHandler;
    private final OutputFormatter outputFormatter;
    private final RequestFactory requestFactory;
    private final CRUDServiceInterface<MaterialEntity, MaterialRecord, MaterialCUDSuccessfully> materialService;
    private final List<String> menuOptions = Arrays.asList("Get all materials", "Get a material by ID", "Create New Material", "Update Material", "Delete Material", "Exit");
    private boolean looping = true;


    protected void RetrieveAll() {
        try {
            List<MaterialRecord> materials = this.materialService.RetrieveAll();
            if (materials.isEmpty()) {outputFormatter.DisplayMessage("There are no materials");}
            this.outputFormatter.DisplayMessage("Displaying all materials");
            outputFormatter.PrintMaterials(materials);
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage("Couldn't retrieve materials", e.hashCode());
        }
    }
    protected void RetrieveByID() {
        int id = inputHandler.GetId("Which ID does the material have?", materialService);
        if (id == -1) {return;}
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
            RequestBuilder<MaterialRecord> requestBuilder = requestFactory.Create(MaterialRecord.class);
            successfully = materialService.Create(requestBuilder.CreateRecord());
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage("Couldn't create the material : " + e.getMessage(), e.hashCode());
            return;
        }
        outputFormatter.DisplayMessage("Successfully created the material:");
        outputFormatter.PrintMaterial(materialService.RetrieveByID(successfully.id()));

    }
    protected void Update() {
        outputFormatter.DisplayMessage("Do you want to print the IDs before choosing?");
        if(inputHandler.AskYesNo())
            RetrieveAll();

        int id = inputHandler.GetId("Which ID do you want to edit?", materialService);
        if(id == -1) {return;}

        MaterialRecord toUpdate = materialService.RetrieveByID(id);
        try{
            RequestBuilder<MaterialRecord> requestBuilder = requestFactory.Create(MaterialRecord.class);
            materialService.Update(requestBuilder.UpdateRecord(toUpdate));
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());
        }
    }
    protected void Delete() {
        outputFormatter.DisplayMessage("Do you want to print the IDs before choosing?");
        if(inputHandler.AskYesNo())
            RetrieveAll();

        int id = inputHandler.GetId("Which material do you want to delete?", materialService);
        if(id == -1) {return;}

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
