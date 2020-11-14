package com.cursos.confg;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cursos.model.Curso;
import com.cursos.model.ERole;
import com.cursos.model.Role;
import com.cursos.model.Usuario;
import com.cursos.repository.CursoRepositorio;
import com.cursos.repository.RoleRepositorio;
import com.cursos.repository.UsuarioRepositorio;

@Configuration
@Profile("dev")
public class CarregaBaseDeDados {

	@Autowired
	private CursoRepositorio repositorio;

	@Autowired
	private RoleRepositorio roleRepo;

	@Autowired
	private UsuarioRepositorio userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner carregaBanco() {
		return args -> {
			Curso curso1 = new Curso("Curso de Java", 123.45);
			Curso curso2 = new Curso("Curso Design de Games", 123.45);

			repositorio.save(curso1);
			repositorio.save(curso2);

			roleRepo.save(new Role(ERole.ROLE_ADMIN));
			roleRepo.save(new Role(ERole.ROLE_USER));

			Usuario user = new Usuario("admin", passwordEncoder.encode("admin123"));
			user.setRoles(Set.of(roleRepo.findByName(ERole.ROLE_ADMIN).orElse(null)));

			userRepo.save(user);

		};
	}

}
