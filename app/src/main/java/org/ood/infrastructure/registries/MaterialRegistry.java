package org.ood.infrastructure.registries;

import org.ood.domain.RepositoryInterface;
import org.ood.domain.entities.MaterialEntity;

/**
 * In-memory registry for {@link MaterialEntity} instances.
 *
 * @see RegistryAbstract
 * @see MaterialEntity
 */
public class MaterialRegistry extends RegistryAbstract<MaterialEntity> {

    /** {@inheritDoc} */
    public MaterialRegistry() {
        super();
    }

    /** {@inheritDoc} */
    public MaterialRegistry(RepositoryInterface<MaterialEntity> repo) {
        super(repo);
    }
}
