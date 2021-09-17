package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

/*
- O resource implementa o controlador REST
- Category = Nome da Entidade
- Resource = Recurso
- @RestController é um annotation que implementa camadas de código para facilitar o desenvolvimento
- O Spring tem muitas annotations, que trazem muitas facilidades e praticidade, que permite seu código ficar limpo e sem muitas lógicas
- @RequestMapping serve para informar qual será a rota de acesso
*/

/*CAMADA CONTROLADOR [CAMADA 2]*/

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	/*Criando os endpoints*/
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll(){
		List<CategoryDTO> list = service.findAll();
		
		
		return ResponseEntity.ok().body(list);
	}
}
