package com.devsuperior.dscatalog.services.exceptions;

//RuntimeException é diferente de Exception, pois o Run... permite você ter uma flexibilização maior para tratar as exceptions 
public class DatabaseException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public DatabaseException(String msg) {
		super(msg);
	}
	
}
