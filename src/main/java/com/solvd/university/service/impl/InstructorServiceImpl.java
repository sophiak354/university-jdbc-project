package com.solvd.university.service.impl;

import com.solvd.university.dao.InstructorDao;
import com.solvd.university.dao.impl.InstructorDaoImpl;
import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Instructor;
import com.solvd.university.service.InstructorService;

import java.util.List;

public class InstructorServiceImpl implements InstructorService {
    private final InstructorDao instructorDao;

    public InstructorServiceImpl() {
        this.instructorDao = new InstructorDaoImpl();
    }

    public InstructorServiceImpl(InstructorDao instructorDao) {
        this.instructorDao = instructorDao;
    }

    @Override
    public Instructor getById(int id) {
        return instructorDao.findById(id)
                .orElseThrow(() -> new ServiceException("Instructor with id " + id + " not found."));
    }

    @Override
    public List<Instructor> getAll() {
        return instructorDao.findAll();
    }

    @Override
    public void create(Instructor instructor) {
        validateInstructor(instructor);

        boolean exists = instructorDao.findById(instructor.getInstructorId()).isPresent();
        if (exists) {
            throw new ServiceException("Instructor with id " + instructor.getInstructorId() + " already exists.");
        }

        instructorDao.save(instructor);
    }

    @Override
    public void update(Instructor instructor) {
        validateInstructor(instructor);

        boolean exists = instructorDao.findById(instructor.getInstructorId()).isPresent();
        if (!exists) {
            throw new ServiceException("Instructor with id " + instructor.getInstructorId() + " not found.");
        }

        instructorDao.update(instructor);
    }

    @Override
    public void deleteById(int id) {
        boolean exists = instructorDao.findById(id).isPresent();
        if (!exists) {
            throw new ServiceException("Instructor with id " + id + " not found.");
        }

        instructorDao.deleteById(id);
    }

    private void validateInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new ServiceException("Instructor cannot be null.");
        }

        if (instructor.getInstructorId() <= 0) {
            throw new ServiceException("Instructor id must be positive.");
        }

        if (instructor.getFirstName() == null || instructor.getFirstName().isBlank()) {
            throw new ServiceException("Instructor first name cannot be empty.");
        }

        if (instructor.getLastName() == null || instructor.getLastName().isBlank()) {
            throw new ServiceException("Instructor last name cannot be empty.");
        }

        if (instructor.getEmail() == null || instructor.getEmail().isBlank()) {
            throw new ServiceException("Instructor email cannot be empty.");
        }
    }
}
