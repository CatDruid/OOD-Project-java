package org.ood.presentation.Helpers;

import org.ood.domain.ProductCategory;
import org.ood.domain.RecyclingCategory;
import org.ood.presentation.records.EntityRecords.MaterialRecord;
import org.ood.presentation.records.EntityRecords.ProductRecord;
import org.ood.presentation.records.Introspectable;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestFactory {
    private final InputHandler inputHandler;

    public RequestFactory(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public <T extends Introspectable> RequestBuilder<T> Create(Class<T> clazz) {
        return new RequestBuilder<T> (inputHandler, clazz, GetMapper(clazz));
    }


    private <T extends Introspectable> RecordMapper<T> GetMapper(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        RecordMapper<T> mapper = (RecordMapper<T>) mappers.get(clazz);
        return mapper;
    }

    /**
     * A map of the available mappers with the class as the key
     */
    private final Map<Class<? extends Introspectable>, RecordMapper<? extends Introspectable>> mappers = Map.of(
            MaterialRecord.class, values ->
                    new MaterialRecord(
                            (Integer) values.get("id"),
                            (String) values.get("name"),
                            (RecyclingCategory) values.get("category"),
                            (float) values.get("mass"),
                            (float) values.get("emissionFactor")
                    ),
            ProductRecord.class, values ->
                    new ProductRecord(
                            (Integer) values.get("id"),
                            (String) values.get("name"),
                            (ProductCategory) values.get("category"),
                            (float) values.get("estimatedLifeSpan"),
                            ((List<?>) values.get("materials")).stream()
                                    .map(mat -> (MaterialRecord) mat)
                                    .collect(Collectors.toList())
                    )
    );
}
