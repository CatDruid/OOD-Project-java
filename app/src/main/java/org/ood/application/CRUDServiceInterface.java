package org.ood.application;

import java.util.List;

public interface CRUDServiceInterface<T, TRecord extends Record, CUDResponse extends Record> {
    CUDResponse Create(TRecord createRequest) throws Exception;
    List<TRecord> RetrieveAll();
    TRecord RetrieveByID(int id);
    CUDResponse Update(TRecord updateRequest) throws Exception;
    CUDResponse Delete(int id) throws Exception;
    boolean IdExists(int id);
}
