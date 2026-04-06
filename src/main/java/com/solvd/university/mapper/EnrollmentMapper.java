package com.solvd.university.mapper;

import com.solvd.university.model.Enrollment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnrollmentMapper {
    void save(Enrollment enrollment);

    List<Enrollment> findByStudentId(int studentId);

    boolean existsByStudentIdAndOfferingId(@Param("studentId") int studentId,
                                           @Param("offeringId") int offeringId);
}
