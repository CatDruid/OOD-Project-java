package org.ood.presentation.records;

import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;

public interface Introspectable {

    default Map<String, Class<?>> GetFields() {
        Map<String, Class<?>> fields = new HashMap<>();
        for(RecordComponent rc : this.getClass().getRecordComponents()) {
            fields.put(rc.getName(), rc.getType());
        }
        return fields;
    }
}
