package com.desafio.logistiqal.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desafio.logistiqal.model.Producto;
import com.desafio.logistiqal.service.ProductoService;
import com.desafio.logistiqal.util.Util;
import com.desafio.logistiqal.vo.ProductoVO;

@Controller
public class ProductoController {

	private static final Logger log = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductoService svc;

	private List<ProductoVO> listaProductos;

	public ProductoController() {
		super();
		listaProductos = new ArrayList<ProductoVO>();
	}

	/**
	 * Las solicitudes de la pagina principal
	 */
//	@GetMapping({ "/", "/home" })
//	public String home(Model model, @RequestParam(defaultValue = "1") Integer p) {
//		model.addAttribute("paginas", nvc.getPageCount(p));
//		model.addAttribute("paginaActual", p);
//		model.addAttribute("VO", svc.getPage(p-1, 6));
////		svc.getAllProductos().getProductos().get(0).getIdProducto()
//		return "home";
//	}

	@GetMapping({ "/", "/home" })
	public String home(Model model, @RequestParam(defaultValue = "", name = "busqueda") String busqueda,
			@RequestParam(defaultValue = "1") Integer p,
			@RequestParam(defaultValue = "3", name = "registrosPorPagina") Integer registrosPorPagina) {
		Integer totalPages;

		totalPages = svc.getPageb(p, registrosPorPagina, busqueda).getTotalPages();

		// first: getPage
		log.info("paginaActual(p): " + p);

		// total pages: totalPages
		log.info("totalPages: " + totalPages);

		model.addAttribute("paginas", Util.getArregloPaginas(p, totalPages));

		log.info("util.getArregloPaginas: " + Util.getArregloPaginas(p, totalPages));
		log.info("util.getArregloPaginas@@@:" + svc.getPage(p - 1, registrosPorPagina, busqueda).getProductos().size());

		log.info("----------------------------");
		model.addAttribute("paginaActual", p);
		model.addAttribute("busqueda", busqueda);
		model.addAttribute("registrosPorPagina", registrosPorPagina);
		model.addAttribute("VO", svc.getPage(p - 1, registrosPorPagina, busqueda));
		log.info("valor del mensje: " + busqueda);
		log.info("valor del registrosPorPagina: " + registrosPorPagina);
		return "home";
	}


	/**
	 * Abre el formulario de edición cargando los datos del Producto
	 */
	@GetMapping("/editarForm")
	public ModelAndView editarForm(Model model, @RequestParam String idProducto, RedirectAttributes ra) {
		ProductoVO respuestaServicio = new ProductoVO();
		respuestaServicio.setMensaje("No se pudo cargar la vista de edición, intente nuevamente.");
		try {
			Integer id = (Integer.parseInt(idProducto));
			respuestaServicio = svc.findById(id);
			model.addAttribute("mensaje", respuestaServicio.getMensaje());
			model.addAttribute("VO", respuestaServicio.getProductos().get(0));
			return new ModelAndView("editar");
		} catch (Exception e) {
			log.error("Error en ProductoController eliminar", e);
		}
		ra.addFlashAttribute("mensaje", respuestaServicio.getMensaje());
		respuestaServicio = svc.getAllProductos();
		return new ModelAndView("redirect:/home");
	}

	/**
	 * Llama al método del servicio que se encarga de actualizar los datos del
	 * Producto en base de datos
	 */
	@PostMapping("/editar")
	public ModelAndView editar(@ModelAttribute Producto Producto, RedirectAttributes ra) {
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% we're in");
		ProductoVO respuestaServicio = svc.update(Producto);
		ra.addFlashAttribute("mensaje", respuestaServicio.getMensaje());
		if (respuestaServicio.getCodigo().equals("0")) {
			return new ModelAndView("redirect:/home");
		} else {
			return new ModelAndView("redirect:/editarForm");
		}
	}

	/**
	 * Abre el formulario de creación de Productos
	 */
	@GetMapping("/agregarForm")
	public String agregarForm(Model model) {
		return "agregar";
	}

	/**
	 * Llama al método del servicio que se encarga de crear los datos del Producto
	 * en base de datos
	 */
	@PostMapping("/agregar")
	public ModelAndView agregarSubmit(@ModelAttribute Producto Producto, RedirectAttributes ra) {
		ProductoVO respuestaServicio = svc.add(Producto);
		ra.addFlashAttribute("mensaje", respuestaServicio.getMensaje());
		if (respuestaServicio.getCodigo().equals("0")) {
			return new ModelAndView("redirect:/home");
		} else {
			return new ModelAndView("redirect:/agregarForm");
		}
	}

	/**
	 * Llama al método del servicio que se encarga de eliminar los datos del
	 * Producto en base de datos y redirecciona al home.
	 */
	@GetMapping("/eliminar")
	public ModelAndView eliminar(Model model, @RequestParam String idProducto, RedirectAttributes ra) {
		ProductoVO respuestaServicio = new ProductoVO();
		respuestaServicio.setMensaje("No se pudo eliminar al Producto, intente nuevamente.");
		try {
			Producto eliminado = new Producto();
			eliminado.setIdProducto(Integer.parseInt(idProducto));
			respuestaServicio = svc.delete(eliminado);
			ra.addFlashAttribute("mensaje", respuestaServicio.getMensaje());
			return new ModelAndView("redirect:/home");
		} catch (Exception e) {
			log.error("Error en ProductoController eliminar", e);
		}
		ra.addFlashAttribute("mensaje", respuestaServicio.getMensaje());
		respuestaServicio = svc.getAllProductos();
		ra.addAttribute("VO", respuestaServicio);
		return new ModelAndView("redirect:/home");
	}

//	@RequestMapping(value = "/findBy", method = RequestMethod.POST)
//	public ModelAndView findBy(ModelMap model, @ModelAttribute(value = "productoBuscado") Producto producto,
//			RedirectAttributes ra) {
//		ProductoVO respuestaServicio = new ProductoVO();
//		respuestaServicio.setMensaje("No se pudo cargar la vista de la busqueda, intente nuevamente.");
//		try {
//			String nombre = producto.getNombre();
//			respuestaServicio = svc.findByNombre(nombre);
//			model.addAttribute("mensaje", respuestaServicio.getMensaje());
//			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& inside the findBy");
////			model.addAttribute("VO", respuestaServicio.getProductos().get(0));
//			model.addAttribute("VO", svc.findByNombre(nombre));
////			model.addAttribute("VO", svc.getAllProductos());
//			return new ModelAndView("home");
//		} catch (Exception e) {
//			log.error("Error en ProductoController findBy", e);
//		}
//		ra.addFlashAttribute("mensaje", respuestaServicio.getMensaje());
//		respuestaServicio = svc.getAllProductos();
//		return new ModelAndView("redirect:/home");
//	}

//		ProductoVO productoBuscado = svc.findByNombre(producto.getNombre());
//		listaProductos.clear();
//		listaProductos.add(productoBuscado);
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + listaProductos);
//		model.put("listaLibros", listaProductos);
//		return "home";
//	}
}
