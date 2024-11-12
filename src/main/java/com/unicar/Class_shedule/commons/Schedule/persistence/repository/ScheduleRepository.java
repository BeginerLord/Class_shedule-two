package com.unicar.Class_shedule.commons.Schedule.persistence.repository;

import com.unicar.Class_shedule.commons.Schedule.persistence.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Long> {
    @NonNull
    Optional<ScheduleEntity> findById(@NonNull Long id);
}
