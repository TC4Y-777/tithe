/**
 * 
 */
package com.tithe.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.tithe.entity.FamilyEntity;
import com.tithe.model.FamilyQueryFilter;
import com.tithe.repository.FamilyRepository;
import com.tithe.repository.KoottaymaRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class FamilyQueryService {

	@Autowired
	private FamilyRepository familyRepository;
	
	@Autowired
	private KoottaymaQueryService koottaymaQueryService;
	
	public FamilyEntity getOneFamily(Long id) {
		Optional<FamilyEntity> family = familyRepository.findById(id);
		return family.orElse(null);
	}

	public List<FamilyEntity> getManyFamilies(FamilyQueryFilter familyQueryFilter) {
		FamilyEntity family = new FamilyEntity();
		if (familyQueryFilter.getFamilyName()!=null) {
			family.setFamilyName(familyQueryFilter.getFamilyName());
		}
		
		family.setPhone(familyQueryFilter.getPhone());
		if (familyQueryFilter.getKoottaymaId()!=null) {
			family.setKoottayma(koottaymaQueryService.getOneKoottayma(familyQueryFilter.getKoottaymaId()));
		}
//		What about families under a parish
		family.setActive(familyQueryFilter.getActive());
		
		Example<FamilyEntity> exampleFamily = Example.of(family);
		List<FamilyEntity> matchingFamilies = familyRepository.findAll(exampleFamily);
		
		if (matchingFamilies.size()!=0) {
			return matchingFamilies;
		}
		return null;
	}

	public List<FamilyEntity> getAllFamilies() {
		List<FamilyEntity> families = familyRepository.findAll();
		if (families.size()!=0) {
			return families;
		}
		return null;
	}

}
