package org.ood.domain.impactStrategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.ood.domain.ProductCategory;
import org.ood.domain.RecyclingCategory;
import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.entities.ProductEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StrategyTests {
    @Test
    void testRawPCFCalculationFromAbstract() {
        // SimpleSumStrategy is just the RawPCF without modification so it will be used to test the RawPCF.

        //Arrange
        SimpleSumStrategy simpleSumStrategy = new SimpleSumStrategy();

        List<MaterialEntity> materialEntityList = new ArrayList<>(Arrays.asList(
                new MaterialEntity("Plastic", 3.5f, RecyclingCategory.Test2, 3, 3.5f),
                new MaterialEntity("Concrete", 2, RecyclingCategory.Test2, 100, 1.2f)
        ));
        ProductEntity productEntity = new ProductEntity("TestProduct", ProductCategory.Test, 2, materialEntityList);

        //Act
        float simpleSumResult = simpleSumStrategy.CalculateImpact(productEntity);

        //Assert
        assertEquals(10.5f , simpleSumResult, 0.001f);
    }

    @Test
    void weightedByLifespanStrategy() {
        WeightedByLifespanStrategy weightedByLifespanStrategy = new WeightedByLifespanStrategy();

        List<MaterialEntity> materialEntityList = new ArrayList<>(Arrays.asList(
                new MaterialEntity("Plastic", 3.5f, RecyclingCategory.Test2, 3, 3.5f),
                new MaterialEntity("Concrete", 2, RecyclingCategory.Test2, 100, 1.2f)
        ));
        ProductEntity productEntity = new ProductEntity("TestProduct", ProductCategory.Test, 2, materialEntityList);

        float weightedResult = weightedByLifespanStrategy.CalculateImpact(productEntity);

        assertEquals(5.25f, weightedResult, 0.001f);
    }

    @Test
    void testRawPCF_SingleMaterial() {
        SimpleSumStrategy strategy = new SimpleSumStrategy();

        List<MaterialEntity> materials = List.of(
                new MaterialEntity("Steel", 4.8f, RecyclingCategory.Test, 50, 2.5f)
        );

        ProductEntity product = new ProductEntity("SingleMaterialProduct",
                ProductCategory.Test, 5, materials);

        float result = strategy.CalculateImpact(product);
        assertEquals(240.0f, result, 0.001f);
    }

    @Test
    void testWeightedByLifespan_SingleMaterial() {
        WeightedByLifespanStrategy strategy = new WeightedByLifespanStrategy();

        List<MaterialEntity> materials = List.of(
                new MaterialEntity("Steel", 4.8f, RecyclingCategory.Test, 50, 2.5f)
        );

        ProductEntity product = new ProductEntity("SingleMaterialProduct",
                ProductCategory.Test, 5, materials);

        float result = strategy.CalculateImpact(product);
        assertEquals(48.0f, result, 0.001f);
    }

    @Test
    void testRawPCF_MultipleMaterials() {
        SimpleSumStrategy strategy = new SimpleSumStrategy();

        List<MaterialEntity> materials = new ArrayList<>(Arrays.asList(
                new MaterialEntity("Plastic", 3.5f, RecyclingCategory.Test2, 10, 4.0f),
                new MaterialEntity("Aluminum", 8.2f, RecyclingCategory.Test, 25, 1.8f),
                new MaterialEntity("Wood", 1.1f, RecyclingCategory.Test, 80, 0.9f)
        ));

        ProductEntity product = new ProductEntity("ComplexProduct",
                ProductCategory.Test, 8, materials);

        float result = strategy.CalculateImpact(product);
        assertEquals(327.0f, result, 0.001f);
    }

    @Test
    void testWeightedByLifespan_MultipleMaterials() {
        WeightedByLifespanStrategy strategy = new WeightedByLifespanStrategy();

        List<MaterialEntity> materials = new ArrayList<>(Arrays.asList(
                new MaterialEntity("Plastic", 3.5f, RecyclingCategory.Test2, 10, 4.0f),
                new MaterialEntity("Aluminum", 8.2f, RecyclingCategory.Test, 25, 1.8f),
                new MaterialEntity("Wood", 1.1f, RecyclingCategory.Test2, 80, 0.9f)
        ));

        ProductEntity product = new ProductEntity("ComplexProduct",
                ProductCategory.Test, 8, materials);

        float result = strategy.CalculateImpact(product);
        assertEquals(40.875f, result, 0.001f);
    }

    @Test
    void testWeightedByLifespan_ZeroLifespan() {
        WeightedByLifespanStrategy strategy = new WeightedByLifespanStrategy();

        List<MaterialEntity> materials = List.of(
                new MaterialEntity("Plastic", 3.5f, RecyclingCategory.Test2, 4, 3.5f)
        );

        ProductEntity product = new ProductEntity("ZeroLifespanProduct",
                ProductCategory.Test, 0, materials);

        float result = strategy.CalculateImpact(product);
        assertEquals(0.0f, result, 0.001f);
    }

    @Test
    void testRawPCF_EmptyMaterials() {
        SimpleSumStrategy strategy = new SimpleSumStrategy();

        ProductEntity product = new ProductEntity("EmptyProduct",
                ProductCategory.Test, 10, new ArrayList<>());

        float result = strategy.CalculateImpact(product);
        assertEquals(0.0f, result, 0.001f);
    }
}
