package org.ood.infrastructure.registries;

import org.ood.domain.RepositoryInterface;
import org.ood.domain.entities.ProductEntity;

/**
 * In-memory registry for {@link ProductEntity} instances.
 *
 * @see RegistryAbstract
 * @see ProductEntity
 */
public class ProductRegistry extends RegistryAbstract<ProductEntity> {

    /** {@inheritDoc} */
    public ProductRegistry() {
        super();
    }

    /** {@inheritDoc} */
    public ProductRegistry(RepositoryInterface<ProductEntity> repo) {
        super(repo);
    }
}
