package org.ood.domain;

import java.util.List;

public interface RegistryInterface<T> {
    boolean Add(List<String> parameters);
    List<T> RetrieveAll();
    T RetrieveByID(int id);
    boolean Update(List<T> parameters);
    boolean Delete(int id);
}
