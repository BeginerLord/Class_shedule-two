package com.unicar.Class_shedule.commons.Schedule.presentation.dto;

import java.time.LocalDateTime;

public record ScheduleDto(
        LocalDateTime startTime,
        LocalDateTime endTime,
        String room,
        String day
) {
}
