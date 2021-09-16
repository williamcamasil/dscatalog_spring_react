package com.devsuperior.dscatalog.entities;

import java.io.Serializable;
import java.util.Objects;

/*
- Serializable possibilita o objeto ser convertido em bytes
- Serve para ser gravado em arquivos, passar na redes 
*/
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idLong;
	private String name;
	
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
