package com.formacionbdi.springboot.app.commons.usuarios.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	@Column(unique = true, length = 20)
	private String username;
	@Column(length = 60)
	private String password;
	private boolean enabled;
	private String name;
	private String lastname;
	@Column(unique = true, length = 100)
	private String email;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"}) })
	private List<Role> roles;
	private Integer attempts;
}
