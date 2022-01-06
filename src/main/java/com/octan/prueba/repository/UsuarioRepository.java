/**
 * 
 */
package com.octan.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.octan.prueba.model.Usuario;

/**
 * @author LUIS MARTINEZ
 * @since 01/2022
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
