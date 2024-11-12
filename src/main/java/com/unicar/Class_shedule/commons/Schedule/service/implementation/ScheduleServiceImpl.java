package com.unicar.Class_shedule.commons.Schedule.service.implementation;

import com.unicar.Class_shedule.commons.Schedule.factory.ScheduleFactory;
import com.unicar.Class_shedule.commons.Schedule.persistence.entity.ScheduleEntity;
import com.unicar.Class_shedule.commons.Schedule.persistence.repository.ScheduleRepository;
import com.unicar.Class_shedule.commons.Schedule.presentation.dto.ScheduleDto;
import com.unicar.Class_shedule.commons.Schedule.presentation.payload.SchedulePayload;
import com.unicar.Class_shedule.commons.Schedule.service.interfaces.IScheduleService;
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
public class ScheduleServiceImpl implements IScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleFactory scheduleFactory;

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
    public void deleteByDay(Long id) {

        ScheduleEntity scheduleEntity = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));
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
}
