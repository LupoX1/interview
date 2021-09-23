package com.example.demo.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Product exists")
public class ProductExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6966455203124424565L;
	
}