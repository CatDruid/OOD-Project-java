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

    /**
     * Adds an object to the registry.
     * @param obj      The object being added to it.
     * @return         False if nothing could be added: either because the object was null, or because an exception occurred.
     */
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

    /**
     * Tries to retrieve a given individual object by its ID.
     * @param id        ID of the object that is being retrieved.
     * @return          Either the object of type T, or a null value if no match was found for the ID.
     */
    public T RetrieveByID(int id) {
        for (T item : items) {
            if (item.GetID() == id) {
                return item;
            }
        }
        return null;
    }


    /**
     * Updates a registry object if it exists (and what is provided is not null).
     * @param newItem   Updated object
     * @return          True if the object could be added. False otherwise for a variety of reasons.
     */
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

    /**
     * Deletes an object from the registry.
     * @param id        ID of the object to delete
     * @return          False if the operation could not be carried out or the object did not exist. True otherwise.
     */
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
