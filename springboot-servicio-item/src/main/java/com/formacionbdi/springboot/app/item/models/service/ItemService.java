package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.commons.models.entity.ProductEntity;
import com.formacionbdi.springboot.app.item.models.Item;

import java.util.List;

public interface ItemService {
	List<Item> findAll();
	Item findById(Long id, Integer amount);
	ProductEntity save(ProductEntity productEntity);
	ProductEntity update(ProductEntity productEntity, Long id);
	void delete(Long id);
}
