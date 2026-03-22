package com.solvd.university.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseOffering {
    private int offeringId;
    private String sectionNumber;
    private int maxStudents;
    private String deliveryMode;
    private int courseId;
    private int semesterId;
    private int instructorId;

    @Override
    public String toString() {
        return """
                Course Offering:
                  ID: %d
                  Section: %s
                  Max Students: %d
                  Delivery Mode: %s
                  Course ID: %d
                  Semester ID: %d
                  Instructor ID: %d
                """.formatted(
                offeringId,
                sectionNumber,
                maxStudents,
                deliveryMode,
                courseId,
                semesterId,
                instructorId
        );
    }
}