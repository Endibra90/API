package com.example.reto.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.reto.model.Ciudades;

public interface CiudadesRepository extends MongoRepository<Ciudades,String> {

	Optional<Ciudades> findByNombre(String nombre);//funcion para buscar por nombre

}
