package com.unicar.Class_shedule.commons.Course.presentation.dto;

import lombok.Builder;

@Builder
public record CourseDto(
        String name,
        Integer cantHrs,
        String level

) {
}
