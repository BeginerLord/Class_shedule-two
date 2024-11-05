package com.unicar.Class_shedule.commons.security.presentation.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank String username,
                               @NotBlank String password) {
}