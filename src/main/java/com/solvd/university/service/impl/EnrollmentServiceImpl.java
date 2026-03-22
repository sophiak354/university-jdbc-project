package com.solvd.university.service.impl;

import com.solvd.university.dao.CourseOfferingDao;
import com.solvd.university.dao.EnrollmentDao;
import com.solvd.university.dao.StudentDao;
import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Enrollment;
import com.solvd.university.service.EnrollmentService;

import java.time.LocalDate;
import java.util.List;

public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentDao enrollmentDao;
    private final StudentDao studentDao;
    private final CourseOfferingDao courseOfferingDao;

    public EnrollmentServiceImpl(
            EnrollmentDao enrollmentDao,
            StudentDao studentDao,
            CourseOfferingDao courseOfferingDao
    ) {
        this.enrollmentDao = enrollmentDao;
        this.studentDao = studentDao;
        this.courseOfferingDao = courseOfferingDao;
    }

    @Override
    public void enrollStudent(int studentId, int offeringId) {
        validateId(studentId, "Student id");
        validateId(offeringId, "Offering id");

        if (studentDao.findById(studentId).isEmpty()) {
            throw new ServiceException("Student not found with id: " + studentId);
        }

        if (courseOfferingDao.findById(offeringId).isEmpty()) {
            throw new ServiceException("Course offering not found with id: " + offeringId);
        }

        if (enrollmentDao.existsByStudentIdAndOfferingId(studentId, offeringId)) {
            throw new ServiceException("Student is already enrolled in this course offering.");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setEnrollmentStatus("ACTIVE");
        enrollment.setStudentId(studentId);
        enrollment.setOfferingId(offeringId);

        enrollmentDao.save(enrollment);
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        validateId(studentId, "Student id");

        if (studentDao.findById(studentId).isEmpty()) {
            throw new ServiceException("Student not found with id: " + studentId);
        }

        return enrollmentDao.findByStudentId(studentId);
    }

    private void validateId(int id, String fieldName) {
        if (id <= 0) {
            throw new ServiceException(fieldName + " must be greater than 0.");
        }
    }
}