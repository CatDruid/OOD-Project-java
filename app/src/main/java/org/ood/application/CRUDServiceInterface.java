package org.ood.application;

import java.util.List;

public interface CRUDServiceInterface<T> {
    int Create(List<String> parameters);
    List<T> RetrieveAll();
    T RetrieveByID(int id);
    boolean Update(List<String> parameters);
    boolean Delete(int id);
}
