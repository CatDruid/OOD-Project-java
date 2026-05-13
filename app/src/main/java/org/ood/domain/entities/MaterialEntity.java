package org.ood.domain.entities;

import org.ood.domain.RecyclingCategory;

public class MaterialEntity implements Entity{
    private int materialID;
    private String name;
    private RecyclingCategory recyclingCategory;
    private float mass;
    private float emissionFactor;


    public MaterialEntity(String name, RecyclingCategory recyclingCategory, float mass, float emissionFactor) throws Exception {
        this.name = name;
        this.recyclingCategory = recyclingCategory;
        this.mass = mass;
        this.emissionFactor = emissionFactor;
        String errors = this.ValidateSelf();
        if(errors.isEmpty() == false)
            throw new Exception(errors);
    }

    public MaterialEntity(String name, RecyclingCategory recyclingCategory, int materialID, float mass, float emissionFactor)  throws Exception  {
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
    public void SetMass(float mass) throws Exception {
        if(mass >= 0)
            this.mass = mass;
        else
            throw new Exception("Mass cannot be less than zero");
    }
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
    public String GetGuidance(){
        return this.name + ": " + this.recyclingCategory.guidance;
    }

    /*Constructs and returns the error message if there are any.
    If the string is empty, however, then there are no errors.
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
