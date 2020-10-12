package com.desafio.logistiqal.service.imp;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.logistiqal.DesafioLogistiqalApplication;
import com.desafio.logistiqal.dao.ProductoRepository;
import com.desafio.logistiqal.model.Producto;
import com.desafio.logistiqal.service.ProductoService;
import com.desafio.logistiqal.vo.NumberVO;
import com.desafio.logistiqal.vo.ProductoVO;


//injectamos el bean del dao mediante autowired, implementamos los metodoes de interfaz para poder definir la logica acá:

@Service
public class ProductoServiceImpl implements ProductoService {

	private static final Logger log = LoggerFactory.getLogger(DesafioLogistiqalApplication.class);

	@Autowired
	ProductoRepository dao;

	ProductoVO respuesta;

	@Override
	@Transactional(readOnly = true)
	public ProductoVO getAllProductos() {
		respuesta = new ProductoVO("Ha ocurrido un error", "102", new ArrayList<Producto>());
		try {
			respuesta.setProductos((List<Producto>) dao.findAll());
			respuesta.setMensaje(String.format("Se ha/n encontrado %d producto/s", respuesta.getProductos().size()));
			respuesta.setCodigo("0");
		} catch (Exception e) {
			log.trace("Producto Service: Error en getAllProductos", e);
		}
		return respuesta;
	}

//	@Override
//	@Transactional(readOnly = true)
//	public ProductoVO findByNombre(String nombre) {
//		respuesta = new ProductoVO("Ha ocurrido un error", "101", new ArrayList<Producto>());
//		try {
//			List<Producto> productos = dao.findByNombre(nombre, null);
//
//			System.out.println("---------------this is what it's returning: " + productos);
//
//			if (productos.size() > 0) {
//				respuesta.setProductos(productos);
//				respuesta.setMensaje("Producto encontrado correctamente.");
//				respuesta.setCodigo("0");
//			} else {
//				respuesta.setMensaje("Producto no encontrado.");
//			}
//		} catch (Exception e) {
//			log.trace("Producto Service: Error en findByNombre", e);
//		}
//		return respuesta;
//	}

	@Override
	public ProductoVO add(Producto producto) {
		respuesta = new ProductoVO("Ha ocurrido un error", "104", new ArrayList<Producto>());
		try {
			dao.save(producto);
			respuesta.setMensaje(String.format("Se ha guardado correctamente al producto %s", producto.getNombre()));
			respuesta.setCodigo("0");
		} catch (Exception e) {
			log.trace("Producto Service: Error en add", e);
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ProductoVO update(Producto producto) {
		respuesta = new ProductoVO("Ha ocurrido un error", "105", new ArrayList<Producto>());
		try {
			System.out.println("in the update $$$$$$$$$$$$$$$$$$$$$$");
			dao.save(producto);
			respuesta.setMensaje(String.format("Se ha actualizado correctamente al producto %s", producto.getNombre()));
			respuesta.setCodigo("0");
		} catch (Exception e) {
			log.trace("Producto Service: Error en update", e);
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ProductoVO delete(Producto producto) {
		respuesta = new ProductoVO("Ha ocurrido un error", "106", new ArrayList<Producto>());
		try {
			dao.delete(producto);
			respuesta.setMensaje("Se ha eliminado correctamente al producto");
			respuesta.setCodigo("0");
		} catch (Exception e) {
			log.trace("Producto Service: Error en delete", e);
		}
		return respuesta;
	}

	@Override
	@Transactional(readOnly = true)
	public ProductoVO findById(Integer id) {
		respuesta = new ProductoVO("Ha ocurrido un error", "107", new ArrayList<Producto>());
		try {
			Producto producto = dao.findById(id).get();
			respuesta.getProductos().add(producto);
			respuesta.setMensaje("Producto encontrado correctamente.");
			respuesta.setCodigo("0");
		} catch (Exception e) {
			log.trace("Producto Service: Error en findById", e);
		}
		return respuesta;
	}
//}

//PAGINACION ######################################################################### PAGE 47,MODELO 2


	@Override// this gives us how many pages it'd take to display all the products.
	@Transactional(readOnly = true)
	public NumberVO getPageCount(long registrosPorPagina) {
		NumberVO respuesta = new NumberVO( 0, "Ha ocurrido un error", "109");
		try {
			long registros = dao.count();
			log.info("registrosPorPagina in the getPageCount: "+ registrosPorPagina);
			log.info("dao.count(): "+ registros);
			
			if (registrosPorPagina == 0 && registros == 0) {
				respuesta.setValor(1);
			} else {
				respuesta.setValor((registros % registrosPorPagina == 0 ? 0 : 1) == 0 ? Math.round(registros/registrosPorPagina) : Math.round(registros/registrosPorPagina)+1);
//				respuesta.setValor((int) (registros / registrosPorPagina) + (registros % registrosPorPagina == 0 ? 0 : Math.round(registros/registrosPorPagina)+1));
				log.info("final answer: "+ registros);
				
			}
			respuesta.setCodigo("201");
			respuesta.setMensaje(String.format("Hay %d paginas", respuesta.getValor()));
		} catch (Exception e) {
			log.trace("Usuario Service: Error en getPageCount", e);
		}
		return respuesta;
	}

	@Override //this shows the amount of products per page.
	@Transactional(readOnly = true)
	public ProductoVO getPage(Integer pagina, Integer cantidad, String buscar) {
		respuesta = new ProductoVO("Ha ocurrido un error", "108", new ArrayList<Producto>());
		try {
			Pageable pageable =  PageRequest.of(pagina, cantidad);
			Page<Producto> responsePage = (Page<Producto>) dao.findByNombre(buscar, pageable);
			respuesta.setProductos(responsePage.getContent());
			
			
			log.info("responsePage.getTotalPages();: "+responsePage.getTotalPages());
			log.info("responsePage.getPageable();: "+responsePage.getPageable());

			
			
			
			log.info("this should be the amount of products per page: "+responsePage.getContent().size());
			log.info("---------------------------- products per page: "+responsePage.getContent());
			respuesta.setMensaje(String.format("Se ha/n encontrado %d registro/s", respuesta.getProductos().size()));
			respuesta.setCodigo("0");
			log.info("inside the productoServiceImpl in the getPage method, value of the amount of results in the search output: "+respuesta.getProductos().size());;
		} catch (Exception e) {
			log.trace("Producto Service: Error en getPage", e);
		}
		return respuesta;
	}

	@Override //this shows the amount of products per page.
	@Transactional(readOnly = true)
	public Page<Producto> getPageb(Integer pagina, Integer cantidad, String buscar) {
		respuesta = new ProductoVO("Ha ocurrido un error", "108", new ArrayList<Producto>());
		Page<Producto> responsePage = null;
		try {
			Pageable pageable =  PageRequest.of(pagina, cantidad);
			responsePage = (Page<Producto>) dao.findByNombre(buscar, pageable);
			respuesta.setProductos(responsePage.getContent());
			
			
			log.info("responsePage.getTotalPages();: "+responsePage.getTotalPages());
			log.info("responsePage.getPageable();: "+responsePage.getPageable());

			
			log.info("this should be the amount of products per page: "+responsePage.getContent().size());
			log.info("---------------------------- products per page: "+responsePage.getContent());
			respuesta.setMensaje(String.format("Se ha/n encontrado %d registro/s", respuesta.getProductos().size()));
			respuesta.setCodigo("0");
			log.info("inside the productoServiceImpl in the getPage method, value of the amount of results in the search output: "+respuesta.getProductos().size());;
		} catch (Exception e) {
			log.trace("Producto Service: Error en getPage", e);
		}
		return responsePage;
	}


}