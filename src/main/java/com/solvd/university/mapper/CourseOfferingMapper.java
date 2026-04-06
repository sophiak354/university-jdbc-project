package com.solvd.university.mapper;

import com.solvd.university.model.CourseOffering;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseOfferingMapper {

    @Select("""
            SELECT offering_id AS offeringId,
                   section_number AS sectionNumber,
                   max_students AS maxStudents,
                   delivery_mode AS deliveryMode,
                   course_id AS courseId,
                   semester_id AS semesterId,
                   instructor_id AS instructorId
            FROM Course_offering
            WHERE offering_id = #{id}
            """)
    CourseOffering findById(int id);

    @Select("""
            SELECT offering_id AS offeringId,
                   section_number AS sectionNumber,
                   max_students AS maxStudents,
                   delivery_mode AS deliveryMode,
                   course_id AS courseId,
                   semester_id AS semesterId,
                   instructor_id AS instructorId
            FROM Course_offering
            """)
    List<CourseOffering> findAll();

}
