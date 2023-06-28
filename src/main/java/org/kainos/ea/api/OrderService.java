package org.kainos.ea.api;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.Product;
import org.kainos.ea.client.*;
import org.kainos.ea.core.OrderValidator;
import org.kainos.ea.core.ProductValidator;
import org.kainos.ea.db.OrderDao;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {
    private OrderDao orderDao = new OrderDao();
    private OrderValidator orderValidator= new OrderValidator();

    private int key = -1;
    private int value = 0;
    public List<Order> getAllOrders() throws FailedToGetOrdersException {

        List<Order> orderList = null;
        try {
            orderList = orderDao.getAllOrders();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetOrdersException();
        }
        Collections.sort(orderList);

        System.out.println(Collections.max(orderList).getOrderDate());
        System.out.println(orderList.stream().filter(p -> p.getCustomerId() == 1).count());


        HashMap<Integer, Integer> cust_order_count1 = new HashMap<>();
        long start3 = System.nanoTime();
        for(Order o : orderList) {
            cust_order_count1.put(o.getCustomerId(),
                    cust_order_count1.get(o.getCustomerId()) == null ? 0 : cust_order_count1.get(o.getCustomerId()) + 1);
            if(cust_order_count1.get(o.getCustomerId()) > this.value){
                this.setKey(o.getCustomerId());
                this.setValue(cust_order_count1.get(o.getCustomerId()));
            }
        }
        System.out.println("CUST: " + this.key);
        long end3 = System.nanoTime();
        double fastest = (end3-start3);
        System.out.println("Time trad foreach (Josh): "+ (end3-start3));

        HashMap<Integer, Integer> cust_order_count2 = new HashMap<>();
        long start2 = System.nanoTime();
        orderList.forEach(o -> {
            cust_order_count2.put(o.getCustomerId(),
                    cust_order_count2.get(o.getCustomerId()) == null ? 0 : cust_order_count2.get(o.getCustomerId()) + 1);
            if(cust_order_count2.get(o.getCustomerId()) > this.value){
                this.setKey(o.getCustomerId());
                this.setValue(cust_order_count2.get(o.getCustomerId()));
            }
        });
        System.out.println("CUST: " + this.key);
        long end2 = System.nanoTime();
        System.out.println("Time stream forEach (Josh): "+ (end2-start2) + " : " + (end2-start2)/fastest);



        long start1 = System.nanoTime();
        Map.Entry<Integer, List<Order>> result = orderList.stream()
                .collect(Collectors.groupingBy(Order::getCustomerId))
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(entry -> entry.getValue().size()))
                .get();
        System.out.println("result: " + result.getKey() + " ," + result.getValue().size());
        long end1 = System.nanoTime();
        System.out.println("Time stream magic (Michael): "+ (end1-start1) + " : " + (end1-start1)/fastest);

        long start4 = System.nanoTime();
        Map<Integer, Long> rMap = orderList.stream()
                .collect(Collectors.groupingBy(Order::getCustomerId, Collectors.counting()));
        System.out.println("result: " + Collections.max(rMap.entrySet(), Map.Entry.comparingByValue()).getKey() + " ," + result.getValue().size());
        long end4 = System.nanoTime();
        System.out.println("Time stream simple (Shaun): "+ (end4-start4) + " : " + (end4-start4)/fastest);


        return orderList;
    }

    public void setKey(int key){
        this.key = key;
    }

    public void setValue(int value){
        this.value = value;
    }




    public Order getOrderById(int id) throws FailedToGetOrdersException, OrderDoesNotExistException {

        try {
            Order order = orderDao.getOrderById(id);

            if(order == null){
                throw new OrderDoesNotExistException();
            }
            return order;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetOrdersException();
        }
    }

    public int createOrder(OrderRequest order)  throws FailedToCreateOrderException, InvalidOrderException {
        try {
            String validation = orderValidator.isValidOrder(order);
            if(validation != null){
                throw new InvalidOrderException(validation);
            }

            int id = orderDao.createOrder(order);

            if(id == -1){
                throw new FailedToCreateOrderException();
            }
            return id;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateOrderException();
        }
    }


    public void deleteOrder(int id) throws OrderDoesNotExistException, FailedToDeleteOrderException{
        try {
            Order orderToUpdate = orderDao.getOrderById(id);

            if(orderToUpdate == null){
                throw new OrderDoesNotExistException();
            }

            orderDao.deleteOrder(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToDeleteOrderException();
        }
    }
}
