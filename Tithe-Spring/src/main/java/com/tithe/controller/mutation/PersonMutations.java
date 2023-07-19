/**
 * 
 */
package com.tithe.controller.mutation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.EducationEntity;
import com.tithe.entity.OccupationEntity;
import com.tithe.entity.PersonEntity;
import com.tithe.entity.RelationEntity;
import com.tithe.model.PersonMutationInput;
import com.tithe.service.mutation.EducationMutationService;
import com.tithe.service.mutation.OccupationMutationService;
import com.tithe.service.mutation.PersonMutationService;
import com.tithe.service.mutation.RelationMutationService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class PersonMutations {
	
	@Autowired
	private PersonMutationService personMutationService;
	
	@Autowired
	private EducationMutationService educationMutationService;
	
	@Autowired
	private OccupationMutationService occupationMutationService;
	
	@Autowired
	private RelationMutationService relationMutationService;

	@MutationMapping(name = "createOnePerson")
	public PersonEntity createOnePerson(@Argument PersonMutationInput personMutationInput) {
		return personMutationService.createOnePerson(personMutationInput);
	}
	
	@MutationMapping(name = "createManyEducations")
	public List<EducationEntity> createManyEducations(List<String> educationNames) {
		return educationMutationService.createManyEducations(educationNames);
	}
	
	@MutationMapping(name = "createManyOccupations")
	public List<OccupationEntity> createManyOccupations(List<String> occupationNames) {
		return occupationMutationService.createManyOccupations(occupationNames);
	}
	
	@MutationMapping(name = "createOneRelation")
	public RelationEntity createOneRelation(String relationName) {
		List<String> relationNames = List.of(relationName);
		List<RelationEntity> relations = relationMutationService.createManyRelations(relationNames);
		return relations.get(0);
	}
	
	@MutationMapping(name = "createManyRelations")
	public List<RelationEntity> createManyRelations(List<String> relationNames) {
		return relationMutationService.createManyRelations(relationNames);
	}
	
}
