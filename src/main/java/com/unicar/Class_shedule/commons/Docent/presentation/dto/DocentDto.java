package com.unicar.Class_shedule.commons.Docent.presentation.dto;

import lombok.Builder;

@Builder
public record DocentDto(String profile,
                        String username,
                        String fullName,
                        String dni,
                        String phoneNumber,
                        String address,
                        String email
) {
}
