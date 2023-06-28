package org.kainos.ea.client;

public class FailedToCreateOrderException extends Exception {
    @Override
    public String getMessage(){
        return "Could not create product.";
    }
}
