package org.ood.infrastructure.registries;

import org.ood.domain.RegistryInterface;
import org.ood.domain.RepositoryInterface;
import org.ood.domain.entities.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Base implementation of {@link RegistryInterface} for a generic type T.
 * @see ProductRegistry
 * @see MaterialRegistry
 */
public abstract class RegistryAbstract<T extends Entity> implements RegistryInterface<T> {
    private List<T> items;

    public RegistryAbstract() {
        items = new ArrayList<>();
    }

    /**
     * Constructs the registry based off a particular repository handling the storage of objects the same type.
     * @param repo              Persistence-layer object that data is being loaded from.
     * @throws RuntimeException If there was an exception in the loading operation.
     */
    public RegistryAbstract(RepositoryInterface<T> repo) {
        try {
            items = repo.Load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    public List<T> RetrieveAll() {
        return items;
    }

    /** {@inheritDoc} */
    public T RetrieveByID(int id) {
        for (T item : items) {
            if (item.GetID() == id) {
                return item;
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    public boolean Update(T newItem) {
        if(newItem == null || !IDExists(newItem.GetID())) {return false;}
        int id = newItem.GetID();
        T oldItem = RetrieveByID(id);
        //The sequence is as follows: Delete the existing object with said ID. If it was successful, add the new one.
        // If that fails, add the previously-deleted one.
        if(Delete(id)) {
            if(Add(newItem)) {
                return true;
            } else {
                Add(oldItem);
                return false;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    public boolean Delete(int id) {
        T item = RetrieveByID(id);
        if(item == null) {return false;}
        try {
            items.remove(item);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks whether an object with a given ID exists already within the registry or not.
     * @param id        ID of the object.
     * @return          True if exists. False if not.
     */
    public boolean IDExists(int id) {
        for (T item : items) {
            if (id == item.GetID()) {
                return true;
            }
        }
        return false;
    }
}
