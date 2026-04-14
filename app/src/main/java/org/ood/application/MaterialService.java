package org.ood.application;

import org.ood.domain.RepositoryInterface;
import org.ood.domain.MaterialEntity;
import org.ood.infrastructure.MaterialRegistry;
import org.ood.infrastructure.MaterialRepository;
import org.ood.domain.RegistryInterface;

import java.util.List;

public class MaterialService extends CRUDServiceAbstract<MaterialEntity> {
    private RegistryInterface<MaterialRegistry> materialRegistry;
    private RepositoryInterface<MaterialRepository> materialRepository;

    @Override
    public int Create(List<String> parameters) {
        return super.Create(parameters);
    }

    @Override
    public List<MaterialEntity> RetrieveAll() {
        return super.RetrieveAll();
    }

    @Override
    public MaterialEntity RetrieveByID(int id) {
        return super.RetrieveByID(id);
    }

    @Override
    public boolean Update(List<String> parameters) {
        return super.Update(parameters);
    }

    @Override
    public boolean Delete(int id) {
        return super.Delete(id);
    }
}
