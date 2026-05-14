package org.ood.infrastructure.repositories;

import org.ood.domain.entities.MaterialEntity;

public class JSONMaterialRepository extends AbstractJSONRepository<MaterialEntity> {

    public JSONMaterialRepository(String path) {
        super(path);
    }
}
