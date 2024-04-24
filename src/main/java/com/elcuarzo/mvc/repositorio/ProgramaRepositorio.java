package com.elcuarzo.mvc.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elcuarzo.mvc.modelo.Programa;

public interface ProgramaRepositorio extends CrudRepository<Programa, Long>{
	List<Programa> findAll();
	Programa getByNombre(String nombre);
	Programa getById(Long id);
}
