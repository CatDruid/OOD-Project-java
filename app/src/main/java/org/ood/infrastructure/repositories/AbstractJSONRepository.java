package org.ood.infrastructure.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ood.domain.RepositoryInterface;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements a base {@link RepositoryInterface} for enabling the long-term persistency of data through JSON files.
 * @param <T>               A type of object that is going to be stored within a file. Each object thus gets stored in a different one.
 */
public abstract class AbstractJSONRepository <T> implements RepositoryInterface<T> {
    protected final File file;

    /**
     * Sets the file location in initialization.
     * @param path          The path to the file.
     */
    public AbstractJSONRepository(String path) {
        this.file = new File(path);
    }

    /**
     * Saves objects into a JSON for long-term storage.
     * @param list          List of objects to store.
     * @return              True if successful, false if there was nothing to store in the first place.
     * @throws Exception    If there was a writing error in the creation of the JSON.
     */
    public boolean Save(List<T> list) throws Exception{
        if (list == null || list.isEmpty()) {return false;}
        try (Writer writer = new FileWriter(file)) {
            new Gson().toJson(list, writer);
        }
        return true;
    }

    /**
     * Loads the list of objects from the JSON the constructor's path points towards.
     * @return              The full list of objects that was stored in the JSON.
     * @throws Exception    If there was a reading error.
     */
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
            // (like T) is replaced with Object. Therefore "T" is unknown inside this abstract class.
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
            // This way we can ask the full concrete type it needs to deserialize correctly.

            Type typeArg = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Type listType = TypeToken.getParameterized(List.class, typeArg).getType();

            // fromJson returns null if the file is empty or contains a non-array root
            // (e.g. corrupted data). Treat that as an empty repository rather than
            // propagating null and causing further issues.

            List<T> result = new Gson().fromJson(reader, listType);
            return result != null ? result : new ArrayList<>();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
