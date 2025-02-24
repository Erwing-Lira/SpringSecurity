package com.elira.springSecurityBasic.service;

import com.elira.springSecurityBasic.controller.request.AuthCreateUserRequest;
import com.elira.springSecurityBasic.controller.response.AuthResponse;
import com.elira.springSecurityBasic.controller.request.AuthLoginRequest;
import com.elira.springSecurityBasic.data.entity.RoleEntity;
import com.elira.springSecurityBasic.data.entity.RoleEnum;
import com.elira.springSecurityBasic.data.entity.UserEntity;
import com.elira.springSecurityBasic.data.repository.RoleRepository;
import com.elira.springSecurityBasic.data.repository.UserRepository;
import com.elira.springSecurityBasic.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            JwtUtils jwtUtils,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Username does not found")
                );
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userEntity.getRoles().forEach(
                role -> authorityList.add(
                        new SimpleGrantedAuthority(
                                "ROLE_".concat(role.getRoleEnum().name())
                        )
                )
        );
        userEntity.getRoles()
                .stream()
                .flatMap(
                        role -> role.getPermissionList().stream()
                )
                .forEach(
                        permission -> authorityList.add(
                                new SimpleGrantedAuthority(permission.getName())
                        )
                );
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList
        );
    }

    public AuthResponse loginUser(
            AuthLoginRequest authLoginRequest
    ) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(
                username,
                "User load successfuly",
                accessToken,
                true
        );
        return authResponse;
    }

    public Authentication authenticate(
            String username,
            String password
    ) {
        UserDetails userDetails = this.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(
                username,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    public AuthResponse createUser(
            AuthCreateUserRequest authCreateUserRequest
    ) {
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        List<String> rolesList = authCreateUserRequest.roleRequest().rolesName();

        List<RoleEnum> validRoles = rolesList.stream()
                .map(roleName -> {
                    try {
                        return RoleEnum.valueOf(roleName);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Set<RoleEntity> roleEntitySet = new HashSet<>(roleRepository.findRoleEntitiesByRoleEnumIn(validRoles));
        if (roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntitySet)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();

        UserEntity userCreated = userRepository.save(userEntity);
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userCreated.getRoles().forEach(role ->
                authorityList.add(
                        new SimpleGrantedAuthority(
                                "ROLE_".concat(role.getRoleEnum().name())
                        )
                )
        );
        userCreated.getRoles()
                .stream()
                .flatMap(role ->
                        role.getPermissionList().stream()
                )
                .forEach(permission ->
                        authorityList.add(
                                new SimpleGrantedAuthority(permission.getName())
                        )
                );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userCreated.getUsername(),
                userCreated.getPassword(),
                authorityList
        );
        String accessToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(
                userCreated.getUsername(),
                "User created successful",
                accessToken,
                true
        );
        return authResponse;
    }
}
