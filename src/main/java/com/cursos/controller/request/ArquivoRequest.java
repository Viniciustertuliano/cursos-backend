package com.cursos.controller.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ArquivoRequest {

	private List<MultipartFile> arquivos;

	public List<MultipartFile> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<MultipartFile> arquivos) {
		this.arquivos = arquivos;
	}
	
	
	
}
