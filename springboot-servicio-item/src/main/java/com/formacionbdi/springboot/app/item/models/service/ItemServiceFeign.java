package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.commons.models.entity.ProductEntity;
import com.formacionbdi.springboot.app.item.clientes.ProductClientRest;
import com.formacionbdi.springboot.app.item.models.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign")
@RequiredArgsConstructor
public class ItemServiceFeign implements ItemService {
	private final ProductClientRest clientFeign;
	
	@Override
	public List<Item> findAll() {
		return clientFeign.list()
				.stream()
				.map(p -> new Item(p, 1))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer amount) {
		return new Item(clientFeign.detail(id), amount);
	}

	@Override
	public ProductEntity save(ProductEntity productEntity) {
		return clientFeign.create(productEntity);
	}

	@Override
	public ProductEntity update(ProductEntity productEntity, Long id) {
		return clientFeign.update(productEntity, id);
	}

	@Override
	public void delete(Long id) {
		clientFeign.delete(id);
	}

}
