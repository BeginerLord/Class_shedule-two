package com.unicar.Class_shedule.commons.utils.exceptions;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String message) {
        super(message);
    }
}