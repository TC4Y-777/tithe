/**
 *
 */
package com.tithe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.KoottaymaEntity;
import com.tithe.entity.ForaneEntity;
import com.tithe.entity.ParishEntity;



/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface KoottaymaRepository extends JpaRepository<KoottaymaEntity, Long> {

	long countByActive(Boolean active);
	long countByParish_ForaneAndActive(ForaneEntity forane, Boolean active);
	long countByParishAndActive(ParishEntity parish, Boolean active);
	
	List<KoottaymaEntity> findAllByParish_Forane(ForaneEntity forane);
	List<KoottaymaEntity> findAllByParish(ParishEntity parish);

}
