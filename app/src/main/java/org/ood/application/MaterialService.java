package org.ood.application;

import org.ood.domain.RecyclingCategory;
import org.ood.domain.RepositoryInterface;
import org.ood.domain.MaterialEntity;
import org.ood.domain.RegistryInterface;
import org.ood.presentation.records.Results.MaterialCUDSuccessfully;
import org.ood.presentation.records.requests.MaterialRequest;

import java.util.List;

public class MaterialService extends CRUDServiceAbstract<MaterialEntity, MaterialRequest, MaterialCUDSuccessfully> {
    /**Dependency injections for initialization.
     * @param materialRegistry Registry of materials in memory.
     * @param materialRepository Repository for long-term storage.
     * */
    public MaterialService(RegistryInterface<MaterialEntity> materialRegistry, RepositoryInterface<MaterialEntity> materialRepository){
        this.materialRegistry = materialRegistry;
        this.materialRepository = materialRepository;
    }

    private final RegistryInterface<MaterialEntity> materialRegistry;
    private final RepositoryInterface<MaterialEntity> materialRepository;

    @Override
    public MaterialCUDSuccessfully Create(MaterialRequest createRequest) throws Exception {
        MaterialEntity createdEntity = new MaterialEntity(createRequest.name(), createRequest.environmentalImpactValue(), createRequest.category());
        if(this.materialRepository.Add(createdEntity))
            return new MaterialCUDSuccessfully(1, "Create worked!!");
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public List<MaterialEntity> RetrieveAll() {
        return this.materialRepository.RetrieveAll();
    }

    @Override
    public MaterialEntity RetrieveByID(int id) {
        return this.materialRepository.RetrieveByID(id);
    }

    @Override
    public MaterialCUDSuccessfully Update(MaterialRequest material, int id) throws Exception {
        MaterialEntity updatedEntity = new MaterialEntity(material.name(), material.environmentalImpactValue(), material.category(), id);
        if(this.materialRepository.Update(updatedEntity))
            return new MaterialCUDSuccessfully(id, "Update worked!");
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public MaterialCUDSuccessfully Delete(int id) throws Exception {
        if(this.materialRepository.Delete(id))
            return new MaterialCUDSuccessfully(id, "Delete worked!");
        else
            throw new Exception("Ooops, something went wrong");
    }
}
