/**
 * 
 */
package com.octan.prueba.model;

import javax.persistence.*;

/**
 * @author LUIS MARTINEZ
 * @since 01/2022
 */

@Entity
@Table (name = "rol")
public class Rol {

	
	private long id;
	private String nombre;
	
	
	public Rol(String nombre) {
		this.nombre = nombre;
	}
	
	public Rol() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
}
