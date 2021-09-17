package com.devsuperior.dscatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

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
		
		/*FORMA NOVA usando lambda MAP, a qual usa-se o valor de stream convertido, a qual coleta os dados e converte novamente para stream*/
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
}
