package com.desafio.logistiqal.vo;

import java.util.List;

import com.desafio.logistiqal.model.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductoVO extends GenericVO{
	List<Producto> productos;

	public ProductoVO(String mensaje, String codigo, List<Producto> productos) {
		super(mensaje, codigo);
		this.productos = productos;
	}	
}
