/**
 * 
 */
package com.tithe.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.tithe.entity.ForaneEntity;
import com.tithe.model.ForaneQueryFilter;
import com.tithe.repository.ForaneRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class ForaneQueryService {
	
	@Autowired
	private ForaneRepository foraneRepository;

	public ForaneEntity getOneForane(Long id) {
		Optional<ForaneEntity> forane = foraneRepository.findById(id);
		return forane.orElse(null);
	}

	public List<ForaneEntity> getManyForanes(ForaneQueryFilter foraneQueryFilter) {
		ForaneEntity forane = new ForaneEntity();
		if (foraneQueryFilter.getForaneName()!=null && !foraneQueryFilter.getForaneName().isBlank()) {
			forane.setForaneName(foraneQueryFilter.getForaneName());
		}
		forane.setPhone(foraneQueryFilter.getPhone());
		forane.setActive(foraneQueryFilter.getActive());
		
		Example<ForaneEntity> exampleForane = Example.of(forane);
		List<ForaneEntity> matchingForanes = foraneRepository.findAll(exampleForane);
		
		if (matchingForanes.size()!=0) {
			return matchingForanes;
		}
		return null;
	}

	public List<ForaneEntity> getAllForanes() {
		List<ForaneEntity> foranes = foraneRepository.findAll();
		if (foranes.size()!=0) {
			return foranes;
		}
		return null;
	}

}
