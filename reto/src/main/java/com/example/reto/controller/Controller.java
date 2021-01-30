package com.example.reto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.reto.model.Localizaciones;
import com.example.reto.model.Rutas;
import com.example.reto.model.Salas;
import com.example.reto.model.Usuario;
import com.example.reto.model.Ciudades;
import com.example.reto.model.Coordenada;
import com.example.reto.repository.CiudadesRepository;
import com.example.reto.repository.LocalizacionesRepository;
import com.example.reto.repository.RutaRepository;
import com.example.reto.repository.SalasRepository;
import com.example.reto.repository.UsuarioRepository;
import com.example.reto.model.Puntuaciones;
import com.example.reto.repository.PuntuacionesRepository;
@CrossOrigin
@RestController
public class Controller extends WebMvcConfigurationSupport {

	//declaracion de todos los repositorios necesarios

	@Autowired
	private SalasRepository repositorySalas;

	@Autowired
	private PuntuacionesRepository repositoryPuntuaciones;

	@Autowired
	private CiudadesRepository repositoryCiudades;

	@Autowired
	private RutaRepository repositoryRutas;

	@Autowired
	private UsuarioRepository repositoryUsuarios;

	@Autowired
	protected MongoTemplate mongoTemplate;

	@Autowired
	private LocalizacionesRepository repository;



	//Salas

	//funcion que devuelve todas las salas
	@GetMapping("/salas/all")
	public List<Salas> getSalas(){
		return repositorySalas.findAll();
	}

	//funcion que se encarga de borrar el usuario de la ruta dentro de una sala al abandonar la sala
	@PostMapping("/salas/removeUser/{idRuta}&{idUsuario}")
	public void removeUserSala(@PathVariable String idRuta,@PathVariable String idUsuario){
		Salas existe = repositorySalas.findByIdRuta(idRuta); //funcion que se encarga de buscarlo por la id de la ruta
		List<String> usuario =existe.getIdUsuario();
		List<String> usuarioFinal = new ArrayList<String>();
		for(int i=0;i<usuario.size();i++) {
			if(usuario.get(i)!=idUsuario) {
				usuarioFinal.add(usuario.get(i)); //añadir los usuarios que realmente nos interesa para la sala
			}
		}
		usuarioFinal.remove(idUsuario);//borrar el usuario con la id ya que no estaria en la sala
		Query query = new Query(Criteria.where("_id").is(existe.get_id()));
 		Update update = new Update().set("idUsuario", usuarioFinal);//actualizarlo
 		mongoTemplate.updateMulti(query, update, Salas.class);
	}
	
	//funcion que se encarga de crear las solas con la ruta seleccionada y los usuarios que se hayan metido
	@GetMapping("/salas/newSalas/{idRuta}&{idUsuario}&{latitude}&{longitude}")
	public List<Coordenada> insertarSalas(@PathVariable String idRuta,@PathVariable String idUsuario,@PathVariable String latitude,@PathVariable String longitude){
		Salas existe = repositorySalas.findByIdRuta(idRuta);//buscarlo por el identificador de la ruta
		Query query = new Query(Criteria.where("_id").is(idUsuario));
 		Update update = new Update().set("latitude", latitude).set("longitude", longitude);//poner la latitud y longitud nueva
 		mongoTemplate.updateMulti(query, update, Usuario.class);
		if(existe==null) {//en caso de que ya exista la sala y ya este un usuario en ella
			List<String> usuarios = new ArrayList<String>();
			usuarios.add(idUsuario);//añadir los usuarios de la sala
			Salas sala = new Salas(idRuta,usuarios);//crear una sala nueva con la ruta y los usuarios
			repositorySalas.save(sala);
	 		List<Coordenada> coordenadas = new ArrayList<Coordenada>();
	 		for(int i = 0;i<usuarios.size();i++) {
	 			Optional<Usuario> user = repositoryUsuarios.findById(usuarios.get(i));
	 			Usuario usuario = (Usuario)user.get();
	 			Coordenada cor = new Coordenada(usuario.getLatitude(),usuario.getLongitude());
	 			coordenadas.add(cor);//añadir todas las coordenadas de los usuarios de la sala
	 		}
	 		return coordenadas;
		}else {//en caso de que no este la sala creada
			List<String> usuarios = existe.getIdUsuario();
			if(!usuarios.contains(idUsuario)) {
				usuarios.add(idUsuario);//lo mismo que antes pero creando un mismo array con los usuarios y actulizando la sala creandola
				Query query2 = new Query(Criteria.where("_id").is(existe.get_id()));
		 		Update update2 = new Update().set("idUsuario", usuarios);
		 		mongoTemplate.updateMulti(query2, update2, Salas.class);
			}
			List<Coordenada> coordenadas = new ArrayList<Coordenada>();
			for(String p : usuarios) {
				Optional<Usuario> user = repositoryUsuarios.findById(p);
	 			Usuario usuario = (Usuario)user.get();
	 			Coordenada cor = new Coordenada(usuario.getLatitude(),usuario.getLongitude());
	 			coordenadas.add(cor);//añadir coordenadas de los usuarios
			}
			return coordenadas;
		}

	}
	//funcion que se encarga de buscar una sala por su id
	@CrossOrigin
	@GetMapping("/salas/buscarPorId/{id}")
	public Salas buscarPorIdSala(@PathVariable String id) {
		return repositorySalas.findByIdRuta(id);//funcion que se encarga de buscar por la id de la ruta
	}


	//Rutas

	//funcion que se encarga de devolver todas las rutas
	@CrossOrigin
	 @GetMapping("/rutas/todas")
	  public List<Rutas> getRutas() {
	    return repositoryRutas.findAll();
	}
	
	//funcion que se encarga de coger las rutas con localizaciones
	@CrossOrigin
	@GetMapping("/rutas/casiTodas/")
	  public List<Rutas> getRutas3() {
		List<Rutas> rutas = repositoryRutas.findAll();
		List<Rutas> rutafinal = new ArrayList<Rutas>();
		for(Rutas r:rutas){
			try{
				if(r.getLoc().size()>1){//comprobrar si realmente la ruta tiene localizaciones
					rutafinal.add(r);
				}
			}catch(Exception e){
			}
		}
	    return rutafinal;
	}

	  //funcion que se encarga de actualizar las localizaciones de las rutas con el nombre
	  @CrossOrigin
	  @PutMapping("/rutas/actualizarRutasLocali/{name}")
	  public void updateRutasLoc2(@PathVariable String name,@RequestBody ArrayList<Object> loc) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		  Query query = new Query(Criteria.where("nombre").is(name));
		  Update update2 = new Update().set("loc",loc);
		  //mongoTemplate.updateMulti(query, update2,Rutas.class);
		  mongoTemplate.updateMulti(query, update2,Rutas.class);
	}
	
	//funcion que se encarga de coger todas las rutas
	@CrossOrigin
	@GetMapping("/rutas/all")
	public List<Rutas> getRutas2() {
		return repositoryRutas.findAll();
	}

	//funcion que se encarga de borrar las localizaciones y las localizaciones dentro de una ruta
	@CrossOrigin
	@DeleteMapping("/rutas/deleteById/{id}")
	public void deleteRutas(@PathVariable String id){
		repository.deleteByIdRuta(id);//borrar por el id de la ruta
		repositoryRutas.deleteById(id);
	}
	
	//funcion que se encarga de crear una ruta pasandole como parametro una entera con todo los datos
  	@PostMapping("/rutas/nuevaRuta")
  	public List<Rutas> insertarRuta(@RequestBody Rutas nuevaRuta) {
	  repositoryRutas.save(nuevaRuta);//funcion para guardar
    return repositoryRutas.findAll();
  	}

	//funcion que sirve para eliminar una ruta por nombre
  	@DeleteMapping("/rutas/eliminarRuta/{name}")
  	public void deleteEmpleados(@PathVariable String name) {
	  repositoryRutas.deleteByNombre(name);//eliminarlo por nombre
  	}

	//funcion que busca una ruta en concreto por el nombre
  	@CrossOrigin
  	@GetMapping("/rutas/buscar/{nombre}")
  	public List<Rutas> getRutasByNombre(@PathVariable String nombre) {
    	return repositoryRutas.findByNombre(nombre);//buscarlo por nombre
  	}

	//funcion que sirve para poder hacer el filtro antes de buscar una ruta
  	@CrossOrigin
  	@GetMapping("/rutas/{ciudad}&{vehiculo}&{kilometro}")
	public List<Rutas> getRutasByCiudad(@PathVariable String ciudad,@PathVariable String vehiculo,@PathVariable int kilometro) {
	  	ArrayList<Integer> kilometros;
	  	int kilometroMinimo=0;
	  	int kilometroMaximo=1;
		//comprobaciones que se encargan de filtrar los km de las rutas para poder usarlo en un slider
	  	if(kilometro<1) {
	  		kilometroMinimo=-1;//poniendo numeros fijos para que de un resultado aproximado
	  		kilometroMaximo=2;
	  	}else if(kilometro<6) {
	  		kilometroMinimo=0;
	  		kilometroMaximo=6;
	  	}else if(kilometro<10) {
	  		kilometroMinimo=4;
	  		kilometroMaximo=11;
	  	}else if(kilometro<20) {
	  		kilometroMinimo=9;
	  		kilometroMaximo=21;
	  	}else {
	  		kilometroMinimo=19;
	  		kilometroMaximo=51;
	  	}
		List<Rutas> rutas = repositoryRutas.findByCiudadAndVehiculoAndLongitudBetween(ciudad,vehiculo,kilometroMinimo,kilometroMaximo);//funcion que encarga de mostrar rutas aproximadas a los filtro que hayan metido
		List<Rutas> rutafinal = new ArrayList<Rutas>();
		for(Rutas r:rutas){
			try{
				if(r.getLoc().size()>1){
					rutafinal.add(r);//en caso de que tenga localizacioens esa ruta la añade, ya que no nos interesa rutas sin localizaciones
				}
			}catch(Exception e){
		
			}
		}
		return rutafinal;
	}

	//funcion que se encarga de actulizar las rutas por el id
	@CrossOrigin
	@PutMapping("/rutas/updateRutas/{id}")
	public List<Rutas> updateRutas(@PathVariable String id , @RequestBody Rutas editRuta){
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().set("nombre", editRuta.getNombre()).set("longitud", editRuta.getLongitud()).set("vehiculos", editRuta.getVehiculo()).set("ciudad", editRuta.getCiudad()).set("dificultad", editRuta.getDificultad()).set("imagen", editRuta.getImagen()); 
		//despues de hacer set de los campos nuevos mediante mongotemplate lo actualizaremos
		mongoTemplate.updateMulti(query, update, Rutas.class);
		return repositoryRutas.findAll();
	}
	
	//funcion que se encarga de poner las localizaciones dentro de una ruta
	@CrossOrigin
	@PutMapping("/rutas/updateRutasLoc/{id}")
		public List<Rutas> updateRutasLoc(@PathVariable String id , @RequestBody ArrayList<Object> loc){
			//List<Localizaciones> listaLocalizaciones = new ArrayList<Localizaciones>();
			//listaLocalizaciones.add((Localizaciones) loc);
			Query query = new Query(Criteria.where("_id").is(id));
			Update update = new Update().set("Loc", loc);//todo lo que le llegue por el requestbody, es decir, localizaciones lo pone en el campo loc como object.
			mongoTemplate.updateMulti(query, update, Rutas.class);
			return repositoryRutas.findAll();
	}


  	//Usuarios

	//funcion que se encarga de traer todos los usuarios de vuelta
	@GetMapping("/usuario/all")
	public List<Usuario> getUsuarios() {
		return repositoryUsuarios.findAll();
	}

	//funcion que se encarga de borrar el usuario por su id
	@DeleteMapping("/usuario/deleteById/{id}")
	public void deleteUsuarios(@PathVariable String id){
		repositoryUsuarios.deleteById(id);//borrar por el id de usuario
	}

	//funcion que se encarga de crear un nuevo usuario
	@PostMapping("/usuario/newUsuario")
	public List<Usuario> insertUsuarios(@RequestBody Usuario newUsuario){
		repositoryUsuarios.save(newUsuario);//añade el usuario nuevo tal cual le llega por el requestbody
		return repositoryUsuarios.findAll();
	}

	//funcion que se encarga de actualizar el usuario
	@PutMapping("/usuario/updateUsuario/{id}&{nombre}&{avatar}")
	public List<Usuario> updateUsuarios(@PathVariable String id , @PathVariable String nombre ,@PathVariable String avatar){
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update().set("nombre", nombre);//actualizar el nombre 
		Update update2 = new Update().set("avatar", avatar);//actualizar el avatar
		mongoTemplate.updateMulti(query, update, Usuario.class);
		mongoTemplate.updateMulti(query, update2, Usuario.class);
		return repositoryUsuarios.findAll();
	}

	//funcion que se encarga de buscar un usuario por su nombre
	@GetMapping("/usuario/{nombre}")
	public List<Usuario> getUsuariosByNombre(@PathVariable String nombre) {
		return repositoryUsuarios.findByNombre(nombre);//buscar por nombre
	}

	//funcion que se encarga de devolver un si o un no para poder loguarse en la aplicacion
	@CrossOrigin
	@GetMapping("/usuario/{nombre}/{contrasena}")
	public Boolean getUsuariosByNombreYcontr(@PathVariable String nombre, @PathVariable String contrasena) {
		List<Usuario> usuarios= repositoryUsuarios.findByNombre(nombre);
		try{
			Usuario usuario = usuarios.get(0);
			if(contrasena.equals(usuario.getContrasena())){
				return true;//si coincide true
			}
		}catch(Exception e){
				return false;//si no false
		}
		return false;
	}

	//funcion que buscar los usuarios por id	
	@CrossOrigin
	@GetMapping("/usuario/id/{id}")
	public Optional<Usuario> getUsuariosById(@PathVariable String id) {
		return repositoryUsuarios.findById(id);
	}

	//funcion que se encarga de buscar los usuarios por nombre
	@CrossOrigin
	@GetMapping("/usuario/id/{nombre}")
	public String getUsuarioId(@PathVariable String nombre) {
		List<Usuario> usuarios= repositoryUsuarios.findByNombre(nombre);//buscar por nombre
		Usuario usuario = usuarios.get(0);
		return usuario.getId();
	}

	//funcion que se encarga de buscar los usuarios por id
	@CrossOrigin
	@GetMapping("/usuario/buscarPorId/{id}")
	 	public Optional<Usuario> getUsuarioId2(@PathVariable String id) {
	 		return repositoryUsuarios.findById(id);
	}

	//funcion que se encarga de buscar los usuarios por id
	@CrossOrigin
	@GetMapping("/usuario/busId/{id}")
	public Optional<Usuario> getUsuariosById2(@PathVariable String id) {
		return repositoryUsuarios.findById(id);
	}

  	//Localizaciones

	//funcion que se encarga de buscar todas las localizaciones
 	@CrossOrigin
	@GetMapping("/localizaciones/ver")
	public List<Localizaciones> getLocalizaciones(){
	return repository.findAll();
	}

	//funcion que se encarga de meter una localizacion
 	@CrossOrigin
	@PostMapping("/localizaciones/anadir")
 	public List<Localizaciones> addLocalizacion(@RequestBody Localizaciones localizacion){
	repository.save(localizacion);
	return repository.findAll();
	}

	//funcion que se encarga de buscar una ruta por la id
 	@CrossOrigin
	@GetMapping("/localizaciones/verIdRuta/{id}")
	public List<Localizaciones> buscarLocalicacion(@PathVariable String id){
		return repository.findByIdRuta(id);
	}

	//funcion que se encarga de actualizar las localizaciones
	@CrossOrigin
 	@PutMapping("/localizaciones/updateLoc/{nombre}")
 	public List<Localizaciones> updateLocalizacion(@PathVariable String nombre, @RequestBody Localizaciones loc){
 		Query query = new Query(Criteria.where("nombre").is(nombre));
 		Update update = new Update().set("nombre", loc.getNombre()).set("latitud", loc.getLatitud()).set("longitud",loc.getLongitud()).set("preguntas", loc.getPreguntas()).set("respuestas", loc.getRespuestas());
 		mongoTemplate.updateMulti(query, update, Localizaciones.class);//actualizandolo con mongotemplate
 		return repository.findAll();
 	}

	//funcion que se encarga de borrar una localizacion por el nombre
 	@CrossOrigin
 	@DeleteMapping("/localizaciones/deleteByName/{nombre}")
 	public void deleteLocalizaciones(@PathVariable String nombre){
 		repository.deleteByNombre(nombre);//borrar por el nombre
 	}
 	
	//funcion que se encarga de eliminar las localizaciones dentro de una ruta
 	@CrossOrigin
	@DeleteMapping("/rutas/eliminarRutasLocali/{name}/{index}")
	public void deleteRutasLoc(@PathVariable String name,@PathVariable int index) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
	    Query query = new Query(Criteria.where("nombre").is(name));
	    ArrayList<Rutas> rt = (ArrayList<Rutas>) mongoTemplate.find(query, Rutas.class);
	    rt.get(0).getLoc().remove(index);//borramos justo en la posicion del array de loc que hemos seleccionado
	    Update update = new Update().set("loc", rt.get(0).getLoc());
	    mongoTemplate.updateMulti(query, update,Rutas.class);//actualizamos las localizaciones sin el punto borrado
	 }

	//Ciudades

	//funcion que se encarga de devolver todas las ciudades
	@GetMapping("/ciudades/all")
	public List<Ciudades> getCiudades() {
		return repositoryCiudades.findAll();
	}

	//funcion que se encarga de devolver la ciudad por id
	@GetMapping("/ciudades/id/{id}")
	public Optional<Ciudades> getCiudadesById(@PathVariable String id) {
		return repositoryCiudades.findById(id);//buscarlo por id de ciudad
	}

	//funcion que se encarga de devolver la ciudad por el nombre
	@CrossOrigin
	@GetMapping("/ciudades/nombre/{nombre}")
	public Optional<Ciudades> getCiudadesByNombre(@PathVariable String nombre) {
		return repositoryCiudades.findByNombre(nombre);//buscarlo por el nombre de la ciudad
	}

	//funcion que se encarga de actualizar las ciudades pasandole los atributos necesarios
	@CrossOrigin
	@PutMapping("/ciudades/updateCiudad/{id}/{nombre}/{latitude}/{longitude}")
	public List<Ciudades> updateCiudades(@PathVariable String id , @PathVariable String nombre, @PathVariable String latitude, @PathVariable String longitude){
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update().set("nombre", nombre);
		Update update2 = new Update().set("latitude", latitude);
		Update update3 = new Update().set("longitude", longitude);
		mongoTemplate.updateMulti(query, update, Ciudades.class);//updateamos por cada cambio nuevo 
		mongoTemplate.updateMulti(query, update2, Ciudades.class);
		mongoTemplate.updateMulti(query, update3, Ciudades.class);
		return repositoryCiudades.findAll();
	}

	//funcion que se encarga de borrar una ciudad por su id
	@DeleteMapping("/ciudades/deleteById/{id}")
	public void deleteCiudades(@PathVariable String id){
		repositoryCiudades.deleteById(id);//borrar por id
	}

	//funcion que añade una nueva ciudad
	@PostMapping("/ciudades/newCiudad")
	public List<Ciudades> insertCiudades(@RequestBody Ciudades newCiudad){
		repositoryCiudades.save(newCiudad);
		return repositoryCiudades.findAll();
	}

	//funcion que actualiza la ciudad
	@PutMapping("/ciudades/updateCiudad/{id}&{nombre}")
	public List<Ciudades> updateCiudades(@PathVariable String id , @PathVariable String nombre){
		Query query = new Query(Criteria.where("id").is(id));//filtrar la ciudad a editar por la id
		Update update = new Update().set("nombre", nombre);
		mongoTemplate.updateMulti(query, update, Ciudades.class);
		return repositoryCiudades.findAll();
	}

	//Puntuaciones

	//funcion que se encarga de devolver las puntuaciones de una ruta
	@GetMapping("/puntuaciones/all/{idRuta}")
	public List<JSONObject> getPuntuaciones(@PathVariable String idRuta) {
		List<Puntuaciones> puntuacion = repositoryPuntuaciones.findTop10ByIdRutaOrderByPuntosDesc(idRuta);//buscar las diez primeras puntuaciones del ranking 
		List<JSONObject> todo = new ArrayList<JSONObject>();
		for(Puntuaciones pu :puntuacion) {
			JSONObject json = new JSONObject();
			Optional<Usuario> user = repositoryUsuarios.findById(pu.getIdUsuario());
			json.put("usuario",(Usuario)user.get());
			json.put("puntuacion", pu.getPuntos());
			json.put("ruta", pu.getIdRuta());
			todo.add(json);//hacemos un json con las puntuaciones de los usuarios junto con la ruta 
		}
		return todo;
	}

	//funcion que se encarga de buscar una puntuacion de un usuario en una ruta en concreto
	@GetMapping("/puntuaciones/buscarPuntuacion/{idRuta}&{idUsuario}")
	public Puntuaciones buscarPuntuacion(@RequestBody @PathVariable String idRuta,@PathVariable String idUsuario){
		Puntuaciones puntuacion = repositoryPuntuaciones.findByIdRutaAndIdUsuario(idRuta,idUsuario);
		return puntuacion;
	}

	//funcion que se encarga de borrar una puntuacion por el id
	@DeleteMapping("/puntuaciones/deleteById/{id}")
	public void deletePuntuaciones(@PathVariable String id){
		repositoryPuntuaciones.deleteById(id);
	}

	//funcion que se encarga de añadir una nueva puntuacion
	@PostMapping("/puntuaciones/newPuntuacion")
	public Puntuaciones insertPuntuaciones(@RequestBody Puntuaciones newPuntuacion){
		repositoryPuntuaciones.save(newPuntuacion);
		return repositoryPuntuaciones.findByIdRutaAndIdUsuario(newPuntuacion.getIdRuta(), newPuntuacion.getIdUsuario());//buscar exactamente la que nos interese
	}

	//funcion que se encarga de actualizar las puntuaciones por el id y pasandole los nuevos puntos
	@PutMapping("/puntuaciones/updatePuntuaciones/{id}&{puntos}")
	public List<Puntuaciones> updatePuntuaciones(@PathVariable String id , @PathVariable int puntos){
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().set("puntos", puntos);//ponemos los nuevos puntos actualizados
		mongoTemplate.updateMulti(query, update, Puntuaciones.class);
		return repositoryPuntuaciones.findAll();
	}
}
