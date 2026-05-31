package org.ood.presentation.Helpers;

import java.util.Collection;

public interface RecordMapper<T> {
    T map(Collection<String>  values) throws  Exception;
}
