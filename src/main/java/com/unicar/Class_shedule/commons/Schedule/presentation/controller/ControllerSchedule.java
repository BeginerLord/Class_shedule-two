package com.unicar.Class_shedule.commons.Schedule.presentation.controller;

import com.unicar.Class_shedule.commons.Schedule.persistence.entity.ScheduleEntity;
import com.unicar.Class_shedule.commons.Schedule.presentation.dto.CourseScheduleDto;
import com.unicar.Class_shedule.commons.Schedule.presentation.dto.ScheduleDto;
import com.unicar.Class_shedule.commons.Schedule.presentation.payload.SchedulePayload;
import com.unicar.Class_shedule.commons.Schedule.service.interfaces.IScheduleService;
import com.unicar.Class_shedule.commons.utils.constants.EndpointsConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointsConstants.ENDPOINT_SCHEDULE)
public class ControllerSchedule {
    private final IScheduleService iScheduleService;


    @Operation(summary = "Crear un nuevo horario", description = "Crea un nuevo horario a partir del payload proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Horario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ScheduleDto> save(@RequestBody SchedulePayload schedulePayload) throws URISyntaxException {
        iScheduleService.saveSchedule(schedulePayload);

        return ResponseEntity.created(new URI(EndpointsConstants.ENDPOINT_SCHEDULE)).build();
    }

    @Operation(summary = "Obtener un horario por ID", description = "Devuelve los detalles de un horario según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ScheduleDto>> getById(@PathVariable Long id) {
        Optional<ScheduleDto> scheduleDtoOptional = iScheduleService.findScheduleById(id);
        return new ResponseEntity<>(scheduleDtoOptional, HttpStatus.OK);
    }

    @Operation(summary = "Obtener lista de horarios", description = "Devuelve una lista paginada de todos los horarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<ScheduleDto>> findSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduleName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<ScheduleDto> scheduleDtos = iScheduleService.findSchedules(pageable);

        return ResponseEntity.ok(scheduleDtos);
    }

    @Operation(summary = "Actualizar un horario", description = "Actualiza los detalles de un horario existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody SchedulePayload schedulePayload) {
        iScheduleService.updateSchedule(schedulePayload, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar un horario", description = "Elimina un horario existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Horario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        iScheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener horario del estudiante logueado", description = "Devuelve el horario del curso para el usuario autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/student/schedule")
    public ResponseEntity<List<CourseScheduleDto>> getCourseScheduleByUsername(Principal principal) {
        List<CourseScheduleDto> schedules = iScheduleService.findCourseScheduleByUsername(principal);
        return ResponseEntity.ok(schedules);
    }

}
