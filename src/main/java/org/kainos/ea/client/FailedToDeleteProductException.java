package org.kainos.ea.client;

public class  FailedToDeleteProductException extends Exception {
    @Override
    public String getMessage(){
        return "Could not delete product.";
    }
}
