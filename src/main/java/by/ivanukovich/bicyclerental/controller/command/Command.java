package by.ivanukovich.bicyclerental.controller.command;

import by.ivanukovich.bicyclerental.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws DaoException;
}
