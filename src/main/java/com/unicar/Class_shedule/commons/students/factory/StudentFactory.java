package com.unicar.Class_shedule.commons.students.factory;

import com.unicar.Class_shedule.commons.students.persistencie.entity.Student;
import com.unicar.Class_shedule.commons.students.presentation.dto.StudentDto;
import org.springframework.stereotype.Component;

@Component
public class StudentFactory {


public StudentDto studentDto (Student student) {


    return  StudentDto.builder()
            .carrer(student.getCarrer())
            .description(student.getDescription())
            .username(student.getUserEntity().getUsername())
            .fullName(student.getUserEntity().getFullName())
            .dni(student.getUserEntity().getDni())
            .phoneNumber(student.getUserEntity().getPhoneNumber())
            .address(student.getUserEntity().getAddress())
            .email(student.getUserEntity().getEmail())
            .build();
}
}
