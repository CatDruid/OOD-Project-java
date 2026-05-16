package org.ood.presentation.entityRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ood.domain.ProductCategory;
import org.ood.domain.RecyclingCategory;
import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.entities.ProductEntity;
import org.ood.presentation.records.EntityRecords.MaterialRecord;
import org.ood.presentation.records.EntityRecords.ProductRecord;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProductRecordTests {
    ProductEntity entity;

    //Initializes the Product Entity and Record for the tests
    @BeforeEach
    void InitializeBaseObject() throws Exception {
        MaterialEntity a = new MaterialEntity(
                1,
                "First Material",
                RecyclingCategory.Clear_Glass,
                0.01f,
                0.02f
        );
        MaterialEntity b = new MaterialEntity(
                2,
                "Second Material",
                RecyclingCategory.Coloured_Glass,
                1.01f,
                2.02f
        );
        List<MaterialEntity> materialEntityList = Arrays.asList(a, b);
        entity = new ProductEntity(
                1,
                "Entity",
                ProductCategory.Electronics,
                1.01f,
                materialEntityList
        );
    }

    @Test
    @DisplayName("Creates from product entity to record.")
    void CheckThatCreatesFromEntityCorrectly(){

    }
    @Test
    @DisplayName("Creates product record name is same as generating entity.")
    void CheckThatRecordNameMatchesEntity() {
        assertEquals(ProductRecord.FromEntity(entity).name(), entity.GetName());
    }
    @Test
    @DisplayName("Creates product record id is same as generating entity.")
    void CheckThatRecordIdMatchesEntity() {
        assertEquals(ProductRecord.FromEntity(entity).id(), entity.GetID());
    }
    @Test
    @DisplayName("Creates product record category is same as generating entity.")
    void CheckThatRecordCategoryMatchesEntity() {
        assertEquals(ProductRecord.FromEntity(entity).category(), entity.GetCategory());
    }
    @Test
    @DisplayName("Creates product record category is same as generating entity.")
    void CheckThatRecordLifespanMatchesEntity() {
        assertEquals(ProductRecord.FromEntity(entity).estimatedLifespan(), entity.GetEstimatedLifeSpan());
    }


}
