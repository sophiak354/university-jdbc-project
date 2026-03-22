package com.solvd.university.dao;

import com.solvd.university.model.Enrollment;

import java.util.List;

public interface EnrollmentDao {
    void save(Enrollment enrollment);
    List<Enrollment> findByStudentId(int studentId);
    boolean existsByStudentIdAndOfferingId(int studentId, int offeringId);
}
