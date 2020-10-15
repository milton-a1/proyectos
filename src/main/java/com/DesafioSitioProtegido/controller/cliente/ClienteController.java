package com.DesafioSitioProtegido.controller.cliente;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.DesafioSitioProtegido.dto.DetalleDTO;

@Controller
public class ClienteController {
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("client");
		return modelAndView;
	}

	@GetMapping("/client")
	public ModelAndView home(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("client/client");
		modelAndView.addObject("detalle", new DetalleDTO
				());
		modelAndView.addObject("valores", session.getAttribute("valores"));
		return modelAndView;
	}

	@PostMapping("/clients")
	public RedirectView home(HttpSession session, @ModelAttribute DetalleDTO detalles) {
		List<DetalleDTO> valores = new ArrayList<>();
		if (session.getAttribute("valores") != null)
			valores.addAll((List<DetalleDTO>) session.getAttribute("valores"));
		valores.add(detalles);
		session.setAttribute("valores", valores);
		return new RedirectView("/client");
	}

}
