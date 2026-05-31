package org.ood.domain.entities;

import org.ood.domain.RecyclingCategory;

import java.util.Objects;

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
     * @param mass               The mass of the material, must be non-negative.
     * @param emissionFactor     The emission factor, must be non-negative.
     * @throws Exception         If any of the provided values violate domain constraints.
     */
    public MaterialEntity(String name, RecyclingCategory recyclingCategory, float mass, float emissionFactor) throws Exception {
        SetName(name);
        SetRecyclingCategory(recyclingCategory);
        SetMass(mass);
        SetEmissionFactor(emissionFactor);
    }

    /**
     * Constructs a {@link MaterialEntity} with a pre-assigned ID.
     * Intended for Update operations wherein an ID already exists.
     *
     * @param materialID         The unique identifier assigned to this material.
     * @param name               The material's name.
     * @param recyclingCategory  The recycling category this material belongs to.
     * @param mass               The mass of the material, must be non-negative.
     * @param emissionFactor     The emission factor, must be non-negative.
     * @throws Exception         If any of the provided values violate domain constraints.
     */
    public MaterialEntity(int materialID, String name, RecyclingCategory recyclingCategory, float mass, float emissionFactor)  throws Exception  {
        SetID(materialID);
        SetName(name);
        SetRecyclingCategory(recyclingCategory);
        SetMass(mass);
        SetEmissionFactor(emissionFactor);
    }

    //Set Methods

    /**
     * Sets the ID and validates it.
     * @param materialID         The material's ID. Sets it to NoName if an empty string is provided.
     * @throws Exception         If the ID is a negative number.
     */
    private void SetID(int materialID) throws Exception{
        if (materialID >= 0) {
            this.materialID = materialID;
        } else {
            throw new Exception("ID cannot be negative");
        }
    }

    /**
     * Sets the name and handles the empty case.
     * @param name The material's name. Sets it to NoName if an empty string is provided.
     */
    public void SetName(String name) {
        this.name = name.isEmpty() ? "NoName" : name;
    }

    /**
     * Sets the recycling category and handles the null case.
     * @param recyclingCategory The material's category. Residual if null value is provided.
     */
    public void SetRecyclingCategory(RecyclingCategory recyclingCategory) {
        this.recyclingCategory = Objects.requireNonNullElse(recyclingCategory, RecyclingCategory.Residual);
    }
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

}
