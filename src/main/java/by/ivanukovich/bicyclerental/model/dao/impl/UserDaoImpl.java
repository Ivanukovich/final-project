package by.ivanukovich.bicyclerental.model.dao.impl;

import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.dao.UserDao;
import by.ivanukovich.bicyclerental.model.entity.User;
import by.ivanukovich.bicyclerental.model.entity.UserRole;
import by.ivanukovich.bicyclerental.model.entity.UserStatus;
import by.ivanukovich.bicyclerental.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.relation.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_USERS_QUERY =
            "SELECT user_id, first_name, last_name, login, password, email, role, status, is_blocked " +
            "FROM user " +
            "JOIN user_role ON user.role_id = user_role.role_id " +
            "JOIN user_status ON user.status_id = user_status.status_id;";

    private static final String SELECT_ALL_ACTIVE_USERS_QUERY =
            "SELECT user_id, first_name, last_name, login, password, email, role, status, is_blocked " +
            "FROM user " +
            "JOIN user_role ON user.role_id = user_role.role_id " +
            "JOIN user_status ON user.status_id = user_status.status_id " +
            "WHERE user_status.status = \"active\";";

    private static final String SELECT_BY_USER_ID_QUERY =
            "SELECT user_id, first_name, last_name, login, password, email, role, status, is_blocked " +
            "FROM user " +
            "JOIN user_role ON user.role_id = user_role.role_id " +
            "JOIN user_status ON user.status_id = user_status.status_id " +
            "WHERE user_id = ?;";

    private static final String SELECT_BY_LOGIN_QUERY =
            "SELECT user_id, first_name, last_name, login, password, email, role, status, is_blocked " +
            "FROM user " +
            "JOIN user_role ON user.role_id = user_role.role_id " +
            "JOIN user_status ON user.status_id = user_status.status_id " +
            "WHERE login = ?;";

    private static final String SELECT_BY_EMAIL_QUERY =
            "SELECT user_id, first_name, last_name, login, password, email, role, status, is_blocked " +
            "FROM user " +
            "JOIN user_role ON user.role_id = user_role.role_id " +
            "JOIN user_status ON user.status_id = user_status.status_id " +
            "WHERE email = ?;";

    private static final String UPDATE_PASSWORD_QUERY =
            "UPDATE user " +
            "SET password = ? " +
            "WHERE user_id = ?;";

    private static final String UPDATE_USER_STATUS_QUERY =
            "UPDATE user " +
            "SET status_id = (SELECT status_id FROM user_status WHERE status = ?) " +
            "WHERE user_id = ?;";

    private static final String UPDATE_QUERY =
            "UPDATE user " +
            "SET first_name = ?, last_name = ?, login = ?, password = ?, email = ?, is_blocked = ?, " +
            "status_id = (SELECT status_id FROM user_status WHERE status = ?) " +
            "role_id = (SELECT role_id FROM user_role WHERE role = ?) " +
            "WHERE user_id = ?;";


    private static UserDaoImpl instance;

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public List<User> findAllUsers() throws DaoException {
        List<User> userList = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                userList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"List: " + userList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public List<User> findAllActiveUsers() throws DaoException {
        List<User> userList = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_ACTIVE_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                userList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"List: " + userList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public User findByUserId(Long id) throws DaoException {
        User foundUser = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundUser = extract(resultSet);
                System.out.println(foundUser);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        logger.log(Level.DEBUG, "Found user: {}", foundUser);
        return foundUser;
    }

    @Override
    public User findByLogin(String login) throws DaoException {
        User foundUser = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN_QUERY);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundUser = extract(resultSet);
                System.out.println(foundUser);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        logger.log(Level.DEBUG, "Found user: {}", foundUser);
        return foundUser;
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        User foundUser = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL_QUERY);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundUser = extract(resultSet);
                System.out.println(foundUser);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        logger.log(Level.DEBUG, "Found user: {}", foundUser);
        return foundUser;
    }

    @Override
    public boolean update(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getEmail());
            statement.setBoolean(6, user.isBlocked());
            statement.setString(7, user.getUserStatus().getstatusName());
            statement.setString(8, user.getRole().getRoleName());
            statement.setLong(9, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        return true;
    }

    @Override
    public boolean updatePassword(Long userId, String newPassword) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_PASSWORD_QUERY);
            statement.setString(1, newPassword);
            statement.setLong(2, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean updateUserStatus(Long userId, UserStatus status) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_USER_STATUS_QUERY);
            statement.setString(1, status.getstatusName());
            statement.setLong(2, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private User extract(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .userId(resultSet.getLong("user_id"))
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .role(UserRole.valueOf(resultSet.getString("role").toUpperCase()))
                .userStatus(UserStatus.valueOf(resultSet.getString("status").toUpperCase()))
                .isBlocked(resultSet.getBoolean("is_blocked"))
                .build();
    }
}
