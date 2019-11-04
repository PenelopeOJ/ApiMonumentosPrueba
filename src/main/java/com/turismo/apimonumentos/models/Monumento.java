package com.turismo.apimonumentos.models;

import java.sql.Date;

public class Monumento {
	private int id;
	private String nombre;
	private int fechaConstruccion;
	private String arquitectura;
	private String disenador;
	private int tiempoConstruccion;
	private String restauraciones;
	private String uso;
	private String funFacts;
	private boolean mostrable;
	private Date fechaRegistro;
	
	public Monumento() {}

	public Monumento(int id, String nombre, int fechaConstruccion, boolean mostrable, Date fechaRegistro) {
		this.id = id;
		this.nombre = nombre;
		this.fechaConstruccion = fechaConstruccion;
		this.mostrable = mostrable;
		this.fechaRegistro = fechaRegistro;
	}

	public Monumento(int id, String nombre, int fechaConstruccion, String arquitectura, String disenador,
			int tiempoConstruccion, String restauraciones, String uso, String funFacts, boolean mostrable,
			Date fechaRegistro) {
		this.id = id;
		this.nombre = nombre;
		this.fechaConstruccion = fechaConstruccion;
		this.arquitectura = arquitectura;
		this.disenador = disenador;
		this.tiempoConstruccion = tiempoConstruccion;
		this.restauraciones = restauraciones;
		this.uso = uso;
		this.funFacts = funFacts;
		this.mostrable = mostrable;
		this.fechaRegistro = fechaRegistro;
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

	public int getFechaConstruccion() {
		return fechaConstruccion;
	}

	public void setFechaConstruccion(int fechaConstruccion) {
		this.fechaConstruccion = fechaConstruccion;
	}

	public String getArquitectura() {
		return arquitectura;
	}

	public void setArquitectura(String arquitectura) {
		this.arquitectura = arquitectura;
	}

	public String getDisenador() {
		return disenador;
	}

	public void setDisenador(String disenador) {
		this.disenador = disenador;
	}

	public int getTiempoConstruccion() {
		return tiempoConstruccion;
	}

	public void setTiempoConstruccion(int tiempoConstruccion) {
		this.tiempoConstruccion = tiempoConstruccion;
	}

	public String getRestauraciones() {
		return restauraciones;
	}

	public void setRestauraciones(String restauraciones) {
		this.restauraciones = restauraciones;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public String getFunFacts() {
		return funFacts;
	}

	public void setFunFacts(String funFacts) {
		this.funFacts = funFacts;
	}

	public boolean isMostrable() {
		return mostrable;
	}

	public void setMostrable(boolean mostrable) {
		this.mostrable = mostrable;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}	

}
