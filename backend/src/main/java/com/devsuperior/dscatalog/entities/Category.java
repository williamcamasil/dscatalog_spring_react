package com.devsuperior.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/*
- Serializable possibilita o objeto ser convertido em bytes
- Serve para ser gravado em arquivos, passar na redes 
*/

/*Conexão com o BD*/
@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*Indicando o campo e que ele deve gerar o id automaticamente*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLong;
	private String name;
	
	//Instant é um tipo para armazenar dia e hora
	//@Column serve para informar ao BD que será utilizado o padrão UTC, ou seja deverá 
	//subtrair o horário -3 para identificar o horário de brasilia. 
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;
	
	public Category() {
		
	}

	public Category(Long idLong, String name) {
		this.idLong = idLong;
		this.name = name;
	}

	public Long getIdLong() {
		return idLong;
	}

	public void setIdLong(Long idLong) {
		this.idLong = idLong;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}
	
	//Essa annotation serve para atualizar a data e hora atual de criação
	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();
	}
	
	//Essa annotation serve para atualizar a data e hora atual de atualização
	@PreUpdate
	public void preUpdate() {
		updatedAt = Instant.now();
	}

	//hashCode serve para comparar se um objeto é igual a outro
	@Override
	public int hashCode() {
		return Objects.hash(idLong);
	}

	//o equal completa o hashCode porem ele é mais robusto
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(idLong, other.idLong);
	}
	
	
}
