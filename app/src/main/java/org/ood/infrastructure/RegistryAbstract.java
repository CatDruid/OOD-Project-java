package org.ood.infrastructure;

import org.ood.domain.RegistryInterface;
import org.ood.domain.RepositoryInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class RegistryAbstract<T> implements RegistryInterface<T> {
    private List<T> items;

    public RegistryAbstract() {
        items = new ArrayList<>();
    }

    public void Load(RepositoryInterface<T> repo) {
        // TODO error handling if already something inside registry
        items = repo.RetrieveAll();
    }

    public boolean Add(T obj) {
        if(obj == null) {return false;}
        try {
            if(!items.contains(obj)) {
                items.add(obj);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public List<T> RetrieveAll() {
        return items;
    }
    public T RetrieveByID(int id) {
        for (T item : items) {
            //if (item.)
        }
    }
    public boolean Update(List<T> parameters) {return false;}
    public boolean Delete(int id) {return false;}
}
