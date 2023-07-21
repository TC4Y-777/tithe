/**
 * 
 */
package com.tithe.service.mutation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.PersonEntity;
import com.tithe.entity.TitheEntity;
import com.tithe.model.PersonMutationInput;
import com.tithe.model.TitheMutationInput;
import com.tithe.repository.PersonRepository;
import com.tithe.repository.TitheRepository;
import com.tithe.service.query.EducationQueryService;
import com.tithe.service.query.FamilyQueryService;
import com.tithe.service.query.OccupationQueryService;
import com.tithe.service.query.RelationQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class PersonMutationService {
	
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private FamilyQueryService familyQueryService;
	
	@Autowired
	private RelationQueryService relationQueryService;
	
	@Autowired
	private EducationQueryService educationQueryService;
	
	@Autowired
	private OccupationQueryService occupationQueryService;
	
	public PersonEntity createOnePerson(PersonMutationInput personMutationInput) {
		PersonEntity person = new PersonEntity();
		person.setBaptismName(personMutationInput.getBaptismName());
		person.setPersonName(personMutationInput.getPersonName());
		
		if (personMutationInput.getFamilyId()!=null) {
			person.setFamily(familyQueryService.getOneFamily(personMutationInput.getFamilyId()));
		}
		if (personMutationInput.getRelationId()!=null) {
			person.setRelation(relationQueryService.getOneRelation(personMutationInput.getRelationId()));
		}
		
		person.setGender(personMutationInput.getGender());
		person.setDob(LocalDate.parse(personMutationInput.getDob()));
		person.setPhone(personMutationInput.getPhone());
		
		List<TitheMutationInput> titheInputs = personMutationInput.getTithes();
		List<TitheEntity> tithes = new ArrayList<>();
		if (titheInputs.size()!=0) {
			for (TitheMutationInput titheInput : titheInputs) {
				TitheEntity tithe = new TitheEntity();
				tithe.setTitheAmount(titheInput.getTitheAmount());
				tithe.setTimeStamp(LocalDate.parse(titheInput.getTimeStamp()));
				tithe.setPerson(person);
				tithe.setFamily(person.getFamily());
				tithe.setKoottayma(tithe.getFamily().getKoottayma());
				tithe.setParish(tithe.getKoottayma().getParish());
				tithe.setForane(tithe.getParish().getForane());
				
				tithes.add(tithe);
			}
		}
		person.setTithes(tithes);
		
		person.setMoved(personMutationInput.getMoved());
		
		if (personMutationInput.getEducationIds().size()!=0) {
			person.setEducations(educationQueryService.getManyEducations(personMutationInput.getEducationIds()));
		}
		if (personMutationInput.getOccupationIds().size()!=0) {
			person.setOccupations(occupationQueryService.getManyOccupations(personMutationInput.getOccupationIds()));
		}
		
		person.setActive(personMutationInput.getActive());
		
		return personRepository.save(person);
	}

	public PersonEntity activateOnePerson(Long id) {
		Optional<PersonEntity> person = personRepository.findById(id);
		if (person.isPresent()) {
			person.get().setActive(true);
			return personRepository.save(person.get());
		}
		return null;
	}

	public List<PersonEntity> activateManyPersons(List<Long> ids) {
		List<PersonEntity> persons = personRepository.findAllById(ids);
		if (persons.size()!=0) {
			for (PersonEntity person : persons) {
				person.setActive(true);
			}
			return personRepository.saveAll(persons);
		}
		return null;
	}

	public PersonEntity deactivateOnePerson(Long id) {
		Optional<PersonEntity> person = personRepository.findById(id);
		if (person.isPresent()) {
			person.get().setActive(false);
			return personRepository.save(person.get());
		}
		return null;
	}

	public List<PersonEntity> deactivateManyPersons(List<Long> ids) {
		List<PersonEntity> persons = personRepository.findAllById(ids);
		if (persons.size()!=0) {
			for (PersonEntity person : persons) {
				person.setActive(false);
			}
			return personRepository.saveAll(persons);
		}
		return null;
	}

}
