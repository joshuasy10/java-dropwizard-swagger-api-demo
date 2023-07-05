package org.kainos.ea.api;

import org.kainos.ea.cli.Customer;
import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.client.*;
import org.kainos.ea.core.OrderValidator;
import org.kainos.ea.db.CustomerDao;
import org.kainos.ea.db.OrderDao;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerService {
    private CustomerDao customerDao = new CustomerDao();
//    private CustomerValidator orderValidator= new CustomerValidator();

    public List<Customer> getAllCustomers() throws Exception {

        List<Customer> customerList = null;
        try {
            customerList = customerDao.getAllCustomers();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new Exception();
        }
//        Collections.sort(customerList);


        return customerList;
    }




}
