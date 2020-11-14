package com.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursos.model.Arquivo;

public interface ArquivoRepositorio extends JpaRepository<Arquivo, String> {

}
