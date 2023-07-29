/**
 * 
 */
package com.tithe.service.mutation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.OccupationEntity;
import com.tithe.repository.OccupationRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class OccupationMutationService {
	
	@Autowired
	private OccupationRepository occupationRepository;

	public List<OccupationEntity> createManyOccupations(List<String> occupationNames) {
		List<OccupationEntity> occupations = new ArrayList<>();
		for (String occupationName : occupationNames) {
			if (occupationName!=null && !occupationName.isBlank()) {
				OccupationEntity occupation = new OccupationEntity();
				occupation.setOccupationName(occupationName);
				occupations.add(occupation);
			}
		}
		return occupationRepository.saveAll(occupations);
	}

}
