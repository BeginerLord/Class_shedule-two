package com.unicar.Class_shedule.commons.students.services.implementation;

import com.unicar.Class_shedule.commons.Course.persistence.entity.CourseEntity;
import com.unicar.Class_shedule.commons.Course.persistence.repository.CourseRepository;
import com.unicar.Class_shedule.commons.security.persistencie.entities.RoleEntity;
import com.unicar.Class_shedule.commons.security.persistencie.entities.RoleEnum;
import com.unicar.Class_shedule.commons.security.persistencie.entities.UserEntity;
import com.unicar.Class_shedule.commons.security.persistencie.repositories.RoleRepository;
import com.unicar.Class_shedule.commons.students.factory.StudentFactory;
import com.unicar.Class_shedule.commons.students.persistencie.entity.Student;
import com.unicar.Class_shedule.commons.students.persistencie.repositories.IStudentsRepository;
 import com.unicar.Class_shedule.commons.students.presentation.dto.StudentDto;
import com.unicar.Class_shedule.commons.students.presentation.payload.EnroolAlCoursePayload;
import com.unicar.Class_shedule.commons.students.presentation.payload.StudentPayload;
import com.unicar.Class_shedule.commons.students.services.interfaces.IStudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private  final IStudentsRepository  iStudentsRepository;
    private final CourseRepository courseRepository;

    private  final StudentFactory studentFactory;
    private final PasswordEncoder passwordEncoder;
    private  final RoleRepository roleRepository;

    @Override
    @Transactional
    public void saveStudent(StudentPayload studentPayload) {

        List<RoleEntity>roleEntities =roleRepository.findByRoleEnum(RoleEnum.STUDENT);
        if (roleEntities.isEmpty()){

            throw new IllegalArgumentException("role student not found");

        }

        RoleEntity roleEntity = roleEntities.stream()
                .filter(role -> role.getRoleEnum() == RoleEnum.STUDENT)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ROLE STUDENT NOT FOUND IN THE FILTER"));
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleEntity);


        // Crear una instancia de UserEntity a partir de los datos del payload
        UserEntity userEntity = UserEntity.builder()
                .username(studentPayload.getUsername())
                .fullName(studentPayload.getFullName())
                .dni(studentPayload.getDni())
                .phoneNumber(studentPayload.getPhoneNumber())
                .address(studentPayload.getAddress())
                .email(studentPayload.getEmail())
                .password(passwordEncoder.encode(studentPayload.getPassword()))
                .roles(roles)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();
   Student student = Student.builder()

           .carrer(studentPayload.getCarrer())
           .description(studentPayload.getDescription())
           .userEntity(userEntity)
           .build();

   iStudentsRepository.save(student);

    }
    @Override
    @Transactional
    public void deleteByDni(String dni) {
        Student student = iStudentsRepository.findByUserEntityDni(dni)
                .orElseThrow(() -> new EntityNotFoundException("STUDENT NOT FOUND WITH DNI " + dni));
        iStudentsRepository.delete(student);
    }

    @Override
    @Transactional
    public void updateStudent(StudentPayload studentPayload, String dni) {
        Student student = iStudentsRepository.findByUserEntityDni(dni)
                .orElseThrow(() -> new EntityNotFoundException("STUDENT NOT FOUND WITH DNI " + dni));

        UserEntity userEntity = student.getUserEntity();
        userEntity.setUsername(studentPayload.getUsername());
        userEntity.setFullName(studentPayload.getFullName());
        userEntity.setPhoneNumber(studentPayload.getPhoneNumber());
        userEntity.setAddress(studentPayload.getAddress());
        userEntity.setEmail(studentPayload.getEmail());

        // Update password only if it's not null or empty
        if (studentPayload.getPassword() != null && !studentPayload.getPassword().isEmpty()) {
            userEntity.setPassword(passwordEncoder.encode(studentPayload.getPassword())); // Hash the password
        }


        student.setCarrer(studentPayload.getCarrer());
        student.setDescription(studentPayload.getDescription());
        student.setUserEntity(userEntity);
        iStudentsRepository.save(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentDto> findStudentByDni(String dni) {
        return iStudentsRepository.findByUserEntityDni(dni)
                .map(student -> studentFactory.studentDto(student))
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException("STUDENT NOT FOUND WITH DNI " + dni));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDto> findStudents(Pageable pageable) {

        Page<Student>studentPage =iStudentsRepository.findAll(pageable);
        List<StudentDto>studentDtoList=studentPage.stream().map(
                student -> studentFactory.studentDto(student)
        ).collect(Collectors.toList());
        return new PageImpl<>(studentDtoList, pageable,studentPage.getTotalElements());
    }

    @Override
    @Transactional
    public void enrollACourse(EnroolAlCoursePayload enroolAlCoursePayload) {
        Optional<Student> studentDto = iStudentsRepository.findByUserEntityDni(enroolAlCoursePayload.getDni());
        Optional<CourseEntity> course = courseRepository.findById(enroolAlCoursePayload.getIdCurso());

        if (studentDto.isPresent() && course.isPresent()) {
            Student estudiante = studentDto.get();
            CourseEntity curso = course.get();

            // Verificar si el estudiante ya está inscrito en el curso
            if (estudiante.getCourses().contains(curso)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El estudiante ya está inscrito en este curso");
            }

            // Si no está inscrito, agregar el curso y guardar
            estudiante.getCourses().add(curso);
            iStudentsRepository.save(estudiante);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante o curso no encontrado");
        }
    }

    @Override
    public List<Object[]> getCourseDetailsForUser() {

        // Obtener el nombre de usuario (username) de la sesión
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println(username);
        // Buscar al estudiante en la base de datos usando el username
        Optional<Student> student = iStudentsRepository.findByUserEntityUsername(username);

        // Verificar si el estudiante fue encontrado
        if (student.isEmpty()) {
            throw new RuntimeException("Estudiante no encontrado para el usuario autenticado");
        }

        // Obtener el studentId desde el estudiante encontrado
        Long studentIdFromDb = student.get().getId();

        // Verificar si el studentId proporcionado coincide con el del estudiante encontrado
        if (!studentIdFromDb.equals(studentIdFromDb)) {
            throw new RuntimeException("El ID del estudiante no coincide con el usuario autenticado");
        }

        // Obtener los detalles de los cursos para el estudiante desde el repositorio de cursos
      //  return iStudentsRepository.getCourseDetailsByStudent(studentIdFromDb);
    return (List<Object[]>) ResponseEntity.ok(ResponseEntity.ok());
    }


}


























