package org.ood.application;
import org.ood.domain.RepositoryInterface;
import org.ood.domain.entities.MaterialEntity;
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
        MaterialEntity createdEntity = new MaterialEntity(createRequest.name(), createRequest.category(), createRequest.mass(), createRequest.emissionFactor());
        if(this.materialRegistry.Add(createdEntity))
            return new MaterialCUDSuccessfully(1, "Create worked!!");
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public List<MaterialEntity> RetrieveAll() {
        try {
            return this.materialRegistry.RetrieveAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MaterialEntity RetrieveByID(int id) {
        return this.materialRegistry.RetrieveByID(id);
    }

    @Override
    public MaterialCUDSuccessfully Update(MaterialRequest updateRequest, int id) throws Exception {
        MaterialEntity updatedEntity = new MaterialEntity(id, updateRequest.category(), updateRequest.name(), updateRequest.mass(), updateRequest.emissionFactor());
        if(this.materialRegistry.Update(updatedEntity))
            return new MaterialCUDSuccessfully(id, "Update worked!");
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public MaterialCUDSuccessfully Delete(int id) throws Exception {
        if(this.materialRegistry.Delete(id))
            return new MaterialCUDSuccessfully(id, "Delete worked!");
        else
            throw new Exception("Ooops, something went wrong");
    }

    @Override
    public boolean IdExists(int id) {
        return this.materialRegistry.RetrieveByID(id) != null;
    }
}
