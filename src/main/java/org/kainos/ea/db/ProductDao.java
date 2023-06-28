package org.kainos.ea.db;

import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    public List<Product> getAllProducts() throws SQLException {
        Connection c = DatabaseConnector.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT ProductID, Name, Description, Price FROM `Products`;");

        List<Product> productList = new ArrayList<>();

        while (rs.next()) {
            Product product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
            );
            productList.add(product);
        }
        return productList;
    }

    public Product getProductById(int id) throws SQLException {
        Connection c = DatabaseConnector.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT ProductID, Name, Description, Price " +
                "FROM `Products`" +
                "WHERE ProductID = " + id +
                ";");

        while (rs.next()) {
            return new Product(
                    rs.getInt("ProductID"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
            );
        }
        return null;
    }

    public int createProduct(ProductRequest product) throws SQLException {
        Connection c = DatabaseConnector.getConnection();

        PreparedStatement st = c.prepareStatement("INSERT INTO Products(Name, Description, Price) " +
                "VALUES (?,?,?)" +
                ";", Statement.RETURN_GENERATED_KEYS);

        st.setString(1, product.getName());
        st.setString(2, product.getDescription());
        st.setDouble(3, product.getPrice());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }


    public void updateProduct(int id, ProductRequest product) throws SQLException {
        Connection c = DatabaseConnector.getConnection();

        PreparedStatement st = c.prepareStatement("UPDATE Products SET Name = ?, Description = ?, Price = ? " +
                "WHERE ProductID = ?" +
                ";");

        st.setString(1, product.getName());
        st.setString(2, product.getDescription());
        st.setDouble(3, product.getPrice());
        st.setInt(4, id);

        st.executeUpdate();
    }

    public void deleteProduct(int id) throws SQLException {
        Connection c = DatabaseConnector.getConnection();

        PreparedStatement st = c.prepareStatement("DELETE FROM  Products " +
                "WHERE ProductID = ?" +
                ";");

        st.setInt(1, id);

        st.executeUpdate();
    }
}
