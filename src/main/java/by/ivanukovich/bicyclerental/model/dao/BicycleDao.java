package by.ivanukovich.bicyclerental.model.dao;

import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.entity.Bicycle;
import by.ivanukovich.bicyclerental.model.entity.BicycleStatus;

import java.util.List;

public interface BicycleDao {
    List<Bicycle> findAllBicycles() throws DaoException;
    List<Bicycle> findAllActiveBicycles()  throws DaoException;
    Bicycle findByBicycleId(long id) throws DaoException;
    boolean updateBicycleStatus(long id, BicycleStatus status) throws DaoException ;
    boolean update(Bicycle bicycle) throws DaoException ;
}
