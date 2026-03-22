package com.solvd.university.service;

import com.solvd.university.model.Enrollment;

import java.util.List;

public interface EnrollmentService {
    void enrollStudent(int studentId, int offeringId);
    List<Enrollment> getEnrollmentsByStudentId(int studentId);
}