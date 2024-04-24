package com.elcuarzo.mvc.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.elcuarzo.mvc.modelo.Calificaciones;
import com.elcuarzo.mvc.modelo.Programa;
import com.elcuarzo.mvc.modelo.Usuario;
import com.elcuarzo.mvc.repositorio.ProgramaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ProgramaServicio {
	
	private final ProgramaRepositorio programaRep;
	
	public ProgramaServicio(ProgramaRepositorio programaRep) {
		this.programaRep = programaRep;
	}
	
	//Obtener todos los programas
		public List<Programa> obtenerTodosLosProgramas(){
			return programaRep.findAll();
		}
	
	//Crear nueva cancion
	public Programa crearPrograma(Programa nuevoPrograma) {
		return this.programaRep.save(nuevoPrograma);
	}
	
	//Obtener cancion por nombre
	public Programa obtenerProgramaPorNombre(String nombrePrograma) {
	       return programaRep.getByNombre(nombrePrograma);
	}
	
	//Obtener cancion por id
	public Programa obtenerProgramaPorId(Long id) {
		return programaRep.getById(id);
	}
	
	//Eliminar programa por id
	public void eliminarPrograma(Long id) {
		programaRep.deleteById(id);
	}
	
	//Calificar Programa
	@Transactional
	public void calificarPrograma(Programa programa, Double calificacion, Usuario usuario) {
        Calificaciones nuevaCalificacion = new Calificaciones();
        nuevaCalificacion.setUsuario(usuario);
        nuevaCalificacion.setCalificacion(calificacion);
        nuevaCalificacion.setPrograma(programa);
        programa.getCalificaciones().add(nuevaCalificacion);
        programaRep.save(programa);
    }
	
	//Calcular promedio de calificaciones
	public double calcularPromedioCalificaciones(Programa programa) {
	    List<Calificaciones> calificaciones = programa.getCalificaciones();
	    if (calificaciones.isEmpty()) {
	        return 0.0;
	    }
	    else{
	    	double sumaCalificaciones = 0.0;
	    	for(Calificaciones calificacion : calificaciones) {
	    		sumaCalificaciones += calificacion.getCalificacion();
	    	}
	    	double a = sumaCalificaciones / (double) calificaciones.size();
	    	return programa.setPromedioCalificaciones(a);
	    }
	}
}
