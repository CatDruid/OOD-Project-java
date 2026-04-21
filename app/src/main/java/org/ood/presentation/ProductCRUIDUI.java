package org.ood.presentation;

import org.jspecify.annotations.NonNull;
import org.ood.application.ProductService;
import org.ood.domain.MaterialEntity;
import org.ood.domain.ProductCategory;
import org.ood.domain.ProductEntity;
import org.ood.presentation.records.MaterialSelection;
import org.ood.presentation.records.requests.ProductRequest;

import java.util.Arrays;
import java.util.List;

public class ProductCRUIDUI extends UICRUDAbstract<ProductEntity> {

    private final InputHandler inputHandler;
    private final OutputFormatter outputFormatter;
    private final ProductService productService;
    private final List<String> menuOptions = Arrays.asList("Create", "Retrieve All", "Retrieve by ID", "Update", "Delete", "Quit menu");
    private boolean menuLoop = true;


    public ProductCRUIDUI(InputHandler inputHandler, OutputFormatter outputFormatter, ProductService productService) {
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.productService = productService;
    }

    public void Create() {
        outputFormatter.DisplayMessage("Do you want to print the IDs before choosing?");
        if(inputHandler.AskYesNo()) {RetrieveAll();}
        outputFormatter.DisplayMessage("Are you sure the product is not present?");
        if(inputHandler.AskYesNo()) {
            outputFormatter.DisplayMessage("You are about to be asked questions about the product. \n If you mistype anything just go though the rest and say no to the last question.");
            String name = inputHandler.GetInput(String.class);
            //TODO handle categories
            ProductCategory productCategory = null;
            float estimatedLifespan = inputHandler.GetInput(Float.class);
            //TODO handle materials
            List<MaterialEntity> materialEntities = null;
            outputFormatter.DisplayMessage("Is everything correct?");
            if(inputHandler.AskYesNo()) {try {productService.Create(new ProductRequest(name,productCategory,estimatedLifespan,materialEntities));} catch( Exception e) { outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());};}
        }
    }

    public void RetrieveAll() {
        List<ProductEntity> productList = productService.RetrieveAll();
        if(productList != null){
        outputFormatter.PrintProducts(productList);
        } else {outputFormatter.DisplayWarningMessage("The productlist is empty.");}
    }

    public void RetrieveByID() {
        outputFormatter.DisplayMessage("Do you want to print the IDs before choosing?");
        if(inputHandler.AskYesNo()) {RetrieveAll();}
        outputFormatter.DisplayMessage("What Product would you like to retrieve(ID)?");
        int id = inputHandler.GetInput(int.class);
        outputFormatter.PrintProduct(productService.RetrieveByID(id));
    }

    public void Update() {
        outputFormatter.DisplayMessage("Do you want to print the IDs before choosing?");
        if(inputHandler.AskYesNo()) {RetrieveAll();}
        outputFormatter.DisplayMessage("What ID do you want to edit?");
        int id = inputHandler.GetInput(Integer.class);
        ProductEntity productEntity = productService.RetrieveByID(id);
        String name = productEntity.GetName();
        ProductCategory category = productEntity.GetCategory();
        float estimatedLifespan = productEntity.GetEstimatedLifeSpan();
        //TODO Missmatch
        List<MaterialEntity> materials = productEntity.getMaterial();
        List<String> choices = Arrays.asList("Name", "Category", "EstimatedLifespan","Add Material by ID", "Finish");
        boolean loop = true;
        while(loop){
            switch (inputHandler.SelectfromRange(choices)){
                case 0 -> name = inputHandler.GetInput(String.class);
                //TODO implement category handling
                case 1 -> {}
                case 2 -> estimatedLifespan = inputHandler.GetInput(Float.class);
                //TODO implement material picking
                case 3 -> {
                    outputFormatter.DisplayMessage("Fetching material list.");

                }
                case 4 -> {if(inputHandler.AskYesNo()) {
                    try {
                    productService.Update(new ProductRequest(name, category, estimatedLifespan, materials), productEntity.GetProductID());
                    } catch (Exception e) {outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());}
                    loop = false;
                }}

            }

        }
    }

    public void Delete() {
        outputFormatter.DisplayMessage("Do you want to print the IDs before choosing?");
        if(inputHandler.AskYesNo()) {RetrieveAll();}
        outputFormatter.DisplayMessage("What product would you like to delete(ID):");
        int id = inputHandler.GetInput(int.class);
        outputFormatter.DisplayMessage("You are about to delete " + productService.RetrieveByID(id).GetName());
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
