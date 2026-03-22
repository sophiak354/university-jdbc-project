package com.solvd.university.dao.impl;

import com.solvd.university.config.ConnectionPool;
import com.solvd.university.dao.CourseDao;
import com.solvd.university.exception.DaoException;
import com.solvd.university.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDaoImpl implements CourseDao {
    private static final String FIND_BY_ID_SQL = """
            SELECT *
            FROM Course
            WHERE course_id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT *
            FROM Course
            ORDER BY course_id
            """;

    @Override
    public Optional<Course> findById(int id) {
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
            throw new DaoException("Failed to find course by id: " + id, e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<Course> findAll() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<Course> courses = new ArrayList<>();

            while (rs.next()) {
                courses.add(mapRow(rs));
            }

            return courses;
        } catch (SQLException e) {
            throw new DaoException("Failed to find all courses.", e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    private Course mapRow(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setCourseId(rs.getInt("course_id"));
        course.setCourseCode(rs.getString("course_code"));
        course.setCourseName(rs.getString("course_name"));
        course.setCredits(rs.getInt("credits"));
        course.setCourseDescription(rs.getString("course_description"));
        course.setDepartmentId(rs.getInt("department_id"));
        return course;
    }
}