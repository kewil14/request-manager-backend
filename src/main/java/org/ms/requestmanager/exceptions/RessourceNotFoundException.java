package org.ms.requestmanager.exceptions;

public class RessourceNotFoundException extends RuntimeException {
    public RessourceNotFoundException(String messages) {
        super(messages);
    }
}
