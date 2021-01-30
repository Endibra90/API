package com.example.reto.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "salas")
public class Salas {
	//documento de salas
	 @Id
	  private String _id;
	  private String idRuta;
	  private List<String> idUsuario;
	public List<String> getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(ArrayList<String> idUsuario) {
		this.idUsuario = idUsuario;
	}
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
	public Salas(String idRuta, List<String> idUsuario) {
		this.idRuta = idRuta;
		this.idUsuario = idUsuario;
	}
}
