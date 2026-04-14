package org.ood.infrastructure;

import org.ood.domain.ProductEntity;

import java.util.List;

public class ProductRegistry extends RegistryAbstract<ProductEntity> {
    private List<ProductEntity> items;

    public boolean Add(List<String> parameters) {return false;}
    public List<ProductEntity> RetrieveAll() {return null;}
    public ProductEntity RetrieveByID(int id) {return null;}
    public boolean Update(List<ProductEntity> parameters) {return false;}
    public boolean Delete(int id) {return false;}
}
