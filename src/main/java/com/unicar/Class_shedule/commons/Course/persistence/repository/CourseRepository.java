package com.unicar.Class_shedule.commons.Course.persistence.repository;

import com.unicar.Class_shedule.commons.Course.persistence.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Long> {
 Optional<CourseEntity> findCourseByName(String name);
}
