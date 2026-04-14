package org.ood.presentation;

public interface UICRUDInterface<T> {
    void Create();
    void RetrieveAll();
    void RetrieveByID();
    void Update();
    void Delete();
}
