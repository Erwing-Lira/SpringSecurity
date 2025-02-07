package com.elira.springSecurityBasic.service;

import com.elira.springSecurityBasic.data.entity.UserEntity;
import com.elira.springSecurityBasic.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
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
}
