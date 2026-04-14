package org.ood.infrastructure;

import org.ood.domain.ProductEntity;
import org.ood.domain.RepositoryInterface;

import java.util.List;

public class ProductRepository implements RepositoryInterface<ProductEntity> {
    public boolean Add(ProductEntity productEntity) {return false;}
    public List<ProductEntity> RetrieveAll() {return null;}
    public ProductEntity RetrieveByID(int id) {return null;}
    public boolean Update(ProductEntity productEntity) {return false;}
    public boolean Delete(int id) {return false;}

}
