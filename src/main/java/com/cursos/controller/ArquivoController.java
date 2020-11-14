package com.cursos.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cursos.model.Arquivo;
import com.cursos.repository.ArquivoRepositorio;

import org.springframework.core.io.Resource;

@RestController
public class ArquivoController {
	
	@Autowired
	private ArquivoRepositorio repositorio;

	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> download(@PathVariable("id") String id) {
		Arquivo arquivo = repositorio.findById(id)
				.orElseThrow(
				() -> new EntityNotFoundException());
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(arquivo.getTipo()))
				.header(HttpHeaders.CONTENT_DISPOSITION, 
						"attachment; filename=\"" + arquivo.getNome() + "\"")
				.body(new ByteArrayResource(arquivo.getDados()));
	}
	
}
