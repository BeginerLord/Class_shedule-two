package com.unicar.Class_shedule.commons.Docent.presentation.controllers;

import com.unicar.Class_shedule.commons.Docent.presentation.dto.DocentDto;
import com.unicar.Class_shedule.commons.Docent.presentation.payload.DocentPayload;
import com.unicar.Class_shedule.commons.Docent.service.interfaces.IDocentService;
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
@RequestMapping(path = EndpointsConstants.ENDPOINT_TEACHER)

public class ControllerDocent {

    private final IDocentService iDocentService;

    @Operation(summary = "Crear un nuevo docente", description = "Crea un nuevo docente a partir del payload proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Docente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<DocentDto> save(@RequestBody DocentPayload docentPayload) throws URISyntaxException {
        iDocentService.saveDocent(docentPayload);

        return ResponseEntity.created(new URI(EndpointsConstants.ENDPOINT_TEACHER)).build();
    }

    @Operation(summary = "Obtener un docente por DNI", description = "Devuelve los detalles de un docente según su DNI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Docente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{dni}")

    public ResponseEntity<Optional<DocentDto>> getDni(@PathVariable String dni) {

        Optional<DocentDto> docentDtoOptional = iDocentService.findDocentByDni(dni);
        return new ResponseEntity<>(docentDtoOptional, HttpStatus.OK);
    }

    @Operation(summary = "Obtener lista de docentes", description = "Devuelve una lista paginada de todos los docentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<DocentDto>> findDocents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userEntity.username") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<DocentDto> docentDtos = iDocentService.findDoncents(pageable);

        return ResponseEntity.ok(docentDtos);
    }

    @Operation(summary = "Actualizar un docente", description = "Actualiza los detalles de un docente existente según su DNI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Docente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Docente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{dni}")
    public ResponseEntity<Void> update(@PathVariable String dni, @RequestBody DocentPayload docentPayload) {
        iDocentService.updateDocent(docentPayload, dni);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar un docente", description = "Elimina un docente existente según su DNI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Docente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Docente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{dni}")
    public ResponseEntity<?> delete(@PathVariable String dni) {
        iDocentService.deleteByDni(dni);

        return ResponseEntity.noContent().build();
    }
}
