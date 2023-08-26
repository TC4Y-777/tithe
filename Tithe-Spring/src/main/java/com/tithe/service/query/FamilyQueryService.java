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
import com.tithe.entity.ForaneEntity;
import com.tithe.entity.KoottaymaEntity;
import com.tithe.entity.ParishEntity;
import com.tithe.model.FamilyQueryFilter;
import com.tithe.repository.FamilyRepository;

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
	
	@Autowired
	private ForaneQueryService foraneQueryService;
	
	@Autowired
	private ParishQueryService parishQueryService;

	public FamilyEntity getOneFamily(Long id) {
		Optional<FamilyEntity> family = familyRepository.findById(id);
		return family.orElse(null);
	}

	public List<FamilyEntity> getManyFamilies(FamilyQueryFilter familyQueryFilter) {
		FamilyEntity family = new FamilyEntity();
		if (familyQueryFilter.getFamilyName()!=null && !familyQueryFilter.getFamilyName().isBlank()) {
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
	
	public List<FamilyEntity> getAllFamiliesByForane(Long foraneId) {
		ForaneEntity forane = foraneQueryService.getOneForane(foraneId);
		if (forane!=null) {
			List<FamilyEntity> families = familyRepository.findAllByKoottayma_Parish_Forane(forane);
			if (families.size()!=0) {
				return families;
			}
		}
		return null;
	}
	
	public List<FamilyEntity> getAllFamiliesByParish(Long parishId) {
		ParishEntity parish = parishQueryService.getOneParish(parishId);
		if (parish!=null) {
			List<FamilyEntity> families = familyRepository.findAllByKoottayma_Parish(parish);
			if (families.size()!=0) {
				return families;
			}
		}
		return null;
	}
	
	public List<FamilyEntity> getAllFamiliesByKoottayma(Long koottaymaId) {
		KoottaymaEntity koottayma = koottaymaQueryService.getOneKoottayma(koottaymaId);
		if (koottayma!=null) {
			List<FamilyEntity> families = familyRepository.findAllByKoottayma(koottayma);
			if (families.size()!=0) {
				return families;
			}
		}
		return null;
	}

	public Long getFamilyCount() {
		return familyRepository.countByActive(true);
	}

	public Long getFamilyCountByForane(Long foraneId) {
		ForaneEntity forane = foraneQueryService.getOneForane(foraneId);
		if (forane!=null) {
			return familyRepository.countByKoottayma_Parish_ForaneAndActive(forane, true);
		}
		return null;
	}

	public Long getFamilyCountByParish(Long parishId) {
		ParishEntity parish = parishQueryService.getOneParish(parishId);
		if (parish!=null) {
			return familyRepository.countByKoottayma_ParishAndActive(parish, true);
		}
		return null;
	}

	public Long getFamilyCountByKoottayma(Long koottaymaId) {
		KoottaymaEntity koottayma = koottaymaQueryService.getOneKoottayma(koottaymaId);
		if (koottayma!=null) {
			return familyRepository.countByKoottaymaAndActive(koottayma, true);
		}
		return null;
	}

}
