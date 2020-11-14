package com.cursos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursos.model.ERole;
import com.cursos.model.Role;

public interface RoleRepositorio extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(ERole roleUser);

}
