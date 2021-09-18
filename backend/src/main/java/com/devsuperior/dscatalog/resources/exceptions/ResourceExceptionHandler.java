package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

/* Essa annotation permite que essa Classe intercepte alguma exceção que 
 * acontecer na Camada de resource de controlador REST e dessa forma é aqui que
 * será tratado as exceções*/
@ControllerAdvice
public class ResourceExceptionHandler {

	//Esse método vai retornar quando houver erro de resposta, a qual será retornada com as configurações que desejamos informar para o user
	/*Excep... essa annotation serve para que saiba o tipo de Exceção que ele irá interceptar, dessa forma quando ocorrer o 
	erro nessa Classe, automaticamente será redirecionado para essse método, por isso foi chamado o orElseThrow com a Classe EntityNotFoundException 
	no CategoryService para conectar todas elas e permitir esse retorno
	 * */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
		
		
		StandardError err = new StandardError();
		//Pega o horário atual
		err.setTimestamp(Instant.now());
		//Pega o código e retorna o status que você desejar
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		//Pega a exceção que deu
		err.setMessage(e.getMessage());
		//Pega o caminho da requisição que foi feito
		err.setPath(request.getRequestURI());
		//Retorna o status que você quiser, e o corpo com as informações sobre o erro nesse caso o objeto err 
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}
	
}
