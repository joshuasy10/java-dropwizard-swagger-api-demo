package org.kainos.ea.client;

public class FailedToUpdateProductException extends Throwable {
    @Override
    public String getMessage(){
        return "Could not create product.";
    }
}
