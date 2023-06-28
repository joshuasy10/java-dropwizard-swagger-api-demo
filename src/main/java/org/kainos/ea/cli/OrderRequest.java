package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class OrderRequest {
    private int customerId;



    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }




    @JsonCreator
    public OrderRequest(@JsonProperty("customerId") int customerId){
        this.setCustomerId(customerId);
    }

}
