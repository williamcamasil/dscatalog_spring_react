package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

/*Camada de Serviço [CAMADA 2]*/
/*Essa annotation serve para gerenciar as instancias do Spring*/
@Service
public class CategoryService {
	
	/*O spring trata de injetar uma depedencia automática*/
	@Autowired
	private CategoryRepository repository;
	
	/*COM PAGINAÇÃO*/
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		/*findAll com paginação*/
		Page<Category> list =  repository.findAll(pageRequest);
		/*dessa forma não precisa mais do stream*/
		return list.map(x -> new CategoryDTO(x));
	}	
	
	/*ANTERIOR SEM PAGINAÇÃO*/
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
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
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

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try { //em caso do id não existir
			//Diferença do finByID para o getOne é que o find ele busca as informações 
			//e o getOne ele instancia os dados e só depois salva sem acessar 2 vezes o BD
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try { //Em caso de tentar deletar o ID que não existe
			repository.deleteById(id);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		//Esse tratamento será quando a categoria tiver produto, dessa forma se 
		//tiver não deixar excluir, apenas se não tiver, pois dessa forma exite integridade referencial 
		catch(DataIntegrityViolationException e) { 
			throw new DatabaseException("Integrity violation");
		}
		
	}
}
