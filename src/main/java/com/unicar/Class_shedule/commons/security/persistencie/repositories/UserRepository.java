package com.unicar.Class_shedule.commons.security.persistencie.repositories;

import com.unicar.Class_shedule.commons.security.persistencie.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);

}
