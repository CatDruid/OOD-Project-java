package org.ood.domain.entities;

import org.ood.domain.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductEntity implements Entity{
    private int productID;
    private String name;
    private ProductCategory category;
    private float estimatedLifespan;
    private List<MaterialEntity> material;

    public ProductEntity(String name, ProductCategory category, float estimatedLifespan, List<MaterialEntity> material) {
        SetName(name);
        SetProductCategory(category);
        SetEstimatedLifeSpan(estimatedLifespan);
        SetMaterials(material);
    }

    public ProductEntity(int productID, String name, ProductCategory category, float estimatedLifespan, List<MaterialEntity> material) {
        SetProductID(productID);
        SetName(name);
        SetProductCategory(category);
        SetEstimatedLifeSpan(estimatedLifespan);
        SetMaterials(material);
    }

    //Set Methods
    public void SetProductID(int productID) {this.productID = productID;}
    public void SetName(String name) {
        this.name = name.isEmpty() ? "NoName" : name;
    }

    public void SetProductCategory(ProductCategory category) {
        this.category = Objects.requireNonNullElse(category, ProductCategory.Other);
    }

    public void SetEstimatedLifeSpan(float estimatedLifespan) {this.estimatedLifespan = estimatedLifespan;}
    public void SetMaterials(List<MaterialEntity> material) {
        this.material = Objects.requireNonNullElse(material, new ArrayList<>());
    }
    
    //Get Methods
    public int GetID() {return productID;}
    public String GetName() {return name;}
    public ProductCategory GetCategory() {return category;}
    public float GetEstimatedLifeSpan() {return estimatedLifespan;}
    public List<MaterialEntity> getMaterial() {return material;}
    public String GetGuidance() {
        StringBuilder guidance = new StringBuilder();
        for(MaterialEntity material : this.material)
            guidance.append(material.GetGuidance()).append("\n");
        return guidance.toString();
    }
}
