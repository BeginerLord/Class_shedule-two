package com.unicar.Class_shedule.commons.Docent.presentation.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocentPayload {
    private String profile;
    private String username;
    private String fullName;
    private String dni;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;
}
