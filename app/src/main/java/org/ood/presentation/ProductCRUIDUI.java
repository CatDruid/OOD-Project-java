package org.ood.presentation;

import org.ood.application.MaterialService;
import org.ood.application.ProductService;
import org.ood.domain.entities.ProductEntity;
import org.ood.presentation.Helpers.InputHandler;
import org.ood.presentation.Helpers.OutputFormatter;
import org.ood.presentation.Helpers.RequestBuilder;
import org.ood.presentation.Helpers.RequestFactory;
import org.ood.presentation.records.EntityRecords.ProductRecord;
import org.ood.presentation.records.Results.ProductCUDSuccessfully;

import java.util.Arrays;
import java.util.List;

public class ProductCRUIDUI extends UICRUDAbstract<ProductEntity> {

    private final InputHandler inputHandler;
    private final OutputFormatter outputFormatter;
    private final ProductService productService;
    private final MaterialService materialService;
    private final RequestFactory requestFactory;
    private final List<String> menuOptions = Arrays.asList("Create", "Retrieve All", "Retrieve by ID", "Update", "Delete", "Quit menu");


    public ProductCRUIDUI(InputHandler inputHandler, OutputFormatter outputFormatter, ProductService productService, MaterialService materialService, RequestFactory requestFactory) {
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.productService = productService;
        this.materialService = materialService;
        this.requestFactory = requestFactory;
    }

    public void Create() {
        if(inputHandler.AskYesNo("Do you want to print the IDs before choosing?")) {
            RetrieveAll();
            if(!inputHandler.AskYesNo("Are you sure the product is not present?")) {return;}
        }
        ProductCUDSuccessfully successfully;

        try {
            RequestBuilder<ProductRecord> requestBuilder = requestFactory.Create(materialService, ProductRecord.class);
            successfully = productService.Create(requestBuilder.CreateRecord());
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage("Couldn't create the product : " + e.getMessage(), e.hashCode());
            return;
        }
        outputFormatter.DisplayMessage("Successfully created the Product:");
        outputFormatter.PrintProduct(productService.RetrieveByID(successfully.id()));
    }

    public void RetrieveAll() {
        List<ProductRecord> productList = productService.RetrieveAll();
        if(productList != null){
            outputFormatter.PrintProducts(productList);
        } else {
            outputFormatter.DisplayWarningMessage("The Product list is empty.");
        }
    }

    public void RetrieveByID() {
        if(inputHandler.AskYesNo("Do you want to print the IDs before choosing?")) {RetrieveAll();}
        int id = inputHandler.GetId("What Product would you like to retrieve(ID)?", productService);
        if(id == -1) {return;}
        outputFormatter.PrintProduct(productService.RetrieveByID(id));
    }

    public void Update() {
        if(inputHandler.AskYesNo("Do you want to print the IDs before choosing?")) {RetrieveAll();}

        int id = inputHandler.GetId("What ID do you want to edit?", productService);
        if(id == -1) {return;}

        ProductCUDSuccessfully successfully;
        ProductRecord toUpdate = productService.RetrieveByID(id);
        try {
            RequestBuilder<ProductRecord> requestBuilder = requestFactory.Create(materialService, ProductRecord.class);
            successfully = productService.Update(requestBuilder.UpdateRecord(toUpdate));
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage("Couldn't update : " + e.getMessage(),e.hashCode());
            return;
        }
        outputFormatter.DisplayMessage("Successfully updated");
        outputFormatter.PrintProduct(productService.RetrieveByID(successfully.id()));
    }

    public void Delete() {
        if(inputHandler.AskYesNo("Do you want to print the IDs before choosing?")) {RetrieveAll();}
        int id = inputHandler.GetId("What product would you like to delete(ID):", productService);
        if(id == -1) {return;}
        outputFormatter.DisplayMessage("You are about to delete " + productService.RetrieveByID(id).name());
        outputFormatter.DisplayWarningMessage("This action is irreversible!");
        if(inputHandler.AskYesNo()) {
            try {
                productService.Delete(id);
            } catch (Exception e) {
                outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());
            }
        }
    }

    public void MenuLoop() {
        boolean menuLoop = true;
        outputFormatter.DisplayMessage("This is the Product menu. What would you like to do?");
        while(menuLoop) {
            switch(inputHandler.SelectfromRange(menuOptions)) {
                case 0 -> Create();
                case 1 -> RetrieveAll();
                case 2 -> RetrieveByID();
                case 3 -> Update();
                case 4 -> Delete();
                case 5 -> menuLoop = false;
            }
        }
    }
}
