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
public class Enrollment {
    private int enrollmentId;
    private LocalDate enrollmentDate;
    private String enrollmentStatus;
    private int studentId;
    private int offeringId;

    @Override
    public String toString() {
        return """
                Enrollment:
                  ID: %d
                  Date: %s
                  Status: %s
                  Student ID: %d
                  Offering ID: %d
                """.formatted(
                enrollmentId,
                enrollmentDate,
                enrollmentStatus,
                studentId,
                offeringId
        );
    }
}
