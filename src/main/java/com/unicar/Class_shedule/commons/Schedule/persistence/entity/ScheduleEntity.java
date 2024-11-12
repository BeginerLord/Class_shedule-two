package com.unicar.Class_shedule.commons.Schedule.persistence.entity;

import com.unicar.Class_shedule.commons.Docent.persistence.entity.Docent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docent docente;

    @Column(name = "room", length = 50, nullable = false)
    private String room;

    @Column(name = "day", length = 10, nullable = false)
    private String day;

}
