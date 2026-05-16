package org.ood.application;

import java.util.List;
import java.util.Map;

public interface CRUDServiceInterface<T, TRecord extends Record, CUDResponse extends Record> {
    CUDResponse Create(TRecord createRequest) throws Exception;
    List<TRecord> RetrieveAll();
    TRecord RetrieveByID(int id);
    CUDResponse Update(TRecord updateRequest) throws Exception;
    CUDResponse Delete(int id) throws Exception;
    Map<String, Class<?>> GetFields();
    boolean IdExists(int id);
}
