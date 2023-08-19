/**
 *
 */
package com.tithe.controller.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.FamilyEntity;
import com.tithe.model.FamilyQueryFilter;
import com.tithe.service.query.FamilyQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class FamilyQueries {

	@Autowired
	private FamilyQueryService familyQueryService;

	@QueryMapping(name = "getOneFamily")
	public FamilyEntity getOneFamily(@Argument Long id) {
		return familyQueryService.getOneFamily(id);
	}

	@QueryMapping(name = "getManyFamilies")
	public List<FamilyEntity> getManyFamilies(@Argument(name = "filter") FamilyQueryFilter familyQueryFilter){
		return familyQueryService.getManyFamilies(familyQueryFilter);
	}

	@QueryMapping(name = "getAllFamilies")
	public List<FamilyEntity> getAllFamilies(){
		return familyQueryService.getAllFamilies();
	}

	@QueryMapping(name = "getFamilyCount")
	public Long getFamilyCount() {
		return familyQueryService.getFamilyCount();
	}
	
	@QueryMapping(name = "getFamilyCountByForane")
	public Long getFamilyCountByForane(@Argument(name = "foraneId") Long foraneId) {
		return familyQueryService.getFamilyCountByForane(foraneId);
	}
	
	@QueryMapping(name = "getFamilyCountByParish")
	public Long getFamilyCountByParish(@Argument(name = "parishId") Long parishId) {
		return familyQueryService.getFamilyCountByParish(parishId);
	}
	
	@QueryMapping(name = "getFamilyCountByKoottayma")
	public Long getFamilyCountByKoottayma(@Argument(name = "koottaymaId") Long koottaymaId) {
		return familyQueryService.getFamilyCountByKoottayma(koottaymaId);
	}

}
