package com.unicar.Class_shedule.commons.security.presentation.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record AuthCreateUserRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String fullName,
        @NotBlank @Size(min = 8, max = 12) String dni,
        @NotBlank @Pattern(regexp = "^[+]?[0-9]{10,15}$") String phoneNumber,
        @NotBlank String address,
        @NotBlank @Email String email,
        @Valid AuthCreateRoleRequest roleRequest) {
}
