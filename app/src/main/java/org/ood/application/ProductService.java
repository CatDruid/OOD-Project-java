package org.ood.application;

import org.ood.domain.*;
import org.ood.domain.entities.ProductEntity;
import org.ood.presentation.records.Results.ProductCUDSuccessfully;
import org.ood.presentation.records.requests.ProductRequest;

import java.util.List;

public class ProductService extends CRUDServiceAbstract<ProductEntity, ProductRequest, ProductCUDSuccessfully> {
    /**Dependency injections for initialization.
     * @param productRegistry Registry of products in memory.
     * @param productRepository Repository for long-term storage.
     * */
    public ProductService(RegistryInterface<ProductEntity> productRegistry, RepositoryInterface<ProductEntity> productRepository){
        this.productRegistry = productRegistry;
        this.productRepository = productRepository;
    }
    private final RegistryInterface<ProductEntity> productRegistry;
    private final RepositoryInterface<ProductEntity> productRepository;

    @Override
    public ProductCUDSuccessfully Create(ProductRequest createRequest) throws Exception {
        int newId = productRegistry.RetrieveAll().stream().mapToInt(ProductEntity::GetID)
                .max()
                .orElse(0) + 1;
        ProductEntity createdEntity = new ProductEntity(newId, createRequest.name(), createRequest.category(), createRequest.estimatedLifespan(), createRequest.materials());
        if(this.productRegistry.Add(createdEntity)) {
            this.productRepository.Save(this.productRegistry.RetrieveAll());
            return new ProductCUDSuccessfully(1, "Create worked!!");
        }
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public List<ProductEntity> RetrieveAll() {
        try {
            return this.productRegistry.RetrieveAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductEntity RetrieveByID(int id) {
        return this.productRegistry.RetrieveByID(id);
    }

    @Override
    public ProductCUDSuccessfully Update(ProductRequest updateRequest, int id)  throws Exception {
        ProductEntity updatedEntity = new ProductEntity(id, updateRequest.name(), updateRequest.category(), updateRequest.estimatedLifespan(), updateRequest.materials());
        if(this.productRegistry.Update(updatedEntity)){
            this.productRepository.Save(this.productRegistry.RetrieveAll());
            return new ProductCUDSuccessfully(1, "Update worked!!");
        }
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public ProductCUDSuccessfully Delete(int id)  throws Exception {
        if(this.productRegistry.Delete(id)){
            this.productRepository.Save(this.productRegistry.RetrieveAll());
            return new ProductCUDSuccessfully(1, "Delete worked!!");
        }
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public boolean IdExists(int id) {
        return this.productRegistry.RetrieveByID(id) != null;
    }

    public String GetGuidance(int id){
        ProductEntity product = this.RetrieveByID(id);
        return product.GetGuidance();
    }

    public Class<ProductCategory> GetCategory() {return ProductCategory.class;}
}
