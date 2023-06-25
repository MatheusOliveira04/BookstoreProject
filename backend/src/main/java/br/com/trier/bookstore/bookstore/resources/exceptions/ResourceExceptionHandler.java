package br.com.trier.bookstore.bookstore.resources.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.trier.bookstore.bookstore.services.exceptions.IntegrityViolation;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(IntegrityViolation.class)
	public ResponseEntity<StandardError> getIntegrityViolationException(
			IntegrityViolation ex, HttpServletRequest req){
		StandardError error = new StandardError(LocalDateTime.now()
				, HttpStatus.BAD_REQUEST.value(), ex.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
