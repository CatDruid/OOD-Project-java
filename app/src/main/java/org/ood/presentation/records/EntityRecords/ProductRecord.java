package org.ood.presentation.records.EntityRecords;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ood.domain.ProductCategory;
import org.ood.domain.entities.ProductEntity;
import org.ood.presentation.records.Introspectable;

public record ProductRecord(
    Integer id,
    String name,
    ProductCategory productCategory,
    float estimatedLifespan,
    List<MaterialRecord> materials
) implements Introspectable {
    /** Converts a {@link ProductEntity} into a {@link ProductRecord}. */
    public static ProductRecord FromEntity(ProductEntity entity) {
        return new ProductRecord(entity.GetID(), entity.GetName(), entity.GetCategory(), entity.GetEstimatedLifeSpan(),
                entity.getMaterial().stream()
                .map(MaterialRecord::FromEntity)
                .collect(Collectors.toList()));
    }

    public static Map<String, Class<?>> GetFields() {
        return Introspectable.GetFields(ProductRecord.class);
    }
}
