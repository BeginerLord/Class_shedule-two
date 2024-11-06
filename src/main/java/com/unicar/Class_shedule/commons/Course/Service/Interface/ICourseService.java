package com.unicar.Class_shedule.commons.Course.Service.Interface;

import com.unicar.Class_shedule.commons.Course.presentation.dto.CourseDto;
import com.unicar.Class_shedule.commons.Course.presentation.payload.CoursePayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICourseService {

    void saveCourse(CoursePayload coursePayload);

    void updateCourseByName(CoursePayload coursePayload,String name);

    void deleteByName(String name);

    Optional<CourseDto> findCourseByName(String name);

    Page<CourseDto> findCourses(Pageable pageable);
}
