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
        //This will probably be different for an update but it is simply for it to work right now until a proper service implementation.
        ProductEntity createdEntity = new ProductEntity(createRequest.name(), createRequest.category(), createRequest.estimatedLifespan(), createRequest.materials());
        if(this.productRepository.Add(createdEntity))
            return new ProductCUDSuccessfully(1, "Create worked!!");
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public List<ProductEntity> RetrieveAll() {
        return this.productRepository.RetrieveAll();
    }

    @Override
    public ProductEntity RetrieveByID(int id) {
        return this.productRepository.RetrieveByID(id);
    }

    @Override
    public ProductCUDSuccessfully Update(ProductRequest updateRequest, int id)  throws Exception {
        //This will probably be different for an update but it is simply for it to work right now until a proper service implementation.
        ProductEntity updatedEntity = new ProductEntity(updateRequest.name(), updateRequest.category(), updateRequest.estimatedLifespan(), updateRequest.materials(), id);
        if(this.productRepository.Update(updatedEntity))
            return new ProductCUDSuccessfully(id, "Update worked!");
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public ProductCUDSuccessfully Delete(int id)  throws Exception {
        if(this.productRepository.Delete(id))
            return new ProductCUDSuccessfully(id, "Delete worked!");
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public boolean IdExists(int id) {
        return this.productRepository.RetrieveByID(id) != null;
    }

    public String GetGuidance(int id){
        ProductEntity product = this.RetrieveByID(id);
        return product.GetGuidance();
    }

    public Class<ProductCategory> GetCategory() {return ProductCategory.class;}
}
