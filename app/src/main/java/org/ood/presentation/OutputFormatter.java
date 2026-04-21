package org.ood.presentation;

import org.ood.domain.MaterialEntity;
import org.ood.domain.ProductCategory;
import org.ood.domain.ProductEntity;
import org.ood.presentation.records.MaterialSelection;

import java.util.List;

public class OutputFormatter {
    public void DisplayMessage(String message) {
        System.out.println(message);
    }

    public void DisplayWarningMessage(String message) {
        System.out.println("\u001B[33m" + "Warning: " + message + "\u001B[37m");
    }

    public void DisplayErrorMessage(String message, int errorcode) {
        System.out.println("\u001B[31m" + "ERROR " + errorcode + ": " + message + "\u001B[37m");
    }

    public void PrintProduct(ProductEntity productEntity) {
        System.out.println(productEntity.GetName() + ":\n"
                + "ID: " + productEntity.GetProductID() + "\n"
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
            System.out.println("(" + productEntity.GetProductID() + ")  " + productEntity.GetName());
        }
    }
}
