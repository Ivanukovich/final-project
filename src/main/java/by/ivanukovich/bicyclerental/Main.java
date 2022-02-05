package by.ivanukovich.bicyclerental;

import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.dao.impl.UserDaoImpl;
import by.ivanukovich.bicyclerental.model.entity.User;
import by.ivanukovich.bicyclerental.model.entity.UserStatus;
import by.ivanukovich.bicyclerental.model.pool.CustomConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, DaoException {
        //CustomConnectionPool.getInstance();
        //UserDaoImpl s = UserDaoImpl.getInstance();
        //s.findAllActiveUsers();
        //Class.forName("com.mysql.cj.jdbc.Driver");
        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bicycles", "root", "mam080171");
    }
}
