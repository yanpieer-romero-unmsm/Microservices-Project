package com.formacionbdi.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {
	//private final Environment env;
	private final IProductService productService;
	@Value("${server.port}")
	private Integer port;

	@GetMapping
	public List<Producto> list(){
		return productService.findAll().stream().map(product -> {
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			product.setPort(port);
			return product;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public Producto detail(@PathVariable Long id)  {
		Producto product = productService.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		product.setPort(port);
		/*
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return product;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Producto create(@RequestBody Producto product) {
		return productService.save(product);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto product, @PathVariable Long id) {
		Producto productDb = productService.findById(id);
		productDb.setNombre(product.getNombre());
		productDb.setPrecio(product.getPrecio());
		return productService.save(productDb);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		productService.deleteById(id);
	}
	
}
