package com.example.reto.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rutas")
public class Rutas{
	//documento de rutas
	 @Id
	  private String _id;
	  private String nombre;
	  private Double longitud;
	  private String vehiculo;
	  private String ciudad;
	  private String dificultad;
	  private ArrayList<Object> loc;
	  private Double tiempo;
	  private Object imagen;
	public ArrayList<Object> getLoc() {
		return loc;
	}
	public void setNumLoc(ArrayList<Object> numLoc) {
		this.loc = numLoc;
	}
	public Object getImagen() {
		return imagen;
	}
	public void setImagen(Object imagen2) {
		this.imagen = imagen2;
	}

	public Rutas() {

	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public String getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public Double getTiempo() {
		return tiempo;
	}

	public void setTiempo(Double tiempo) {
		this.tiempo = tiempo;
	}

	public Rutas(String _id, String nombre, Double longitud, String vehiculo, String ciudad, String dificultad, Double tiempo,Object imagen) {
	    this._id = _id;
	    this.nombre = nombre;
	    this.longitud = longitud;
	    this.vehiculo = vehiculo;
	    this.ciudad = ciudad;
	    this.dificultad = dificultad;
	    this.tiempo = tiempo;
	    this.imagen = imagen;
	 }

}