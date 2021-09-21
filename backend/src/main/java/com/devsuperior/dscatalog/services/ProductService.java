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
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

/*Camada de Serviço [CAMADA 2]*/
/*Essa annotation serve para gerenciar as instancias do Spring*/
@Service
public class ProductService {
	
	/*O spring trata de injetar uma depedencia automática*/
	@Autowired
	private ProductRepository repository;
	
	//Usado no método copyDtoToEntity
	@Autowired
	private CategoryRepository categoryRepository;
	
	/*COM PAGINAÇÃO*/
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		/*findAll com paginação*/
		Page<Product> list =  repository.findAll(pageRequest);
		/*dessa forma não precisa mais do stream*/
		return list.map(x -> new ProductDTO(x));
	}	
	
	/*ANTERIOR SEM PAGINAÇÃO*/
	/*Com a annotation Transactional, o próprio framework faz a transação entre o banco de dados
	 * o readOnly melhora a performance do BD*/
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {
		/*Busca todas as informações do BD e inseri na list*/
		List<Product> list =  repository.findAll();
		
		/*FORMA NOVA usando lambda MAP, a qual usa-se o valor de stream convertido, a qual coleta os dados e converte novamente para stream
		 * 						transformando o elemento x do tipo category(lista) em um novo categoryDTO (lista)
		 * 														Collect transforma uma stream em uma lista*/
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		
		/*FORMA ANTIGA
		 * Criando uma lista de ProductDTO para receber os dados do BD*/
		/*List<ProductDTO> listDto = new ArrayList<>();*/
		/*Inserindo as informações do BD dentro da listDto*/
		/*for (Product cat : list) {
			listDto.add(new ProductDTO(cat));
		}*/
		
		/*return listDto;*/
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		//Repository é o responsavel por acessar o BD
		//O Optional evita trabalhar com valor Null, o Spring Data da JPA inseriu ele
		Optional<Product> obj = repository.findById(id);
		//1# - Sem o tratamento de erro 
		//Product entity = obj.get();
		//2# - Com o tratamento de erro, baseado no pacote de exceptions da entity que eu criei
						//O orElseThrow serve para retornar uma exceção, através dele evitamos erro 500 para o usuário quando não tiver dado cadastrado no BD
						//Outros chamadas permitem retornar obj alternativo ou null
						//Nesse caso usamos orElseThrow com lambda, caso não traga nada do repository ele vai retornar a exceção
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		//Com esse novo parametro de getCategories, no construtor dessa Classe irá popular o tipo de categoria que esse produto pertence
		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		
		//Esse método irá servir para não precisar ficar declarando mais de uma vez o mesmo código e 
		//assim otimizar o que será usado e mostrado quando ele for necessário
		copyDtoToEntity(dto, entity);
		// ---> entity.setName(dto.getName());
		//Salvando dados no BD
		entity = repository.save(entity);		
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try { //em caso do id não existir
			//Diferença do finByID para o getOne é que o find ele busca as informações 
			//e o getOne ele instancia os dados e só depois salva sem acessar 2 vezes o BD
			Product entity = repository.getOne(id);
			
			//Esse método irá servir para não precisar ficar declarando mais de uma vez o mesmo código e 
			//assim otimizar o que será usado e mostrado quando ele for necessário
			copyDtoToEntity(dto, entity);
			// ---> entity.setName(dto.getName());
			entity = repository.save(entity);
			return new ProductDTO(entity);
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
	
	//Método privado que irá setar os dados 
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		
		//Povoando as entidades, não coloca o id porque ele é automatico
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		//Copiando as categorias do DTO para as categorias da entidade
		//Limpar o conjunto de categorias que estiver nas categorias 
		entity.getCategories().clear();
		//foreach
		for(CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId());
			//Add categorias de verdade na entidade de produto
			entity.getCategories().add(category);
		}
		
	}
}
