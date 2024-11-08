package com.unicar.Class_shedule.commons.security.persistencie.repositories;

 import com.unicar.Class_shedule.commons.security.persistencie.entities.RoleEntity;
 import com.unicar.Class_shedule.commons.security.persistencie.entities.RoleEnum;
 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
 import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
    List<RoleEntity>findByRoleEnum(RoleEnum roleEnum);
}
