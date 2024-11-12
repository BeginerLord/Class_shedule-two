package com.unicar.Class_shedule.commons.students.persistencie.repositories;

import com.unicar.Class_shedule.commons.students.persistencie.entity.Student;
 import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IStudentsRepository  extends JpaRepository<Student, Integer> {

    Optional<Student>findByUserEntityDni(String dni);
     Optional<Student>findByUserEntityUsername(String username);


}
