package com.solvd.university.mapper;

import com.solvd.university.model.Semester;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SemesterMapper {

    @Select("""
            SELECT semester_id AS semesterId,
                   semester_name AS semesterName,
                   start_date AS startDate,
                   end_date AS endDate,
                   academic_year AS academicYear
            FROM Semester
            WHERE semester_id = #{id}
            """)
    Semester findById(int id);

    @Select("""
            SELECT semester_id AS semesterId,
                   semester_name AS semesterName,
                   start_date AS startDate,
                   end_date AS endDate,
                   academic_year AS academicYear
            FROM Semester
            """)
    List<Semester> findAll();

    @Insert("""
            INSERT INTO Semester (semester_name, start_date, end_date, academic_year)
            VALUES (#{semesterName}, #{startDate}, #{endDate}, #{academicYear})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "semesterId")
    void save(Semester semester);

    @Update("""
            UPDATE Semester
            SET semester_name = #{semesterName},
                start_date = #{startDate},
                end_date = #{endDate},
                academic_year = #{academicYear}
            WHERE semester_id = #{semesterId}
            """)
    void update(Semester semester);

    @Delete("""
            DELETE FROM Semester
            WHERE semester_id = #{id}
            """)
    void deleteById(int id);

}
