package org.ood.presentation.records.EntityRecords;

import org.ood.domain.RecyclingCategory;
import org.ood.domain.entities.MaterialEntity;
import org.ood.presentation.records.Introspectable;

public record MaterialRecord(
    Integer id,
    String name,
    RecyclingCategory category,
    float mass,
    float emissionFactor
) implements Introspectable {
    /** Converts a {@link MaterialEntity} into a {@link MaterialRecord}. */
    public static MaterialRecord FromEntity(MaterialEntity entity) {
        return new MaterialRecord(entity.GetID(), entity.GetName(), entity.GetRecyclingCategory(), entity.GetMass(), entity.GetEmissionFactor());
    }
    /** Converts this {@link MaterialRecord} into a {@link MaterialEntity}. */
    public MaterialEntity ToEntity() throws Exception {
        return new MaterialEntity(id, name, category, mass, emissionFactor);
    }


}
