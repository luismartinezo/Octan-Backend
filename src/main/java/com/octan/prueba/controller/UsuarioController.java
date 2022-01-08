/**
 * 
 */
package com.octan.prueba.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octan.prueba.Util.restResponse;
import com.octan.prueba.exception.ResourceNotFoundException;
import com.octan.prueba.exception.octanExcepcion;
import com.octan.prueba.model.Usuario;
import com.octan.prueba.repository.UsuarioRepository;

/**
 * @author LUIS MARTINEZ
 * @since 01/2022
 */

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	protected ObjectMapper mapper;
	
	@GetMapping
	private ResponseEntity<List<Usuario>> getAllUsuarios (){
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable(value = "id") Long usuarioId)
			throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no funciona para el id :: " + usuarioId));
		return ResponseEntity.ok().body(usuario);
	}

	@PostMapping("/create")
	public restResponse createUsuario(@Validated @RequestBody String usuarioJson)
			throws JsonMappingException, JsonProcessingException, octanExcepcion {
		this.mapper = new ObjectMapper();
        Usuario usuario = this.mapper.readValue(usuarioJson, Usuario.class);
        
		if (!this.validate(usuario)) {
            return new restResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Campo obligatorio sin diligenciar");
        }
		if (usuarioRepository.existsById(usuario.getId())) {
            return new restResponse(HttpStatus.NOT_FOUND.value(), "El Usuario ya existe");
        }
		this.usuarioRepository.save(usuario);
		 return new restResponse(HttpStatus.OK.value(), "Usuario creado con exito");
		
	}
	
	@PostMapping("/update")
	public restResponse update(@RequestBody String usuarioJson)
			throws JsonMappingException, JsonProcessingException, octanExcepcion {
		this.mapper = new ObjectMapper();

		Usuario usuario = this.mapper.readValue(usuarioJson, Usuario.class);

		if (!usuarioRepository.existsById(usuario.getId())) {
			return new restResponse(HttpStatus.NOT_FOUND.value(), "No existe el usuario en la base de datos");
		}

		if (!this.validate(usuario)) {
			return new restResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Campo obligatorio sin diligenciar");
		}
		this.usuarioRepository.save(usuario);
		return new restResponse(HttpStatus.OK.value(), "Usuario actualizo con exito");
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteUsuario(@PathVariable(value = "id") Long usuarioId)
			throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Rol con id :: " + usuarioId +" No existe"));

		usuarioRepository.delete(usuario);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Usuario Eliminado", Boolean.TRUE);
		return response;
	}
	
	// Validaciones
		private boolean validate(Usuario user) {
			boolean isValid = true;

			if (user.getNombre() == null || user.getNombre() == "" ) {
				isValid = false;
			}
			
			return isValid;
		}
}
