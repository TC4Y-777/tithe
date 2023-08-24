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
import com.tithe.entity.PersonEntity;
import com.tithe.model.PersonQueryFilter;
import com.tithe.repository.PersonRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class PersonQueryService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ForaneQueryService foraneQueryService;

	@Autowired
	private ParishQueryService parishQueryService;
	
	@Autowired
	private KoottaymaQueryService koottaymaQueryService;
	
	@Autowired
	private FamilyQueryService familyQueryService;
	
	public PersonEntity getOnePerson(Long id) {
		Optional<PersonEntity> person = personRepository.findById(id);
		return person.orElse(null);
	}

	public List<PersonEntity> getManyPersons(PersonQueryFilter personQueryFilter) {
		PersonEntity personEntity = new PersonEntity();

//		TODO Use constraints in filter if more checking is needed

		if (personQueryFilter.getBaptismName()!=null && !personQueryFilter.getBaptismName().isBlank()) {
			personEntity.setBaptismName(personQueryFilter.getBaptismName());
		}
		if (personQueryFilter.getPersonName()!=null && !personQueryFilter.getPersonName().isBlank()) {
			personEntity.setPersonName(personQueryFilter.getPersonName());
		}
		personEntity.setDob(personQueryFilter.getDob());
		personEntity.setPhone(personQueryFilter.getPhone());
		personEntity.setGender(personQueryFilter.getGender());
		personEntity.setActive(personQueryFilter.getActive());

		Example<PersonEntity> examplePerson = Example.of(personEntity);
		List<PersonEntity> matchingPersons = personRepository.findAll(examplePerson);

		if (matchingPersons.size()!=0) {
			return matchingPersons;
		}
		return null;
	}

	public List<PersonEntity> getAllPersons() {
		List<PersonEntity> persons = personRepository.findAll();
		if (persons.size()!=0) {
			return persons;
		}
		return null;
	}
	
	public List<PersonEntity> getAllPersonsByForane(Long foraneId) {
		ForaneEntity forane = foraneQueryService.getOneForane(foraneId);
		if (forane!=null) {
			List<PersonEntity> persons = personRepository.findAllByFamily_Koottayma_Parish_Forane(forane);
			if (persons.size()!=0) {
				return persons;
			}
		}
		return null;
	}

	public List<PersonEntity> getAllPersonsByParish(Long parishId) {
		ParishEntity parish = parishQueryService.getOneParish(parishId);
		if(parish!=null) {
			List<PersonEntity> persons = personRepository.findAllByFamily_Koottayma_Parish(parish);
			if (persons.size()!=0) {
				return persons;
			}
		}
		return null;
	}

	public List<PersonEntity> getAllPersonsByKoottayma(Long koottaymaId) {
		KoottaymaEntity koottayma = koottaymaQueryService.getOneKoottayma(koottaymaId);
		if (koottayma!=null) {
			List<PersonEntity> persons = personRepository.findAllByFamily_Koottayma(koottayma);
			if (persons.size()!=0) {
				return persons;
			}
		}
		return null;
	}

	public List<PersonEntity> getAllPersonsByFamily(Long familyId) {
		FamilyEntity family = familyQueryService.getOneFamily(familyId);
		if (family!=null) {
			List<PersonEntity> persons = personRepository.findAllByFamily(family);
			if (persons.size()!=0) {
				return persons;
			}
		}
		return null;
	}

	public Long getPersonCount() {
		return personRepository.countByActive(true);
	}

	public Long getPersonCountByForane(Long foraneId) {
		ForaneEntity forane = foraneQueryService.getOneForane(foraneId);
		if (forane!=null) {
			return personRepository.countByFamily_Koottayma_Parish_ForaneAndActive(forane, true);
		}
		return null;
	}

	public Long getPersonCountByParish(Long parishId) {
		ParishEntity parish = parishQueryService.getOneParish(parishId);
		if(parish!=null) {
			return personRepository.countByFamily_Koottayma_ParishAndActive(parish, true);
		}
		return null;
	}

	public Long getPersonCountByKoottayma(Long koottaymaId) {
		KoottaymaEntity koottayma = koottaymaQueryService.getOneKoottayma(koottaymaId);
		if (koottayma!=null) {
			return personRepository.countByFamily_KoottaymaAndActive(koottayma, true);
		}
		return null;
	}

	public Long getPersonCountByFamily(Long familyId) {
		FamilyEntity family = familyQueryService.getOneFamily(familyId);
		if (family!=null) {
			return personRepository.countByFamilyAndActive(family, true);
		}
		return null;
	}

}
