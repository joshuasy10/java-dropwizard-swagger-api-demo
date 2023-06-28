package org.kainos.ea.client;

public class InvalidOrderException extends Exception {
    public InvalidOrderException(String error) {
        super(error);
    }
}
