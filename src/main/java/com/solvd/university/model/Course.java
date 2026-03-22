package com.solvd.university.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private int credits;
    private String courseDescription;
    private int departmentId;

    @Override
    public String toString() {
        return """
                Course:
                  ID: %d
                  Code: %s
                  Name: %s
                  Credits: %d
                  Description: %s
                  Department ID: %d
                """.formatted(
                courseId,
                courseCode,
                courseName,
                credits,
                courseDescription,
                departmentId
        );
    }
}
