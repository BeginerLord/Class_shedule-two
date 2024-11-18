package com.unicar.Class_shedule.commons.Course.presentation.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoursePayload {
    private String name;
    private Integer cantHrs;
    private String level;
    private String dniProffesor;
    private Long idHorario;
}
