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
@Table(name = "monumento")
public class Monumento {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String nombre;

	@Column
	private int fechaConstruccion;

	@Column
	private String arquitectura;

	@Column
	private String disenador;

	@Column
	private int tiempoConstruccion;

	@Column
	private String restauraciones;

	@Column
	private String uso;

	@Column
	private String funFacts;

	@Column
	private boolean mostrable;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Date fechaRegistro;

	public Monumento() {
	}

	/**
	 * @param id                (int)
	 * @param nombre            (String)
	 * @param fechaConstruccion (int)
	 * @param mostrable         (boolean)
	 * @param fechaRegistro     (Date)
	 */
	public Monumento(int id, String nombre, int fechaConstruccion, Date fechaRegistro) {
		this.id = id;
		this.nombre = nombre;
		this.fechaConstruccion = fechaConstruccion;
		this.mostrable = true;
		this.fechaRegistro = fechaRegistro;
		this.tiempoConstruccion = -1;
	}

	/**
	 * @param id                 (int)
	 * @param nombre             (String)
	 * @param fechaConstruccion  (int)
	 * @param arquitectura       (String)
	 * @param disenador(String)
	 * @param tiempoConstruccion (int)
	 * @param restauraciones     (String)
	 * @param uso                (String)
	 * @param funFacts           (String)
	 * @param mostrable          (boolean)
	 * @param fechaRegistro      (Date)
	 */
	public Monumento(int id, String nombre, int fechaConstruccion, String arquitectura, String disenador,
			int tiempoConstruccion, String restauraciones, String uso, String funFacts, Date fechaRegistro) {
		this.id = id;
		this.nombre = nombre;
		this.fechaConstruccion = fechaConstruccion;
		this.arquitectura = arquitectura;
		this.disenador = disenador;
		this.tiempoConstruccion = tiempoConstruccion;
		this.restauraciones = restauraciones;
		this.uso = uso;
		this.funFacts = funFacts;
		this.mostrable = true;
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

	/**
	 * Iguala los atributos del objeto que utiliza el metodo con los del objeto que
	 * esta en el argumento
	 * 
	 * @param m
	 */
	public void actualizar(Monumento m) {
		this.id = m.getId();
		this.nombre = m.getNombre();
		this.fechaConstruccion = m.getFechaConstruccion();
		this.arquitectura = m.getArquitectura();
		this.disenador = m.getDisenador();
		this.tiempoConstruccion = m.getTiempoConstruccion();
		this.restauraciones = m.getRestauraciones();
		this.uso = m.getUso();
		this.funFacts = m.getUso();
		this.mostrable = m.isMostrable();
		this.fechaRegistro = m.getFechaRegistro();
	}

	@Override
	public String toString() {
		return "Monumento [id=" + id + ", nombre=" + nombre + ", fechaConstruccion=" + fechaConstruccion
				+ ", arquitectura=" + arquitectura + ", disenador=" + disenador + ", tiempoConstruccion="
				+ tiempoConstruccion + ", restauraciones=" + restauraciones + ", uso=" + uso + ", funFacts=" + funFacts
				+ ", mostrable=" + mostrable + ", fechaRegistro=" + fechaRegistro + "]";
	}

}
