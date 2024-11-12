package com.unicar.Class_shedule.commons.students.persistencie.entity;

import com.unicar.Class_shedule.commons.security.persistencie.entities.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    @Column(nullable = false) // La columna no puede ser nula
    private String description;

    @NotBlank(message = "La carrera no puede estar vacía")
    @Size(max = 100, message = "La carrera no puede superar los 100 caracteres")
    @Column(nullable = false) // La columna no puede ser nula
    private String carrer;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
}
