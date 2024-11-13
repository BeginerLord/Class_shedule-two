package com.unicar.Class_shedule.commons.Schedule.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;


import lombok.Builder;

import java.time.LocalDateTime;


@Builder


public record ProfessorScheduleDto(
        String courseName,
        Integer courseHours,
        String level,
        String professorName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String room,
        String day
) {}
