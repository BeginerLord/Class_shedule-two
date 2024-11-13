package com.unicar.Class_shedule.commons.Docent.factory;

import com.unicar.Class_shedule.commons.Docent.persistence.entity.Docent;
import com.unicar.Class_shedule.commons.Docent.presentation.dto.DocentDto;
import org.springframework.stereotype.Component;

@Component
public class DocentFactory {

    public DocentDto docentsDto (Docent docent){
        return DocentDto.builder()
                .profile(docent.getProfile())
                .username(docent.getUser().getUsername())
                .fullName(docent.getUser().getFullName())
                .dni(docent.getUser().getDni())
                .phoneNumber(docent.getUser().getPhoneNumber())
                .address(docent.getUser().getAddress())
                .email(docent.getUser().getEmail())
                .build();
    }

}
