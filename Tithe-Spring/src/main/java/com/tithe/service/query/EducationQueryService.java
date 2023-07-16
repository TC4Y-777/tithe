/**
 * 
 */
package com.tithe.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.EducationEntity;
import com.tithe.repository.EducationRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class EducationQueryService {
	
	@Autowired
	private EducationRepository educationRepository;

	public EducationEntity getOneEducation(Long id) {
		Optional<EducationEntity> education = educationRepository.findById(id);
		return education.orElse(null);
	}

	public List<EducationEntity> getAllEducations() {
		List<EducationEntity> educations = educationRepository.findAll();
		if (educations.size() != 0) {
			return educations;
		}
		return null;
	}

}
