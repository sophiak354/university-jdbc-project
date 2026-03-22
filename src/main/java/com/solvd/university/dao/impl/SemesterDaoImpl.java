package com.solvd.university.dao.impl;

import com.solvd.university.config.ConnectionPool;
import com.solvd.university.dao.SemesterDao;
import com.solvd.university.exception.DaoException;
import com.solvd.university.model.Semester;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SemesterDaoImpl implements SemesterDao {
    private static final String FIND_BY_ID_SQL = """
            SELECT *
            FROM Semester
            WHERE semester_id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT *
            FROM Semester
            ORDER BY semester_id
            """;

    @Override
    public Optional<Semester> findById(int id) {
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
            throw new DaoException("Failed to find semester by id: " + id, e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<Semester> findAll() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<Semester> semesters = new ArrayList<>();

            while (rs.next()) {
                semesters.add(mapRow(rs));
            }

            return semesters;
        } catch (SQLException e) {
            throw new DaoException("Failed to find all semesters.", e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    private Semester mapRow(ResultSet rs) throws SQLException {
        Semester semester = new Semester();
        semester.setSemesterId(rs.getInt("semester_id"));
        semester.setSemesterName(rs.getString("semester_name"));
        semester.setStartDate(rs.getDate("start_date").toLocalDate());
        semester.setEndDate(rs.getDate("end_date").toLocalDate());
        semester.setAcademicYear(rs.getString("academic_year"));
        return semester;
    }
}