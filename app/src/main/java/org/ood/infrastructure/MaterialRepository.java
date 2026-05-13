package org.ood.infrastructure;

import org.ood.domain.MaterialEntity;
import org.ood.domain.RecyclingCategory;
import org.ood.domain.RepositoryInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaterialRepository implements RepositoryInterface<MaterialEntity> {
    // Single instance
    MaterialEntity mockMaterial = new MaterialEntity("Plastic Bottle", 3.5f, RecyclingCategory.Test, 1, 2.01f, 3.01f);

    // Mock list
    List<MaterialEntity> mockMaterials = new ArrayList<>(Arrays.asList(
            new MaterialEntity("Plastic Bottle",  3.5f, RecyclingCategory.Test,  1, 2.01f, 3.01f),
            new MaterialEntity("Glass Jar",       1.2f, RecyclingCategory.Test2, 2, 1.50f, 0.85f),
            new MaterialEntity("Aluminium Can",   2.8f, RecyclingCategory.Test,  3, 0.75f, 2.30f),
            new MaterialEntity("Cardboard Box",   0.9f, RecyclingCategory.Test2, 4, 3.20f, 0.45f),
            new MaterialEntity("Styrofoam Cup",   4.7f, RecyclingCategory.Test,  5, 0.30f, 4.10f)
    ));

    public boolean Add(MaterialEntity materialEntity) {
        return true;
    }
    public List<MaterialEntity> RetrieveAll() {
        return mockMaterials;
    }
    public MaterialEntity RetrieveByID(int id) {return mockMaterials.get(id-1);}
    public boolean Update(MaterialEntity materialEntity) {
        return true;
    }
    public boolean Delete(int id) {
        return true;
    }
}
