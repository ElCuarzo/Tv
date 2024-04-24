package com.elcuarzo.mvc.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elcuarzo.mvc.modelo.Usuario;
import com.elcuarzo.mvc.modelo.UsuarioLogin;
import com.elcuarzo.mvc.servicios.LoginServicio;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ControladorLogin {
	
	private final LoginServicio loginServ;
	
	public ControladorLogin(LoginServicio loginServ) {
		this.loginServ = loginServ;
	}
	
	@RequestMapping("/")
	public String index(@ModelAttribute("usuario") Usuario usuario,
						@ModelAttribute("usuarioLogin") UsuarioLogin usuarioLogin) {
		return "registroylogin.jsp";
	}
	
	@RequestMapping(value="/registro", method = RequestMethod.POST)
	public String procesarRegistro(@Valid @ModelAttribute("usuario") Usuario nuevoUsuario,
								   BindingResult resultado,
								   @ModelAttribute("usuarioLogin") UsuarioLogin usuarioLogin,
								   HttpSession sesion) {
		resultado = this.loginServ.validarRegistro(resultado, nuevoUsuario);
		if(resultado.hasErrors()) {
			return "registroylogin.jsp";
		}
		this.loginServ.insertarUsuario(nuevoUsuario);
		sesion.setAttribute("nombre", nuevoUsuario.getNombre());
		sesion.setAttribute("idUsuario", nuevoUsuario.getId());
		return "redirect:/";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String procesarLogin(@Valid @ModelAttribute("usuarioLogin") UsuarioLogin usuarioLogin,
								BindingResult resultado,
								@ModelAttribute("usuario") Usuario usuario,
								HttpSession sesion){
		resultado = this.loginServ.validarLogin(resultado, usuarioLogin);
		if(resultado.hasErrors()) {
			return "registroylogin.jsp";
		}
		
		Usuario usuarioExistente = this.loginServ.selectPorGmail(usuarioLogin.getCorreoLogin());
		
		sesion.setAttribute("idUsuario", usuarioExistente.getId());
		sesion.setAttribute("nombre", usuarioExistente.getNombre());
		return "redirect:/home";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession sesion) {
		sesion.invalidate();
		return "redirect:/";
	}
}
