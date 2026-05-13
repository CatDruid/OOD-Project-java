package org.ood.domain;

import java.util.List;

public interface RepositoryInterface<T> {
    boolean Add(T t);
    List<T> RetrieveAll();
    T RetrieveByID(int id);
    boolean Update(T t);
    boolean Delete(int id);
}
