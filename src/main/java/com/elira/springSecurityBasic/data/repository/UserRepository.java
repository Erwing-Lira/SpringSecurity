package com.elira.springSecurityBasic.data.repository;

import com.elira.springSecurityBasic.data.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
