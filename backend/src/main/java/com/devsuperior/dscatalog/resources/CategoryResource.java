package com.devsuperior.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.entities.Category;

/*
- O resource implementa o controlador REST
- Category = Nome da Entidade
- Resource = Recurso
- @RestController é um annotation que implementa camadas de código para facilitar o desenvolvimento
- O Spring tem muitas annotations, que trazem muitas facilidades e praticidade, que permite seu código ficar limpo e sem muitas lógicas
- @RequestMapping serve para informar qual será a rota de acesso
*/

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	/*Criando os endpoints*/
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll(){
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Electronics"));
		
		return ResponseEntity.ok().body(list);
	}
}
