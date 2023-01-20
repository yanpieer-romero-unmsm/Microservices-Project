package com.formacionbdi.springboot.app.item.controllers;

import com.formacionbdi.springboot.app.commons.models.entity.ProductEntity;
import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RefreshScope
@RestController
public class ItemController {
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	@Autowired
	private Environment env;
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;
	@Value("${configuration.text: Configuring development environment ...}")
	private String text;

	@GetMapping
	public List<Item> list(@RequestParam(name = "name", required = false) String name, @RequestHeader(name = "token-request", required = false) String token){
		log.info(name);
		log.info(token);
		return itemService.findAll();
	}

	//@HystrixCommand(fallbackMethod = "alternativeMethod")
	@GetMapping("/{id}/amount/{amount}")
	public Item detail(@PathVariable Long id, @PathVariable Integer amount) {
		return circuitBreakerFactory.create("items")
				.run(() -> itemService.findById(id, amount), e -> alternativeMethod(id, amount, e));
	}

	public Item alternativeMethod(Long id, Integer amount, Throwable e) {
		log.info(e.getMessage());
		Item item = new Item();
		ProductEntity productEntity = new ProductEntity();

		item.setAmount(amount);
		productEntity.setId(id);
		productEntity.setName("Camara Sony");
		productEntity.setPrice(500.00);
		item.setProductEntity(productEntity);

		return item;
	}

	@GetMapping("/configurations")
	public ResponseEntity<?> getConfig(@Value("${server.port}") String port){

		log.info(text);

		Map<String, String> json = new HashMap<>();
		json.put("text", text);
		json.put("port", port);

		if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("author.name", env.getProperty("configuration.author.name"));
			json.put("author.email", env.getProperty("configuration.author.email"));
		}

		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductEntity create(@RequestBody ProductEntity productEntity) {
		return itemService.save(productEntity);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductEntity update(@RequestBody ProductEntity productEntity, @PathVariable Long id) {
		return itemService.update(productEntity, id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		itemService.delete(id);
	}

}

