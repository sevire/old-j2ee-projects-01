package uk.co.genonline.ldav03.model;

import java.io.Serializable;

/**
 * Interface which defines method to be implemented by a class which manages a persisted entity within the application.
 *
 * Methods for adding, updating, deleting a single record, or for retrieving multiple records.
 *
 * Not sure whether this is the right way to do this - in practice individual Entities may require more specific methods.
 */
public interface EntityManager {

    public void add(Serializable entity);

    public void update(Serializable entity);

    public Serializable get(Object key);

    public Serializable[] getAll();

    public void delete(Object key);

}
