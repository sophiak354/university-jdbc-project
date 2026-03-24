package com.solvd.university.service.impl;

import com.solvd.university.dao.SemesterDao;
import com.solvd.university.exception.DaoException;
import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Semester;
import com.solvd.university.service.SemesterService;

import java.util.List;

public class SemesterServiceImpl implements SemesterService {
    private final SemesterDao semesterDao;

    public SemesterServiceImpl(SemesterDao semesterDao) {
        this.semesterDao = semesterDao;
    }

    @Override
    public Semester getById(int id) {
        if (id <= 0) {
            throw new ServiceException("Semester id must be greater than 0.");
        }

        try {
            return semesterDao.findById(id)
                    .orElseThrow(() -> new ServiceException("Semester not found with id: " + id));
        } catch (DaoException e) {
            throw new ServiceException("Failed to get semester by id: " + id, e);
        }
    }

    @Override
    public List<Semester> getAll() {
        try {
            return semesterDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to get all semesters.", e);
        }

    }

    @Override
    public void create(Semester semester) {
        if (semester == null) {
            throw new ServiceException("Semester cannot be null.");
        }

        try {
            semesterDao.save(semester);
        } catch (DaoException e) {
            throw new ServiceException("Failed to create semester.", e);
        }
    }

    @Override
    public void update(Semester semester) {
        if (semester == null || semester.getSemesterId() <= 0) {
            throw new ServiceException("Invalid semester for update.");
        }

        try {
            semesterDao.update(semester);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update semester.", e);
        }
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) {
            throw new ServiceException("Semester id must be greater than 0.");
        }

        try {
            semesterDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete semester with id: " + id, e);
        }
    }
}