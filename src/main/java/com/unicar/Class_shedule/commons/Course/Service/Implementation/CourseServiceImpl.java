package com.unicar.Class_shedule.commons.Course.Service.Implementation;

import com.unicar.Class_shedule.commons.Course.Service.Interface.ICourseService;
import com.unicar.Class_shedule.commons.Course.factory.FactoryCourse;
import com.unicar.Class_shedule.commons.Course.persistence.entity.CourseEntity;
import com.unicar.Class_shedule.commons.Course.persistence.repository.CourseRepository;
import com.unicar.Class_shedule.commons.Course.presentation.dto.CourseDto;
import com.unicar.Class_shedule.commons.Course.presentation.payload.CoursePayload;
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

    @Override
    @Transactional
    public void saveCourse(CoursePayload coursePayload) {
        CourseEntity courseEntity = CourseEntity.builder()
                .name(coursePayload.getName())
                .level(coursePayload.getLevel())
                .cantHrs(coursePayload.getCantHrs())
                .build();
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
