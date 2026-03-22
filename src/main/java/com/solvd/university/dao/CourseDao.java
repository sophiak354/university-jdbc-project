package com.solvd.university.dao;

import com.solvd.university.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {
    Optional<Course> findById(int id);
    List<Course> findAll();
}