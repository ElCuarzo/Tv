package com.elcuarzo.mvc.repositorio;

import com.elcuarzo.mvc.modelo.Usuario;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepositorio extends CrudRepository<Usuario, Long>{
	List<Usuario> findAll();
	Usuario getByCorreo(String correo);
	Usuario getById(Long id);
}
