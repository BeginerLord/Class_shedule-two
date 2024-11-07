package com.unicar.Class_shedule.commons.Course.factory;

import com.unicar.Class_shedule.commons.Course.persistence.entity.CourseEntity;
import com.unicar.Class_shedule.commons.Course.presentation.dto.CourseDto;
import org.springframework.stereotype.Component;

@Component
public class FactoryCourse {
    public CourseDto courseDto(CourseEntity courseEntity) {

        return CourseDto.builder()
                .name(courseEntity.getName())
                .level(courseEntity.getLevel())
                .cantHrs(courseEntity.getCantHrs())
                .build();
    }
}
