/**
 * 
 */
package com.octan.prueba.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octan.prueba.Util.restResponse;
import com.octan.prueba.exception.ResourceNotFoundException;
import com.octan.prueba.exception.octanExcepcion;
import com.octan.prueba.model.Rol;
import com.octan.prueba.repository.RolRepository;

/**
 * @author LUIS MARTINEZ
 * @since 01/2022
 */
@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(origins = "*")
public class RolController {

	@Autowired
	private RolRepository rolRepository;

	protected ObjectMapper mapper;
	
	@GetMapping
	private ResponseEntity<List<Rol>> getAllRoles() {
		return ResponseEntity.ok(rolRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Rol> getRolById(@PathVariable(value = "id") Long rolId) throws ResourceNotFoundException {
		Rol rol = rolRepository.findById(rolId)
				.orElseThrow(() -> new ResourceNotFoundException("Rol no funciona para el id :: " + rolId));
		return ResponseEntity.ok().body(rol);
	}

	@PostMapping("/create")
	public restResponse createRol(@Validated @RequestBody String rolJson)
			throws JsonMappingException, JsonProcessingException, octanExcepcion {
		this.mapper = new ObjectMapper();
        Rol rol = this.mapper.readValue(rolJson, Rol.class);
        
		if (!this.validate(rol)) {
            return new restResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Campo obligatorio sin diligenciar");
        }
		if (rolRepository.existsById(rol.getId())) {
            return new restResponse(HttpStatus.NOT_FOUND.value(), "El Rol ya existe");
        }
		this.rolRepository.save(rol);
		 return new restResponse(HttpStatus.OK.value(), "Rol creado con exito");
		
	}

	@PostMapping("/update")
	public restResponse updateRol(@RequestBody String rolJson)
			throws JsonMappingException, JsonProcessingException, octanExcepcion {
		this.mapper = new ObjectMapper();
		Rol rol = this.mapper.readValue(rolJson, Rol.class);
		
		if (!rolRepository.existsById(rol.getId())) {
            return new restResponse(HttpStatus.NOT_FOUND.value(), "No existe este Rol en la base de datos");
        }

        if (!this.validate(rol)) {
            return new restResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Campo obligatorio sin diligenciar");
        }
        this.rolRepository.save(rol);
        return new restResponse(HttpStatus.OK.value(), "Rol actualizo con exito");
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteRol(@PathVariable(value = "id") Long rolId) throws ResourceNotFoundException {
		Rol rol = rolRepository.findById(rolId)
				.orElseThrow(() -> new ResourceNotFoundException("Rol no funciona para este id :: " + rolId));

		rolRepository.delete(rol);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Rol Eliminado", Boolean.TRUE);
		return response;
	}

	// Validaciones
	private boolean validate(Rol rol) {
		boolean isValid = true;

		if (rol.getNombre() == null || rol.getNombre() == "") {
			isValid = false;
		}
		
		return isValid;
	}
}
