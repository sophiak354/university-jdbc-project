package com.solvd.university.dao.impl;

import com.solvd.university.config.ConnectionPool;
import com.solvd.university.dao.CourseOfferingDao;
import com.solvd.university.exception.DaoException;
import com.solvd.university.model.CourseOffering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseOfferingDaoImpl implements CourseOfferingDao {
    private static final String FIND_BY_ID_SQL = """
            SELECT *
            FROM Course_offering
            WHERE offering_id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT *
            FROM Course_offering
            ORDER BY offering_id
            """;

    @Override
    public Optional<CourseOffering> findById(int id) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find course offering by id: " + id, e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<CourseOffering> findAll() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<CourseOffering> offerings = new ArrayList<>();

            while (rs.next()) {
                offerings.add(mapRow(rs));
            }

            return offerings;
        } catch (SQLException e) {
            throw new DaoException("Failed to find all course offerings.", e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    private CourseOffering mapRow(ResultSet rs) throws SQLException {
        CourseOffering offering = new CourseOffering();
        offering.setOfferingId(rs.getInt("offering_id"));
        offering.setSectionNumber(rs.getString("section_number"));
        offering.setMaxStudents(rs.getInt("max_students"));
        offering.setDeliveryMode(rs.getString("delivery_mode"));
        offering.setCourseId(rs.getInt("course_id"));
        offering.setSemesterId(rs.getInt("semester_id"));
        offering.setInstructorId(rs.getInt("instructor_id"));
        return offering;
    }
}