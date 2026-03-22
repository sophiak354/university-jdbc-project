package com.solvd.university.service.impl;

import com.solvd.university.dao.StudentDao;
import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Student;
import com.solvd.university.service.StudentService;

import java.util.List;


public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student getById(int id) {
        validateId(id);

        return studentDao.findById(id)
                .orElseThrow(() -> new ServiceException("Student not found with id: " + id));
    }

    @Override
    public List<Student> getAll() {
        return studentDao.findAll();
    }

    @Override
    public void create(Student student) {
        validateStudent(student);
        studentDao.save(student);
    }

    @Override
    public void update(Student student) {
        if (student == null) {
            throw new ServiceException("Student cannot be null.");
        }

        validateId(student.getStudentId());
        validateStudent(student);

        if (studentDao.findById(student.getStudentId()).isEmpty()) {
            throw new ServiceException("Cannot update. Student not found with id: " + student.getStudentId());
        }

        studentDao.update(student);
    }

    @Override
    public void deleteById(int id) {
        validateId(id);

        if (studentDao.findById(id).isEmpty()) {
            throw new ServiceException("Cannot delete. Student not found with id: " + id);
        }

        studentDao.deleteById(id);
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new ServiceException("Id must be greater than 0.");
        }
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new ServiceException("Student cannot be null.");
        }

        if (isBlank(student.getFirstName())) {
            throw new ServiceException("Student first name cannot be empty.");
        }

        if (isBlank(student.getLastName())) {
            throw new ServiceException("Student last name cannot be empty.");
        }

        if (student.getDateOfBirth() == null) {
            throw new ServiceException("Student date of birth cannot be null.");
        }

        if (isBlank(student.getEmail())) {
            throw new ServiceException("Student email cannot be empty.");
        }

        if (isBlank(student.getPhone())) {
            throw new ServiceException("Student phone cannot be empty.");
        }

        if (student.getAdmissionDate() == null) {
            throw new ServiceException("Student admission date cannot be null.");
        }

        if (isBlank(student.getStudentStatus())) {
            throw new ServiceException("Student status cannot be empty.");
        }

        if (student.getProgramId() <= 0) {
            throw new ServiceException("Program id must be greater than 0.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
