package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.commons.models.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.productos.models.dao.ProductDao;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
	private final ProductDao productDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ProductEntity> findAll() {
		return (List<ProductEntity>) productDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ProductEntity findById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ProductEntity save(ProductEntity product) {
		return productDao.save(product);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productDao.deleteById(id);
	}

}
