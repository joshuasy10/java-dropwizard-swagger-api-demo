package org.kainos.ea.api;

import org.kainos.ea.cli.Login;
import org.kainos.ea.db.AuthDao;

import java.sql.SQLException;

public class AuthService {
    private AuthDao  authDao = new AuthDao();

    public String login(Login login) throws Exception {
        if(authDao.validLogin(login)){
            try{
                return authDao.generateToken(login.getUsername());
            } catch (SQLException e) {
                throw new Exception("Failed to generate token");
            }
        }
        throw new Exception("Failed to login");
    }
}
