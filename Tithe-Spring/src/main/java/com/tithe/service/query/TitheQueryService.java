/**
 *
 */
package com.tithe.service.query;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.model.AnnualTitheTotalInterface;
import com.tithe.repository.TitheRepository;

import graphql.GraphQLException;


/**
 * @author Ashish Sam T George
 *
 */
@Service
public class TitheQueryService {

	@Autowired
	private TitheRepository titheRepository;

	public Double getCurrentYearPersonTitheTotal(Long personId) {
		if (personId == null) {
			throw new GraphQLException("Person Id should be valid");
		}
		Double titheTotal = titheRepository.findCurrentYearPersonTitheTotal(personId);
		if (titheTotal != null && !titheTotal.isNaN()) {
			return titheTotal;
		}
		return 0.0;
	}

	public List<AnnualTitheTotalInterface> getAnnualPersonTitheTotal(Long personId) {
		if (personId == null) {
			throw new GraphQLException("Person Id should be valid");
		}
		return titheRepository.findAnnualPersonTitheTotal(personId);
	}

	public Double getCurrentYearFamilyTitheTotal(Long familyId) {
		if (familyId == null) {
			throw new GraphQLException("Family Id should be valid");
		}
		Double titheTotal = titheRepository.findCurrentYearFamilyTitheTotal(familyId);
		if (titheTotal != null && !titheTotal.isNaN()) {
			return titheTotal;
		}
		return 0.0;
	}

	public List<AnnualTitheTotalInterface> getAnnualFamilyTitheTotal(Long familyId) {
		if (familyId == null) {
			throw new GraphQLException("Family Id should be valid");
		}
		return titheRepository.findAnnualFamilyTitheTotal(familyId);
	}

	public Double getCurrentYearKoottaymaTitheTotal(Long koottaymaId) {
		if (koottaymaId == null) {
			throw new GraphQLException("Koottayma Id should be valid");
		}
		Double titheTotal = titheRepository.findCurrentYearKoottaymaTitheTotal(koottaymaId);
		if (titheTotal != null && !titheTotal.isNaN()) {
			return titheTotal;
		}
		return 0.0;
	}

	public List<AnnualTitheTotalInterface> getAnnualKoottaymaTitheTotal(Long koottaymaId) {
		if (koottaymaId == null) {
			throw new GraphQLException("Koottayma Id should be valid");
		}
		return titheRepository.findAnnualKoottaymaTitheTotal(koottaymaId);
	}

	public Double getCurrentYearParishTitheTotal(Long parishId) {
		if (parishId == null) {
			throw new GraphQLException("Parish Id should be valid");
		}
		Double titheTotal = titheRepository.findCurrentYearParishTitheTotal(parishId);
		if (titheTotal != null && !titheTotal.isNaN()) {
			return titheTotal;
		}
		return 0.0;
	}

	public List<AnnualTitheTotalInterface> getAnnualParishTitheTotal(Long parishId) {
		if (parishId == null) {
			throw new GraphQLException("Parish Id should be valid");
		}
		return titheRepository.findAnnualParishTitheTotal(parishId);
	}
	
	public Double getCurrentYearForaneTitheTotal(Long foraneId) {
		if (foraneId == null) {
			throw new GraphQLException("Forane Id should be valid");
		}
		Double titheTotal = titheRepository.findCurrentYearForaneTitheTotal(foraneId);
		if (titheTotal != null && !titheTotal.isNaN()) {
			return titheTotal;
		}
		return 0.0;
	}

	public List<AnnualTitheTotalInterface> getAnnualForaneTitheTotal(Long foraneId) {
		if (foraneId == null) {
			throw new GraphQLException("Forane Id should be valid");
		}
		return titheRepository.findAnnualForaneTitheTotal(foraneId);
	}

}
