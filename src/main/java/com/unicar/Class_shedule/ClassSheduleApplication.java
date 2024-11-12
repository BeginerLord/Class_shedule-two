package com.unicar.Class_shedule;

import com.unicar.Class_shedule.commons.security.persistencie.entities.RoleEntity;
import com.unicar.Class_shedule.commons.security.persistencie.entities.RoleEnum;
import com.unicar.Class_shedule.commons.security.persistencie.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ClassSheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassSheduleApplication.class, args);
	}
	@Bean
	CommandLineRunner init(RoleRepository roleRepository) {
		return args -> {
			// Verificar si los roles ya existen
			if (roleRepository.findByRoleEnum(RoleEnum.ADMIN).isEmpty()) {
				RoleEntity roleSecretary = RoleEntity.builder()
						.roleEnum(RoleEnum.ADMIN)
						.build();
				roleRepository.save(roleSecretary);
			}

			if (roleRepository.findByRoleEnum(RoleEnum.TEACHER).isEmpty()) {
				RoleEntity roleTeacher = RoleEntity.builder()
						.roleEnum(RoleEnum.TEACHER)
						.build();
				roleRepository.save(roleTeacher);
			}

			if (roleRepository.findByRoleEnum(RoleEnum.STUDENT).isEmpty()) {
				RoleEntity roleStudent = RoleEntity.builder()
						.roleEnum(RoleEnum.STUDENT)
						.build();
				roleRepository.save(roleStudent);
			}
		};
	}}