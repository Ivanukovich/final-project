package by.ivanukovich.bicyclerental.model.dao;

import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.entity.User;
import by.ivanukovich.bicyclerental.model.entity.UserStatus;

import java.util.List;

public interface UserDao {
    User findByUserId(Long id) throws DaoException;
    User findByLogin(String login) throws DaoException;
    User findByEmail(String email) throws DaoException;
    List<User> findAllUsers() throws DaoException;
    List<User> findAllActiveUsers()  throws DaoException;
    boolean update(User user) throws DaoException;
    boolean updatePassword(Long id, String newPassword) throws DaoException;
    boolean updateUserStatus(Long id, UserStatus status) throws DaoException;
}
