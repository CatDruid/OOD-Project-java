package org.ood.domain.entities;

import org.ood.domain.RecyclingCategory;

public class MaterialEntity implements Entity{
    private int materialID;
    private String name;

    private float environmentalImpactValue;
    private RecyclingCategory recyclingCategory;
    private float mass;
    private float emissionFactor;


    public MaterialEntity(String name, float environmentalImpactValue, RecyclingCategory recyclingCategory, float mass, float emissionFactor) {
        this.name = name;
        this.environmentalImpactValue = environmentalImpactValue;
        this.recyclingCategory = recyclingCategory;
        this.mass = mass;
        this.emissionFactor = emissionFactor;
    }

    public MaterialEntity(String name, float environmentalImpactValue, RecyclingCategory recyclingCategory, int materialID, float mass, float emissionFactor) {
        this.materialID = materialID;
        this.name = name;
        this.environmentalImpactValue = environmentalImpactValue;
        this.recyclingCategory = recyclingCategory;
        this.mass = mass;
        this.emissionFactor = emissionFactor;
    }

    // Set Methods
    public void SetName(String name) {this.name = name;}
    public void SetEnvironmentalImpactValue(float environmentalImpactValue) {this.environmentalImpactValue = environmentalImpactValue;}
    public void SetRecyclingCategory(RecyclingCategory recyclingCategory) {this.recyclingCategory = recyclingCategory;}
    public void SetMass(float mass) {this.mass = mass;}
    public void SetEmissionFactor(float emissionFactor) {this.emissionFactor = emissionFactor;}

    // Get Methods
    public int GetID() {return materialID;}
    public String GetName() {return name;}
    public float GetEnvironmentalImpactValue() {return environmentalImpactValue;}
    public RecyclingCategory GetRecyclingCategory() {return recyclingCategory;}
    public float GetMass() {return mass;}
    public float GetEmissionFactor() {return emissionFactor;}
}
