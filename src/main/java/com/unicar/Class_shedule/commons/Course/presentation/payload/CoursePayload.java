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
    @NotBlank(message = "can't be in blank")
    private String name;

    @NotBlank(message = "can't be in blank")
    private Integer cantHrs;

    @NotBlank(message = "can't be in blank")
    @Size(max = 50, message = "Level must be at most 50 characters")
    private String level;
}
