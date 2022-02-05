package by.ivanukovich.bicyclerental.model.service;

import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.exception.ServiceException;
import by.ivanukovich.bicyclerental.model.entity.User;
import by.ivanukovich.bicyclerental.model.entity.UserStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> signIn(String login, String password) throws ServiceException;
    boolean validateRegistrationData(Map<String, String> formData) throws DaoException;
    List<User> findAll() throws ServiceException;
    User findByUserId(Long id) throws DaoException, ServiceException;
    User findByLogin(String login) throws DaoException;
    User findByEmail(String email) throws DaoException;
    List<User> findAllActiveUsers()  throws DaoException;
    boolean update(User user) throws DaoException;
    boolean updatePassword(Long id, String newPassword) throws DaoException;
    boolean updateUserStatus(Long id, UserStatus status) throws DaoException;
}
