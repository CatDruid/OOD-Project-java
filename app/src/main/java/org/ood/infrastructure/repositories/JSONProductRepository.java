package org.ood.infrastructure.repositories;

import org.ood.domain.entities.ProductEntity;

public class JSONProductRepository extends AbstractJSONRepository<ProductEntity> {

    public JSONProductRepository(String path) {
        super(path);
    }
}
