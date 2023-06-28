package org.kainos.ea.cli;

import java.util.Date;

public class Customer {
    private int customerId;
    private String name;
    private String address;
    private String phone;


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public Customer(int customerId, String name, String address, String phone) {
        this.setCustomerId(customerId);
        this.setName(name);
        this.setAddress(address);
        this.setPhone(phone);
    }




}
