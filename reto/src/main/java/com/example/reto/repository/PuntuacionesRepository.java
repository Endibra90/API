package com.example.reto.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.reto.model.Puntuaciones;


public interface PuntuacionesRepository extends MongoRepository<Puntuaciones,String>{

	List<Puntuaciones> findTop10ByOrderByPuntosDesc();//buscar los diez primeros ordenados descendentes
	
	Puntuaciones findByIdRutaAndIdUsuario(String idRuta, String idUsuario);//funcion que se encarga de buscar por id de ruta y de usuario

	List<Puntuaciones> findTop10ByIdRutaOrderByPuntosDesc(String idRuta);//funcion que se encarga de buscar por diez primeros por idruta y los puntos descendentes
}
