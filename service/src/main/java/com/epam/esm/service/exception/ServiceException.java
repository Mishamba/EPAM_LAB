package com.epam.esm.service.exception;

/**
 * Service layer exception.
 *
 * @version 1.0
 * @author mishamba
 *
 * @see com.epam.esm.service
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
