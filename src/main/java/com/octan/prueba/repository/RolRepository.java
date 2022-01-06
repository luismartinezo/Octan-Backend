/**
 * 
 */
package com.octan.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.octan.prueba.model.Rol;

/**
 * @author LUIS MARTINEZ
 * @since 01/2022
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{

}
