package org.kainos.ea.db;

import jdk.jfr.Description;
import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderDao {


    public List<Order> getAllOrders() throws SQLException {
        Connection c = DatabaseConnector.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT Customers.`Name`, OrderID, Orders.`CustomerID`, PlacedAt " +
                "FROM `Orders` " +
                "LEFT JOIN Customers ON Orders.CustomerID = Customers.CustomerID;");

        List<Order> orderList = new ArrayList<>();

        while (rs.next()) {
            Order order = new Order(
                    rs.getInt("OrderID"),
                    rs.getInt("CustomerID"),
                    rs.getDate("PlacedAt"),
                    rs.getString("Name")
                    );
            orderList.add(order);
        }
        return orderList;

    }

    public Order getOrderById(int id) throws SQLException {
        Connection c = DatabaseConnector.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT Price, Customers.`Name`, Products.`Name`, Products.ProductID, Quantity, Products.Description, Orders.OrderID, Orders.CustomerID, PlacedAt " +
                "FROM OrderProducts " +
                "JOIN Orders ON OrderProducts.OrderId = Orders.OrderID " +
                "JOIN Products ON OrderProducts.ProductId = Products.ProductID " +
                "JOIN Customers ON Orders.CustomerID = Customers.CustomerID " +
                "  WHERE Orders.OrderID = " + id +
                ";");


        if(rs.next()){
            Order o = new Order(
                    rs.getInt("Orders.OrderID"),
                    rs.getInt("Orders.CustomerID"),
                    rs.getDate("PlacedAt"),
                    rs.getString("Customers.Name")
            );

            Product pTemp = new Product(
                    rs.getInt("ProductId"),
                    rs.getString("Products.Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
            );
            pTemp.setQuantity(rs.getInt("Quantity"));
            o.addProduct(pTemp);

            while (rs.next()) {
                pTemp = new Product(
                        rs.getInt("ProductId"),
                        rs.getString("Products.Name"),
                        rs.getString("Description"),
                        rs.getDouble("Price")
                );
                pTemp.setQuantity(rs.getInt("Quantity"));
                o.addProduct(pTemp);
            }
            return o;
        }
        return null;
    }

    public int createOrder(OrderRequest order) throws SQLException{
        Connection c = DatabaseConnector.getConnection();

        PreparedStatement st = c.prepareStatement("INSERT INTO Orders(CustomerID) " +
                "VALUES (?)" +
                ";", Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, order.getCustomerId());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }


//    public void updateProduct(int id, OrderRequest order) throws SQLException {
//        Connection c = DatabaseConnector.getConnection();
//
//        PreparedStatement st = c.prepareStatement("UPDATE Orders SET Name = ?, Description = ?, Price = ? " +
//                "WHERE OrderID = ?" +
//                ";");
//
//        st.setString(1, order.getName());
//        st.setString(2, order.getDescription());
//        st.setDouble(3, order.getPrice());
//        st.setInt(4, id);
//
//        st.executeUpdate();
//    }


    public void deleteOrder(int id) throws SQLException {
        Connection c = DatabaseConnector.getConnection();

        PreparedStatement st = c.prepareStatement("DELETE FROM  Orders " +
                "WHERE OrderID = ?" +
                ";");

        st.setInt(1, id);

        st.executeUpdate();
    }
}
