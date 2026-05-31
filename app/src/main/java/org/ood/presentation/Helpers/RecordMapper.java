package org.ood.presentation.Helpers;

import org.ood.presentation.records.Introspectable;

import java.util.Map;

/**
 *
 * @param <T>
 */
public interface RecordMapper<T extends Introspectable> {
    T map(Map<String, Object> values) throws Exception;
}
