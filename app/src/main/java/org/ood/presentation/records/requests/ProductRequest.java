package org.ood.presentation.records.requests;

import java.util.List;

import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.ProductCategory;

public record ProductRequest(
    String name,
    ProductCategory category,
    float estimatedLifespan,
    List<MaterialEntity> materials
) {}
