/**
 *
 */
package com.tithe.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.RelationEntity;
import com.tithe.repository.RelationRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class RelationQueryService {

	@Autowired
	private RelationRepository relationRepository;

	public RelationEntity getOneRelation(Long id) {
		Optional<RelationEntity> relation = relationRepository.findById(id);
		return relation.orElse(null);
	}

	public List<RelationEntity> getAllRelations() {
		List<RelationEntity> relations = relationRepository.findAll();
		if(relations.size() != 0) {
			return relations;
		}
		return null;
	}

}
