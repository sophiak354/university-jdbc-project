package com.solvd.university.dao.impl.mybatis;

import com.solvd.university.config.MyBatisUtil;
import com.solvd.university.dao.SemesterDao;
import com.solvd.university.mapper.SemesterMapper;
import com.solvd.university.model.Semester;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class SemesterMyBatisDaoImpl implements SemesterDao {

    @Override
    public Optional<Semester> findById(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            SemesterMapper mapper = session.getMapper(SemesterMapper.class);
            return Optional.ofNullable(mapper.findById(id));
        }
    }

    @Override
    public List<Semester> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            SemesterMapper mapper = session.getMapper(SemesterMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public void save(Semester semester) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            SemesterMapper mapper = session.getMapper(SemesterMapper.class);
            mapper.save(semester);
            session.commit();
        }
    }

    @Override
    public void update(Semester semester) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            SemesterMapper mapper = session.getMapper(SemesterMapper.class);
            mapper.update(semester);
            session.commit();
        }
    }

    @Override
    public void deleteById(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            SemesterMapper mapper = session.getMapper(SemesterMapper.class);
            mapper.deleteById(id);
            session.commit();
        }
    }

}
