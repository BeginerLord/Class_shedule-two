package com.unicar.Class_shedule.commons.Course.Service.Implementation;

import com.unicar.Class_shedule.commons.Course.Service.Interface.ICourseService;
import com.unicar.Class_shedule.commons.Course.factory.FactoryCourse;
import com.unicar.Class_shedule.commons.Course.persistence.entity.CourseEntity;
import com.unicar.Class_shedule.commons.Course.persistence.repository.CourseRepository;
import com.unicar.Class_shedule.commons.Course.presentation.dto.CourseDto;
import com.unicar.Class_shedule.commons.Course.presentation.payload.CoursePayload;
import com.unicar.Class_shedule.commons.Docent.persistence.entity.Docent;
import com.unicar.Class_shedule.commons.Docent.persistence.repositories.DocentRepository;
import com.unicar.Class_shedule.commons.Schedule.persistence.entity.ScheduleEntity;
import com.unicar.Class_shedule.commons.Schedule.persistence.repository.ScheduleRepository;
import com.unicar.Class_shedule.commons.utils.exceptions.DocentNotFoundException;
import com.unicar.Class_shedule.commons.utils.exceptions.ScheduleNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {
    private final CourseRepository courseRepository;
    private final FactoryCourse factoryCourse;
    private final ScheduleRepository scheduleRepository;
    private final DocentRepository docentRepository;

    @Override
    @Transactional
    public void saveCourse(CoursePayload coursePayload) {
        // Validate payload (optional, depending on your use case)
        if (coursePayload == null || coursePayload.getName() == null || coursePayload.getLevel() == null) {
            throw new IllegalArgumentException("Course details cannot be null");
        }

        // Creating the CourseEntity from the CoursePayload
        CourseEntity courseEntity = CourseEntity.builder()
                .name(coursePayload.getName())
                .level(coursePayload.getLevel())
                .cantHrs(coursePayload.getCantHrs())
                .build();

        // Finding the docent by DNI (with improved exception handling)
        Docent docent = docentRepository.findByUserEntityDni(coursePayload.getDniProffesor())
                .orElseThrow(() -> new DocentNotFoundException("Docente con cédula no encontrada: " + coursePayload.getDniProffesor()));

        // Setting the docent to the course entity
        courseEntity.setDocent(docent);

        // Finding the schedule by ID from the payload
        ScheduleEntity scheduleEntity = scheduleRepository.findById(coursePayload.getIdHorario())
                .orElseThrow(() -> new ScheduleNotFoundException("Horario no encontrado: " + coursePayload.getIdHorario()));

        // Setting the schedule to the course entity
        courseEntity.setSchedule(scheduleEntity);

        // Saving the course entity
        courseRepository.save(courseEntity);
    }



    @Override
    @Transactional
    public void updateCourseByName(CoursePayload coursePayload, String name) {

        Optional<CourseEntity> optionalCourse = courseRepository.findCourseByName(name);

        if (optionalCourse.isPresent()) {
            CourseEntity courseEntity = optionalCourse.get();

            courseEntity.setName(coursePayload.getName());
            courseEntity.setCantHrs(coursePayload.getCantHrs());
            courseEntity.setLevel(coursePayload.getLevel());

            courseRepository.save(courseEntity);
        } else {

            throw new UnsupportedOperationException("COURSE WITH NAME NOT WAS FOUND " + name);
        }

    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        CourseEntity courseEntity = courseRepository.findCourseByName(name)
                .orElseThrow(() -> new IllegalArgumentException("COURSE NOT FOUND WITH NAME " + name));
        courseRepository.delete(courseEntity);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseDto> findCourseByName(String name) {
        Optional<CourseEntity> optionalCourse = courseRepository.findCourseByName(name);

        if (optionalCourse.isPresent()) {
            CourseEntity courseEntity = optionalCourse.get();
            CourseDto courseDto = factoryCourse.courseDto(courseEntity);

            return Optional.of(courseDto);
        } else {

            throw new EntityNotFoundException("COURSE NOT FOUND WITH NAME " + name);
        }

    }

    @Override
    public Page<CourseDto> findCourses(Pageable pageable) {
        Page<CourseEntity> courseEntityPage = courseRepository.findAll(pageable);

        List<CourseDto> courseDtoList = courseEntityPage.stream()
                .map(courseEntity -> factoryCourse.courseDto(courseEntity))
                .collect(Collectors.toList());
        return new PageImpl<>(courseDtoList, pageable, courseEntityPage.getTotalElements());
    }
}
