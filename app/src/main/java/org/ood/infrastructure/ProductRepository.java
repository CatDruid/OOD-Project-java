package org.ood.infrastructure;

import org.ood.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductRepository implements RepositoryInterface<ProductEntity> {
    ProductEntity mockProduct = new ProductEntity("Reusable Water Bottle", ProductCategory.Test, 5.0f, new ArrayList<>(Arrays.asList(
            new MaterialEntity("Plastic Bottle", 3.5f, RecyclingCategory.Test)
    )), 1);

    List<ProductEntity> mockProducts = new ArrayList<>(Arrays.asList(
            new ProductEntity("Reusable Water Bottle", ProductCategory.Test,  5.0f, new ArrayList<>(Arrays.asList(
                    new MaterialEntity("Plastic Bottle", 3.5f, RecyclingCategory.Test, 1)
            )), 1),
            new ProductEntity("Glass Food Container", ProductCategory.Test2, 8.0f, new ArrayList<>(Arrays.asList(
                    new MaterialEntity("Glass Jar", 1.2f, RecyclingCategory.Test2, 2)
            )), 2),
            new ProductEntity("Aluminium Lunchbox",   ProductCategory.Test,  6.5f, new ArrayList<>(Arrays.asList(
                    new MaterialEntity("Aluminium Can", 2.8f, RecyclingCategory.Test, 3)
            )), 3),
            new ProductEntity("Cardboard Organiser",  ProductCategory.Test2, 2.0f, new ArrayList<>(Arrays.asList(
                    new MaterialEntity("Cardboard Box", 0.9f, RecyclingCategory.Test2, 4)
            )), 4),
            new ProductEntity("Foam Packaging Block", ProductCategory.Test,  1.5f, new ArrayList<>(Arrays.asList(
                    new MaterialEntity("Styrofoam Cup", 4.7f, RecyclingCategory.Test, 5)
            )), 5)
    ));

    public boolean Add(ProductEntity productEntity) {
        return true;
    }
    public List<ProductEntity> RetrieveAll() {
        return mockProducts;
    }
    public ProductEntity RetrieveByID(int id) {
        return mockProduct;
    }
    public boolean Update(ProductEntity productEntity) {
        return true;
    }
    public boolean Delete(int id) {
        return true;
    }

}
