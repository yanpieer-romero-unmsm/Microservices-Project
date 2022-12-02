package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.commons.models.entity.ProductEntity;
import com.formacionbdi.springboot.app.item.models.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
	private final RestTemplate clientRest;
	
	@Override
	public List<Item> findAll() {
		List<ProductEntity> productEntities = Arrays.asList(Objects.requireNonNull(clientRest.getForObject("http://service-products", ProductEntity[].class)));
		
		return productEntities.stream()
				.map(p -> new Item(p, 1))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer amount) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		ProductEntity productEntity = clientRest.getForObject("http://service-products/{id}", ProductEntity.class, pathVariables);
		return new Item(productEntity, amount);
	}

	@Override
	public ProductEntity save(ProductEntity productEntity) {
		HttpEntity<ProductEntity> body = new HttpEntity<ProductEntity>(productEntity);
		
		ResponseEntity<ProductEntity> response = clientRest.exchange("http://service-products", HttpMethod.POST, body, ProductEntity.class);

		return response.getBody();
	}

	@Override
	public ProductEntity update(ProductEntity productEntity, Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		HttpEntity<ProductEntity> body = new HttpEntity<ProductEntity>(productEntity);
		ResponseEntity<ProductEntity> response = clientRest.exchange("http://service-products/{id}",
				HttpMethod.PUT, body, ProductEntity.class, pathVariables);
		
		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		clientRest.delete("http://service-products/{id}", pathVariables);
		
	}

}
