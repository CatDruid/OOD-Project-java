package org.ood.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialEntityTests {

    // Test Methods for the Material Entity
    private MaterialEntity material;

    //Prepares the materialEntity for the tests by initializing the object with a basic set of values.
    @BeforeEach
    void initializeBaseObject() {
        material = new MaterialEntity(
                "Test",
                1.01f,
                RecyclingCategory.Test,
                1,
                2.01f,
                3.01f
        );
    }

    @Test
    @DisplayName("GET Id check")
    void CheckThatGetIdReturnsItsValue(){
        assertEquals(1, material.GetMaterialID());
    }
    @Test
    @DisplayName("GET Name check")
    void CheckThatGetNameReturnsItsValue(){
        assertEquals("Test", material.GetName());
    }
    @Test
    @DisplayName("GET Impact Value check")
    void CheckThatImpactValueReturnsItsValue(){
        assertEquals(1.01f, material.GetEnvironmentalImpactValue());
    }
    @Test
    @DisplayName("GET Mass check")
    void CheckThatMassReturnsItsValue(){
        assertEquals(2.01f, material.GetMass());
    }
    @Test
    @DisplayName("GET Recycling Category check")
    void CheckThatRecyclingCategoryReturnsItsValue(){
        assertEquals(RecyclingCategory.Test, material.GetRecyclingCategory());
    }
    @Test
    @DisplayName("GET Emission Factor check")
    void CheckThatEmissionFactorReturnsItsValue(){
        assertEquals(3.01f, material.GetEmissionFactor());
    }
    @Test
    @DisplayName("No validation errors when there shouldn't be.")
    void CheckThatValidateSelfDoesNotReturnAnErrorWhenThereShouldNotBe(){
        assertTrue(material.ValidateSelf().isEmpty());   
    }
    @Test
    @DisplayName("Validation error exists when Emission Factor is below zero")
    void CheckThatValidationErrorWorksWhenEmissionIsLessThanZero(){
        material = new MaterialEntity(
                "Test",
                1.01f,
                RecyclingCategory.Test,
                1,
                2.01f,
                -1f
        );
        assertFalse(material.ValidateSelf().isEmpty());
    }
    @Test
    @DisplayName("Validation error exists when Mass is below zero")
    void CheckThatValidationErrorWorksWhenMassIsLessThanZero(){
        material = new MaterialEntity(
                "Test",
                1.01f,
                RecyclingCategory.Test,
                1,
                -2.01f,
                1f
        );
        assertFalse(material.ValidateSelf().isEmpty());
    }
    @Test
    @DisplayName("Validation error exists when Impact Value is below zero")
    void CheckThatValidationErrorWorksWhenImpactValueIsLessThanZero(){
        material = new MaterialEntity(
                "Test",
                -1.01f,
                RecyclingCategory.Test,
                1,
                2.01f,
                1f
        );
        assertFalse(material.ValidateSelf().isEmpty());
    }
}
