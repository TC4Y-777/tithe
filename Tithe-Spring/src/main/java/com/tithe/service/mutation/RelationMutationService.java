/**
 * 
 */
package com.tithe.service.mutation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.RelationEntity;
import com.tithe.repository.RelationRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class RelationMutationService {
	
	@Autowired
	private RelationRepository relationRepository;

	public List<RelationEntity> createManyRelations(List<String> relationNames) {
		List<RelationEntity> relations = new ArrayList<>();
		for (String relationName : relationNames) {
			if (relationName!=null) {
				RelationEntity relation = new RelationEntity();
				relation.setRelationName(relationName);
				relations.add(relation);
			}
		}
		return relationRepository.saveAll(relations);
	}

}
