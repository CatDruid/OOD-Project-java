package org.ood.presentation.records.EntityRecords;

import java.util.List;
import java.util.stream.Collectors;

import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.ProductCategory;
import org.ood.domain.entities.ProductEntity;
import org.ood.presentation.records.Introspectable;

public record ProductRecord(
    Integer id,
    String name,
    ProductCategory category,
    float estimatedLifespan,
    List<MaterialRecord> materials
) implements Introspectable {
    /** Converts a {@link ProductEntity} into a {@link ProductRecord}. */
    public static ProductRecord fromEntity(ProductEntity entity) {
        return new ProductRecord(entity.GetID(), entity.GetName(), entity.GetCategory(), entity.GetEstimatedLifeSpan(),
                entity.getMaterial().stream()
                .map(MaterialRecord::fromEntity)
                .collect(Collectors.toList()));
    }
}
