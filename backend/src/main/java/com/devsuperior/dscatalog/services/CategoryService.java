package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

/*Camada de Serviço [CAMADA 2]*/
/*Essa annotation serve para gerenciar as instancias do Spring*/
@Service
public class CategoryService {
	
	/*O spring trata de injetar uma depedencia automática*/
	@Autowired
	private CategoryRepository repository;
	
	/*Com a annotation Transactional, o próprio framework faz a transação entre o banco de dados
	 * o readOnly melhora a performance do BD*/
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		/*Busca todas as informações do BD e inseri na list*/
		List<Category> list =  repository.findAll();
		
		/*FORMA NOVA usando lambda MAP, a qual usa-se o valor de stream convertido, a qual coleta os dados e converte novamente para stream
		 * 						transformando o elemento x do tipo category(lista) em um novo categoryDTO (lista)
		 * 														Collect transforma uma stream em uma lista*/
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		/*FORMA ANTIGA
		 * Criando uma lista de CategoryDTO para receber os dados do BD*/
		/*List<CategoryDTO> listDto = new ArrayList<>();*/
		/*Inserindo as informações do BD dentro da listDto*/
		/*for (Category cat : list) {
			listDto.add(new CategoryDTO(cat));
		}*/
		
		/*return listDto;*/
	}

	public CategoryDTO findById(Long id) {
		//Repository é o responsavel por acessar o BD
		//O Optional evita trabalhar com valor Null, o Spring Data da JPA inseriu ele
		Optional<Category> obj = repository.findById(id);
		//1# - Sem o tratamento de erro 
		//Category entity = obj.get();
		//2# - Com o tratamento de erro, baseado no pacote de exceptions da entity que eu criei
						//O orElseThrow serve para retornar uma exceção, através dele evitamos erro 500 para o usuário quando não tiver dado cadastrado no BD
						//Outros chamadas permitem retornar obj alternativo ou null
						//Nesse caso usamos orElseThrow com lambda, caso não traga nada do repository ele vai retornar a exceção
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		//Salvando dados no BD
		entity = repository.save(entity);		
		return new CategoryDTO(entity);
	}
}
