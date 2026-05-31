package org.ood.presentation.entityRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ood.domain.RecyclingCategory;
import org.ood.domain.entities.MaterialEntity;
import org.ood.presentation.records.EntityRecords.MaterialRecord;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialRecordTests {
    MaterialEntity entity;
    MaterialRecord record;
    //Initializes the Material Entity and Record for the tests
    @BeforeEach
    void InitializeBaseObject() throws Exception {
        entity = new MaterialEntity(
                1,
                "Entity",
                RecyclingCategory.Clear_Glass,
                0.01f,
                0.02f
        );
        record = new MaterialRecord(
                2,
                "Record",
                RecyclingCategory.Coloured_Glass,
                1.01f,
                2.02f
        );
    }

    @Test
    @DisplayName("Creates from material entity to record.")
    void CheckThatCreatesFromEntity(){
        MaterialRecord.FromEntity(entity);
    }
    @Test
    @DisplayName("Creates material record name is same as generating entity.")
    void CheckThatRecordNameMatchesEntity() {
        assertEquals(MaterialRecord.FromEntity(entity).name(), entity.GetName());
    }
    @Test
    @DisplayName("Creates material record id is same as generating entity.")
    void CheckThatRecordIdMatchesEntity() {
        assertEquals(MaterialRecord.FromEntity(entity).id(), entity.GetID());
    }
    @Test
    @DisplayName("Creates material record productCategory is same as generating entity.")
    void CheckThatRecordCategoryMatchesEntity() {
        assertEquals(MaterialRecord.FromEntity(entity).recyclingCategory(), entity.GetRecyclingCategory());
    }
    @Test
    @DisplayName("Creates material record mass is same as generating entity.")
    void CheckThatRecordMassMatchesEntity() {
        assertEquals(MaterialRecord.FromEntity(entity).mass(), entity.GetMass());
    }
    @Test
    @DisplayName("Creates material record emission factor is same as generating entity.")
    void CheckThatRecordEmissionMatchesEntity() {
        assertEquals(MaterialRecord.FromEntity(entity).emissionFactor(), entity.GetEmissionFactor());
    }
    @Test
    @DisplayName("Creates from material record to entity.")
    void CheckThatCreatesToEntity() throws Exception {
        record.ToEntity();
    }
    @Test
    @DisplayName("Creates material entity name is same as generating record.")
    void CheckThatEntityNameMatchesRecord() throws Exception {
        assertEquals(record.ToEntity().GetName(), record.name());
    }
    @Test
    @DisplayName("Creates material entity id is same as generating record.")
    void CheckThatEntityIdMatchesRecord() throws Exception {
        assertEquals(record.ToEntity().GetID(), record.id());
    }
    @Test
    @DisplayName("Creates material entity productCategory is same as generating record.")
    void CheckThatEntityCategoryMatchesRecord() throws Exception {
        assertEquals(record.ToEntity().GetRecyclingCategory(), record.recyclingCategory());
    }
    @Test
    @DisplayName("Creates material entity mass is same as generating record.")
    void CheckThatEntityMassMatchesRecord() throws Exception {
        assertEquals(record.ToEntity().GetMass(), record.mass());
    }
    @Test
    @DisplayName("Creates material entity emission factor is same as generating record.")
    void CheckThatEntityEmissionMatchesRecord() throws Exception {
        assertEquals(record.ToEntity().GetEmissionFactor(), record.emissionFactor());
    }

}
