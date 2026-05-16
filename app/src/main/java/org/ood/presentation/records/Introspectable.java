package org.ood.presentation.records;

import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface Introspectable {

    default Map<String, Class<?>> GetFields() {
        Map<String, Class<?>> fields = new HashMap<>();
        for (RecordComponent rc : this.getClass().getRecordComponents()) {
            fields.put(rc.getName(), rc.getType());
        }
        return fields;
    }
    default Map<String, Object> GetValues() {
        return Arrays.stream(this.getClass().getRecordComponents()).collect(Collectors.toMap(
                RecordComponent::getName,
                rc -> {
                    try {
                        return rc.getAccessor().invoke(this);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ));
    }
}
