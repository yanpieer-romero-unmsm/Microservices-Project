package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;

public interface IProductService {
	List<Producto> findAll();
	Producto findById(Long id);
	Producto save(Producto product);
	void deleteById(Long id);
}
