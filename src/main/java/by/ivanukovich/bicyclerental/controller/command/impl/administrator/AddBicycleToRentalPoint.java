package by.ivanukovich.bicyclerental.controller.command.impl.administrator;

import by.ivanukovich.bicyclerental.controller.command.Command;
import by.ivanukovich.bicyclerental.controller.command.Router;
import by.ivanukovich.bicyclerental.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public class AddBicycleToRentalPoint implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws DaoException {
        return null;
    }
}
