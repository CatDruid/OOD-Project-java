package org.ood.domain;

import java.util.List;

public interface RegistryInterface<T> {
    boolean Add(T item);
    List<T> RetrieveAll();
    T RetrieveByID(int id);
    boolean Update(T newItem);
    boolean Delete(int id);
}
