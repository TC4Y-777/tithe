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
	
	@MutationMapping(name = "createOneEducation")
	public EducationEntity createOneEducation(@Argument String educationName) {
		List<String> educationNames = List.of(educationName);
		List<EducationEntity> educations = educationMutationService.createManyEducations(educationNames);
		return educations.get(0);
	}
	
	@MutationMapping(name = "createManyEducations")
	public List<EducationEntity> createManyEducations(@Argument List<String> educationNames) {
		return educationMutationService.createManyEducations(educationNames);
	}
	
	@MutationMapping(name = "createOneOccupation")
	public OccupationEntity createOneOccupation(@Argument String occupationName) {
		List<String> occupationNames = List.of(occupationName);
		List<OccupationEntity> occupations = occupationMutationService.createManyOccupations(occupationNames);
		return occupations.get(0);
	}
	
	@MutationMapping(name = "createManyOccupations")
	public List<OccupationEntity> createManyOccupations(@Argument List<String> occupationNames) {
		return occupationMutationService.createManyOccupations(occupationNames);
	}
	
	@MutationMapping(name = "createOneRelation")
	public RelationEntity createOneRelation(@Argument String relationName) {
		List<String> relationNames = List.of(relationName);
		List<RelationEntity> relations = relationMutationService.createManyRelations(relationNames);
		return relations.get(0);
	}
	
	@MutationMapping(name = "createManyRelations")
	public List<RelationEntity> createManyRelations(@Argument List<String> relationNames) {
		return relationMutationService.createManyRelations(relationNames);
	}
	
}
