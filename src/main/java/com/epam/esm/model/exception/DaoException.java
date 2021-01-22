package com.epam.esm.model.exception;

/**
 * Dao layer exception.
 *
 * @version 1.0
 * @author mishamba
 *
 * @see com.epam.esm.model.dao
 */
public class DaoException extends Exception {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
