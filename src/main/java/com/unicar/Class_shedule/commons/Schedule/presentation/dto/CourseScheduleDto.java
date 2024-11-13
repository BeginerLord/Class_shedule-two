package com.unicar.Class_shedule.commons.Schedule.presentation.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CourseScheduleDto(
        String courseName,
        int courseHours,
        String courseLevel,
        String docentName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String room,
        String day
) {
}
