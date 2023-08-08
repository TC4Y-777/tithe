/**
 * 
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.ForaneEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface ForaneRepository extends JpaRepository<ForaneEntity, Long> {
	
	long countByActive(Boolean active);

}
