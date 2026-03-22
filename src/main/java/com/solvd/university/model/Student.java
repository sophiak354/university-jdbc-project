package com.solvd.university.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String phone;
    private LocalDate admissionDate;
    private String studentStatus;
    private int programId;

    @Override
    public String toString() {
        return """
                Student:
                  ID: %d
                  Name: %s %s
                  DOB: %s
                  Gender: %s
                  Email: %s
                  Phone: %s
                  Admission: %s
                  Status: %s
                  Program ID: %d
                """.formatted(
                studentId,
                firstName,
                lastName,
                dateOfBirth,
                gender,
                email,
                phone,
                admissionDate,
                studentStatus,
                programId
        );
    }
}
