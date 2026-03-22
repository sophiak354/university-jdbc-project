package com.solvd.university.dao;

import com.solvd.university.model.CourseOffering;

import java.util.List;
import java.util.Optional;

public interface CourseOfferingDao {
    Optional<CourseOffering> findById(int id);
    List<CourseOffering> findAll();
}