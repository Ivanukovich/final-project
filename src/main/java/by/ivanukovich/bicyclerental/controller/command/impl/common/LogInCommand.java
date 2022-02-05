package by.ivanukovich.bicyclerental.controller.command.impl.common;

import by.ivanukovich.bicyclerental.controller.command.Command;
import by.ivanukovich.bicyclerental.controller.command.Literal;
import by.ivanukovich.bicyclerental.controller.command.Router;
import by.ivanukovich.bicyclerental.exception.ServiceException;
import by.ivanukovich.bicyclerental.model.entity.User;
import by.ivanukovich.bicyclerental.model.service.UserService;
import by.ivanukovich.bicyclerental.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LogInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(Literal.USER_LOGIN);
        String password = request.getParameter(Literal.USER_PASSWORD);
        try {
            Optional<User> optionalUser = userService.signIn(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(Literal.USER_ID, user.getUserId());
                session.setAttribute(Literal.USER_FIRST_NAME, user.getLastName());
                session.setAttribute(Literal.USER_LAST_NAME, user.getLastName());
                session.setAttribute(Literal.USER_LOGIN, user.getLogin());
                session.setAttribute(Literal.USER_PASSWORD, user.getLogin());
                session.setAttribute(Literal.USER_EMAIL, user.getLogin());
                session.setAttribute(Literal.USER_ROLE, user.getRole());
                session.setAttribute(Literal.USER_STATUS, user.getEmail());
                switch (user.getRole()){
                    case ADMINISTRATOR:{

                    }
                    case USER:{
                        if(user.isBlocked()){

                        }
                        else {

                        }
                    }
                }
            }
            else {

            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }
}
