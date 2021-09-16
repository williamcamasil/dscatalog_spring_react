package com.devsuperior.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

/*Camada de Serviço [CAMADA 2]*/
/*Essa annotation serve para gerenciar as instancias do Spring*/
@Service
public class CategoryService {
	
	/*O spring trata de injetar uma depedencia automática*/
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAll() {
		return repository.findAll();
	}
}
