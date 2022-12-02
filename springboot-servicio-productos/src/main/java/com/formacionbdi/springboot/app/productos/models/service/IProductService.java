package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.commons.models.entity.ProductEntity;

public interface IProductService {
	List<ProductEntity> findAll();
	ProductEntity findById(Long id);
	ProductEntity save(ProductEntity product);
	void deleteById(Long id);
}
