package com.unicar.Class_shedule.commons.Schedule.persistence.repository;

import com.unicar.Class_shedule.commons.Schedule.persistence.entity.ScheduleEntity;
import com.unicar.Class_shedule.commons.Schedule.presentation.dto.CourseScheduleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Long> {
    @NonNull
    Optional<ScheduleEntity> findById(@NonNull Long id);

    @Query("SELECT new com.unicar.Class_shedule.commons.Schedule.presentation.dto.CourseScheduleDto(" +
            "c.name, c.cantHrs, c.level, u.fullName, sch.startTime, sch.endTime, sch.room, sch.day) " +
            "FROM StudentCourse sc " +
            "JOIN sc.course c " +
            "JOIN c.schedule sch " +
            "LEFT JOIN c.docent d " +
            "LEFT JOIN d.user u " +
            "WHERE sc.student.id = :studentId")
    List<CourseScheduleDto> findCourseScheduleByStudentId(@Param("studentId") Long studentId);
}
