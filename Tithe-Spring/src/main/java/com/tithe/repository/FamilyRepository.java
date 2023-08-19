/**
 *
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.FamilyEntity;
import com.tithe.entity.ForaneEntity;
import com.tithe.entity.ParishEntity;
import com.tithe.entity.KoottaymaEntity;




/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {

	long countByActive(Boolean active);
	long countByKoottayma_Parish_ForaneAndActive(ForaneEntity forane, Boolean active);
	long countByKoottayma_ParishAndActive(ParishEntity parish, Boolean active);
	long countByKoottaymaAndActive(KoottaymaEntity koottayma, Boolean active);

}
