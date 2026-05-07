package org.ood.infrastructure;

import org.ood.domain.entities.MaterialEntity;

import java.util.List;

public class MaterialRegistry extends RegistryAbstract<MaterialEntity> {
    private List<MaterialEntity> items;

    public boolean Add(List<String> parameters) {return false;}
    public List<MaterialEntity> RetrieveAll() {return null;}
    public MaterialEntity RetrieveByID(int id) {return null;}
    public boolean Update(List<MaterialEntity> parameters) {return false;}
    public boolean Delete(int id) {return false;}
}
