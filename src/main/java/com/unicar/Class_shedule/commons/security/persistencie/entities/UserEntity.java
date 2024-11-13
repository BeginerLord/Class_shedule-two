package com.unicar.Class_shedule.commons.security.persistencie.entities;

import com.unicar.Class_shedule.commons.students.persistencie.entity.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(name = "full_name", nullable = false)
    @NotBlank(message = "El nombre completo es obligatorio")
    private String fullName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 12, message = "El DNI debe tener entre 8 y 12 caracteres")
    private String dni;

    @Column(unique = true, name = "phone_number", nullable = false)
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "El número de teléfono debe tener entre 10 y 15 dígitos y puede contener un símbolo '+' opcional al inicio")
    private String phoneNumber;

    @Column(nullable = false)
    @NotBlank(message = "La dirección es obligatoria")
    private String address;

    @Email(message = "Debe proporcionar un correo válido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    // Getters y setters

    @Column(name = "is_enabled")

    private boolean isEnabled;
    @Column(name = "account_No_Locked")

    private boolean accountNoLocked;
    @Column(name = "account_No_Expired")

    private boolean accountNoExpired;
    @Column(name = "credential_No_Expired")

    private boolean credentialNoExpired;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Student student;

}
