package com.solvd.university.dao;

import com.solvd.university.model.Semester;

import java.util.List;
import java.util.Optional;

public interface SemesterDao {
    Optional<Semester> findById(int id);

    List<Semester> findAll();

    void save(Semester semester);

    void update(Semester semester);

    void deleteById(int id);
}