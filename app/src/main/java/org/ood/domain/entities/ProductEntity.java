package org.ood.domain.entities;

import org.ood.domain.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An {@code ProductEntity} that holds it's material properties and knows what composes itself.
 */
public class ProductEntity implements Entity{
    private int productID;
    private String name;
    private ProductCategory productCategory;
    private float estimatedLifespan;
    private List<MaterialEntity> material;

    /**
     * Constructs a {@link ProductEntity} without a pre-assigned ID.
     * Intended for new products.
     *
     * @param name               The material's name.
     * @param productCategory    The product category this product belongs to.
     * @param estimatedLifespan  The product's estimated lifespan.
     * @param material           The list of materials composing the product.
     */
    public ProductEntity(String name, ProductCategory productCategory, float estimatedLifespan, List<MaterialEntity> material) {
        SetName(name);
        SetProductCategory(productCategory);
        SetEstimatedLifeSpan(estimatedLifespan);
        SetMaterials(material);
    }

    /**
     * Constructs a {@link ProductEntity} with a pre-assigned ID.
     * Intended for Update operations  wherein an ID already exists.
     *
     * @param name               The material's name.
     * @param productCategory    The product category this product belongs to.
     * @param estimatedLifespan  The product's estimated lifespan.
     * @param material           The list of materials composing the product.
     */
    public ProductEntity(int productID, String name, ProductCategory productCategory, float estimatedLifespan, List<MaterialEntity> material) {
        SetProductID(productID);
        SetName(name);
        SetProductCategory(productCategory);
        SetEstimatedLifeSpan(estimatedLifespan);
        SetMaterials(material);
    }

    //Set Methods
    public void SetProductID(int productID) {this.productID = productID;}

    /**
     * Sets the name and handles the empty case.
     * @param name The material's name. Sets it to NoName if an empty string is provided.
     */
    public void SetName(String name) {
        this.name = name.isEmpty() ? "NoName" : name;
    }

    /**
     * Sets the product category and handles the null case.
     * @param category The product's category. Other if null value is provided.
     */
    public void SetProductCategory(ProductCategory category) {
        this.productCategory = Objects.requireNonNullElse(category, ProductCategory.Other);
    }


    public void SetEstimatedLifeSpan(float estimatedLifespan) {this.estimatedLifespan = estimatedLifespan;}

    public void SetMaterials(List<MaterialEntity> material) {
        this.material = Objects.requireNonNullElse(material, new ArrayList<>());
    }
    
    //Get Methods
    public int GetID() {return productID;}
    public String GetName() {return name;}
    public ProductCategory GetCategory() {return productCategory;}
    public float GetEstimatedLifeSpan() {return estimatedLifespan;}
    public List<MaterialEntity> getMaterial() {return material;}

    /**
     * Builds the guidance for a product based on the various {@link MaterialEntity} that compose it.
     * Each material holds it's guidance, and this accesses them all in turn.
     * @return Processed string prepared for an end-user display thereof
     */
    public String GetGuidance() {
        StringBuilder guidance = new StringBuilder();
        for(MaterialEntity material : this.material)
            guidance.append(material.GetGuidance()).append("\n");
        return guidance.toString();
    }
}
