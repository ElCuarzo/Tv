package com.elcuarzo.mvc.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="programas")
public class Programa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Size(min=2, message="Por favor proporciona el nombre de tu programa.")
	private String nombre;
	@Size(min=2, message="Por favor proporciona la red de tu programa.")
	private String red;
	@Size(min=5, message="Por favor proporciona la descripción de tu programa.")
	private String descripcion;
	private String creador;
	private double promedioCalificaciones;
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="usuarios_programas",
			joinColumns = @JoinColumn(name="programas_id"),
			inverseJoinColumns= @JoinColumn(name="usuarios_id")
			)
	private List<Usuario> usuarios;
	
	@OneToMany(mappedBy = "programa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Calificaciones> calificaciones = new ArrayList<>();
	
	public Programa() {
		this.usuarios = new ArrayList<>();
	}
	
	
	
	
	public double setPromedioCalificaciones(double promedioCalificaciones) {
		return this.promedioCalificaciones = promedioCalificaciones;
	}




	public double getPromedioCalificaciones() {
		return promedioCalificaciones;
	}




	public List<Calificaciones> getCalificaciones() {
		return calificaciones;
	}




	public void setCalificaciones(List<Calificaciones> calificaciones) {
		this.calificaciones = calificaciones;
	}




	public String getCreador() {
		return creador;
	}



	public void setCreador(String creador) {
		this.creador = creador;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getRed() {
		return red;
	}



	public void setRed(String red) {
		this.red = red;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



	public List<Usuario> getUsuarios() {
		return usuarios;
	}



	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}



	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
