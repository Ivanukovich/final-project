package by.ivanukovich.bicyclerental.controller.command.impl.administrator;

import by.ivanukovich.bicyclerental.controller.command.Command;
import by.ivanukovich.bicyclerental.controller.command.Literal;
import by.ivanukovich.bicyclerental.controller.command.PagePath;
import by.ivanukovich.bicyclerental.controller.command.Router;
import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.entity.Bicycle;
import by.ivanukovich.bicyclerental.model.entity.BicycleStatus;
import by.ivanukovich.bicyclerental.model.service.BicycleService;
import by.ivanukovich.bicyclerental.model.service.impl.BicycleServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AddNewBicycleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final BicycleService bicycleService = new BicycleServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws DaoException {
        String model = request.getParameter(Literal.BICYCLE_MODEL);
        String status = request.getParameter(Literal.BICYCLE_STATUS);
        Map<String,String> formData = new HashMap<>();
        formData.put(Literal.BICYCLE_MODEL, model);
        formData.put(Literal.BICYCLE_STATUS, status);
        Bicycle bicycle = new Bicycle.Builder()
                .bicycleId(1)
                .model(model)
                .status(BicycleStatus.valueOf(status))
                .build();
        bicycleService.addBicycletoDataBase(bicycle);
        return new Router(PagePath.ADMIN_PAGE, Router.RouterType.REDIRECT);
    }
}
