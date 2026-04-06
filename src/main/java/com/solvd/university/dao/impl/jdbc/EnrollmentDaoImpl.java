package com.solvd.university.dao.impl.jdbc;

import com.solvd.university.config.ConnectionPool;
import com.solvd.university.dao.EnrollmentDao;
import com.solvd.university.exception.DaoException;
import com.solvd.university.model.Enrollment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImpl implements EnrollmentDao {
    private static final String SAVE_SQL = """
            INSERT INTO Enrollment (enrollment_date, enrollment_status, student_id, offering_id)
            VALUES (?, ?, ?, ?)
            """;

    private static final String FIND_BY_STUDENT_ID_SQL = """
            SELECT *
            FROM Enrollment
            WHERE student_id = ?
            ORDER BY enrollment_date DESC
            """;

    private static final String EXISTS_SQL = """
            SELECT *
            FROM Enrollment
            WHERE student_id = ? AND offering_id = ?
            """;

    @Override
    public void save(Enrollment enrollment) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setDate(1, Date.valueOf(enrollment.getEnrollmentDate()));
            statement.setString(2, enrollment.getEnrollmentStatus());
            statement.setInt(3, enrollment.getStudentId());
            statement.setInt(4, enrollment.getOfferingId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to save enrollment: " + enrollment, e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_STUDENT_ID_SQL)) {
            statement.setInt(1, studentId);

            try (ResultSet rs = statement.executeQuery()) {
                List<Enrollment> enrollments = new ArrayList<>();

                while (rs.next()) {
                    enrollments.add(mapRow(rs));
                }

                return enrollments;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find enrollments by student id: " + studentId, e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public boolean existsByStudentIdAndOfferingId(int studentId, int offeringId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(EXISTS_SQL)) {
            statement.setInt(1, studentId);
            statement.setInt(2, offeringId);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Failed to check enrollment existence for student id " + studentId +
                            " and offering id " + offeringId, e
            );
        } finally {
            pool.releaseConnection(connection);
        }
    }

    private Enrollment mapRow(ResultSet rs) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(rs.getInt("enrollment_id"));
        enrollment.setEnrollmentDate(rs.getDate("enrollment_date").toLocalDate());
        enrollment.setEnrollmentStatus(rs.getString("enrollment_status"));
        enrollment.setStudentId(rs.getInt("student_id"));
        enrollment.setOfferingId(rs.getInt("offering_id"));
        return enrollment;
    }
}
