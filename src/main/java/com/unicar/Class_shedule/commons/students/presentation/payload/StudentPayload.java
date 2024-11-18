package com.unicar.Class_shedule.commons.students.presentation.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentPayload {
    private String description;
    private String carrer;
    private String username;
    private String fullName;
    private String dni;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;
}
