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
import com.tithe.entity.KoottaymaEntity;
import com.tithe.entity.ParishEntity;
import com.tithe.model.KoottaymaQueryFilter;
import com.tithe.repository.KoottaymaRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class KoottaymaQueryService {

	@Autowired
	private KoottaymaRepository koottaymaRepository;

	@Autowired
	private ParishQueryService parishQueryService;
	
	@Autowired
	private ForaneQueryService foraneQueryService;

	public KoottaymaEntity getOneKoottayma(Long id) {
		Optional<KoottaymaEntity> koottayma = koottaymaRepository.findById(id);
		return koottayma.orElse(null);
	}

	public List<KoottaymaEntity> getManyKoottaymas(KoottaymaQueryFilter koottaymaQueryFilter) {
		KoottaymaEntity koottayma = new KoottaymaEntity();
		if (koottaymaQueryFilter.getKoottaymaName()!=null && !koottaymaQueryFilter.getKoottaymaName().isBlank()) {
			koottayma.setKoottaymaName(koottaymaQueryFilter.getKoottaymaName());
		}
		if (koottaymaQueryFilter.getParishId()!=null) {
			koottayma.setParish(parishQueryService.getOneParish(koottaymaQueryFilter.getParishId()));
		}
		koottayma.setActive(koottaymaQueryFilter.getActive());

		Example<KoottaymaEntity> exampleKoottayma = Example.of(koottayma);
		List<KoottaymaEntity> matchingKoottaymas = koottaymaRepository.findAll(exampleKoottayma);

		if (matchingKoottaymas.size()!=0) {
			return matchingKoottaymas;
		}
		return null;
	}

	public List<KoottaymaEntity> getAllKoottaymas() {
		List<KoottaymaEntity> koottaymas = koottaymaRepository.findAll();
		if (koottaymas.size()!=0) {
			return koottaymas;
		}
		return null;
	}

	public Long getKoottaymaCount() {
		return koottaymaRepository.countByActive(true);
	}

	public Long getKoottaymaCountByForane(Long foraneId) {
		ForaneEntity forane = foraneQueryService.getOneForane(foraneId);
		if (forane!=null) {
			return koottaymaRepository.countByParish_ForaneAndActive(forane, true);
		}
		return null;
	}

	public Long getKoottaymaCountByParish(Long parishId) {
		ParishEntity parish = parishQueryService.getOneParish(parishId);
		if (parish!=null) {
			return koottaymaRepository.countByParishAndActive(parish, true);
		}
		return null;
	}

}
