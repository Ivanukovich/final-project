package by.ivanukovich.bicyclerental.controller.command.impl.common;

import by.ivanukovich.bicyclerental.controller.command.Command;
import by.ivanukovich.bicyclerental.controller.command.Literal;
import by.ivanukovich.bicyclerental.controller.command.PagePath;
import by.ivanukovich.bicyclerental.controller.command.Router;
import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.entity.User;
import by.ivanukovich.bicyclerental.model.service.UserService;
import by.ivanukovich.bicyclerental.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistrationCommand implements Command {
    UserService userService = new UserServiceImpl();
    @Override
    public Router execute(HttpServletRequest request) throws DaoException {
        Map<String, String> formData = new HashMap<>();
        Router router;
        formData.put(Literal.USER_FIRST_NAME, request.getParameter(Literal.USER_FIRST_NAME));
        formData.put(Literal.USER_LAST_NAME, request.getParameter(Literal.USER_LAST_NAME));
        formData.put(Literal.USER_LOGIN, request.getParameter(Literal.USER_LOGIN));
        formData.put(Literal.USER_PASSWORD, request.getParameter(Literal.USER_PASSWORD));
        formData.put(Literal.USER_EMAIL, request.getParameter(Literal.USER_EMAIL));
        formData.put(Literal.USER_ROLE, request.getParameter(Literal.USER_ROLE));
        formData.put(Literal.USER_STATUS, request.getParameter(Literal.USER_STATUS));
        if (userService.validateRegistrationData(formData)){
            return new Router(PagePath.USER_PAGE, Router.RouterType.REDIRECT);
        }
        else {
            router = new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
        }
        return null;
    }
}
