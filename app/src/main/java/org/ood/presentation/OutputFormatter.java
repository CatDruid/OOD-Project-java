package org.ood.presentation;

import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.entities.ProductEntity;

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

    public void PrintProduct(ProductEntity productEntity) {
        System.out.println(productEntity.GetName() + ":\n"
                + "ID: " + productEntity.GetID() + "\n"
                + "Category: " + productEntity.GetCategory() + "\n"
                + "Estimated lifespan: " + productEntity.GetEstimatedLifeSpan() + "\n"
        );
        System.out.println("Materials:");
        List<MaterialEntity> materials = productEntity.getMaterial();
        for (MaterialEntity material : materials) {
            System.out.println(material.GetName());
        }
    }

    public void PrintProducts(List<ProductEntity>productEntityList) {
        System.out.println("Products:");
        for(ProductEntity productEntity : productEntityList) {
            System.out.println("(" + productEntity.GetID() + ")  " + productEntity.GetName());
        }
    }

    public void DisplayMaterials(List<MaterialEntity> materialEntityList){
        System.out.println("Materials:");
        for(MaterialEntity entity : materialEntityList){
            System.out.println("(" + entity.GetID() + ")  " + entity.GetName());
        }
    }

    public void DisplayMaterials(List<MaterialEntity> materialEntityList, List<MaterialEntity> currentMaterialList){
        System.out.println("Materials(x means selected.):");
        for(MaterialEntity entity : materialEntityList){
            String brackets;
            if(currentMaterialList.contains(entity)) {brackets = "(x)";} else {brackets = "()";}
            System.out.println(brackets + "    (" + entity.GetID() + ")  " + entity.GetName());
        }
    }

    public void DisplayMaterial(MaterialEntity material){
        System.out.println(material.GetName() + ":\n"
                + "ID: " + material.GetID() + "\n"
                + "Category: " + material.GetRecyclingCategory() + "\n"
        );
    }
}
