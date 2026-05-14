package org.ood.domain.impactStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.ood.domain.ProductCategory;
import org.ood.domain.RecyclingCategory;
import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.entities.ProductEntity;

import java.util.ArrayList;

import java.util.List;


public class StrategyTests {

    private List<MaterialEntity> dualMaterial;
    private List<MaterialEntity> singleMaterial;

    @BeforeEach
     void prepareTests() throws Exception {
         dualMaterial = new ArrayList<>(List.of(
                new MaterialEntity("Plastic", RecyclingCategory.Plastic, 3.0f, 3.5f),
                new MaterialEntity("Concrete", RecyclingCategory.Residual, 100.0f, 1.2f)
        ));
         singleMaterial = new ArrayList<>(List.of(new MaterialEntity("Steel", RecyclingCategory.Metal, 50, 2.5f)));
    }

    @Test
    void testRawPCF_SingleMaterial() {
        SimpleSumStrategy strategy = new SimpleSumStrategy();

        ProductEntity product = new ProductEntity("SingleMaterialProduct",
                ProductCategory.Kitchenware, 5, singleMaterial);

        float result = strategy.CalculateImpact(product);
        assertEquals(125.0f, result, 0.001f);
    }

    @Test
    void testWeightedByLifespan_SingleMaterial() {
        WeightedByLifespanStrategy strategy = new WeightedByLifespanStrategy();

        ProductEntity product = new ProductEntity("SingleMaterialProduct",
                ProductCategory.Kitchenware, 5, singleMaterial);

        float result = strategy.CalculateImpact(product);
        assertEquals(25.0f, result, 0.001f);
    }

    @Test
    void testRawPCF_MultipleMaterials() {
        SimpleSumStrategy strategy = new SimpleSumStrategy();

        ProductEntity product = new ProductEntity("ComplexProduct",
                ProductCategory.Electronics, 8, dualMaterial);

        float result = strategy.CalculateImpact(product);
        assertEquals(130.5f, result, 0.001f);
    }

    @Test
    void testWeightedByLifespan_MultipleMaterials() {
        WeightedByLifespanStrategy strategy = new WeightedByLifespanStrategy();

        ProductEntity product = new ProductEntity("ComplexProduct",
                ProductCategory.Electronics, 8, dualMaterial);

        float result = strategy.CalculateImpact(product);
        assertEquals(16.3125f, result, 0.001f);
    }

    @Test
    void testWeightedByLifespan_ZeroLifespan() {
        WeightedByLifespanStrategy strategy = new WeightedByLifespanStrategy();

        ProductEntity product = new ProductEntity("ZeroLifespanProduct",
                ProductCategory.Other, 0, singleMaterial);

        float result = strategy.CalculateImpact(product);
        assertEquals(0.0f, result, 0.001f);
    }

    @Test
    void testRawPCF_EmptyMaterials() {
        SimpleSumStrategy strategy = new SimpleSumStrategy();

        ProductEntity product = new ProductEntity("EmptyProduct",
                ProductCategory.Other, 10, new ArrayList<>());

        float result = strategy.CalculateImpact(product);
        assertEquals(0.0f, result, 0.001f);
    }
}
