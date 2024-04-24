package com.elcuarzo.mvc.modelo;

import jakarta.validation.constraints.Size;

public class UsuarioLogin {
	
	@Size(min = 1, message="Por favor escribe tu correo.")
	private String correoLogin;
	@Size(min = 1, message="Por favor escribe tu contraseña.")
	private String contrasenaLogin;
	
	public UsuarioLogin() {
		
	}

	public String getCorreoLogin() {
		return correoLogin;
	}

	public void setCorreoLogin(String correoLogin) {
		this.correoLogin = correoLogin;
	}

	public String getContrasenaLogin() {
		return contrasenaLogin;
	}

	public void setContrasenaLogin(String contrasenaLogin) {
		this.contrasenaLogin = contrasenaLogin;
	}
}
