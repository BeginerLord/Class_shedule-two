package com.unicar.Class_shedule.commons.Docent.service.interfaces;

import com.unicar.Class_shedule.commons.Docent.presentation.dto.DocentDto;
import com.unicar.Class_shedule.commons.Docent.presentation.payload.DocentPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IDocentService {
    void saveDocent(DocentPayload docentPayload);

    void deleteByDni(String dni);

    void updateDocent(DocentPayload docentPayload, String dni);

    Optional<DocentDto> findDocentByDni(String dni);

    Page<DocentDto>findDoncents(Pageable pageable);


}
