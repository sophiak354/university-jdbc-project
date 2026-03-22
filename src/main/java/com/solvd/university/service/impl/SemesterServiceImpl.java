package com.solvd.university.service.impl;

import com.solvd.university.dao.SemesterDao;
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

        return semesterDao.findById(id)
                .orElseThrow(() -> new ServiceException("Semester not found with id: " + id));
    }

    @Override
    public List<Semester> getAll() {
        return semesterDao.findAll();
    }
}