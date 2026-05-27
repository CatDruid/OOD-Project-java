package org.ood.domain;

import java.util.List;

public interface RepositoryInterface<T> {
    boolean Save(List<T> list) throws Exception;
    List<T> Load() throws Exception;
}
