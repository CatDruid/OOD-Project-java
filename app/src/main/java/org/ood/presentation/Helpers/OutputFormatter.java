package org.ood.presentation.Helpers;

import org.ood.presentation.records.EntityRecords.MaterialRecord;
import org.ood.presentation.records.EntityRecords.ProductRecord;

import java.util.List;

public class OutputFormatter {
    public void DisplayMessage(String message) {
        System.out.println(message);
    }

    public void DisplayWarningMessage(String message) {
        System.out.println("\u001B[33m" + "Warning: " + message + "\u001B[0m");
    }

    public void DisplayErrorMessage(String message, int errorcode) {
        System.out.println("\u001B[31m" + "ERROR " + errorcode + ": " + message + "\u001B[0m");
    }

    public void PrintProduct(ProductRecord productEntity) {
        System.out.println(productEntity.name() + ":\n"
                + "ID: " + productEntity.id() + "\n"
                + "Category: " + productEntity.category() + "\n"
                + "Estimated lifespan: " + productEntity.estimatedLifespan() + "\n"
        );
        System.out.println("Materials:");
        List<MaterialRecord> materials = productEntity.materials();
        for (MaterialRecord material : materials) {
            System.out.println(material.name());
        }
    }

    public void PrintProducts(List<ProductRecord>productEntityList) {
        System.out.println("Products:");
        for(ProductRecord productEntity : productEntityList) {
            System.out.println("(" + productEntity.id() + ")  " + productEntity.name());
        }
    }

    public void PrintMaterials(List<MaterialRecord> materialEntityList){
        System.out.println("Materials:");
        for(MaterialRecord entity : materialEntityList){
            System.out.println("(" + entity.id() + ")  " + entity.name());
        }
    }

    public void PrintMaterials(List<MaterialRecord> materialEntityList, List<MaterialRecord> currentMaterialList){
        System.out.println("Materials(x means selected.):");
        for(MaterialRecord entity : materialEntityList){
            String brackets;
            if(currentMaterialList.contains(entity)) {brackets = "(x)";} else {brackets = "()";}
            System.out.println(brackets + "    (" + entity.id() + ")  " + entity.name());
        }
    }

    public void PrintMaterial(MaterialRecord material){
        System.out.println(material.name() + ":\n"
                + "ID: " + material.id() + "\n"
                + "Category: " + material.category() + "\n"
        );
    }
}
