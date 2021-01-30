package com.example.reto.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "puntuaciones")
public class Puntuaciones{
	//documento de puntuaciones
	 @Id
	  private String _id;
	  private String idRuta;
	  private String idUsuario;
	  private Double puntos;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Double getPuntos() {
		return puntos;
	}

	public void setPuntos(Double puntos) {
		this.puntos = puntos;
	}
	public Puntuaciones(String _id, String idRuta, String idUsuario, Double puntos) {
		this._id = _id;
		this.idRuta = idRuta;
		this.idUsuario = idUsuario;
		this.puntos = puntos;
	}
	public Puntuaciones(){

	}
}
