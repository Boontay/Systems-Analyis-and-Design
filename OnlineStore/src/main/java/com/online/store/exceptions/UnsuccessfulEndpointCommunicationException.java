package com.online.store.exceptions;

public class UnsuccessfulEndpointCommunicationException extends RuntimeException{
    public UnsuccessfulEndpointCommunicationException() {
        super("Unsuccessful communication with endpoint.", new Exception());
    }
}
