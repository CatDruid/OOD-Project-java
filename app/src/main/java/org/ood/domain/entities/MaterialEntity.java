package org.ood.domain.entities;

import org.ood.domain.RecyclingCategory;

import java.util.Objects;

public class MaterialEntity implements Entity{
    private int materialID;
    private String name;
    private RecyclingCategory recyclingCategory;
    private float mass;
    private float emissionFactor;


    public MaterialEntity(String name, RecyclingCategory recyclingCategory, float mass, float emissionFactor) throws Exception {
        SetName(name);
        SetRecyclingCategory(recyclingCategory);
        SetMass(mass);
        SetEmissionFactor(emissionFactor);
    }

    public MaterialEntity(int materialID, String name, RecyclingCategory recyclingCategory, float mass, float emissionFactor)  throws Exception  {
        SetID(materialID);
        SetName(name);
        SetRecyclingCategory(recyclingCategory);
        SetMass(mass);
        SetEmissionFactor(emissionFactor);
    }

    //Set Methods
    private void SetID(int materialID) throws Exception{
        if (materialID >= 0) {
            this.materialID = materialID;
        } else {
            throw new Exception("ID cannot be negative");
        }
    }
    public void SetName(String name) {
        this.name = name.isEmpty() ? "NoName" : name;
    }
    public void SetRecyclingCategory(RecyclingCategory recyclingCategory) {
        this.recyclingCategory = Objects.requireNonNullElse(recyclingCategory, RecyclingCategory.Residual);
    }
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

}
