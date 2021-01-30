package com.example.reto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.reto.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario,String> {
	List<Usuario> findByNombre(String name);//funcion que busca por el nombre
	
	Optional<Usuario> findById(String id);//funcion que busca por id
}