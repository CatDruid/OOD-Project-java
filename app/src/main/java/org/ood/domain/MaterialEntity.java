package org.ood.domain;

public class MaterialEntity {
    private int materialID;
    private String name;
    private float environmentalImpactValue;
    private RecyclingCategory recyclingCategory;

    public MaterialEntity() {
        
    }


    //Set Methods
    public void SetMaterialID(int materialID) {this.materialID = materialID;}
    public void SetName(String name) {this.name = name;}
    public void SetEnvironmentalImpactValue(float environmentalImpactValue) {this.environmentalImpactValue = environmentalImpactValue;}
    public void SetRecyclingCategory(RecyclingCategory recyclingCategory) {this.recyclingCategory = recyclingCategory;}

    // Get Methods
    public int GetMaterialID() {return materialID;}
    public String GetName() {return name;}
    public float GetEnvironmentalImpactValue() {return environmentalImpactValue;}
    public RecyclingCategory GetRecyclingCategory() {return recyclingCategory;}

}
