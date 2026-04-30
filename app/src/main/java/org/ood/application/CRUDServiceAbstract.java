package org.ood.application;

import org.ood.domain.RepositoryInterface;
import org.ood.domain.RegistryInterface;

import java.util.List;

public abstract class CRUDServiceAbstract<T, CUDRequest extends Record, CUDResponse extends Record> implements CRUDServiceInterface<T, CUDRequest, CUDResponse> {
    protected RepositoryInterface<T> repository;
    protected RegistryInterface<T> registry;

    @Override
    public abstract CUDResponse Create(CUDRequest createRequest) throws Exception;
    public List<T> RetrieveAll() {return null;}
    public T RetrieveByID(int id) { return null;}
    public abstract CUDResponse Update(CUDRequest updateRequest, int id) throws Exception;
    public abstract CUDResponse Delete(int id) throws Exception;
}
