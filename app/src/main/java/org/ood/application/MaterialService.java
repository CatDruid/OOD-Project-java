package org.ood.application;

import org.ood.domain.RepositoryInterface;
import org.ood.domain.MaterialEntity;
import org.ood.infrastructure.MaterialRegistry;

public class MaterialService extends CRUDServiceAbstract<MaterialEntity> {
    private MaterialRegistry materialRegistry;
    private RepositoryInterface materialRepository;
}
