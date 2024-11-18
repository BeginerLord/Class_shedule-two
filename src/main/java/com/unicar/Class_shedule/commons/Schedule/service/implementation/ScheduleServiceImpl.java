package com.unicar.Class_shedule.commons.Schedule.service.implementation;

import com.unicar.Class_shedule.commons.Schedule.factory.ScheduleFactory;
import com.unicar.Class_shedule.commons.Schedule.persistence.entity.ScheduleEntity;
import com.unicar.Class_shedule.commons.Schedule.persistence.repository.ScheduleRepository;
import com.unicar.Class_shedule.commons.Schedule.presentation.dto.CourseScheduleDto;
import com.unicar.Class_shedule.commons.Schedule.presentation.dto.ProfessorScheduleDto;
import com.unicar.Class_shedule.commons.Schedule.presentation.dto.ScheduleDto;
import com.unicar.Class_shedule.commons.Schedule.presentation.payload.SchedulePayload;
import com.unicar.Class_shedule.commons.Schedule.service.interfaces.IScheduleService;
import com.unicar.Class_shedule.commons.security.persistencie.entities.UserEntity;
import com.unicar.Class_shedule.commons.security.persistencie.repositories.UserRepository;
import com.unicar.Class_shedule.commons.utils.exceptions.ScheduleConflictException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements IScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleFactory scheduleFactory;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void saveSchedule(SchedulePayload schedulePayload) {
        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .startTime(schedulePayload.getStartTime())
                .endTime(schedulePayload.getEndTime())
                .room(schedulePayload.getRoom())
                .day(schedulePayload.getDay())
                .build();

        scheduleRepository.save(scheduleEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Buscar el horario por su ID
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));

        // Verificar si el horario tiene cursos asociados
        if (scheduleEntity.getCourses() != null && !scheduleEntity.getCourses().isEmpty()) {
            throw new ScheduleConflictException("Cannot delete schedule with active course associations.");
        }

        // Eliminar el horario si no tiene cursos asociados
        scheduleRepository.delete(scheduleEntity);
    }


    @Override
    public void updateSchedule(SchedulePayload schedulePayload, Long id) {

        ScheduleEntity scheduleEntity = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));

        scheduleEntity.setStartTime(schedulePayload.getStartTime());
        scheduleEntity.setEndTime(schedulePayload.getEndTime());
        scheduleEntity.setRoom(schedulePayload.getRoom());
        scheduleEntity.setDay(schedulePayload.getDay());

        scheduleRepository.save(scheduleEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleDto> findScheduleById(Long id) {

        Optional<ScheduleEntity> optionalScheduleDto = scheduleRepository.findById(id);

        if (optionalScheduleDto.isPresent()) {
            ScheduleEntity scheduleEntity = optionalScheduleDto.get();
            ScheduleDto scheduleDto = scheduleFactory.scheduleDto(scheduleEntity);
            return Optional.of(scheduleDto);
        } else {

            throw new EntityNotFoundException("SCHEDULE NOT FOUND WITH ID: " + id);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleDto> findSchedules(Pageable pageable) {
        Page<ScheduleEntity> schedulePage = scheduleRepository.findAll(pageable);

        List<ScheduleDto> scheduleDtoList = schedulePage.stream()
                .map(schedule -> scheduleFactory.scheduleDto(schedule))
                .collect(Collectors.toList());

        return new PageImpl<>(scheduleDtoList, pageable, schedulePage.getTotalElements());

    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseScheduleDto> findCourseScheduleByUsername(Principal principal) {
        String username = principal.getName();
        System.out.println("Fetching user with username: " + username);

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        System.out.println("User found: " + userEntity);

        if (userEntity.getStudent() == null) {
            throw new IllegalArgumentException("No student associated with user: " + username);
        }

        Long studentId = userEntity.getStudent().getId();
        System.out.println("Fetching course schedule for student ID: " + studentId);

        List<CourseScheduleDto> courseSchedules = scheduleRepository.findCourseScheduleByStudentId(studentId)
                .stream()
                .map(schedule -> scheduleFactory.courseScheduleDto(
                        ScheduleEntity.builder()
                                .startTime(schedule.startTime())
                                .endTime(schedule.endTime())
                                .room(schedule.room())
                                .day(schedule.day())
                                .build(),
                        schedule.courseName(),
                        schedule.courseHours(),
                        schedule.courseLevel(),
                        schedule.docentName()
                ))
                .collect(Collectors.toList());

        System.out.println("Course schedules found: " + courseSchedules);

        return courseSchedules;
    }

    @Override
    @Transactional(readOnly = true)

    public List<ProfessorScheduleDto> findProfessorScheduleByUsername(Principal principal) {
        String username = principal.getName();
        System.out.println("Fetching professor with username: " + username);

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Professor not found with username: " + username));

        System.out.println("Professor found: " + userEntity);

        if (userEntity.getDocent() == null) {
            throw new IllegalArgumentException("No professor associated with user: " + username);
        }

        Long professorId = userEntity.getDocent().getId();
        System.out.println("Fetching professor schedule for professor ID: " + professorId);

        List<ProfessorScheduleDto> professorSchedules = scheduleRepository.findCourseScheduleByProfessorId(professorId)
                .stream()
                .map(schedule -> scheduleFactory.professorScheduleDto(
                        ScheduleEntity.builder()
                                .startTime(schedule.startTime())
                                .endTime(schedule.endTime())
                                .room(schedule.room())
                                .day(schedule.day())
                                .build(),
                        schedule.courseName(),
                        schedule.courseHours(),
                        schedule.level(),
                        schedule.professorName()
                ))
                .collect(Collectors.toList());

        System.out.println("Professor schedules found: " + professorSchedules);

        return professorSchedules;
    }

}