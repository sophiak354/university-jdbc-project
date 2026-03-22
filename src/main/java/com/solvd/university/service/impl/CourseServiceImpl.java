package com.solvd.university.service.impl;

import com.solvd.university.dao.CourseDao;
import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Course;
import com.solvd.university.service.CourseService;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    private final CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public Course getById(int id) {
        if (id <= 0) {
            throw new ServiceException("Course id must be greater than 0.");
        }

        return courseDao.findById(id)
                .orElseThrow(() -> new ServiceException("Course not found with id: " + id));
    }

    @Override
    public List<Course> getAll() {
        return courseDao.findAll();
    }
}