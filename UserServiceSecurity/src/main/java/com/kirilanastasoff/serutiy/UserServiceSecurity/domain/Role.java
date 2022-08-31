package com.kirilanastasoff.serutiy.UserServiceSecurity.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Role(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Role() {
		super();
	}
	
	
	
	

//	@ManyToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id")
//	private Collection<User> users = new ArrayList<>();
	
//	@ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User users;
	
	
}
