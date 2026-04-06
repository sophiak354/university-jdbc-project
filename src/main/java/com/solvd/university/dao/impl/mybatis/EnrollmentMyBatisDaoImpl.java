package com.solvd.university.dao.impl.mybatis;

import com.solvd.university.config.MyBatisUtil;
import com.solvd.university.dao.EnrollmentDao;
import com.solvd.university.mapper.EnrollmentMapper;
import com.solvd.university.model.Enrollment;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class EnrollmentMyBatisDaoImpl implements EnrollmentDao {

    @Override
    public void save(Enrollment enrollment) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            EnrollmentMapper mapper = session.getMapper(EnrollmentMapper.class);
            mapper.save(enrollment);
            session.commit();
        }
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            EnrollmentMapper mapper = session.getMapper(EnrollmentMapper.class);
            return mapper.findByStudentId(studentId);
        }
    }

    @Override
    public boolean existsByStudentIdAndOfferingId(int studentId, int offeringId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            EnrollmentMapper mapper = session.getMapper(EnrollmentMapper.class);
            return mapper.existsByStudentIdAndOfferingId(studentId, offeringId);
        }
    }
}