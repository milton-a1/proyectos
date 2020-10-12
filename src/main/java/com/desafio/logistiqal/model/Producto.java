package com.desafio.logistiqal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Producto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idProducto;
	private String nombre;
	private Integer precio;
	private Integer stock;
}


//create table Producto(
//		ID_PRODUCTO INT not null auto_increment,
//		NOMBRE VARCHAR(200),
//		PRECIO INTEGER,
//		STOCK INTEGER,
//	    CONSTRAINT primary_key_producto PRIMARY KEY(ID_PRODUCTO)
//	);