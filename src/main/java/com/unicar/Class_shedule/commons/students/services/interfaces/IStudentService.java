package com.unicar.Class_shedule.commons.students.services.interfaces;

import com.unicar.Class_shedule.commons.students.presentation.dto.StudentDto;
import com.unicar.Class_shedule.commons.students.presentation.payload.StudentPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IStudentService {

    void saveStudent(StudentPayload studentPayload);

    void deleteByDni(String dni);

    void updateStudent(StudentPayload studentPayload, String dni);

    Optional<StudentDto> findStudentByDni(String dni);

    Page<StudentDto> findStudents(Pageable pageable);
}
