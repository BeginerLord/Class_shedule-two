package com.unicar.Class_shedule.commons.Docent.persistence.repositories;

import com.unicar.Class_shedule.commons.Docent.persistence.entity.Docent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocentRepository extends JpaRepository<Docent,Long> {

    Optional<Docent>findByUserEntityDni(String dni);


}
