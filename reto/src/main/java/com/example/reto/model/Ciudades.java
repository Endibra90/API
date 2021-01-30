package com.example.reto.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ciudades")
//documento de ciudades
public class Ciudades {
@Id
private String id;
private String nombre;
private String longitude;
private String latitude;
public Ciudades(String id , String nombre,String latitude,String longitude){
	this.id=id;
	this.nombre=nombre;
	this.latitude=latitude;
	this.longitude=longitude;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
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
}
