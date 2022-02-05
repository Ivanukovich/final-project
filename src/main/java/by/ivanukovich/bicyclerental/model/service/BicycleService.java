package by.ivanukovich.bicyclerental.model.service;

import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.dao.BicycleDao;
import by.ivanukovich.bicyclerental.model.dao.impl.BicycleDaoImpl;
import by.ivanukovich.bicyclerental.model.entity.Bicycle;

public interface BicycleService {
    boolean addBicycletoDataBase(Bicycle bicycle) throws DaoException;
}
