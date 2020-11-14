package com.cursos.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursos.controller.response.JwtResponse;
import com.cursos.model.ERole;
import com.cursos.model.Role;
import com.cursos.model.Usuario;
import com.cursos.repository.RoleRepositorio;
import com.cursos.repository.UsuarioRepositorio;
import com.cursos.security.JwtUtils;
import com.cursos.security.UserDetailsImpl;

@Service
public class UsuarioService {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioRepositorio userRepository;
	
	@Autowired
	RoleRepositorio roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	public JwtResponse authenticateUser(String username, String password) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) 
				authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return new JwtResponse(jwt, userDetails.getId(), 
				userDetails.getUsername(), roles);
	}
	
	public void registerUser(
			String username, 
			String password,
			String confirmPassword,
			Set<String> strRoles) {
		
		if (userRepository.existsByUsername(username)) {
			throw new EntityExistsException("Usuário já existe");
		}
		
		Usuario user = new Usuario(username, passwordEncoder.encode(password));
		
		Set<Role> roles = new HashSet<Role>();
		
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new EntityNotFoundException(
							"Role não encontrada"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new EntityNotFoundException(
							"Role não encontrada"));
					roles.add(adminRole);
					break;

				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new EntityNotFoundException(
							"Role não encontrada"));
					roles.add(userRole);
				}
			});
		}
		
		user.setRoles(roles);
		userRepository.save(user);
	}

}
