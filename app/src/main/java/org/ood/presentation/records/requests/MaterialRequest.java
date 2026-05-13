package org.ood.presentation.records.requests;

import org.ood.domain.RecyclingCategory;

public record MaterialRequest(
    String name,
    RecyclingCategory category,
    float mass,
    float emissionFactor
) {}
