package by.ivanukovich.bicyclerental.model.service.impl;

import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.dao.BicycleDao;
import by.ivanukovich.bicyclerental.model.dao.impl.BicycleDaoImpl;
import by.ivanukovich.bicyclerental.model.entity.Bicycle;
import by.ivanukovich.bicyclerental.model.service.BicycleService;

public class BicycleServiceImpl implements BicycleService {
    BicycleDao bicycleDao = new BicycleDaoImpl();

    @Override
    public boolean addBicycletoDataBase(Bicycle bicycle) throws DaoException {
        boolean result = bicycleDao.create(bicycle);
        return result;
    }
}
