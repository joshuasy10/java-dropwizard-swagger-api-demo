package org.kainos.ea.cli;

import java.sql.Time;
import java.util.Date;

public class Order implements Comparable<Order> {
    private int orderId;
    private int customerId;
    private Date orderDate;


    public Order(int orderId, int customerId, Date orderDate) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setOrderDate(orderDate);
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public int compareTo(Order o) {
        if(this.getOrderDate() == o.getOrderDate()) return 0;
        return this.getOrderDate().before(o.getOrderDate()) ? 1 : -1;
    }

//    @Override
//    public String toString() {
//        return "Product Name: " + this.getName() +", Product Price: £"+this.getPrice();
//    }


}
