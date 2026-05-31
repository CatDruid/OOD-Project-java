package org.ood.infrastructure.repositories;

import org.ood.domain.entities.MaterialEntity;

/**
 * Long-term storage for {@link MaterialEntity}.
 *
 * @see AbstractJSONRepository
 * @see MaterialEntity
 */
public class JSONMaterialRepository extends AbstractJSONRepository<MaterialEntity> {

    /** {@inheritDoc} */
    public JSONMaterialRepository(String path) {
        super(path);
    }
}
