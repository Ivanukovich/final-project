package by.ivanukovich.bicyclerental.controller.command.impl.common;

import by.ivanukovich.bicyclerental.controller.command.Command;
import by.ivanukovich.bicyclerental.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return null;
    }
}
