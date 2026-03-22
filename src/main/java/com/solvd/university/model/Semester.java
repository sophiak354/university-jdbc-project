package com.solvd.university.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Semester {
    private int semesterId;
    private String semesterName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String academicYear;

    @Override
    public String toString() {
        return """
                Semester:
                  ID: %d
                  Name: %s
                  Start Date: %s
                  End Date: %s
                  Academic Year: %s
                """.formatted(
                semesterId,
                semesterName,
                startDate,
                endDate,
                academicYear
        );
    }
}