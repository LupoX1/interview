package com.example.demo.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	
	private long currentId = 0;
	private final Map<Long, Product> products = new TreeMap<>();
	
	private long nextId() {
		return currentId++;
	}
	
	@GetMapping("/products")
	public List<Product> findAll() {
		return new ArrayList<>(products.values());
	}
	
	@GetMapping("/products/{productId}")
	public Product findOne(@PathVariable long productId) {
		if(products.containsKey(productId)) {
			return products.get(productId);		
		}
		throw new ProductNotFoundException();
	}
	
	@PutMapping("/products")
	@ResponseBody
	public Product create(@RequestBody Product product) {
		long productId = nextId();
		if(!products.containsKey(productId)) {
			product.setProductId(productId);
			products.put(productId, product);
			return product;					
		}
		throw new ProductExistsException();
	}

	@PostMapping("/products/{productId}")
	@ResponseBody
	public Product update(@PathVariable long productId, @RequestBody Product product) {
		if(products.containsKey(productId)) {
			products.put(productId, product);
			return products.get(productId);
		}
		throw new ProductNotFoundException();
	}
	
	@DeleteMapping("/products/{productId}")
	public Product delete(@PathVariable long productId) {
		if(products.containsKey(productId)) {
			return products.remove(productId);		
		}
		throw new ProductNotFoundException();
	}

}
