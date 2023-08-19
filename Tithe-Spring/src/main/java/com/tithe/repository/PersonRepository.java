/**
 *
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.PersonEntity;
import com.tithe.entity.ForaneEntity;
import com.tithe.entity.ParishEntity;
import com.tithe.entity.KoottaymaEntity;
import com.tithe.entity.FamilyEntity;





/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

	long countByActive(Boolean active);
	long countByFamily_Koottayma_Parish_ForaneAndActive(ForaneEntity forane, Boolean active);
	long countByFamily_Koottayma_ParishAndActive(ParishEntity parish, Boolean active);
	long countByFamily_KoottaymaAndActive(KoottaymaEntity koottayma, Boolean active);
	long countByFamilyAndActive(FamilyEntity family, Boolean active);

}
