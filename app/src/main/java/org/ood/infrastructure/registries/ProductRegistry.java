package org.ood.infrastructure.registries;

import org.ood.domain.RepositoryInterface;
import org.ood.domain.entities.ProductEntity;

public class ProductRegistry extends RegistryAbstract<ProductEntity> {

    public ProductRegistry() {
        super();
    }

    public ProductRegistry(RepositoryInterface<ProductEntity> repo) {
        super(repo);
    }
}
