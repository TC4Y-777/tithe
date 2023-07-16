/**
 * 
 */
package com.tithe.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.OccupationEntity;
import com.tithe.repository.OccupationRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class OccupationQueryService {
	
	@Autowired
	private OccupationRepository occupationRepository;

	public OccupationEntity getOneOccupation(Long id) {
		Optional<OccupationEntity> occupation = occupationRepository.findById(id);
		return occupation.orElse(null);
	}

	public List<OccupationEntity> getAllOccupations() {
		List<OccupationEntity> occupations = occupationRepository.findAll();
		if (occupations.size() != 0) {
			return occupations;
		}
		return null;
	}

}
