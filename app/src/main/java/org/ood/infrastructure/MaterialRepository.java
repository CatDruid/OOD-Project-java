package org.ood.infrastructure;

import org.ood.domain.MaterialEntity;
import org.ood.domain.RepositoryInterface;

import java.util.List;

public class MaterialRepository implements RepositoryInterface<MaterialEntity> {
    public boolean Add(MaterialEntity materialEntity) {
        return false;
    }
    public List<MaterialEntity> RetrieveAll() {
        return null;
    }
    public MaterialEntity RetrieveByID(int id) {
        return null;
    }
    public boolean Update(MaterialEntity materialEntity) {
        return false;
    }
    public boolean Delete(int id) {
        return false;
    }
}
