package com.example.reto.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.reto.model.Rutas;

public interface RutaRepository extends MongoRepository<Rutas , String>{
	void deleteByNombre(String name);//funcion que se encarga de borrar por nombre

	List<Rutas> findByCiudadAndVehiculo(String ciudad, String vehiculo);//funcion que se encarga de buscar por ciudad y vehiculo

	List<Rutas> findByCiudadAndVehiculoAndLongitudBetween(String ciudad, String vehiculo, int kilometroMinimo,int kilometroMaximo);//funcion que se encarga de buscar por ciudad,vehiculo y longitud para los filtros del usuario
	
	List<Rutas> findByNombre(String nombre);//funcion que busca por nombre
}
