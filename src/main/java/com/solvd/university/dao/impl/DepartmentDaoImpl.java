package com.solvd.university.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.university.dao.DepartmentDao;
import com.solvd.university.model.Department;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDaoImpl implements DepartmentDao {
    private static final String FILE_PATH = "src/main/resources/json/departments.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Optional<Department> findById(int id) {
        return readFromFile().stream()
                .filter(department -> department.getDepartmentId() == id)
                .findFirst();
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(readFromFile());
    }

    @Override
    public void save(Department department) {
        List<Department> departments = readFromFile();
        departments.add(department);
        writeToFile(departments);
    }

    @Override
    public void update(Department updatedDepartment) {
        List<Department> departments = readFromFile();

        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getDepartmentId() == updatedDepartment.getDepartmentId()) {
                departments.set(i, updatedDepartment);
                writeToFile(departments);
                return;
            }
        }
        throw new RuntimeException("Department with id " + updatedDepartment.getDepartmentId() + " not found.");
    }

    @Override
    public void deleteById(int id) {
        List<Department> departments = readFromFile();
        boolean removed = departments.removeIf(department -> department.getDepartmentId() == id);
        if (!removed) {
            throw new RuntimeException("Department with id " + id + " not found.");
        }
        writeToFile(departments);
    }

    private List<Department> readFromFile() {
        File file = getFile();

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Department>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read departments from JSON.", e);
        }
    }

    private void writeToFile(List<Department> departments) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(getFile(), departments);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write departments to JSON.", e);
        }
    }

    private File getFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            throw new RuntimeException("File not found: " + FILE_PATH);
        }
        return file;
    }
}
