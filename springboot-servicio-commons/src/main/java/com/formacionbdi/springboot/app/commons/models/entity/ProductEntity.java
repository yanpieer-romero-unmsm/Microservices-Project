package com.formacionbdi.springboot.app.commons.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity implements Serializable{
	/**
	 * Se implementó el serializable que permite comvertir el objeto en bytes
	 * Esta implementación es opcional
	 */
	private static final long serialVersionUID = 1285454306356845809L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Double price;
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	@Transient
	private Integer port;
}
