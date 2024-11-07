package com.unicar.Class_shedule.commons.Docent.service.Implementation;

import com.unicar.Class_shedule.commons.Docent.factory.DocentFactory;
import com.unicar.Class_shedule.commons.Docent.persistence.entity.Docent;
import com.unicar.Class_shedule.commons.Docent.persistence.repositories.DocentRepository;
import com.unicar.Class_shedule.commons.Docent.presentation.dto.DocentDto;
import com.unicar.Class_shedule.commons.Docent.presentation.payload.DocentPayload;
import com.unicar.Class_shedule.commons.Docent.service.interfaces.IDocentService;
import com.unicar.Class_shedule.commons.security.persistencie.entities.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocentServiceImpl implements IDocentService {

    private final DocentRepository docentRepository;
    private final DocentFactory docentFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveDocent(DocentPayload docentPayload) {
        // Crear una instancia de UserEntity a partir de los datos del payload
        UserEntity userEntity = UserEntity.builder()
                .username(docentPayload.getUsername())
                .fullName(docentPayload.getFullName())
                .dni(docentPayload.getDni())
                .phoneNumber(docentPayload.getPhoneNumber())
                .address(docentPayload.getAddress())
                .email(docentPayload.getEmail())
                .password(passwordEncoder.encode(docentPayload.getPassword()))
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();
        Docent docent = Docent.builder()
                .profile(docentPayload.getProfile())
                .userEntity(userEntity)
                .build();

        docentRepository.save(docent);

    }

    @Override
    @Transactional
    public void deleteByDni(String dni) {
        Docent docent = docentRepository.findByUserEntityDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("DOCENT NOT FOUND WITH DNI " + dni));
        docentRepository.delete(docent);
    }

    @Override
    @Transactional
    public void updateDocent(DocentPayload docentPayload, String dni) {

        Optional<Docent> optionalDocent = docentRepository.findByUserEntityDni(dni);

        if(optionalDocent.isPresent()){
            Docent docent= optionalDocent.get();
            UserEntity userEntity = docent.getUserEntity();

            userEntity.setUsername(docentPayload.getUsername());
            userEntity.setFullName(docentPayload.getFullName());
            userEntity.setDni(docentPayload.getDni());
            userEntity.setPhoneNumber(docentPayload.getPhoneNumber());
            userEntity.setAddress(docentPayload.getAddress());
            userEntity.setEmail(docentPayload.getEmail());
            userEntity.setPassword(docentPayload.getPassword());

            docent.setProfile(docentPayload.getProfile());
            docent.setUserEntity(userEntity);

            docentRepository.save(docent);

        }else {
            throw new UnsupportedOperationException("DOCENT NOT FOUND WITH DNI "+ dni);
        }


    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocentDto> findDocentByDni(String dni) {

       Optional<Docent> optionalDocent = docentRepository.findByUserEntityDni(dni);

       if (optionalDocent.isPresent()){
           Docent docent= optionalDocent.get();
           DocentDto docentDto = docentFactory.docentsDto(docent);
           return Optional.of(docentDto);
       }else {
           throw new EntityNotFoundException("DOCENT NOT FOUND WITH DNI "+ dni);
       }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocentDto> findDoncents(Pageable pageable) {
        Page<Docent>docentPage = docentRepository.findAll(pageable);

        List<DocentDto> docentDtoList = docentPage.stream()
                .map(docent ->docentFactory.docentsDto(docent))
                .collect(Collectors.toList());

        return new PageImpl<>(docentDtoList,pageable,docentPage.getTotalElements());
    }
}
