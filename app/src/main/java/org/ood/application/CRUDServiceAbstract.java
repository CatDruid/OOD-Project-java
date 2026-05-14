package org.ood.application;

import org.ood.domain.RepositoryInterface;
import org.ood.domain.RegistryInterface;

import java.util.List;

public abstract class CRUDServiceAbstract<T, TRecord extends Record, CUDResponse extends Record> implements CRUDServiceInterface<T, TRecord, CUDResponse> {
    protected RepositoryInterface<T> repository;
    protected RegistryInterface<T> registry;

    @Override
    public abstract CUDResponse Create(TRecord createRequest) throws Exception;
    public List<TRecord> RetrieveAll() {return null;}
    public TRecord RetrieveByID(int id) { return null;}
    public abstract CUDResponse Update(TRecord updateRequest) throws Exception;
    public abstract CUDResponse Delete(int id) throws Exception;
    public abstract boolean IdExists(int id);
}
