package com.unicar.Class_shedule.commons.Course.factory;

import com.unicar.Class_shedule.commons.Course.persistence.entity.CourseEntity;
import com.unicar.Class_shedule.commons.Course.presentation.dto.CourseDto;
import org.springframework.stereotype.Component;

@Component
public class FactoryCourse {
    public CourseDto courseDto(CourseEntity courseEntity) {
        // Asegurarse de que 'docenteCedula' obtiene el 'dni' del docente (si no es null)
        String docenteCedula = (courseEntity.getDocent() != null) ? courseEntity.getDocent().getUserEntity().getDni() : null;

        String fullName = (courseEntity.getDocent() != null) ? courseEntity.getDocent().getUserEntity().getFullName() : null;

        String correo = (courseEntity.getDocent() != null) ? courseEntity.getDocent().getUserEntity().getEmail() : null;

        // Asegúrate de que 'codigoHorario' obtiene el 'id' del horario (si no es null)
        String codigoHorario = (courseEntity.getSchedule() != null) ? courseEntity.getSchedule().getId().toString() : null;

        // Devuelves el DTO con los valores correspondientes
        return CourseDto.builder()
                .id(courseEntity.getId())
                .name(courseEntity.getName())
                .level(courseEntity.getLevel())
                .cantHrs(courseEntity.getCantHrs())
                .docenteCedula(docenteCedula)
                .fullName(fullName)
                .correo(correo)// Campo para la cédula del docente
                .codigoHorario(codigoHorario) // Campo para el código del horario
                .build();
    }
}
