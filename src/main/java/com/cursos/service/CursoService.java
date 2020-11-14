package com.cursos.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.cursos.model.Curso;
import com.cursos.repository.CursoRepositorio;

@Service
public class CursoService {

	@Autowired
	private CursoRepositorio repositorio;
	
	public Curso salvar(Curso curso) {
		return repositorio.save(curso);
	}
	
	public Curso alterar(Integer id, Curso curso) {
		Curso cursoBanco = getCurso(id);
		cursoBanco.setNome(curso.getNome());
		cursoBanco.setPreco(curso.getPreco());
		cursoBanco.setAtualizadoEm(LocalDateTime.now());
		if (cursoBanco.getArquivo() != null)
			cursoBanco.setArquivo(curso.getArquivo());
		
		return salvar(cursoBanco);
	}
	
	public List<Curso> filtraPor(Curso filtro) {
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING);
		
		Example<Curso> exemplo = Example.of(filtro, matcher);
		
		return repositorio.findAll(exemplo);
	}
	
	public Curso getCurso(Integer id) {
		return repositorio
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
	}
	
	public void excluir(Integer id) {
		repositorio.deleteById(id);
	}
	
}
