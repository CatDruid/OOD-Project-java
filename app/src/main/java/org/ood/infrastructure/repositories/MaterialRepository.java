package org.ood.infrastructure.repositories;

import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.RepositoryInterface;

import java.util.List;

public class MaterialRepository implements RepositoryInterface<MaterialEntity> {

    public boolean Add(MaterialEntity materialEntity) {
        return true;
    }
    public List<MaterialEntity> RetrieveAll() {
        return null;
    }
    public MaterialEntity RetrieveByID(int id) {return null;}
    public boolean Update(MaterialEntity materialEntity) {
        return true;
    }
    public boolean Delete(int id) {
        return true;
    }
}
