package com.unicar.Class_shedule.commons.Course.persistence.entity;

import com.unicar.Class_shedule.commons.Docent.persistence.entity.Docent;
import com.unicar.Class_shedule.commons.Schedule.persistence.entity.ScheduleEntity;
import com.unicar.Class_shedule.commons.security.persistencie.entities.UserEntity;
import com.unicar.Class_shedule.commons.students.persistencie.entity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_entity")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "cant_hrs", length = 5, nullable = false)
    private Integer cantHrs;
    @Column(name = "level", length = 50, nullable = false)
    private String level;

    @ManyToOne
    @JoinColumn(name = "docente_id")  // No puede ser nulo
    private Docent docent;

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id", nullable = false)
    private ScheduleEntity schedule;


}
