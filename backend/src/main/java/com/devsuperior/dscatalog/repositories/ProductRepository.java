package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Product;

/*Camada de acesso a dados e monitoramento com entidades [CAMADA 1]*/
/*Essa implementação funciona para qualquer Banco de Dados relacional*/

/*Com essa annotation os objetos passam a ser gerenciados pela spring*/
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
}
