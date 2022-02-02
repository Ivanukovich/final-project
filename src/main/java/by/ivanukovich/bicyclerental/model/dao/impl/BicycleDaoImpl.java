package by.ivanukovich.bicyclerental.model.dao.impl;

import by.ivanukovich.bicyclerental.exception.DaoException;
import by.ivanukovich.bicyclerental.model.dao.BicycleDao;
import by.ivanukovich.bicyclerental.model.entity.*;
import by.ivanukovich.bicyclerental.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.relation.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BicycleDaoImpl implements BicycleDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_BICYCLES_QUERY =
            "SELECT bicycle_id, model, status, rental_point_name " +
            "FROM bicycle " +
            "JOIN bicycle_model ON bicycle.model_id = bicycle_model.model_id " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id " +
            "JOIN rental_point ON bicycle.rental_point_id = rental_point.rental_point_id;";

    private static final String SELECT_ALL_ACTIVE_BICYCLES_QUERY =
            "SELECT bicycle_id, model, status, rental_point_name " +
            "FROM bicycle " +
            "JOIN bicycle_model ON bicycle.model_id = bicycle_model.model_id " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id " +
            "JOIN rental_point ON bicycle.rental_point_id = rental_point.rental_point_id " +
            "WHERE bicycle_status.status = \"active\";";

    private static final String SELECT_BY_BICYCLE_ID_QUERY =
            "SELECT bicycle_id, model, status, rental_point_name " +
            "FROM bicycle " +
            "JOIN bicycle_model ON bicycle.model_id = bicycle_model.model_id " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id " +
            "JOIN rental_point ON bicycle.rental_point_id = rental_point.rental_point_id " +
            "WHERE bicycle_id = ?;";

    private static final String UPDATE_BICYCLE_STATUS_QUERY =
            "UPDATE bicycle " +
            "SET status_id = (SELECT status_id FROM bicycle_status WHERE status = ?) " +
            "WHERE bicycle_id = ?;";

    private static final String UPDATE_QUERY =
            "UPDATE bicycle " +
            "SET model_id = (SELECT model_id FROM bicycle_model WHERE model = ?) " +
            "status_id = (SELECT status_id FROM bicycle_status WHERE status = ?) " +
            "rental_point_id = (SELECT rental_point_id FROM rental_point WHERE rental_point_name = ?) " +
            "WHERE bicycle_id = ?;";

    private static UserDaoImpl instance;

    @Override
    public List<Bicycle> findAllBicycles() throws DaoException {
        List<Bicycle> bicycleList = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_BICYCLES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bicycleList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"List: " + bicycleList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return bicycleList;
    }

    @Override
    public List<Bicycle> findAllActiveBicycles() throws DaoException {
        List<Bicycle> bicycleList = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_ACTIVE_BICYCLES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bicycleList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"List: " + bicycleList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return bicycleList;
    }

    @Override
    public Bicycle findByBicycleId(long id) throws DaoException {
        Bicycle foundBicycle = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_BICYCLE_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //foundUser = extract(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        logger.log(Level.DEBUG, "Found user: {}", foundBicycle);
        return foundBicycle;
    }

    @Override
    public boolean updateBicycleStatus(long bicycleId, BicycleStatus status) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_BICYCLE_STATUS_QUERY);
            statement.setString(1, status.getStatusName());
            statement.setLong(2, bicycleId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Bicycle bicycle) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, bicycle.getModel().getModelName());
            statement.setString(2, bicycle.getStatus().getStatusName());
            statement.setString(3, bicycle.getLocation());
            statement.setLong(4, bicycle.getBicycleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        return true;
    }

    private Bicycle extract(ResultSet resultSet) throws SQLException {
        return new Bicycle.Builder()
                .bicycleId(resultSet.getLong("user_id"))
                .model(BicycleModel.valueOf(resultSet.getString("model")))
                .status(BicycleStatus.valueOf(resultSet.getString("status")))
                .location(String.valueOf(resultSet.getString("rental_point_name")))
                .build();
    }
}
