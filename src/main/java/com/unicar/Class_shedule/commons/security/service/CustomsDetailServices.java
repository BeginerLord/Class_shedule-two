package com.unicar.Class_shedule.commons.security.service;




import com.unicar.Class_shedule.commons.security.persistencie.entities.RoleEntity;
import com.unicar.Class_shedule.commons.security.persistencie.entities.UserEntity;
import com.unicar.Class_shedule.commons.security.persistencie.repositories.RoleRepository;
import com.unicar.Class_shedule.commons.security.persistencie.repositories.UserRepository;
import com.unicar.Class_shedule.commons.security.presentation.dtos.AuthCreateUserRequest;
import com.unicar.Class_shedule.commons.security.presentation.dtos.AuthLoginRequest;
import com.unicar.Class_shedule.commons.security.presentation.dtos.AuthResponse;
import com.unicar.Class_shedule.commons.security.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CustomsDetailServices implements UserDetailsService {

    // Inyecta el componente JwtUtils, que se utiliza para manejar la creación y validación de tokens JWT
    @Autowired
    private JwtUtils jwtUtils;

    // Inyecta el componente PasswordEncoder para codificar y verificar contraseñas
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Inyecta el repositorio de usuarios para acceder y manipular datos de usuarios en la base de datos
    @Autowired
    private UserRepository userRepository;

    // Inyecta el repositorio de roles para acceder y manipular datos de roles en la base de datos
    @Autowired
    private RoleRepository roleRepository;

    // Sobrescribe el método de la interfaz UserDetailsService para cargar un usuario por su nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario en la base de datos por su nombre de usuario
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                // Si el usuario no se encuentra, lanza una excepción UsernameNotFoundException
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Crea una lista de SimpleGrantedAuthority para los roles del usuario


        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().getRoleName()))));

        // Retorna un objeto User que implementa UserDetails con la información del usuario, incluyendo las autoridades (roles)
        return new User(userEntity.getUsername(), userEntity.getPassword(),
                userEntity.isEnabled(), userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(), userEntity.isAccountNoLocked(),
                authorityList);
    }


    // Método para crear un nuevo usuario y devolver una respuesta de autenticación con un token JWT
    public AuthResponse createUser(@Valid AuthCreateUserRequest createUserRequest) {
        // Extrae los datos del objeto de solicitud
        String username = createUserRequest.username();
        String password = createUserRequest.password();
        String fullName = createUserRequest.fullName();
        String dni = createUserRequest.dni();
        String address = createUserRequest.address();
        String email = createUserRequest.email();
        String phoneNumber = createUserRequest.phoneNumber();

        List<String> rolesRequest = createUserRequest.roleRequest().roleListName();

        Set<RoleEntity> roleEntityList = roleRepository.findRoleEntitiesByRoleEnumIn(rolesRequest).stream().collect(Collectors.toSet());

        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist.");
        }




        // Construye una nueva entidad de usuario con los datos proporcionados y los roles encontrados
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))  // Codifica la contraseña
                .fullName(fullName)  // Añade el nombre completo
                .dni(dni)  // Añade el DNI
                .address(address)  // Añade la dirección
                .email(email)  // Añade el correo electrónico
                .phoneNumber(phoneNumber)  // Añade el número de teléfono
                .roles(roleEntityList)  // Asigna los roles al usuario
                .isEnabled(true)  // Habilita la cuenta
                .accountNoLocked(true)  // Marca la cuenta como no bloqueada
                .accountNoExpired(true)  // Marca la cuenta como no expirada
                .credentialNoExpired(true)  // Marca las credenciales como no expiradas
                .build();

        // Guarda la nueva entidad de usuario en la base de datos
        UserEntity userSaved = userRepository.save(userEntity);

        // Crea una lista de SimpleGrantedAuthority para los roles del usuario guardado
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userEntity.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        // Establece la autenticación en el contexto de seguridad actual con el usuario recién creado y sus roles
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        // Crea un token JWT para el usuario autenticado
        String accessToken = jwtUtils.createToken(authentication);

        // Retorna una respuesta de autenticación con el token JWT
        return new AuthResponse(username, "User created successfully", accessToken, true);
    }


    // Método para autenticar un usuario y devolver una respuesta de autenticación con un token JWT
    public AuthResponse loginUser(@Valid AuthLoginRequest authLoginRequest) {
        // Extrae el nombre de usuario y la contraseña del objeto de solicitud
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        // Realiza la autenticación del usuario
        Authentication authentication = this.authenticate(username, password);
        // Establece la autenticación en el contexto de seguridad actual
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Crea un token JWT para el usuario autenticado
        String accessToken = jwtUtils.createToken(authentication);
        // Retorna una respuesta de autenticación con el token JWT
        return new AuthResponse(username, "User logged in successfully", accessToken, true);
    }

    // Método auxiliar para autenticar un usuario comparando su nombre de usuario y contraseña
    public Authentication authenticate(String username, String password) {
        // Carga los detalles del usuario utilizando su nombre de usuario
        UserDetails userDetails = this.loadUserByUsername(username);

        // Si el usuario no se encuentra, lanza una excepción de credenciales inválidas
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Verifica si la contraseña proporcionada coincide con la contraseña almacenada
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            // Si la contraseña es incorrecta, lanza una excepción de credenciales inválidas
            throw new BadCredentialsException("Incorrect Password");
        }

        // Retorna un token de autenticación con el nombre de usuario y sus roles (authorities)
        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}