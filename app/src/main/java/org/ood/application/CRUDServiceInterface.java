package org.ood.application;

import org.ood.presentation.records.Introspectable;

import java.util.List;
import java.util.Map;

/**
 * The interface is utilized to declare the contract of an array of methods for service classes involved in the orchestration of the creation, retrieval, update and deletion of entities.
 * In other words: this is the contract that all that control the lifecycle of domain objects have to abide by.
 * @param <T>               The type of domain object that it handles.
 * @param <TRecord>         The record representing said domain object in an immutable manner.
 * @param <CUDResponse>     The response type for Create, Update and Delete operations,
 */
public interface CRUDServiceInterface<T, TRecord extends Record, CUDResponse extends Record> {
    /**
     * Orchestrates the creation of a new object; this can be delimited to in-memory, or involve long-term storage as well.
     * @param createRequest         The new object to be created.
     * @return                      A response record with the information the presentation layer needs to display.
     * @throws Exception            If there was an error somewhere in the creation process.
     */
    CUDResponse Create(TRecord createRequest) throws Exception;

    /**
     * Retrieves all objects of the CRUD's type in existence.
     * @return                      A list of records of the objects of type T that currently exist.
     */
    List<TRecord> RetrieveAll();


    /**
     * Retrieves a particular object of the CRUD's type by ID.
     * @param id                    The ID of the object being retrieved.
     * @return                      A specific record. Null if nothing was found.
     */
    TRecord RetrieveByID(int id);

    /**
     * Orchestrates the update of an object throughout the various layers.
     * @param updateRequest         The object to be updated.
     * @return                      A response record with the information the presentation layer needs to display.
     * @throws Exception            If there was an error somewhere in the update process.
     */
    CUDResponse Update(TRecord updateRequest) throws Exception;

    /**
     * Orchestrates the deletion of an object.
     * @param id                    ID of the object to be deleted.
     * @return                      A response record with the information the presentation layer needs to display.
     * @throws Exception            If there was an error somewhere in the deletion process.
     */
    CUDResponse Delete(int id) throws Exception;

    /**
     * This is intended to retrieve the fields of the record managed by the service through {@link Introspectable#GetFields()}
     * for usage in {@link org.ood.presentation.Helpers.RequestBuilder}
     * @return              A map of tuples of string (name) and class (type) of each field.
     */
    Map<String, Class<?>> GetFields();

    /**
     * Retrieves the values of a given object, as mapped in {@link Introspectable#GetValues()}}}
     * @param id            The ID of the object whose values is being mapped.
     * @return              A map of tuples of type "field" (as string) : value (as whichever object it'd be. String, int, etc.)
     */
    Map<String, Object> GetValues(int id);

    /**
     * Checks whether an object with a specific ID exists.
     * @param id                    ID of the object whose existence is being checked.
     * @return                      True if it exists. False if not.
     */
    boolean IdExists(int id);
}
