package com.solvd.university.dao;

import com.solvd.university.model.Instructor;

import java.util.List;
import java.util.Optional;

public interface InstructorDao {
    Optional<Instructor> findById(int id);

    List<Instructor> findAll();

    void save(Instructor instructor);

    void update(Instructor instructor);

    void deleteById(int id);
}
