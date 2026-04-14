package org.ood.application;

import org.ood.domain.RepositoryInterface;
import org.ood.domain.RegistryInterface;

import java.util.List;

public abstract class CRUDServiceAbstract<T> implements CRUDServiceInterface<T> {
    protected RepositoryInterface<T> repository;
    protected RegistryInterface<T> registry;

    public int Create(List<String> parameters) {return 0;}
    public List<T> RetrieveAll() {return null;}
    public T RetrieveByID(int id) { return null;}
    public boolean Update(List<String> parameters) {return false;}
    public boolean Delete(int id) {return false;}
}
