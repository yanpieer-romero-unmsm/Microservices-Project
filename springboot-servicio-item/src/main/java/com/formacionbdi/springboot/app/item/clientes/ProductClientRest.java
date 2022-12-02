package com.formacionbdi.springboot.app.item.clientes;

import com.formacionbdi.springboot.app.commons.models.entity.ProductEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {
	@GetMapping
	List<ProductEntity> list();
	@GetMapping("/{id}")
	ProductEntity detail(@PathVariable Long id);
	@PostMapping
	ProductEntity create(@RequestBody ProductEntity productEntity);
	@PutMapping("/{id}")
	ProductEntity update(@RequestBody ProductEntity productEntity, @PathVariable Long id);
	@DeleteMapping("/{id}")
	void delete(@PathVariable Long id);
}
