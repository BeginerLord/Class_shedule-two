package com.unicar.Class_shedule.commons.Docent.presentation.controllers;

import com.unicar.Class_shedule.commons.Docent.presentation.dto.DocentDto;
import com.unicar.Class_shedule.commons.Docent.presentation.payload.DocentPayload;
import com.unicar.Class_shedule.commons.Docent.service.interfaces.IDocentService;
import com.unicar.Class_shedule.commons.utils.constants.EndpointsConstants;
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

    @PostMapping
    public ResponseEntity<DocentDto>save(@RequestBody DocentPayload docentPayload) throws URISyntaxException {
        iDocentService.saveDocent(docentPayload);

        return ResponseEntity.created(new URI(EndpointsConstants.ENDPOINT_TEACHER)).build();
    }

    @GetMapping("/{dni}")

    public ResponseEntity< Optional<DocentDto>>getDni(@PathVariable String dni){

        Optional<DocentDto> docentDtoOptional = iDocentService.findDocentByDni(dni);
        return  new ResponseEntity<>(docentDtoOptional, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<DocentDto>>  findDocents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<DocentDto> docentDtos = iDocentService.findDoncents(pageable);

        return ResponseEntity.ok(docentDtos);
    }

    @PutMapping("/{dni}")
    public ResponseEntity<Void> update(@PathVariable String dni, @RequestBody DocentPayload docentPayload){
        iDocentService.updateDocent(docentPayload, dni);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<?> delete(@PathVariable String dni){
        iDocentService.deleteByDni(dni);

       return ResponseEntity.noContent().build();
    }
}
