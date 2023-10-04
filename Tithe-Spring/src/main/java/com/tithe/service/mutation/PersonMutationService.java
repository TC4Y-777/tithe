/**
 *
 */
package com.tithe.service.mutation;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.EducationEntity;
import com.tithe.entity.FamilyEntity;
import com.tithe.entity.OccupationEntity;
import com.tithe.entity.PersonBuilder;
import com.tithe.entity.PersonEntity;
import com.tithe.entity.RelationEntity;
import com.tithe.entity.TitheBuilder;
import com.tithe.entity.TitheEntity;
import com.tithe.model.PersonMutationInput;
import com.tithe.model.TitheMutationInput;
import com.tithe.repository.PersonRepository;
import com.tithe.service.query.EducationQueryService;
import com.tithe.service.query.FamilyQueryService;
import com.tithe.service.query.OccupationQueryService;
import com.tithe.service.query.PersonQueryService;
import com.tithe.service.query.RelationQueryService;
import com.tithe.service.query.TitheQueryService;
import com.tithe.utils.ObjectValidation;

import graphql.GraphQLException;


/**
 * @author Ashish Sam T George
 *
 */
@Service
public class PersonMutationService {

	@Autowired
	private ObjectValidation objectValidation;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PersonQueryService personQueryService;

	@Autowired
	private FamilyQueryService familyQueryService;

	@Autowired
	private RelationQueryService relationQueryService;

	@Autowired
	private EducationQueryService educationQueryService;

	@Autowired
	private OccupationQueryService occupationQueryService;

	@Autowired
	private TitheQueryService titheQueryService;

	public PersonEntity createOnePerson(PersonMutationInput personMutationInput) {

//		Validate the obtained input
		objectValidation.validateObject(personMutationInput);

//		Get family using the id, if exists, or else return error
		FamilyEntity family = familyQueryService.getOneFamily(personMutationInput.getFamilyId());
		if (family == null) {
			throw new GraphQLException("Family does not exist");
		}

		PersonEntity person = buildPersonWithTithe(family, personMutationInput);

		return personRepository.save(person);
	}

	public List<PersonEntity> createManyPersonsInOneFamily(Long familyId,
			List<PersonMutationInput> personMutationInputs) {
		FamilyEntity family = familyQueryService.getOneFamily(familyId);

		if (family != null) {
			List<PersonEntity> persons = buildPersonsWithTithe(family, personMutationInputs);
			return personRepository.saveAll(persons);
		}
		throw new GraphQLException("Family does not exist");
	}

	public PersonEntity changeRelation(Long personId, Long relationId) {
		if (personId == null || relationId == null) {
			throw new GraphQLException("Person Id and Relation Id should be valid");
		}

		PersonEntity person = personQueryService.getOnePerson(personId);
		RelationEntity relation = relationQueryService.getOneRelation(relationId);

		if (person == null || relation == null) {
			throw new GraphQLException("Person and Relation should exist");
		}

		person.setRelation(relation);
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
		if (persons.size() != 0) {
			for (PersonEntity person : persons) {
				person.setActive(true);
			}
			return personRepository.saveAll(persons);
		}
		return null;
	}

//	TODO If the operation is not successful, should throw an error or something to the frontend
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
		if (persons.size() != 0) {
			for (PersonEntity person : persons) {
				person.setActive(false);
			}
			return personRepository.saveAll(persons);
		}
		return null;
	}

	public List<EducationEntity> getEducations(List<Long> educationIds) {
		List<EducationEntity> educations = new ArrayList<>();
		if (educationIds != null && educationIds.size() != 0) {
			educations = educationQueryService.getManyEducations(educationIds);
		}
		return educations;
	}

	public List<OccupationEntity> getOccupations(List<Long> occupationIds) {
		List<OccupationEntity> occupations = new ArrayList<>();
		if (occupationIds != null && occupationIds.size() != 0) {
			occupations = occupationQueryService.getManyOccupations(occupationIds);
		}
		return occupations;
	}

	public PersonEntity buildPersonWithTithe(FamilyEntity family,
			PersonMutationInput personMutationInput) {
		objectValidation.validateObject(personMutationInput);

		RelationEntity relation = relationQueryService
				.getOneRelation(personMutationInput.getRelationId());

		List<EducationEntity> educations = getEducations(personMutationInput.getEducationIds());

		List<OccupationEntity> occupations = getOccupations(personMutationInput.getOccupationIds());

		PersonBuilder personBuilder = new PersonBuilder();
		PersonEntity person = personBuilder.baptismName(personMutationInput.getBaptismName())
				.personName(personMutationInput.getPersonName())
				.family(family)
				.relation(relation)
				.gender(personMutationInput.getGender())
				.dob(personMutationInput.getDob())
				.phone(personMutationInput.getPhone())
				.moved(personMutationInput.getMoved())
				.educations(educations)
				.occupations(occupations)
				.active(personMutationInput.getActive())
				.build();

//		Build Tithe entity
		TitheBuilder titheBuilder = new TitheBuilder();
		List<TitheEntity> tithes = titheBuilder.buildTithe(person, personMutationInput.getTithes());
		person.setTithes(tithes);

		return person;
	}

	public List<PersonEntity> buildPersonsWithTithe(FamilyEntity family,
			List<PersonMutationInput> personMutationInputs) {
		List<PersonEntity> persons = new ArrayList<>();
		for (PersonMutationInput personMutationInput : personMutationInputs) {
			PersonEntity person = buildPersonWithTithe(family, personMutationInput);
			persons.add(person);
		}
		return persons;
	}

}
