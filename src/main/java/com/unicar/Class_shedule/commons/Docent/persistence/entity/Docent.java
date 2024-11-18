package com.unicar.Class_shedule.commons.Docent.persistence.entity;

import com.unicar.Class_shedule.commons.Course.persistence.entity.CourseEntity;
import com.unicar.Class_shedule.commons.security.persistencie.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "docents")
public class Docent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile", length = 50, nullable = false)
    private String profile;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
    @OneToMany(mappedBy = "docent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseEntity> cursos;
}
