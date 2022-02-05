package by.ivanukovich.bicyclerental.model.service.impl;

import by.ivanukovich.bicyclerental.controller.command.Literal;
import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.exception.ServiceException;
import by.ivanukovich.bicyclerental.model.dao.UserDao;
import by.ivanukovich.bicyclerental.model.dao.impl.UserDaoImpl;
import by.ivanukovich.bicyclerental.model.entity.User;
import by.ivanukovich.bicyclerental.model.entity.UserStatus;
import by.ivanukovich.bicyclerental.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private static final Logger logger = LogManager.getLogger();
    private final UserDao userDao = UserDaoImpl.getInstance();

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        return null;
    }

    @Override
    public boolean validateRegistrationData(Map<String, String> formData) throws DaoException {
        UserDao userDao = UserDaoImpl.getInstance();
        //String firstName = formData.get(Literal.USER_FIRST_NAME);
        //String lastName = formData.get(Literal.USER_LAST_NAME);
        String login = formData.get(Literal.USER_LOGIN);
        String password = formData.get(Literal.USER_PASSWORD);
        String email = formData.get(Literal.USER_EMAIL);
        if(userDao.findByEmail(email) != null){
            return false;
        }
        if(userDao.findByLogin(login) != null){
            return false;
        }
        return true;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        List<User> allUsers;
        try {
            allUsers = userDao.findAllUsers();
        } catch (DaoException e) {
            throw new ServiceException("findAll() - Failed to all users: ", e);
        }

        logger.log(Level.DEBUG, "All users found: {}", allUsers);
        return allUsers;
    }

    @Override
    public User findByUserId(Long id) throws DaoException, ServiceException {
        User user;
        try {
            user = userDao.findByUserId(id);
        } catch (DaoException e) {
            throw new ServiceException("findById() - Failed to find user by id " + id, e);
        }

        logger.log(Level.DEBUG, "Found user by id {}: {}", id, user);
        return user;
    }

    @Override
    public User findByLogin(String login) throws DaoException {
        return null;
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        return null;
    }

    @Override
    public List<User> findAllActiveUsers() throws DaoException {
        return null;
    }

    @Override
    public boolean update(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean updatePassword(Long id, String newPassword) throws DaoException {
        return false;
    }

    @Override
    public boolean updateUserStatus(Long id, UserStatus status) throws DaoException {
        return false;
    }
}
