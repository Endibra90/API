package com.example.reto.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.reto.model.Salas;

public interface SalasRepository extends MongoRepository<Salas,String>{

	Salas findByIdRuta(String idRuta);//funcion para buscar por el id ruta

}
