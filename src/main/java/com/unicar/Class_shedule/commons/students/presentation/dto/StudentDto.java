package com.unicar.Class_shedule.commons.students.presentation.dto;

import lombok.Builder;

@Builder
public record StudentDto(

        String username,
        String fullName,
        String dni,
        String phoneNumber,
        String address,
        String email,
        String description,
        String carrer

) {
}
