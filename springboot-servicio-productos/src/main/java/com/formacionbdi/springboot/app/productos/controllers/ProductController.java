package com.formacionbdi.springboot.app.productos.controllers;

import com.formacionbdi.springboot.app.commons.models.entity.ProductEntity;
import com.formacionbdi.springboot.app.productos.models.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController {
	private final IProductService productService;
	@Value("${server.port}")
	private Integer port;

	@GetMapping
	public List<ProductEntity> list(){
		return productService.findAll()
				.stream()
				.peek(product -> product.setPort(port))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public ProductEntity detail(@PathVariable Long id) throws InterruptedException {
		if (id.equals(10L))
			throw new IllegalStateException("Product not found");

		if (id.equals(7L))
			TimeUnit.SECONDS.sleep(5L);

		ProductEntity product = productService.findById(id);
		product.setPort(port);
		return product;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductEntity create(@RequestBody ProductEntity product) {
		return productService.save(product);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductEntity update(@RequestBody ProductEntity product, @PathVariable Long id) {
		ProductEntity productDb = productService.findById(id);
		productDb.setName(product.getName());
		productDb.setPrice(product.getPrice());
		return productService.save(productDb);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		productService.deleteById(id);
	}
}
