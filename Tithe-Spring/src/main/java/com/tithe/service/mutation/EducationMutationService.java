/**
 * 
 */
package com.tithe.service.mutation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.EducationEntity;
import com.tithe.repository.EducationRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class EducationMutationService {
	
	@Autowired
	private EducationRepository educationRepository;

	public List<EducationEntity> createManyEducations(List<String> educationNames) {
		List<EducationEntity> educations = new ArrayList<>();
		for (String educationName : educationNames) {
			if (educationName!=null) {
				EducationEntity education = new EducationEntity();
				education.setEducationName(educationName);
				educations.add(education);
			}
		}
//		TODO If any of the element is null, returns an error. How to handle it?
		return educationRepository.saveAll(educations);
	}
	
}
