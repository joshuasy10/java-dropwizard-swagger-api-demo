package org.kainos.ea.core;

import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.db.CustomerDao;

import java.sql.SQLException;

public class OrderValidator {
    public String isValidOrder(OrderRequest order){

        try{
            if((new CustomerDao()).getCustomerById(order.getCustomerId()) == null){
                return  "Customer id " + order.getCustomerId() + " does not exist";
            }
        } catch (SQLException e){
            System.out.println("Error accessing Customer data in validator");
        }
        return null;
    }
}
