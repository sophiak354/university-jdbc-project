package com.solvd.university.dao.impl.mybatis;

import com.solvd.university.config.MyBatisUtil;
import com.solvd.university.dao.CourseDao;
import com.solvd.university.mapper.CourseMapper;
import com.solvd.university.model.Course;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class CourseMyBatisDaoImpl implements CourseDao {

    @Override
    public Optional<Course> findById(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CourseMapper mapper = session.getMapper(CourseMapper.class);
            return Optional.ofNullable(mapper.findById(id));
        }
    }

    @Override
    public List<Course> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CourseMapper mapper = session.getMapper(CourseMapper.class);
            return mapper.findAll();
        }
    }
}
