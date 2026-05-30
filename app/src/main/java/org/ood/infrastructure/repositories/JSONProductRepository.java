package org.ood.infrastructure.repositories;
import org.ood.domain.entities.ProductEntity;

/**
 * Long-term storage for {@link ProductEntity}.
 *
 * @see AbstractJSONRepository
 * @see ProductEntity
 */
public class JSONProductRepository extends AbstractJSONRepository<ProductEntity> {

    /** {@inheritDoc} */
    public JSONProductRepository(String path) {
        super(path);
    }
}
