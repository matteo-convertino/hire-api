package com.convertino.hire.exceptions.entity;

/**
 * Exception thrown when there is an error during the updating of an entity.
 */
public class EntityUpdateException extends RuntimeException {

    /**
     * Constructs a new EntityUpdateException with a formatted message indicating the object name, property name, and value that caused the updating error.
     *
     * @param objectName   the name of the object that failed to be updated
     * @param propertyName the name of the property that caused the updating error
     * @param value        the value of the property that caused the updating error
     */
    public EntityUpdateException(String objectName, String propertyName, long value) {
        super(String.format("There was an error while trying to update %s with %s = %d.", objectName, propertyName, value));
    }

    public EntityUpdateException(String objectName) {
        super(String.format("There was an error while trying to update %s", objectName));
    }
}