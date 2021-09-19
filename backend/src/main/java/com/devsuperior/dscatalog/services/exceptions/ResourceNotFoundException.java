package com.devsuperior.dscatalog.services.exceptions;

//RuntimeException é diferente de Exception, pois o Run... permite você ter uma flexibilização maior para tratar as exceptions 
public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	
}
