package org.ood.domain.entities;

/**
 * Defines base contract for all domain entities. Each must be identifiable by ID and carry a readable name.
 * @see MaterialEntity
 * @see ProductEntity
 */
public interface Entity {

    /** Returns the unique identifier for this entity. */
    int GetID();

    /** Returns the name for this entity. */
    String GetName();
}
