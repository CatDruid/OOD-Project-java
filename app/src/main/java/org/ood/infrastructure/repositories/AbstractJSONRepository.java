package org.ood.infrastructure.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ood.domain.RepositoryInterface;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractJSONRepository <T> implements RepositoryInterface<T> {
    protected final File file;

    public AbstractJSONRepository(String path) {
        this.file = new File(path);
    }

    public boolean Save(List<T> list) throws Exception{
        // Basic error handling
        if (list == null || list.isEmpty()) {return false;}

        // Convert list to JSON and write to file
        Gson gson = new Gson();
        gson.toJson(gson.toJson(list), new FileWriter(file));
        // Return true if the saving was successful
        return true;
    }

    public List<T> Load() throws Exception {
        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<T>>(){}.getType();

            return new Gson().fromJson(reader, listType);

        } catch (FileNotFoundException e) {
            throw new Exception("File not found");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
