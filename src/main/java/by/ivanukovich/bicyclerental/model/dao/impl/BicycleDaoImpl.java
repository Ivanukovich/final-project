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
            "SELECT bicycle_id, model, status " +
            "FROM bicycle " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id ";

    private static final String SELECT_ALL_ACTIVE_BICYCLES_QUERY =
            "SELECT bicycle_id, model, status " +
            "FROM bicycle " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id " +
            "WHERE bicycle_status.status = \"active\";";

    private static final String SELECT_BY_BICYCLE_ID_QUERY =
            "SELECT bicycle_id, model, status " +
            "FROM bicycle " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id " +
            "WHERE bicycle_id = ?;";

    private static final String UPDATE_BICYCLE_STATUS_QUERY =
            "UPDATE bicycle " +
            "SET status_id = (SELECT status_id FROM bicycle_status WHERE status = ?) " +
            "WHERE bicycle_id = ?;";

    private static final String UPDATE_QUERY =
            "UPDATE bicycle " +
            "SET model_id = ? " +
            "status_id = (SELECT status_id FROM bicycle_status WHERE status = ?) " +
            "WHERE bicycle_id = ?;";

    private static final String INSERT_BICYCLE_QUERY =
            "INSERT INTO bicycle (bicycle_id, model, status_id) " +
            "VALUES (?, ?, (SELECT status_id FROM bicycle_status WHERE status = ?))";

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
            statement.setString(1, bicycle.getModel());
            statement.setString(2, bicycle.getStatus().getStatusName());
            statement.setLong(3, bicycle.getBicycleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        return true;
    }

    @Override
    public boolean create(Bicycle bicycle) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_BICYCLE_QUERY);
            statement.setLong(1, bicycle.getBicycleId());
            statement.setString(2, bicycle.getModel());
            statement.setString(3, bicycle.getStatus().getStatusName());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
    }

    private Bicycle extract(ResultSet resultSet) throws SQLException {
        return new Bicycle.Builder()
                .bicycleId(resultSet.getLong("user_id"))
                .model(resultSet.getString("model"))
                .status(BicycleStatus.valueOf(resultSet.getString("status")))
                .build();
    }
}
