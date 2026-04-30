package org.ood.domain;

import java.util.List;

public class ProductEntity {
    private int productID;
    private String name;
    private ProductCategory category;
    private float estimatedLifespan;
    private List<MaterialEntity> material;

    public ProductEntity(String name, ProductCategory category, float estimatedLifespan, List<MaterialEntity> material) {
        this.name = name;
        this.category = category;
        this.estimatedLifespan = estimatedLifespan;
        this.material = material;
    }

    public ProductEntity(String name, ProductCategory category, float estimatedLifespan, List<MaterialEntity> material, int productID) {
        this.productID = productID;
        this.name = name;
        this.category = category;
        this.estimatedLifespan = estimatedLifespan;
        this.material = material;
    }

    //Set Methods
    public void SetProductID(int productID) {this.productID = productID;}
    public void SetName(String name) {this.name = name;}
    public void SetProductCategory(ProductCategory category) {this.category = category;}
    public void SetEstimatedLifeSpan(float estimatedLifespan) {this.estimatedLifespan = estimatedLifespan;}
    public void SetMaterials(List<MaterialEntity> material) {this.material = material;}
    
    //Get Methods
    public int GetProductID() {return productID;}
    public String GetName() {return name;}
    public ProductCategory GetCategory() {return category;}
    public float GetEstimatedLifeSpan() {return estimatedLifespan;}
    public List<MaterialEntity> getMaterial() {return material;}
}
