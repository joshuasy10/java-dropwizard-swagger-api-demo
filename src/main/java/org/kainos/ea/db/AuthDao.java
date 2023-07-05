package org.kainos.ea.db;

import org.apache.commons.lang3.time.DateUtils;
import org.kainos.ea.cli.Login;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class AuthDao {

        public boolean validLogin(Login login) {
            try (Connection c = DatabaseConnector.getConnection()){
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT Password FROM `Users` " +
                        "WHERE Username = '" + login.getUsername() + "'");

                while (rs.next()){
                    return rs.getString("password").equals(login.getPassword());
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
            return false;
        }

        public String generateToken(String username) throws SQLException {
            String token = UUID.randomUUID().toString();
            Date expiry = DateUtils.addHours(new Date(), 8);

            Connection c = DatabaseConnector.getConnection();

            String insertStatement = "INSERT INTO Tokens (Username, Token, Expiry) VALUES (?,?,?)";

            PreparedStatement st = c.prepareStatement(insertStatement);

            st.setString(1, username);
            st.setString(2, token);
            st.setTimestamp(3, new Timestamp(expiry.getTime()));

            st.executeUpdate();
            return token;
        }
}
