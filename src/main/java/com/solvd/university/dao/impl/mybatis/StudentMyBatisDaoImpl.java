package com.solvd.university.dao.impl.mybatis;

import com.solvd.university.config.MyBatisUtil;
import com.solvd.university.dao.StudentDao;
import com.solvd.university.mapper.StudentMapper;
import com.solvd.university.model.Student;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class StudentMyBatisDaoImpl implements StudentDao {

    @Override
    public Optional<Student> findById(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            return Optional.ofNullable(mapper.findById(id));
        }
    }

    @Override
    public List<Student> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public void save(Student student) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            mapper.save(student);
            session.commit();
        }
    }

    @Override
    public void update(Student student) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            mapper.update(student);
            session.commit();
        }
    }

    @Override
    public void deleteById(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            mapper.deleteById(id);
            session.commit();
        }
    }
}
