package com.turismo.apimonumentos.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String nombre;
	
	@Column
	private int edad;
	
	@Column
	private boolean admin;
	
	@Column
	private String domicilio;
	
	@Column
	private char genero;
	
	@Column
	private String procedencia;
	
	@Column
	private int telefono;
	
	@Column
	private String email;
	
	@Column
	private boolean visitado;
	
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Date fechaVisita;
	
	/**
	 * 
	 */
	public Usuario() {
		
	}
	/**
	 * 
	 * @param id
	 * @param nombre
	 * @param edad
	 * @param admin
	 * @param domicilio
	 * @param genero
	 * @param procedencia
	 * @param telefono
	 * @param email
	 * @param visitado
	 * @param fechaVisita
	 */
	public Usuario(int id, String nombre, int edad, boolean admin, String domicilio, char genero, String procedencia,
			int telefono, String email, boolean visitado, Date fechaVisita) {
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.admin = admin;
		this.domicilio = domicilio;
		this.genero = genero;
		this.procedencia = procedencia;
		this.telefono = telefono;
		this.email = email;
		this.visitado = visitado;
		this.fechaVisita = fechaVisita;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

	public Date getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}
}
