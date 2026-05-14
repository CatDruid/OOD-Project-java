package org.ood.infrastructure.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ood.domain.RepositoryInterface;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJSONRepository <T> implements RepositoryInterface<T> {
    protected final File file;

    public AbstractJSONRepository(String path) {
        this.file = new File(path);
    }

    public boolean Save(List<T> list) throws Exception{
        if (list == null || list.isEmpty()) {return false;}
        // toJson(list, writer) serialises directly to the file.
        // The old code called gson.toJson(gson.toJson(list), writer), which first
        // converted the list to a JSON string, then serialised that string again,
        // producing escaped/double-quoted output instead of valid JSON.
        try (Writer writer = new FileWriter(file)) {
            new Gson().toJson(list, writer);
        }
        return true;
    }

    public List<T> Load() throws Exception {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try (Writer writer = new FileWriter(file)) {
                writer.write("[]");
            }
            return new java.util.ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            // Java generics use "type erasure" — at runtime, every type parameter
            // (like T) is replaced with Object. The compiler uses T only to enforce
            // type safety at compile time; the JVM never sees it. So at runtime,
            // "T" is unknown inside this abstract class.
            //
            // When a concrete subclass is compiled, e.g.
            //   class JSONProductRepository extends AbstractJSONRepository<ProductEntity>
            // the actual type argument <ProductEntity> is stored in the subclass's
            // bytecode as part of its "generic superclass" descriptor. It survives
            // erasure because it's recorded on the *declaration site*, not inside
            // the body of the generic class.
            //
            // getClass()              → JSONProductRepository.class  (the real runtime class)
            // getGenericSuperclass()  → AbstractJSONRepository<ProductEntity>  (a ParameterizedType)
            // getActualTypeArguments()[0] → ProductEntity  (the concrete T we need)
            //
            // We then ask TypeToken to build List<ProductEntity>, giving Gson the
            // full concrete type it needs to deserialize correctly.

            Type typeArg = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Type listType = TypeToken.getParameterized(List.class, typeArg).getType();

            // fromJson returns null if the file is empty or contains a non-array root
            // (e.g. corrupted data). Treat that as an empty registry rather than
            // propagating null and causing NPEs in every caller.

            List<T> result = new Gson().fromJson(reader, listType);
            return result != null ? result : new ArrayList<>();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
