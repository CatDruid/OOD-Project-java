package org.ood.domain.entities;

import org.ood.domain.RecyclingCategory;

/**
 * An {@link Entity} representing the materials that then go on to compose a {@link ProductEntity}, carrying physical and environmental properties.
 */
public class MaterialEntity implements Entity{
    private int materialID;
    private String name;
    private RecyclingCategory recyclingCategory;
    private float mass;
    private float emissionFactor;

    /**
     * Constructs a {@link MaterialEntity} without a pre-assigned ID.
     * Intended for new materials.
     *
     * @param name               The material's name.
     * @param recyclingCategory  The recycling category this material belongs to.
     * @param mass               The mass of the material in kilograms, must be non-negative.
     * @param emissionFactor     The CO₂e emission factor per kilogram, must be non-negative.
     * @throws Exception         If any of the provided values violate domain constraints.
     */
    public MaterialEntity(String name, RecyclingCategory recyclingCategory, float mass, float emissionFactor) throws Exception {
        this.name = name;
        this.recyclingCategory = recyclingCategory;
        this.mass = mass;
        this.emissionFactor = emissionFactor;
        String errors = this.ValidateSelf();
        if(errors.isEmpty() == false)
            throw new Exception(errors);
    }

    /**
     * Constructs a {@link MaterialEntity} with a pre-assigned ID.
     * Intended for materials being loaded from a repository.
     *
     * @param materialID         The unique identifier assigned to this material.
     * @param name               The material's name.
     * @param recyclingCategory  The recycling category this material belongs to.
     * @param mass               The mass of the material, must be non-negative.
     * @param emissionFactor     The emission factor, must be non-negative.
     * @throws Exception         If any of the provided values violate domain constraints.
     */
    public MaterialEntity(int materialID, String name, RecyclingCategory recyclingCategory, float mass, float emissionFactor)  throws Exception  {
        this.materialID = materialID;
        this.name = name;
        this.recyclingCategory = recyclingCategory;
        this.mass = mass;
        this.emissionFactor = emissionFactor;
        String errors = this.ValidateSelf();
        if(errors.isEmpty() == false)
            throw new Exception(errors);
    }

    //Set Methods
    public void SetName(String name) {this.name = name;}
    public void SetRecyclingCategory(RecyclingCategory recyclingCategory) {this.recyclingCategory = recyclingCategory;}

    /**
     * Sets the mass and validates the field at once.
     * @param mass              The mass of the material in question.
     * @throws Exception        If the mass is less or equal to zero.
     */
    public void SetMass(float mass) throws Exception {
        if(mass >= 0)
            this.mass = mass;
        else
            throw new Exception("Mass cannot be less than zero");
    }


    /**
     * Sets the mass and validates the field at once.
     * @param emissionFactor    The emission factor of the material.
     * @throws Exception        If the emission factor is less or equal to zero.
     */
    public void SetEmissionFactor(float emissionFactor) throws Exception {
        if(emissionFactor >= 0)
            this.emissionFactor = emissionFactor;
        else
            throw new Exception("Emission Factor cannot be less than zero");
    }

    // Get Methods
    public int GetID() {return materialID;}
    public String GetName() {return name;}
    public RecyclingCategory GetRecyclingCategory() {return recyclingCategory;}
    public float GetMass() {return mass;}
    public float GetEmissionFactor() {return emissionFactor;}

    /**
     * Creates the recycling guidance for the material.}
     * @return Formatted string with the material's recycling guidance.
     */
    public String GetGuidance(){
        return this.name + ": " + this.recyclingCategory.guidance;
    }

    /**
     * Validates an instance's field values against domain constraints.
     *
     * @return A string of error messages, or an empty string if valid.
     */
    public String ValidateSelf(){
        StringBuilder err = new StringBuilder();
        if (this.mass < 0)
            err.append("Mass cannot be less than zero \n");
        if (this.emissionFactor < 0)
            err.append("Emission Factor cannot be less than zero \n");
        return err.toString();
    }
}
