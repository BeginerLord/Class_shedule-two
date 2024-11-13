package com.unicar.Class_shedule.commons.Schedule.factory;

import com.unicar.Class_shedule.commons.Schedule.persistence.entity.ScheduleEntity;
import com.unicar.Class_shedule.commons.Schedule.presentation.dto.CourseScheduleDto;
import com.unicar.Class_shedule.commons.Schedule.presentation.dto.ScheduleDto;
import org.springframework.stereotype.Component;

@Component
public class ScheduleFactory {
//convierte una entidad ScheduleEntity en un ScheduleDto
    public ScheduleDto scheduleDto(ScheduleEntity scheduleEntity) {
        return new ScheduleDto(
                scheduleEntity.getStartTime(),
                scheduleEntity.getEndTime(),
                scheduleEntity.getRoom(),
                scheduleEntity.getDay()
        );
    }
//convierte un ScheduleDto en una entidad ScheduleEntity
    public ScheduleEntity scheduleEntity(ScheduleDto scheduleDto) {

        return ScheduleEntity.builder()
                .startTime(scheduleDto.startTime())
                .endTime(scheduleDto.endTime())
                .room(scheduleDto.room())
                .day(scheduleDto.day())
                .build();
    }
    // Convierte una entidad ScheduleEntity y otros parámetros en un CourseScheduleDto
    public CourseScheduleDto courseScheduleDto(ScheduleEntity scheduleEntity, String courseName, int courseHours, String courseLevel, String docentName) {
        return CourseScheduleDto.builder()
                .courseName(courseName)
                .courseHours(courseHours)
                .courseLevel(courseLevel)
                .docentName(docentName)
                .startTime(scheduleEntity.getStartTime())
                .endTime(scheduleEntity.getEndTime())
                .room(scheduleEntity.getRoom())
                .day(scheduleEntity.getDay())
                .build();
    }

}
