package com.devsuperior.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

/*Controlador TEM que ser enxuto, não pode ficar carregando com muito código, dentre eles também NÃO para try catch*/

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
	
	//Criando um requisição para chamar um unico categoria
	@GetMapping(value = "/{id}")
	//PathVariable serve para conectar o id da rota acima com o id do parametro passado
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	//@PostMapping serve para efetuar a request POST
	@PostMapping							//@RequestBody annotation serve para reconhecer e enviar um objeto
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
		dto = service.insert(dto);
		
		//Essa é a estrutura que mostra o estado 201 de criação de conteúdo, e a location para informar o endpoint no Headers
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
							//Trocado de ok para created para dar 201 de criação
		return ResponseEntity.created(uri).body(dto);
	}
	
	//@PutMapping serve para efetuar a request PUT
	@PutMapping(value = "/{id}")						
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")						
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		//Retorna 204 que significa que o conteúdo foi excluído com sucesso
		return ResponseEntity.noContent().build();
	}
}
