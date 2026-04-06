package com.solvd.university.mapper;

import com.solvd.university.model.Course;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseMapper {

    @Select("""
            SELECT course_id AS courseId,
                   course_code AS courseCode,
                   course_name AS courseName,
                   credits,
                   course_description AS courseDescription,
                   department_id AS departmentId
            FROM Course
            WHERE course_id = #{id}
            """)
    Course findById(int id);

    @Select("""
            SELECT course_id AS courseId,
                   course_code AS courseCode,
                   course_name AS courseName,
                   credits,
                   course_description AS courseDescription,
                   department_id AS departmentId
            FROM Course
            """)
    List<Course> findAll();

}
