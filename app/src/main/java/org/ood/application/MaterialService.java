package org.ood.application;
import org.ood.domain.RepositoryInterface;
import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.RegistryInterface;
import org.ood.presentation.records.Results.MaterialCUDSuccessfully;
import org.ood.presentation.records.EntityRecords.MaterialRecord;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MaterialService extends CRUDServiceAbstract<MaterialEntity, MaterialRecord, MaterialCUDSuccessfully> {
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

    /** {@inheritDoc} */
    @Override
    public MaterialCUDSuccessfully Create(MaterialRecord createRequest) throws Exception {
        int newId = materialRegistry.RetrieveAll().stream().mapToInt(MaterialEntity::GetID)
                .max()
                .orElse(0) + 1;
        MaterialEntity createdEntity = new MaterialEntity(newId, createRequest.name(), createRequest.recyclingCategory(), createRequest.mass(), createRequest.emissionFactor());
        if(this.materialRegistry.Add(createdEntity)) {
            this.materialRepository.Save(this.materialRegistry.RetrieveAll());
            return new MaterialCUDSuccessfully(createdEntity.GetID(), "Create worked!!");
        }
        else
            throw new Exception("Ooops, something went wrong");
    }

    /** {@inheritDoc} */
    @Override
    public List<MaterialRecord> RetrieveAll() {
        try {
            return this.materialRegistry.RetrieveAll().stream()
                    .map(MaterialRecord::FromEntity)
                    .sorted(Comparator.comparingInt(MaterialRecord::id))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public MaterialRecord RetrieveByID(int id) {
        MaterialEntity entity = this.materialRegistry.RetrieveByID(id);
        if(entity != null)
            return MaterialRecord.FromEntity(entity);
        else
            return null;
    }

    /** {@inheritDoc} */
    @Override
    public MaterialCUDSuccessfully Update(MaterialRecord updateRequest) throws Exception {
        MaterialEntity updatedEntity = new MaterialEntity(updateRequest.id(), updateRequest.name(), updateRequest.recyclingCategory(), updateRequest.mass(), updateRequest.emissionFactor());
        if(this.materialRegistry.Update(updatedEntity)) {
            this.materialRepository.Save(this.materialRegistry.RetrieveAll());
            return new MaterialCUDSuccessfully(updateRequest.id(), "Update worked!");
        } else
            throw new Exception("Ooops, something went wrong");
    }

    /** {@inheritDoc} */
    @Override
    public MaterialCUDSuccessfully Delete(int id) throws Exception {
        if(this.materialRegistry.Delete(id)) {
            this.materialRepository.Save(this.materialRegistry.RetrieveAll());
            return new MaterialCUDSuccessfully(id, "Delete worked!");
        } else
            throw new Exception("Ooops, something went wrong");
    }

    /** {@inheritDoc} */
    public Map<String, Class<?>> GetFields() {
        return MaterialRecord.GetFields();
    }
    /** {@inheritDoc} */
    public Map<String, Object> GetValues(int id) {return RetrieveByID(id).GetValues();}

    /** {@inheritDoc} */
    @Override
    public boolean IdExists(int id) {
        return this.materialRegistry.RetrieveByID(id) != null;
    }
}
