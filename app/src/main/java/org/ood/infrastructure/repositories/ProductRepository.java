package org.ood.infrastructure.repositories;

import org.ood.domain.*;
import org.ood.domain.entities.ProductEntity;

import java.util.List;

public class ProductRepository implements RepositoryInterface<ProductEntity> {

    public boolean Add(ProductEntity productEntity) {
        return true;
    }
    public List<ProductEntity> RetrieveAll() {
        return null;
    }
    public ProductEntity RetrieveByID(int id) {
        return null;
    }
    public boolean Update(ProductEntity productEntity) {
        return true;
    }
    public boolean Delete(int id) {
        return true;
    }
}
