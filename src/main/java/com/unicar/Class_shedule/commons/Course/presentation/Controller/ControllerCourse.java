package com.unicar.Class_shedule.commons.Course.presentation.Controller;

import com.unicar.Class_shedule.commons.Course.Service.Interface.ICourseService;
import com.unicar.Class_shedule.commons.Course.presentation.dto.CourseDto;
import com.unicar.Class_shedule.commons.Course.presentation.payload.CoursePayload;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointsConstants.ENDPOINT_COURSES)
public class ControllerCourse {
    private final ICourseService iCourseService;

    @Operation(summary = "Crear un nuevo curso", description = "Crea un nuevo curso a partir del payload proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<CourseDto> save(@RequestBody CoursePayload coursePayload) throws URISyntaxException {
        iCourseService.saveCourse(coursePayload);
        return ResponseEntity.created(new URI(EndpointsConstants.ENDPOINT_COURSES)).build();
    }

    @Operation(summary = "Obtener un curso por nombre", description = "Devuelve los detalles de un curso según su nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{name}")
    public ResponseEntity<Optional<CourseDto>> getByName(@PathVariable String name) {
        Optional<CourseDto> courseDtoOptional = iCourseService.findCourseByName(name);
        return new ResponseEntity<>(courseDtoOptional, HttpStatus.OK);
    }

    @Operation(summary = "Obtener lista de cursos", description = "Devuelve una lista paginada de todos los cursos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<CourseDto>> findCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<CourseDto> courseDtos = iCourseService.findCourses(pageable);
        return ResponseEntity.ok(courseDtos);
    }

    @Operation(summary = "Actualizar un curso", description = "Actualiza los detalles de un curso existente según su nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{name}")
    public ResponseEntity<Void> update(@PathVariable String name, @RequestBody CoursePayload coursePayload) {
        iCourseService.updateCourseByName(coursePayload, name);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar un curso", description = "Elimina un curso existente según su nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Curso eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        iCourseService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }
}
