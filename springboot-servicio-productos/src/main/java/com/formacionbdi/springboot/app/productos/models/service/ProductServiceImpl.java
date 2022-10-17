package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.productos.models.dao.ProductDao;
import com.formacionbdi.springboot.app.commons.models.entity.Producto;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
	private final ProductDao productDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto product) {
		return productDao.save(product);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productDao.deleteById(id);
	}

}
