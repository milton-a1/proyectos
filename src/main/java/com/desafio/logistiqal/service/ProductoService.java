package com.desafio.logistiqal.service;

import org.springframework.data.domain.Page;

import com.desafio.logistiqal.model.Producto;
import com.desafio.logistiqal.vo.NumberVO;
import com.desafio.logistiqal.vo.ProductoVO;

//ESTABLECES LOS METODOS A USAR EN ESTA INTERFACE:


public interface ProductoService {
	public ProductoVO getAllProductos();

//	public ProductoVO findByNombre(String nombre);

//	public ProductoVO login(String nombre, String clave);

	public ProductoVO add(Producto producto);

	public ProductoVO update(Producto producto);

	public ProductoVO delete(Producto producto);

	public ProductoVO findById(Integer id);

	public ProductoVO getPage(Integer pagina, Integer cantidad, String buscar);
	
//	public NumberVO getPageCount(Long registrosPorPagina);

	NumberVO getPageCount(long registrosPorPagina);

	Page<Producto> getPageb(Integer pagina, Integer cantidad, String buscar);
}
