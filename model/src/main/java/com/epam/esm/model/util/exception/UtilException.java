package com.epam.esm.model.util.exception;

/**
 * Exception used by utils.
 *
 * @version 1.0
 * @author mishamba
 *
 * @see com.epam.esm.model.util
 */
public class UtilException extends Exception {
    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }
}
