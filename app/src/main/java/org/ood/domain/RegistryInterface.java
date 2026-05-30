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
    boolean Add(T item);
    List<T> RetrieveAll();
    T RetrieveByID(int id);
    boolean Update(T newItem);
    boolean Delete(int id);
}
