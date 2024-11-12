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
    @NotNull(message = "Start time can't be null")
    private LocalDateTime startTime;

    @NotNull(message = "End time can't be null")
    private LocalDateTime endTime;

    @NotBlank(message = "Room can't be blank")
    private String room;

    @NotBlank(message = "Day can't be blank")
    private String day;
}
