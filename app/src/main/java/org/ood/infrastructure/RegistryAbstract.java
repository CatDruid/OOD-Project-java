package org.ood.infrastructure;

import org.ood.domain.RegistryInterface;

import java.util.List;

public abstract class RegistryAbstract<T> implements RegistryInterface<T> {
    private List<T> items;

    public boolean Add(List<String> parameters) {return false;}
    public List<T> RetrieveAll() {return null;}
    public T RetrieveByID(int id) {return null;}
    public boolean Update(List<T> parameters) {return false;}
    public boolean Delete(int id) {return false;}
}
