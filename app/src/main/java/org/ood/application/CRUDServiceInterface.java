package org.ood.application;

import java.util.List;

public interface CRUDServiceInterface<T, CUDRequest extends Record, CUDResponse extends Record> {
    CUDResponse Create(CUDRequest createRequest) throws Exception;
    List<T> RetrieveAll();
    T RetrieveByID(int id);
    CUDResponse Update(CUDRequest updateRequest, int id) throws Exception;
    CUDResponse Delete(int id) throws Exception;
    boolean IdExists(int id);
}
