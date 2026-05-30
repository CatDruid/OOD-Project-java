package org.ood.domain;

import org.junit.jupiter.api.*;
import org.ood.domain.entities.MaterialEntity;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialEntityTests {

    // Test Methods for the Material Entity
    private MaterialEntity material;

    //Prepares the materialEntity for the tests by initializing the object with a basic set of values.
    @BeforeEach
    void InitializeBaseObject() throws Exception {
        material = new MaterialEntity(
                1,
                "Test",
                RecyclingCategory.Food,
                2.01f,
                3.01f
        );
    }

    //GET tests
    @Test
    @DisplayName("GET Id check")
    void CheckThatGetIdReturnsItsValue(){
        assertEquals(1, material.GetID());
    }
    @Test
    @DisplayName("GET Name check")
    void CheckThatGetNameReturnsItsValue(){
        assertEquals("Test", material.GetName());
    }
    @Test
    @DisplayName("GET Mass check")
    void CheckThatMassReturnsItsValue(){
        assertEquals(2.01f, material.GetMass());
    }
    @Test
    @DisplayName("GET Recycling Category check")
    void CheckThatRecyclingCategoryReturnsItsValue(){
        assertEquals(RecyclingCategory.Food, material.GetRecyclingCategory());
    }
    @Test
    @DisplayName("GET Emission Factor check")
    void CheckThatEmissionFactorReturnsItsValue(){
        assertEquals(3.01f, material.GetEmissionFactor());
    }
    @Test
    @DisplayName("GET Recycling Guidance check")
    void CheckRecyclingGuidanceReturnsAValue(){
        assertFalse(material.GetGuidance().isEmpty());
    }
    @Test
    @DisplayName("SET Name check")
    void CheckThatSetNameChangesWork(){
        material.SetName("Test2");
        assertEquals("Test2", material.GetName());
    }
    @Test
    @DisplayName("SET Mass check (valid)")
    void CheckThatSetMassChangesWork() throws Exception {
        material.SetMass(0.1f);
        assertEquals(0.1f, material.GetMass());
    }
    @Test
    @DisplayName("SET Mass check (invalid)")
    void CheckThatSetMassChangesThrowsException() {
        assertThrows(Exception.class, () -> material.SetMass(-0.1f));
    }
    @Test
    @DisplayName("SET Recycling Category check")
    void CheckThatSetCategoryChangesWork(){
        material.SetRecyclingCategory(RecyclingCategory.Metal);
        assertEquals(RecyclingCategory.Metal, material.GetRecyclingCategory());
    }
    @Test
    @DisplayName("SET Emission Factor check (valid)")
    void CheckThatSetEmissionFactorChangesWork() throws Exception{
            material.SetMass(0.1f);
            assertEquals(0.1f, material.GetMass());
    }
    @Test
    @DisplayName("SET Emission Factor (invalid)")
    void CheckThatSetEmissionFactorChangesThrowsException() {
        assertThrows(Exception.class, () -> material.SetEmissionFactor(-0.1f));
    }

    //Setter logic tests
    @Test
    @DisplayName("Validation error in constructor exists when Emission Factor is below zero")
    void CheckThatValidationErrorWorksWhenEmissionIsLessThanZero(){
        assertThrows(Exception.class, () -> new MaterialEntity(
                1,
                "Test",
                RecyclingCategory.Food,
                2.01f,
                -1f
        ));
    }
    @Test
    @DisplayName("Recycling category defaults to Residual when null is provided")
    void CheckThatRecyclingCategoryDefaultsToResidualWhenNull() throws Exception {
        MaterialEntity entity = new MaterialEntity(
                1,
                "Test",
                null,
                2.01f,
                1f);
        assertEquals(RecyclingCategory.Residual, entity.GetRecyclingCategory());
    }
    @Test
    @DisplayName("Name defaults to NoName when an empty string is provided")
    void CheckThatNameDefaultsToNoNameWhenEmpty() throws Exception {
        MaterialEntity entity = new MaterialEntity(
                1,
                "",
                RecyclingCategory.Food,
                2.01f,
                1f);
        assertEquals("NoName", entity.GetName());
    }
    @Test
    @DisplayName("Validation error thrown in constructor when ID is negative")
    void CheckThatIDThrowsWhenNegative() {
        assertThrows(Exception.class, () -> new MaterialEntity(
                -1,
                "Test",
                RecyclingCategory.Food,
                2.01f,
                1f));
    }

    @Test
    @DisplayName("Validation error in constructor exists when Mass is below zero")
    void CheckThatValidationErrorWorksWhenMassIsLessThanZero(){
        assertThrows(Exception.class, () -> new MaterialEntity(
                1,
                "Test",
                RecyclingCategory.Food,
                -2.01f,
                1f
        ));
    }
}
