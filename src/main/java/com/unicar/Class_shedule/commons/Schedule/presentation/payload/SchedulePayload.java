package com.unicar.Class_shedule.commons.Schedule.presentation.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulePayload {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String room;
    private String day;
}
