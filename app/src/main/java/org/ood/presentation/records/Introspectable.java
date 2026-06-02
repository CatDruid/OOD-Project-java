package org.ood.presentation.records;

import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This is an interface for records used for the creation of a generic request builders as per {@link org.ood.presentation.Helpers.RequestBuilder}
 * Thus lessening the coupling required in the Create / Update forms if fields are added or removed from an entity and it's matching record.
 */
public interface Introspectable {

    /**
     * Retrieves the fields of the record implementing Introspectable.
     * @return              A map of tuples of string (name) and class (type) of each field.
     */
    static Map<String, Class<?>> GetFields(Class<? extends Introspectable> clazz) {
        Map<String, Class<?>> fields = new HashMap<>();
        for (RecordComponent rc : clazz.getRecordComponents()) {
            fields.put(rc.getName(), rc.getType());
        }
        return fields;
    }

    /**
     * Retrieves the values of the record implementing Introspectable.
     * @return              A map of tuples of type "field" (as string) : value (as whichever object it'd be. String, int, etc.)
     */
    default Map<String, Object> GetValues() {
        return Arrays.stream(this.getClass().getRecordComponents()).collect(
                HashMap::new,
                (map, rc) -> {
                    try {
                        map.put(rc.getName(), rc.getAccessor().invoke(this));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                HashMap::putAll
        );
    }

    Integer id();
    String name();
}
