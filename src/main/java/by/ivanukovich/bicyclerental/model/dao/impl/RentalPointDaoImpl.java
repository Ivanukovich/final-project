package by.ivanukovich.bicyclerental.model.dao.impl;

import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.dao.RentalPointDao;
import by.ivanukovich.bicyclerental.model.entity.Bicycle;
import by.ivanukovich.bicyclerental.model.pool.CustomConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RentalPointDaoImpl implements RentalPointDao {
    private static final String ADD_BICYCLE_QUERY =
            "INSERT INTO bicycle_rental_point (bicycle_rental_point_id, bicycle_id, rental_point_id " +
            "VALUES (?, ?, ?)";

    @Override
    public List<Bicycle> findAllRentalPoints() throws DaoException {
        return null;
    }

    @Override
    public boolean addBicycle(long bicycle_id, long rental_point_id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(ADD_BICYCLE_QUERY);
            statement.setLong(1, 1);
            statement.setLong(2, bicycle_id);
            statement.setLong(2, rental_point_id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
