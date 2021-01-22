package com.epam.esm.model.exception;

/**
 * Controller layer exception.
 *
 * @version 1.0
 * @author mishamba
 * @see com.epam.esm.controller
 */
public class ControllerException extends Exception {
    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }
}
