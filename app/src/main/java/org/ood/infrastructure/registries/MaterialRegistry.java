package org.ood.infrastructure.registries;

import org.ood.domain.RepositoryInterface;
import org.ood.domain.entities.MaterialEntity;

public class MaterialRegistry extends RegistryAbstract<MaterialEntity> {

    public MaterialRegistry() {
        super();
    }

    public MaterialRegistry(RepositoryInterface<MaterialEntity> repo) {
        super(repo);
    }
}
