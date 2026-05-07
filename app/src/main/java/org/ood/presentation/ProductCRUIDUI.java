package org.ood.presentation;

import org.ood.application.MaterialService;
import org.ood.application.ProductService;
import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.ProductCategory;
import org.ood.domain.entities.ProductEntity;
import org.ood.presentation.records.requests.ProductRequest;

import java.util.Arrays;
import java.util.List;

public class ProductCRUIDUI extends UICRUDAbstract<ProductEntity> {

    private final InputHandler inputHandler;
    private final OutputFormatter outputFormatter;
    private final ProductService productService;
    private final MaterialService materialService;
    private final List<String> menuOptions = Arrays.asList("Create", "Retrieve All", "Retrieve by ID", "Update", "Delete", "Quit menu");


    public ProductCRUIDUI(InputHandler inputHandler, OutputFormatter outputFormatter, ProductService productService, MaterialService materialService) {
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.productService = productService;
        this.materialService = materialService;
    }

    public void Create() {
        if(inputHandler.AskYesNo("Do you want to print the IDs before choosing?")) {RetrieveAll();}
        if(inputHandler.AskYesNo("Are you sure the product is not present?")) {
            outputFormatter.DisplayMessage("You are about to be asked questions about the product. \n If you mistype anything just go though the rest and say no to the last question.");
            String name = inputHandler.GetInput(String.class, "What's the product's name?");
            ProductCategory productCategory = inputHandler.categoryPicker(ProductCategory.class);
            float estimatedLifespan = inputHandler.GetInput(Float.class, "What is the estimated lifespan of the product?");
            List<MaterialEntity> materialEntities = null;
            outputFormatter.DisplayMaterials(materialService.RetrieveAll());
            outputFormatter.DisplayMessage("Choose the ID of the desired products materials. Choose again to remove. Type -1 to exit");
            while(true) {
                int choice = inputHandler.GetInput(Integer.class);
                if(choice == -1){break;} else {
                    try{
                        MaterialEntity materialEntity = materialService.RetrieveByID(choice);
                        if(materialEntity != null) {
                            if (materialEntities.contains(materialEntity)) {materialEntities.remove(materialEntity);
                            } else {materialEntities.add(materialEntity);}
                        } else
                            outputFormatter.DisplayWarningMessage("No material with that ID exists");
                    } catch(Exception e){outputFormatter.DisplayWarningMessage("Unknown Material");}
                }
            }
            if(inputHandler.AskYesNo("Is everything correct?")) {try {productService.Create(new ProductRequest(name,productCategory,estimatedLifespan,materialEntities));} catch( Exception e) { outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());}}
        }
    }

    public void RetrieveAll() {
        List<ProductEntity> productList = productService.RetrieveAll();
        if(productList != null){
        outputFormatter.PrintProducts(productList);
        } else {outputFormatter.DisplayWarningMessage("The productlist is empty.");}
    }

    public void RetrieveByID() {
        if(inputHandler.AskYesNo("Do you want to print the IDs before choosing?")) {RetrieveAll();}
        outputFormatter.DisplayMessage("What Product would you like to retrieve(ID)?");
        int id = inputHandler.GetInput(Integer.class);
        outputFormatter.PrintProduct(productService.RetrieveByID(id));
    }

    public void Update() {
        if(inputHandler.AskYesNo("Do you want to print the IDs before choosing?")) {RetrieveAll();}
        outputFormatter.DisplayMessage("What ID do you want to edit?");
        int id = inputHandler.GetInput(Integer.class);
        ProductEntity productEntity = productService.RetrieveByID(id);
        String name = productEntity.GetName();
        ProductCategory category = productEntity.GetCategory();
        float estimatedLifespan = productEntity.GetEstimatedLifeSpan();
        List<MaterialEntity> materials = productEntity.getMaterial();
        List<String> choices = Arrays.asList("Name", "Category", "EstimatedLifespan","Add Material by ID", "Finish");
        boolean loop = true;
        while(loop){
            switch (inputHandler.SelectfromRange(choices)){
                case 0 -> name = inputHandler.GetInput(String.class);
                case 1 -> category = inputHandler.categoryPicker(productService.GetCategory());
                case 2 -> estimatedLifespan = inputHandler.GetInput(Float.class);
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
        if(inputHandler.AskYesNo("Do you want to print the IDs before choosing?")) {RetrieveAll();}
        outputFormatter.DisplayMessage("What product would you like to delete(ID):");
        int id = inputHandler.GetInput(Integer.class);
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
