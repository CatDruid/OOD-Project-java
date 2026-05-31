package org.ood.application;

import org.ood.domain.*;
import org.ood.domain.entities.ProductEntity;
import org.ood.presentation.records.EntityRecords.ProductRecord;
import org.ood.presentation.records.Results.ProductCUDSuccessfully;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductService extends CRUDServiceAbstract<ProductEntity, ProductRecord, ProductCUDSuccessfully> {
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

    /** {@inheritDoc} */
    @Override
    public ProductCUDSuccessfully Create(ProductRecord createRequest) throws Exception {
        int newId = productRegistry.RetrieveAll().stream().mapToInt(ProductEntity::GetID)
                .max()
                .orElse(0) + 1;
        ProductEntity createdEntity = new ProductEntity(newId, createRequest.name(), createRequest.productCategory(), createRequest.estimatedLifespan(),
                createRequest.materials().stream()
                        .map(r -> {
                            try {
                                return r.ToEntity();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toList())
        );
        if(this.productRegistry.Add(createdEntity)) {
            this.productRepository.Save(this.productRegistry.RetrieveAll());
            return new ProductCUDSuccessfully(newId, "Create worked!!");
        }
        else
            throw new Exception("Ooops, something went wrong");
    }

    /** {@inheritDoc} */
    @Override
    public List<ProductRecord> RetrieveAll() {
        try {
            return this.productRegistry.RetrieveAll().stream()
                    .map(ProductRecord::FromEntity)
                    .sorted(Comparator.comparingInt(ProductRecord::id))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public ProductRecord RetrieveByID(int id) {
        ProductEntity entity = this.productRegistry.RetrieveByID(id);
        if(entity != null)
            return ProductRecord.FromEntity(entity);
        else
            return null;
    }

    /** {@inheritDoc} */
    @Override
    public ProductCUDSuccessfully Update(ProductRecord updateRequest)  throws Exception {
        ProductEntity updatedEntity = new ProductEntity(updateRequest.id(), updateRequest.name(), updateRequest.productCategory(), updateRequest.estimatedLifespan(),
                updateRequest.materials().stream()
                        .map(r -> {
                            try {
                                return r.ToEntity();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toList()));
        if(this.productRegistry.Update(updatedEntity)){
            this.productRepository.Save(this.productRegistry.RetrieveAll());
            return new ProductCUDSuccessfully(updateRequest.id(), "Update worked!!");
        }
        else
            throw new Exception("Ooops, something went wrong");
    }

    /** {@inheritDoc} */
    @Override
    public ProductCUDSuccessfully Delete(int id)  throws Exception {
        if(this.productRegistry.Delete(id)){
            this.productRepository.Save(this.productRegistry.RetrieveAll());
            return new ProductCUDSuccessfully(id, "Delete worked!!");
        }
        else
            throw new Exception("Ooops, something went wrong");
    }

    /** {@inheritDoc} */
    @Override
    public boolean IdExists(int id) {
        return this.productRegistry.RetrieveByID(id) != null;
    }

    public String GetGuidance(int id){
        ProductEntity product = this.productRegistry.RetrieveByID(id);
        return product.GetGuidance();
    }

    /**
     * Gets the product productCategory class.
     * @return          An {@link ProductCategory} object.
     */
    public Class<ProductCategory> GetCategory() {return ProductCategory.class;}
    /** {@inheritDoc} */
    public Map<String, Class<?>> GetFields() {
        return ProductRecord.GetFields();
    }
    /** {@inheritDoc} */
    public Map<String, Object> GetValues(int id) {return RetrieveByID(id).GetValues();}
}
