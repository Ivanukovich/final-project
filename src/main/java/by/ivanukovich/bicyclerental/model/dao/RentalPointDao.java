package by.ivanukovich.bicyclerental.model.dao;

import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.entity.Bicycle;

import java.util.List;

public interface RentalPointDao {
    List<Bicycle> findAllRentalPoints() throws DaoException;
    boolean addBicycle(long bicycle_id, long rental_point_id) throws DaoException;

}
