package com.neocortex.exceptions;

/**
 * Exception thrown when a data integrity violation occurs.
 */
public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(String message) {
        super(message);
    }
}