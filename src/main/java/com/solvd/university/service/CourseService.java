package com.solvd.university.service;

import com.solvd.university.model.Course;

import java.util.List;

public interface CourseService {
    Course getById(int id);
    List<Course> getAll();
}