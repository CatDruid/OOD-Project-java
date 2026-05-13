package org.ood.domain;

import org.junit.jupiter.api.*;
import org.ood.domain.entities.MaterialEntity;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialEntityTests {

    // Test Methods for the Material Entity
    private MaterialEntity material;

    //Prepares the materialEntity for the tests by initializing the object with a basic set of values.
    @BeforeEach
    void initializeBaseObject() {
        material = new MaterialEntity(
                "Test",
                RecyclingCategory.Test,
                1,
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
        assertEquals(RecyclingCategory.Test, material.GetRecyclingCategory());
    }
    @Test
    @DisplayName("GET Emission Factor check")
    void CheckThatEmissionFactorReturnsItsValue(){
        assertEquals(3.01f, material.GetEmissionFactor());
    }

    @Test
    @DisplayName("SET Name check")
    void CheckThatSetNameChangesWork(){
        material.SetName("Test2");
        assertEquals("Test2", material.GetName());
    }
    @Test
    @DisplayName("SET Mass check")
    void CheckThatSetMassChangesWork(){
        material.SetMass(0.1f);
        assertEquals(0.1f, material.GetMass());
    }
    @Test
    @DisplayName("SET Recycling Category check")
    void CheckThatSetCategoryChangesWork(){
        material.SetRecyclingCategory(RecyclingCategory.Test2);
        assertEquals(RecyclingCategory.Test2, material.GetRecyclingCategory());
    }
    @Test
    @DisplayName("SET Emission Factor check")
    void CheckThatSetEmissionFactorChangesWork(){
        material.SetEmissionFactor(0.1f);
        assertEquals(0.1f, material.GetEmissionFactor());
    }

    //Self-validate tests
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
                RecyclingCategory.Test,
                1,
                -2.01f,
                1f
        );
        assertFalse(material.ValidateSelf().isEmpty());
    }
}
