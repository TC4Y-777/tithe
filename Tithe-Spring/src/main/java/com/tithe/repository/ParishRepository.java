/**
 *
 */
package com.tithe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.ForaneEntity;
import com.tithe.entity.ParishEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface ParishRepository extends JpaRepository<ParishEntity, Long> {

	long countByActive(Boolean active);
	long countByForaneAndActive(ForaneEntity forane, Boolean active);
	
	List<ParishEntity> findAllByForane(ForaneEntity forane);

}
