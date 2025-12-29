package com.ruby.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){
		List<Map<String, String>> errorDetails = e.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error->{
					Map<String, String> err = new HashMap<>();
					err.put("code",error.getCode());
					err.put("defaultMessage", error.getDefaultMessage());
					return err;
				})
				.collect(Collectors.toList());
		
		Map<String, Object> response = new HashMap<>();
		response.put("status", 400);
		response.put("errors", errorDetails);
		
		return ResponseEntity.badRequest().body(response);
	}
}
