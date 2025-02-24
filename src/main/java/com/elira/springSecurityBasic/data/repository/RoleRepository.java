package com.elira.springSecurityBasic.data.repository;

import com.elira.springSecurityBasic.data.entity.RoleEntity;
import com.elira.springSecurityBasic.data.entity.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<RoleEnum> roles);
}
