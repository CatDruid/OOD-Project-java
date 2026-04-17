package org.ood.presentation.records.requests;

import java.util.List;

import org.ood.domain.ProductCategory;
import org.ood.presentation.records.MaterialSelection;

public record ProductRequest(
    String name,
    ProductCategory category,
    float estimatedLifespan,
    List<MaterialSelection> materials
) {}
