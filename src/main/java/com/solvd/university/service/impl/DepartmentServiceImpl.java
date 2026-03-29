package com.solvd.university.service.impl;

import com.solvd.university.dao.DepartmentDao;
import com.solvd.university.dao.impl.DepartmentDaoImpl;
import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Department;
import com.solvd.university.service.DepartmentService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDao departmentDao;

    public DepartmentServiceImpl() {
        this.departmentDao = new DepartmentDaoImpl();
    }

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public Department getById(int id) {
        return departmentDao.findById(id)
                .orElseThrow(() -> new ServiceException("Department with id " + id + " not found."));
    }

    @Override
    public List<Department> getAll() {
        return departmentDao.findAll();
    }

    @Override
    public void create(Department department) {
        validateDepartment(department);

        boolean exists = departmentDao.findById(department.getDepartmentId()).isPresent();
        if (exists) {
            throw new ServiceException("Department with id " + department.getDepartmentId() + " already exists.");
        }
        departmentDao.save(department);
    }

    @Override
    public void update(Department department) {
        validateDepartment(department);

        boolean exists = departmentDao.findById(department.getDepartmentId()).isPresent();
        if (!exists) {
            throw new ServiceException("Department with id " + department.getDepartmentId() + " not found.");
        }
        departmentDao.update(department);
    }

    @Override
    public void deleteById(int id) {
        boolean exists = departmentDao.findById(id).isPresent();
        if (!exists) {
            throw new ServiceException("Department with id " + id + " not found.");
        }
        departmentDao.deleteById(id);
    }

    private void validateDepartment(Department department) {
        if (department == null) {
            throw new ServiceException("Department cannot be null.");
        }
        if (department.getDepartmentId() <= 0) {
            throw new ServiceException("Department id must be positive.");
        }
        if (department.getDepartmentName() == null || department.getDepartmentName().isBlank()) {
            throw new ServiceException("Department name cannot be empty.");
        }
        if (department.getOfficeLocation() == null || department.getOfficeLocation().isBlank()) {
            throw new ServiceException("Department location cannot be empty.");
        }
    }
}
