package org.ood.application;

import org.ood.domain.RepositoryInterface;
import org.ood.domain.RegistryInterface;

import java.util.List;

/**
 * An abstract implementing {@link CRUDServiceInterface}.
 * The main purpose thereof is to standardize the existence of an repository and registry variable.
 * @param <T>               The type of domain object that it handles.
 * @param <TRecord>         The record representing said domain object in an immutable manner.
 * @param <CUDResponse>     The response type for Create, Update and Delete operations,
 */
public abstract class CRUDServiceAbstract<T, TRecord extends Record, CUDResponse extends Record> implements CRUDServiceInterface<T, TRecord, CUDResponse> {
    /** Repository class handling the long-term storage of the variables this service manipulates*/
    protected RepositoryInterface<T> repository;
    /** Registry class handling the memory storage of the variables this service manipulates*/
    protected RegistryInterface<T> registry;

    /** {@inheritDoc} */
    public abstract CUDResponse Create(TRecord createRequest) throws Exception;
    /** {@inheritDoc} */
    public abstract List<TRecord> RetrieveAll();
    /** {@inheritDoc} */
    public abstract TRecord RetrieveByID(int id);
    /** {@inheritDoc} */
    public abstract CUDResponse Update(TRecord updateRequest) throws Exception;
    /** {@inheritDoc} */
    public abstract CUDResponse Delete(int id) throws Exception;
    /** {@inheritDoc} */
    public abstract boolean IdExists(int id);
}
