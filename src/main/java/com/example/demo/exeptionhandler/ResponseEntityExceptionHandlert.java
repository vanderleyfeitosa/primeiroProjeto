package com.example.demo.exeptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class ResponseEntityExceptionHandlert extends ResponseEntityExceptionHandler {
	
@Autowired
private MessageSource messageSource;
 @Override
protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
	 Locale locale = LocaleContextHolder.getLocaleContext().getLocale();
	String mesagemUsuario = messageSource.getMessage("mensagem.inavalida", null, locale);
	String mensagemDesenvolvidor =ex.getCause() !=null ? ex.getCause().toString() :ex.toString();
	List<Erro> erros = Arrays.asList(new Erro(mesagemUsuario, mensagemDesenvolvidor));
	return handleExceptionInternal(ex,erros , headers, HttpStatus.BAD_REQUEST, request);
	
	
}
 @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		 List<Erro> erros= criarListaDeErro(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers,  HttpStatus.BAD_REQUEST, request);
	}
 @ExceptionHandler({EmptyResultDataAccessException.class})
 public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,WebRequest request) {
	 Locale locale = LocaleContextHolder.getLocaleContext().getLocale();
	 String mesagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, locale);
		String mensagemDesenvolvidor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mesagemUsuario, mensagemDesenvolvidor));
	 return handleExceptionInternal(ex, erros, new HttpHeaders(),  HttpStatus.NOT_FOUND, request);
	 
 }
 private List<Erro>criarListaDeErro( BindingResult bindingResult ){
	 List<Erro> erros = new ArrayList<>();
	 for(FieldError fielderro: bindingResult.getFieldErrors()) {
	 String mensagemUsuario = messageSource.getMessage(fielderro, LocaleContextHolder.getLocale());
	 String mensagemDesenvolvidor =fielderro.toString();
	 erros.add(new Erro(mensagemUsuario, mensagemDesenvolvidor));
	 }
	 return erros;
 }
 
 public static class Erro {
	 private String mensagemUsuario;
	 private String mensagemDesenvolvidor;
	public Erro(String mensagemUsuario, String mensagemDesenvolvidor) {
	
		this.mensagemUsuario = mensagemUsuario;
		this.mensagemDesenvolvidor = mensagemDesenvolvidor;
	}
	public String getMensagemUsuario() {
		return mensagemUsuario;
	}
	public String getMensagemDesenvolvidor() {
		return mensagemDesenvolvidor;
	}

 }
}
