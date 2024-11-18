package com.unicar.Class_shedule.commons.Course.presentation.dto;

import lombok.Builder;

import lombok.Builder;

@Builder
public record CourseDto(
        Long id,
        String name,
        Integer cantHrs,
        String level,
        String docenteCedula,  // Campo para la cédula del docente
        String codigoHorario ,
        String fullName,
        String correo

        // Campo para el código del horario
) {
}
