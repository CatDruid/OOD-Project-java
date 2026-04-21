package org.ood.application;

import org.ood.domain.MaterialEntity;
import org.ood.infrastructure.ProductRegistry;
import org.ood.domain.RepositoryInterface;
import org.ood.domain.ProductEntity;
import org.ood.infrastructure.ProductRepository;
import org.ood.domain.RegistryInterface;
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
    private RegistryInterface<ProductEntity> productRegistry;
    private RepositoryInterface<ProductEntity> productRepository;

    @Override
    public ProductCUDSuccessfully Create(ProductRequest createRequest) throws Exception {
        if(this.productRepository.Add(new ProductEntity()))
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
        if(this.productRepository.Update(new ProductEntity()))
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
}
