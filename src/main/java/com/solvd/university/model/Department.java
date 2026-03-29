package com.solvd.university.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"departmentId", "departmentName", "officeLocation"})
public class Department {
    private int departmentId;
    private String departmentName;
    private String officeLocation;

    @Override
    public String toString() {
        return """
                Department:
                ID: %d
                Name: %s
                Location: %s
                """.formatted(
                departmentId,
                departmentName,
                officeLocation
        );
    }
}
