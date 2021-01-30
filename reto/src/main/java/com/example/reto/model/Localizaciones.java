package com.example.reto.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="localizaciones")
public class Localizaciones {
	//documento de localizaciones
	@Id
	private String _id;
	private String nombre;
	private float latitud;
	private float longitud;
	private int oculto;
	private String idRuta;
	private ArrayList<String> preguntas;
	private ArrayList<ArrayList<String>> respuestas;
	private ArrayList<String> lococultas;
	public String getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
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
	public void setLatitud(float latitud){
		this.latitud=latitud;
	}
	public float getLatitud(){
		return latitud;
	}
	public void setLongitud(float longitud){
		this.longitud=longitud;
	}
	public float getLongitud(){
		return longitud;
	}
	public int isOculto() {
		return oculto;
	}
	public void setOculto(int oculto) {
		this.oculto = oculto;
	}
	public ArrayList<String> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(ArrayList<String> preguntas) {
		this.preguntas = preguntas;
	}
	public ArrayList<ArrayList<String>> getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(ArrayList<ArrayList<String>> respuestas) {
		this.respuestas = respuestas;
	}
	public ArrayList<String> getLococultas() {
		return lococultas;
	}
	public void setLococultas(ArrayList<String> lococultas) {
		this.lococultas = lococultas;
	}
	public Localizaciones(String _id, String nombre, float latitud,float longitud, int oculto, ArrayList<String> preguntas,
			ArrayList<ArrayList<String>> respuestas, ArrayList<String> lococultas) {
		this._id = _id;
		this.nombre = nombre;
		this.latitud=latitud;
		this.longitud=longitud;
		this.oculto = oculto;
		this.preguntas = preguntas;
		this.respuestas = respuestas;
		this.lococultas = lococultas;
	}
	public Localizaciones() {

	}
}