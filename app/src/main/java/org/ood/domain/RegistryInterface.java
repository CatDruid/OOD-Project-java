package org.ood.domain;

import java.util.List;

/**
 * Provides a contract with the basic operations for all registries, which are the in-memory storage of products and materials.
 * @param <T> The type that is being manipulated with the CRUD operations.
 * @see org.ood.infrastructure.registries.RegistryAbstract
 * @see org.ood.infrastructure.registries.MaterialRegistry
 * @see org.ood.infrastructure.registries.ProductRegistry
 */
public interface RegistryInterface<T> {
    /**
     * Adds an object to the registry.
     * @param item     The object being added to it.
     * @return         A boolean value, true if the operation was successful, false if not.
     */
    boolean Add(T item);

    /**
     * Retrieves objects from the registry.
     * @return         A list of objects of the declared type.
     */
    List<T> RetrieveAll();

    /**
     * Tries to retrieve a given individual object by its ID.
     * @param id        The unique ID of the object that is being retrieved.
     * @return          Either the object of type T, or a null value if no match was found for the ID.
     */
    T RetrieveByID(int id);


    /**
     * Updates a registry object if it exists (and what is provided is not null).
     * @param newItem  The updated object (with matching ID)
     * @return         A boolean value, true if the operation was successful, false if not.
     */
    boolean Update(T newItem);

    /**
     * Deletes an object from the registry.
     * @param id        ID of the object to delete
     * @return         A boolean value, true if the operation was successful, false if not.
     */
    boolean Delete(int id);
}
