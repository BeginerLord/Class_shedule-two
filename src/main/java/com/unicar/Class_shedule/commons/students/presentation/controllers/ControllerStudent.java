package com.unicar.Class_shedule.commons.students.presentation.controllers;

import com.unicar.Class_shedule.commons.students.presentation.dto.StudentDto;
import com.unicar.Class_shedule.commons.students.presentation.payload.EnroolAlCoursePayload;
import com.unicar.Class_shedule.commons.students.presentation.payload.StudentPayload;
import com.unicar.Class_shedule.commons.students.services.interfaces.IStudentService;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointsConstants.ENDPOINT_STUDENT)
public class ControllerStudent {

private  final IStudentService iStudentService;
    @Operation(summary = "Crear un nuevo estudiante", description = "Crea un nuevo estudiante a partir del payload proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> save(@RequestBody StudentPayload studentPayload) throws URISyntaxException {
        iStudentService.saveStudent(studentPayload);

        return ResponseEntity.created(new URI(EndpointsConstants.ENDPOINT_STUDENT)).build();
    }

    @Operation(summary = "Obtener un estudiante por DNI", description = "Devuelve los detalles de un estudiante según su DNI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{dni}")
    public ResponseEntity<Optional<StudentDto>> getDni(@PathVariable String dni) {

        Optional<StudentDto> studentDtoOptional = iStudentService.findStudentByDni(dni);
        return new ResponseEntity<>(studentDtoOptional, HttpStatus.OK);
    }









    @Operation(summary = "Obtener lista de estudiantes", description = "Devuelve una lista paginada de todos los estudiantes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<StudentDto>> findStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userEntity.username") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<StudentDto> studentDtos = iStudentService.findStudents(pageable);

        return ResponseEntity.ok(studentDtos);
    }


    @Operation(summary = "Actualizar un estudiante", description = "Actualiza los detalles de un estudiante existente según su DNI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{dni}")
    public ResponseEntity<Void> update(@PathVariable String dni, @RequestBody StudentPayload studentPayload) {
        iStudentService.updateStudent(studentPayload, dni);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar un estudiante", description = "Elimina un estudiante existente según su DNI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{dni}")
    public ResponseEntity<?> delete(@PathVariable String dni) {
        iStudentService.deleteByDni(dni);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/enroll")

    public  ResponseEntity<?>saveStudentWithCourse (@RequestBody EnroolAlCoursePayload enroolAlCoursePayload ) throws URISyntaxException {

        iStudentService.enrollACourse(enroolAlCoursePayload);

        return ResponseEntity.created(new URI(EndpointsConstants.ENDPOINT_STUDENT+"/enroll")).build();





    }}
