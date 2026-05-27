package org.ood.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.entities.ProductEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityTests {

    // Fixtures

    private MaterialEntity plasticMaterial;
    private MaterialEntity glassMaterial;
    private List<MaterialEntity> twoMaterials;

    @BeforeEach
    void setUp() throws Exception {
        plasticMaterial = new MaterialEntity("Bottle Cap",  RecyclingCategory.Plastic, 0.05f, 1.2f);
        glassMaterial   = new MaterialEntity("Glass Panel", RecyclingCategory.Clear_Glass,   0.80f, 0.8f);
        twoMaterials    = new ArrayList<>(List.of(plasticMaterial, glassMaterial));
    }

    // Helper – mirrors MaterialEntity.GetGuidance()
    private static String guidanceOf(MaterialEntity m) {
        return m.GetName() + ": " + m.GetRecyclingCategory().guidance;
    }

    // Constructor – without ID

    @Test
    void constructor_withoutID_storesAllFields() {
        ProductEntity product = new ProductEntity(
                "Reusable Bottle", ProductCategory.Kitchenware, 5.0f, twoMaterials);

        assertEquals("Reusable Bottle",           product.GetName());
        assertEquals(ProductCategory.Kitchenware, product.GetCategory());
        assertEquals(5.0f,                        product.GetEstimatedLifeSpan(), 0.001f);
        assertEquals(twoMaterials,                product.getMaterial());
    }

    @Test
    void constructor_withoutID_productIDDefaultsToZero() {
        ProductEntity product = new ProductEntity(
                "Reusable Bottle", ProductCategory.Kitchenware, 5.0f, twoMaterials);

        assertEquals(0, product.GetID());
    }

    // Constructor – with ID

    @Test
    void constructor_withID_storesAllFieldsIncludingID() {
        ProductEntity product = new ProductEntity(
                42, "Solar Panel", ProductCategory.Electronics, 20.0f, twoMaterials);

        assertEquals(42,                          product.GetID());
        assertEquals("Solar Panel",               product.GetName());
        assertEquals(ProductCategory.Electronics, product.GetCategory());
        assertEquals(20.0f,                       product.GetEstimatedLifeSpan(), 0.001f);
        assertEquals(twoMaterials,                product.getMaterial());
    }

    // Setters

    @Test
    void setProductID_updatesID() {
        ProductEntity product = new ProductEntity(
                "Chair", ProductCategory.Furniture, 10.0f, twoMaterials);

        product.SetProductID(99);

        assertEquals(99, product.GetID());
    }

    @Test
    void setName_updatesName() {
        ProductEntity product = new ProductEntity(
                "Old Name", ProductCategory.Furniture, 3.0f, twoMaterials);

        product.SetName("New Name");

        assertEquals("New Name", product.GetName());
    }

    @Test
    void setProductCategory_updatesCategory() {
        ProductEntity product = new ProductEntity(
                "Widget", ProductCategory.Electronics, 2.0f, twoMaterials);

        product.SetProductCategory(ProductCategory.Furniture);

        assertEquals(ProductCategory.Furniture, product.GetCategory());
    }

    @Test
    void setEstimatedLifeSpan_updatesLifespan() {
        ProductEntity product = new ProductEntity(
                "Widget", ProductCategory.Electronics, 2.0f, twoMaterials);

        product.SetEstimatedLifeSpan(8.5f);

        assertEquals(8.5f, product.GetEstimatedLifeSpan(), 0.001f);
    }

    @Test
    void setMaterials_replacesMaterialList() throws Exception {
        ProductEntity product = new ProductEntity(
                "Widget", ProductCategory.Electronics, 2.0f, twoMaterials);

        List<MaterialEntity> newMaterials = List.of(
                new MaterialEntity("Steel Frame", RecyclingCategory.Metal, 2.0f, 3.5f));
        product.SetMaterials(newMaterials);

        assertEquals(newMaterials, product.getMaterial());
    }

    // GetGuidance – delegates to each MaterialEntity.GetGuidance()

    @Test
    void getGuidance_combinesAllMaterialGuidanceLinesWithNewline() {
        ProductEntity product = new ProductEntity(
                "Mixed Product", ProductCategory.Kitchenware, 3.0f, twoMaterials);

        String expected = guidanceOf(plasticMaterial) + "\n"
                + guidanceOf(glassMaterial)   + "\n";

        assertEquals(expected, product.GetGuidance());
    }

    @Test
    void getGuidance_withSingleMaterial_returnsSingleLineWithNewline() {
        ProductEntity product = new ProductEntity(
                "Plastic Cup", ProductCategory.Kitchenware, 1.0f,
                List.of(plasticMaterial));

        assertEquals(guidanceOf(plasticMaterial) + "\n", product.GetGuidance());
    }

    @Test
    void getGuidance_withNoMaterials_returnsEmptyString() {
        ProductEntity product = new ProductEntity(
                "Mystery Product", ProductCategory.Other, 1.0f, new ArrayList<>());

        assertEquals("", product.GetGuidance());
    }

    @Test
    void getGuidance_afterMaterialListReplaced_reflectsNewMaterials() throws Exception {
        ProductEntity product = new ProductEntity(
                "Widget", ProductCategory.Electronics, 2.0f, twoMaterials);

        MaterialEntity metalFrame = new MaterialEntity("Steel Frame", RecyclingCategory.Metal, 2.0f, 3.5f);
        product.SetMaterials(List.of(metalFrame));

        assertEquals(guidanceOf(metalFrame) + "\n", product.GetGuidance());
    }

    @Test
    void getGuidance_orderMatchesMaterialListOrder() throws Exception {
        MaterialEntity m1 = new MaterialEntity("Part A", RecyclingCategory.Plastic, 0.1f, 0.5f);
        MaterialEntity m2 = new MaterialEntity("Part B", RecyclingCategory.Clear_Glass,   0.2f, 0.6f);
        MaterialEntity m3 = new MaterialEntity("Part C", RecyclingCategory.Metal,   0.3f, 0.7f);

        ProductEntity product = new ProductEntity(
                "Multi-part Item", ProductCategory.Electronics, 4.0f,
                new ArrayList<>(List.of(m1, m2, m3)));

        String expected = guidanceOf(m1) + "\n"
                + guidanceOf(m2) + "\n"
                + guidanceOf(m3) + "\n";

        assertEquals(expected, product.GetGuidance());
    }

    // Edge cases

    @Test
    void estimatedLifespan_acceptsFractionalYears() {
        ProductEntity product = new ProductEntity(
                "Battery", ProductCategory.Electronics, 2.5f, twoMaterials);

        assertEquals(2.5f, product.GetEstimatedLifeSpan(), 0.001f);
    }

    @Test
    void getMaterial_returnsTheSameListReference() {
        // Documents current contract: the list is not defensively copied.
        ProductEntity product = new ProductEntity(
                "Widget", ProductCategory.Electronics, 2.0f, twoMaterials);

        assertSame(twoMaterials, product.getMaterial());
    }

    @Test
    void constructorWithID_assignsCorrectIDWhenZero() {
        ProductEntity product = new ProductEntity(
                0, "Free Sample", ProductCategory.Other, 0.5f, twoMaterials);

        assertEquals(0, product.GetID());
    }
}
