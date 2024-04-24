package com.elcuarzo.mvc.controladores;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.elcuarzo.mvc.modelo.Calificaciones;
import com.elcuarzo.mvc.modelo.Programa;
import com.elcuarzo.mvc.modelo.Usuario;
import com.elcuarzo.mvc.servicios.LoginServicio;
import com.elcuarzo.mvc.servicios.ProgramaServicio;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ControladorProgramas {

	private final ProgramaServicio programaServicio;
	private final LoginServicio loginServicio;
	
	public ControladorProgramas(ProgramaServicio programaServicio, LoginServicio loginServicio) {
		this.programaServicio = programaServicio;
		this.loginServicio = loginServicio;
	}
	
	@RequestMapping("/home")
	public String desplegarProgramas(Model modelo, HttpSession sesion) {
		List<Programa> programas = this.programaServicio.obtenerTodosLosProgramas();
		for (Programa programa : programas) {
	        double promedioCalificaciones = programaServicio.calcularPromedioCalificaciones(programa);
	        programa.setPromedioCalificaciones(promedioCalificaciones);
	    }
		modelo.addAttribute("programas", programas);
		Long idUsuario = (Long) sesion.getAttribute("idUsuario");
		if(idUsuario == null) {
			return "redirect:/";
		}
		return "Home.jsp";
	}
	
	@RequestMapping("/nueva/programa")
	public String desplegarFormularioPrograma(@ModelAttribute("programas") Programa programas,
			HttpSession sesion) {
		Long idUsuario = (Long) sesion.getAttribute("idUsuario");
		if(idUsuario == null) {
			return "redirect:/";
		}
		return "FormularioPrograma.jsp";
	}
	
	@RequestMapping(value="/nueva/programa", method = RequestMethod.POST)
	public String agregarPrograma(@Valid @ModelAttribute("programas") Programa programaNuevo,
								BindingResult resultado,
								HttpSession sesion) {
		
		if(resultado.hasErrors()) {
			return "FormularioPrograma.jsp";
		}
		Long idUsuario= (Long) sesion.getAttribute("idUsuario");
		if(idUsuario == null) {
			return "redirect:/";
		}
		Usuario usuarioActual = this.loginServicio.selectPorId(idUsuario);
		programaNuevo.setCreador(usuarioActual.getNombre());
		programaNuevo.getUsuarios().add(usuarioActual);
		
		Programa programaExistente = this.programaServicio.obtenerProgramaPorNombre(programaNuevo.getNombre());
	    if (programaExistente != null) {
	        resultado.rejectValue("nombre", "Duplicate", "Ya existe un programa con este nombre.");
	        return "FormularioPrograma.jsp";
	    }
		this.programaServicio.crearPrograma(programaNuevo);
		return "redirect:/home";
	}
	
	@RequestMapping("/programa/{id}")
	public String mostrarPrograma(@PathVariable("id") Long id, Model model, HttpSession sesion) {
		Long idUsuario = (Long) sesion.getAttribute("idUsuario");
	    if (idUsuario == null) {
	        return "redirect:/";
	    }
	    Programa programaActual = programaServicio.obtenerProgramaPorId(id);
	    List<Calificaciones> calificaciones = programaActual.getCalificaciones();
        Collections.sort(calificaciones, Comparator.comparing(Calificaciones::getCalificacion));
	    Usuario usuarioActual = loginServicio.obtenerUsuarioPorId(idUsuario);
	    boolean verificacionUsuario = programaActual.getCalificaciones().stream().anyMatch(calificacion -> calificacion.getUsuario().equals(usuarioActual));
	    model.addAttribute("programaActual", programaActual);
	    model.addAttribute("usuarioHaCalificado", verificacionUsuario);
	    return "mostrarPrograma.jsp";
	}
	
	@RequestMapping("/editar/programa/{id}")
	public String editarPrograma(@PathVariable("id") Long id ,Model model, HttpSession sesion) {
		Long idUsuario = (Long) sesion.getAttribute("idUsuario");
	    if(idUsuario == null) {
	        return "redirect:/";
	    }
	    Programa programaActual = programaServicio.obtenerProgramaPorId(id);
		model.addAttribute("programaActual", programaActual);
		return "editarPrograma.jsp";
	}
	
	@RequestMapping(value="/procesar/editar/{id}", method = RequestMethod.POST)
	public String procesarEditar(@Valid @ModelAttribute("programaActual") Programa programaActual,
            					 BindingResult resultado,
								 @PathVariable("id") Long id,
	                             HttpSession sesion){
		
		if(resultado.hasErrors()) {
	        return "editarPrograma.jsp";
	    }

	    Long idUsuario = (Long) sesion.getAttribute("idUsuario");
	    if(idUsuario == null) {
	        return "redirect:/";
	    }
	    Programa programaExistente = this.programaServicio.obtenerProgramaPorId(id);
	    Programa programaExistentePorNombre = this.programaServicio.obtenerProgramaPorNombre(programaActual.getNombre());
	    if (programaExistentePorNombre != null) {
	    	resultado.rejectValue("nombre", "Duplicate", "Ya existe un programa con este nombre.");
	    	return "editarPrograma.jsp";
	    }
	    programaExistente.setNombre(programaActual.getNombre());
	    programaExistente.setRed(programaActual.getRed());
	    programaExistente.setDescripcion(programaActual.getDescripcion());
	    this.programaServicio.crearPrograma(programaExistente);
	    return "redirect:/home";
	}
	
	@RequestMapping(value="/eliminarPrograma/{id}", method=RequestMethod.DELETE)
	public String eliminarPrograma(@PathVariable("id") Long id) {
		programaServicio.eliminarPrograma(id);
		return "redirect:/home";
	}
	
	@RequestMapping(value="/calificarPrograma/{id}", method = RequestMethod.POST)
	public String calificarPrograma(@PathVariable("id") Long id, 
	                                @RequestParam("calificacion") Double calificacion,
	                                HttpSession sesion) {
	    Programa programa = programaServicio.obtenerProgramaPorId(id);
	    Usuario usuario = loginServicio.obtenerUsuarioPorId((Long) sesion.getAttribute("idUsuario"));  
	    programaServicio.calificarPrograma(programa, calificacion, usuario);
	    return "redirect:/programa/" + id;
	}
}
