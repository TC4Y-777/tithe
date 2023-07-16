/**
 * 
 */
package com.tithe.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;

import com.tithe.entity.FamilyEntity;
import com.tithe.entity.PersonEntity;
import com.tithe.model.PersonQueryFilter;
import com.tithe.repository.FamilyRepository;
import com.tithe.repository.PersonRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class PersonQueryService {
	
	@Autowired
	private PersonRepository personRepository;
	
	public PersonEntity getOnePerson(Long id) {
		Optional<PersonEntity> person = personRepository.findById(id);
		return person.orElse(null);
	}

	public List<PersonEntity> getManyPersons(PersonQueryFilter personQueryFilter) {
		PersonEntity personEntity = new PersonEntity();
		personEntity.setBaptismName(personQueryFilter.getBaptismName());
		personEntity.setPersonName(personQueryFilter.getPersonName());
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

}
