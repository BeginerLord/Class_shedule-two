package com.unicar.Class_shedule.commons.students.presentation.dto;

import java.time.LocalTime;

public record StudentCourseDTO(
        String courseName,
        Integer courseHours,
        String courseLevel,
        String docentName,
        LocalTime startTime,
        LocalTime endTime,
        String room,
        String day
) {}