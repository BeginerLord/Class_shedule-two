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
			// Crear ROLES
			RoleEntity roleSecretary = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.build();

			RoleEntity roleTeacher = RoleEntity.builder()
					.roleEnum(RoleEnum.TEACHER)
					.build();

			RoleEntity roleStudent = RoleEntity.builder()
					.roleEnum(RoleEnum.STUDENT)
					.build();

			// Guardar roles en la base de datos
			roleRepository.saveAll(List.of(roleSecretary, roleTeacher, roleStudent));
		};
	}
}