package com.example.reto.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.reto.model.Localizaciones;


public interface LocalizacionesRepository extends MongoRepository<Localizaciones,String>{

	List<Localizaciones> findByIdRuta(String id);//funcion para buscar por idRuta

	void deleteByNombre(String nombre);//funcion para borrar por el nombre 

	void deleteByIdRuta(String id);//funcion para borrar por el id

}
