package com.solvd.university.dao.impl;

import com.solvd.university.config.ConnectionPool;
import com.solvd.university.dao.StudentDao;
import com.solvd.university.exception.DaoException;
import com.solvd.university.model.Student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao {
    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM Student WHERE student_id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT * FROM Student;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO Student
            (first_name, last_name, date_of_birth, gender, email, phone, admission_date, student_status, program_id)
            VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE Student
            SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, email = ?, phone = ?,
                admission_date = ?, student_status = ?, program_id = ?
            WHERE student_id = ?
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM Student
            WHERE student_id = ?
            """;

    @Override
    public Optional<Student> findById(int id) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("Failed to find student by id: " + id, e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<Student> findAll() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<Student> students = new ArrayList<>();

            while (rs.next()) {
                students.add(mapRow(rs));
            }

            return students;
        } catch (SQLException e) {
            throw new DaoException("Failed to find all students.", e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void save(Student student) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
            fillStatementForSaveOrUpdate(statement, student);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to save student: " + student, e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void update(Student student) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            fillStatementForSaveOrUpdate(statement, student);
            statement.setInt(10, student.getStudentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update student: " + student, e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteById(int id) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to delete student by id: " + id, e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
        student.setGender(rs.getString("gender"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setAdmissionDate(rs.getDate("admission_date").toLocalDate());
        student.setStudentStatus(rs.getString("student_status"));
        student.setProgramId(rs.getInt("program_id"));
        return student;
    }

    private void fillStatementForSaveOrUpdate(PreparedStatement statement, Student student) throws SQLException {
        statement.setString(1, student.getFirstName());
        statement.setString(2, student.getLastName());
        statement.setDate(3, Date.valueOf(student.getDateOfBirth()));
        statement.setString(4, student.getGender());
        statement.setString(5, student.getEmail());
        statement.setString(6, student.getPhone());
        statement.setDate(7, Date.valueOf(student.getAdmissionDate()));
        statement.setString(8, student.getStudentStatus());
        statement.setInt(9, student.getProgramId());
    }
}
