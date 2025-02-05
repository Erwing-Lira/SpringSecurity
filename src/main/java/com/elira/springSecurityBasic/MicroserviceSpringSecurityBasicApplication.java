package com.elira.springSecurityBasic;

import com.elira.springSecurityBasic.data.entity.PermissionEntity;
import com.elira.springSecurityBasic.data.entity.RoleEntity;
import com.elira.springSecurityBasic.data.entity.RoleEnum;
import com.elira.springSecurityBasic.data.entity.UserEntity;
import com.elira.springSecurityBasic.data.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MicroserviceSpringSecurityBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceSpringSecurityBasicApplication.class, args);
	}

	@Bean
	CommandLineRunner init(
			UserRepository userRepository
	) {
		return args -> {
			// Create Permissions
			PermissionEntity createPermissionEntity = PermissionEntity.builder()
					.name("CREATE")
					.build();
			PermissionEntity readPermissionEntity = PermissionEntity.builder()
					.name("READ")
					.build();
			PermissionEntity updatePermissionEntity = PermissionEntity.builder()
					.name("UPDATE")
					.build();
			PermissionEntity deletePermissionEntity = PermissionEntity.builder()
					.name("DELETE")
					.build();
			// Create Roles
			RoleEntity adminRoleEntity = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionList(
							Set.of(
									createPermissionEntity,
									readPermissionEntity,
									updatePermissionEntity,
									deletePermissionEntity
							)
					)
					.build();
			RoleEntity userRoleEntity = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissionList(
							Set.of(
									createPermissionEntity,
									readPermissionEntity,
									updatePermissionEntity
							)
					)
					.build();
			RoleEntity invitedRoleEntity = RoleEntity.builder()
					.roleEnum(RoleEnum.INVITED)
					.permissionList(
							Set.of(
									readPermissionEntity
							)
					)
					.build();
			// Create Users
			UserEntity oneUserEntity = UserEntity.builder()
					.username("root")
					.password("root")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(
							Set.of(adminRoleEntity)
					)
					.build();
			UserEntity twoUserEntity = UserEntity.builder()
					.username("root1")
					.password("root")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(
							Set.of(userRoleEntity)
					)
					.build();
			UserEntity threeUserEntity = UserEntity.builder()
					.username("root2")
					.password("root")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(
							Set.of(invitedRoleEntity)
					)
					.build();
			userRepository.saveAll(
					List.of(
							oneUserEntity,
							twoUserEntity,
							threeUserEntity
					)
			);
		};
	}
}
