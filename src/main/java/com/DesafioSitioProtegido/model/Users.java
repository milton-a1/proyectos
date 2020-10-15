package com.DesafioSitioProtegido.model;

import java.io.Serializable;

import com.DesafioSitioProtegido.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable{

	private String email;
	private String password;
	private Role role;
	
}
