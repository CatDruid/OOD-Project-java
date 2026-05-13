package org.ood.domain.entities;

import org.ood.domain.RecyclingCategory;

public class MaterialEntity implements Entity{
    private int materialID;
    private String name;
    private RecyclingCategory recyclingCategory;
    private float mass;
    private float emissionFactor;


    public MaterialEntity(String name, RecyclingCategory recyclingCategory, float mass, float emissionFactor) {
        this.name = name;
        this.recyclingCategory = recyclingCategory;
        this.mass = mass;
        this.emissionFactor = emissionFactor;
    }

    public MaterialEntity(int materialID, String name, RecyclingCategory recyclingCategory, float mass, float emissionFactor) {
        this.materialID = materialID;
        this.name = name;
        this.recyclingCategory = recyclingCategory;
        this.mass = mass;
        this.emissionFactor = emissionFactor;
    }

    // Set Methods
    public void SetName(String name) {this.name = name;}
    public void SetRecyclingCategory(RecyclingCategory recyclingCategory) {this.recyclingCategory = recyclingCategory;}
    public void SetMass(float mass) {this.mass = mass;}
    public void SetEmissionFactor(float emissionFactor) {this.emissionFactor = emissionFactor;}

    // Get Methods
    public int GetID() {return materialID;}
    public String GetName() {return name;}
    public RecyclingCategory GetRecyclingCategory() {return recyclingCategory;}
    public float GetMass() {return mass;}
    public float GetEmissionFactor() {return emissionFactor;}
}
