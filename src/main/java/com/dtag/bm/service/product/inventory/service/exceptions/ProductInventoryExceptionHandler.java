package com.dtag.bm.service.product.inventory.service.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ProductInventoryExceptionHandler {
	 @ExceptionHandler(ProductInventoryValidatorException.class)
	  public final ResponseEntity<ErrorMessage> handleFieldsValidation(ProductInventoryValidatorException ex, WebRequest request) {
	    
	    ErrorMessage errorObj = new ErrorMessage(new Date(), ex.getMessage(),
	        request.getDescription(false), HttpStatus.BAD_REQUEST);
	    return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	  }
}
