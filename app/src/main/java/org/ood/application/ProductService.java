package org.ood.application;

import org.ood.infrastructure.ProductRegistry;
import org.ood.domain.RepositoryInterface;
import org.ood.domain.ProductEntity;
import org.ood.infrastructure.ProductRepository;
import org.ood.domain.RegistryInterface;

import java.util.List;

public class ProductService extends CRUDServiceAbstract<ProductEntity> {
    private RegistryInterface<ProductRegistry> productRegistry;
    private RepositoryInterface<ProductRepository> productRepository;

    @Override
    public int Create(List<String> parameters) {
        return super.Create(parameters);
    }

    @Override
    public List<ProductEntity> RetrieveAll() {
        return super.RetrieveAll();
    }

    @Override
    public ProductEntity RetrieveByID(int id) {
        return super.RetrieveByID(id);
    }

    @Override
    public boolean Update(List<String> parameters) {
        return super.Update(parameters);
    }

    @Override
    public boolean Delete(int id) {
        return super.Delete(id);
    }
}
