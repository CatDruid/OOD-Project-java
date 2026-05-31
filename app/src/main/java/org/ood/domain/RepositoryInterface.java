package org.ood.domain;

import java.util.List;

/**
 * Provides a contract for repositories, which provide the long-term storage thereof in some manner or another.
 * @param <T> The type that is being stored.
 * @see org.ood.infrastructure.repositories.AbstractJSONRepository
 * @see org.ood.infrastructure.repositories.JSONMaterialRepository
 * @see org.ood.infrastructure.repositories.JSONProductRepository
 */
public interface RepositoryInterface<T> {
    /**
     * Saves data into the long-term storage.
     * @param list          List of objects that is being stored.
     * @return              Boolean declaring whether data was saved or not.
     * @throws Exception    In case there was some I/O error
     */
    boolean Save(List<T> list) throws Exception;

    /**
     * Returns all from long-term storage for operations such as to populating a registry.
     * @return              List of fetched objects saved in the persistency layer.
     * @throws Exception    In case there was some error whilst loading them.
     */
    List<T> Load() throws Exception;
}
