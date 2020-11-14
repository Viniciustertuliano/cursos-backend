package br.com.cursos.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import com.cursos.model.Curso;
import com.cursos.repository.CursoRepositorio;

@SpringBootTest
public class CursoRepositorioTest {

	@Autowired
	private CursoRepositorio repositorio;
	
	@Test
	void findByNomeSimples() {
		List<Curso> cursos = repositorio.findByNome("Curso de Java");
		Assertions.assertThat(cursos).hasSize(1);
	}
	
	@Test
	void findByNomeLike() {
		List<Curso> cursos = repositorio.findByNomeLike("%Java");
		Assertions.assertThat(cursos).hasSize(1);
	}
	
	@Test
	void findByNomeLikeOrdenado() {
		List<Curso> cursos = repositorio.findByNomeLikeOrderByNomeAscPrecoDesc("Curso%");
		Assertions.assertThat(cursos).hasSize(2);
	}
	
	@Test
	void findByNomeComQuery() {
		List<Curso> cursos = repositorio.buscaPorNome("Curso de Java");
		Assertions.assertThat(cursos).hasSize(1);
	}
	
	@Test
	void findByNomeComQueryNativa() {
		List<Curso> cursos = repositorio.buscaPorNomeNativo("Curso de Java");
		Assertions.assertThat(cursos).hasSize(1);
	}
	
	@Test
	void findByNomeNamedQuery() {
		List<Curso> cursos = repositorio.filtraPorNome("Curso de Java");
		Assertions.assertThat(cursos).hasSize(1);
	}
	
	@Test
	void findByExample() {
		
		Curso filtro = new Curso();
		filtro.setNome("java");
		filtro.setPreco(123.45);
		
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING);
		
		Example<Curso> exemplo = Example.of(filtro, matcher);
		
		List<Curso> cursos = repositorio.findAll(exemplo);
		Assertions.assertThat(cursos).hasSize(1);
	}
	
}
