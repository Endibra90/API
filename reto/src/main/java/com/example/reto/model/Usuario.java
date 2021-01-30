package com.example.reto.model;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "Usuario")
//documento de usuario
public class Usuario {
	@Id
	private String _id;
	private String nombre;
	private String contrasena;
	private String avatar;
	private boolean admin;
	private String longitude;
	private String latitude;
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Usuario(String _id, String nombre ,String avatar,boolean admin){
		this._id=_id;
		this.nombre=nombre;
		this.avatar=avatar;
		this.admin=admin;
	}
	public Usuario(){
	}
	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}