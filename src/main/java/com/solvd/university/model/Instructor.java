package com.solvd.university.model;

import com.solvd.university.dao.xml.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "instructor")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "instructorId",
        "firstName",
        "lastName",
        "email",
        "phone",
        "hireDate",
        "academicTitle",
        "departmentId"
})
public class Instructor {
    private int instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate hireDate;
    private String academicTitle;
    private int departmentId;

    @Override
    public String toString() {
        return """
                Instructor:
                ID: %d
                Name: %s %s
                Email: %s
                Phone: %s
                Hire Date: %s
                Academic Title: %s
                Department ID: %d
                """.formatted(
                instructorId,
                firstName,
                lastName,
                email,
                phone,
                hireDate,
                academicTitle,
                departmentId
        );
    }
}
