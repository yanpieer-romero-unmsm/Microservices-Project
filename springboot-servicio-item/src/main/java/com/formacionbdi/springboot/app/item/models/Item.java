package com.formacionbdi.springboot.app.item.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.formacionbdi.springboot.app.commons.models.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	@JsonProperty("product")
	private ProductEntity productEntity;
	private Integer amount;
}
