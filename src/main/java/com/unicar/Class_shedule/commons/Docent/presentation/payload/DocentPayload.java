package com.unicar.Class_shedule.commons.Docent.presentation.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocentPayload {

    @NotBlank(message = "El perfil no puede estar vacío")
    @Size(max = 50, message = "El perfil no puede exceder los 50 caracteres")
    private String profile;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    @NotBlank(message = "El nombre completo no puede estar vacío")
    private String fullName;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(min = 8, max = 12, message = "El DNI debe tener entre 8 y 12 caracteres")
    private String dni;

    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "El número de teléfono debe tener entre 10 y 15 dígitos y puede contener un símbolo '+' opcional al inicio")
    private String phoneNumber;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String address;

    @Email(message = "Debe ser un correo electrónico válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
}
