package org.ood.application;

import org.ood.infrastructure.ProductRegistry;
import org.ood.domain.RepositoryInterface;
import org.ood.domain.ProductEntity;

public class ProductService extends CRUDServiceAbstract<ProductEntity> {
    private ProductRegistry productRegistry;
    private RepositoryInterface productRepository;
}
