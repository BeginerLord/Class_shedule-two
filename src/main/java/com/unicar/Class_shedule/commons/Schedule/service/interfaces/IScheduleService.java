package com.unicar.Class_shedule.commons.Schedule.service.interfaces;

import com.unicar.Class_shedule.commons.Schedule.presentation.dto.ScheduleDto;
import com.unicar.Class_shedule.commons.Schedule.presentation.payload.SchedulePayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IScheduleService {

    void saveSchedule(SchedulePayload schedulePayload);

    void deleteById(Long id);

    void updateSchedule(SchedulePayload schedulePayload, Long id);

    Optional<ScheduleDto> findScheduleById(Long id);

    Page<ScheduleDto> findSchedules(Pageable pageable);
}
