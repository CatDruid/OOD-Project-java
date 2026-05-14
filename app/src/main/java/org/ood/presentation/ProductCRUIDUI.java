package org.ood.presentation;

import org.ood.application.MaterialService;
import org.ood.application.ProductService;
import org.ood.domain.ProductCategory;
import org.ood.domain.entities.ProductEntity;
import org.ood.presentation.records.EntityRecords.MaterialRecord;
import org.ood.presentation.records.EntityRecords.ProductRecord;

import java.util.ArrayList;
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
            List<MaterialRecord> materialEntities = new ArrayList<>();
            outputFormatter.DisplayMessage("Choose the ID of the desired products materials. Choose again to remove. Type -1 to exit");
            List<MaterialRecord> allMaterial = materialService.RetrieveAll();
            while(true){
                outputFormatter.PrintMaterials(allMaterial, materialEntities);
                int selectedID = inputHandler.GetInput(Integer.class, "Choose the id to toggle material(-1 to exit)");
                if(selectedID == -1) {break;}
                try{
                    MaterialRecord toggledMaterial = materialService.RetrieveByID(selectedID);
                    if(materialEntities.contains(toggledMaterial)){materialEntities.remove(toggledMaterial);
                    } else {
                        materialEntities.add(toggledMaterial);
                    }
                } catch (Exception e) {outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());}
            }
            if(inputHandler.AskYesNo("Is everything correct?")) {try {productService.Create(new ProductRecord(-1, name,productCategory,estimatedLifespan,materialEntities));} catch(Exception e) { outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());}}
        }
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
        outputFormatter.DisplayMessage("What Product would you like to retrieve(ID)?");
        int id = inputHandler.GetInput(Integer.class);
        outputFormatter.PrintProduct(productService.RetrieveByID(id));
    }

    public void Update() {
        if(inputHandler.AskYesNo("Do you want to print the IDs before choosing?")) {RetrieveAll();}
        outputFormatter.DisplayMessage("What ID do you want to edit?");
        int id = inputHandler.GetInput(Integer.class);
        ProductRecord productRecord = productService.RetrieveByID(id);
        String name = productRecord.name();
        ProductCategory category = productRecord.category();
        float estimatedLifespan = productRecord.estimatedLifespan();
        List<MaterialRecord> materials = productRecord.materials();
        List<String> choices = Arrays.asList("Name", "Category", "EstimatedLifespan","Add Material by ID", "Finish");
        boolean loop = true;
        while(loop){
            switch (inputHandler.SelectfromRange(choices)){
                case 0 -> name = inputHandler.GetInput(String.class);
                case 1 -> category = inputHandler.categoryPicker(productService.GetCategory());
                case 2 -> estimatedLifespan = inputHandler.GetInput(Float.class);
                case 3 -> {
                    List<MaterialRecord> allMaterial = materialService.RetrieveAll();
                    List<MaterialRecord> currentMaterials = productRecord.materials();
                    while(true){
                        outputFormatter.PrintMaterials(allMaterial, currentMaterials);
                        int selectedID = inputHandler.GetInput(Integer.class, "Choose the id to toggle material(-1 to exit)");
                        if(selectedID == -1) {break;}
                        try{
                            MaterialRecord toggledMaterial = materialService.RetrieveByID(selectedID);
                            if(currentMaterials.contains(toggledMaterial)){currentMaterials.remove(toggledMaterial);
                            } else {
                                currentMaterials.add(toggledMaterial);
                            }
                        } catch (Exception e) {outputFormatter.DisplayErrorMessage(e.getMessage(),e.hashCode());}
                    }
                }
                case 4 -> {if(inputHandler.AskYesNo()) {
                    try {
                    productService.Update(new ProductRecord(productRecord.id(), name, category, estimatedLifespan, materials));
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
