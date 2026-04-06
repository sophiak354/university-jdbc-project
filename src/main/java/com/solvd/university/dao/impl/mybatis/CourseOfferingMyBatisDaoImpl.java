package com.solvd.university.dao.impl.mybatis;

import com.solvd.university.config.MyBatisUtil;
import com.solvd.university.dao.CourseOfferingDao;
import com.solvd.university.mapper.CourseOfferingMapper;
import com.solvd.university.model.CourseOffering;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class CourseOfferingMyBatisDaoImpl implements CourseOfferingDao {

    @Override
    public Optional<CourseOffering> findById(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CourseOfferingMapper mapper = session.getMapper(CourseOfferingMapper.class);
            return Optional.ofNullable(mapper.findById(id));
        }
    }

    @Override
    public List<CourseOffering> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CourseOfferingMapper mapper = session.getMapper(CourseOfferingMapper.class);
            return mapper.findAll();
        }
    }

}
