package com.unicar.Class_shedule.commons.Course.persistence.entity;

import com.unicar.Class_shedule.commons.security.persistencie.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
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

}
