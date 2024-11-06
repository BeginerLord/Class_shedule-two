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

    @NotBlank(message = "can't be in blank")
    private String profile;

    @NotBlank(message = "can't be in blank")
    private String username;

    @NotBlank(message = "can't be in blank")
    private String fullName;

    @NotBlank(message = "can't be in blank")
    @Size(min = 8, max = 12, message = "DNI must be between 8 and 12 characters")
    private String dni;

    @NotBlank(message = "can't be in blank")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits and can contain an optional '+' symbol at the start")
    private String phoneNumber;

    @NotBlank(message = "can't be in blank")
    private String address;

    @Email(message = "must be a valid email")
    @NotBlank(message = "can't be in blank")
    private String email;

    @NotBlank(message = "can't be in blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;


}
