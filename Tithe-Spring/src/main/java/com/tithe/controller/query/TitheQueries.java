/**
 * 
 */
package com.tithe.controller.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.tithe.model.AnnualTitheTotalInterface;
import com.tithe.service.query.TitheQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class TitheQueries {
	
	@Autowired
	private TitheQueryService titheQueryService;
	
	@QueryMapping(name = "getCurrentYearPersonTitheTotal")
	public Double getCurrentYearPersonTitheTotal(@Argument(name = "personId") Long personId){
		return titheQueryService.getCurrentYearPersonTitheTotal(personId);
	}
	
	@QueryMapping(name = "getAnnualPersonTitheTotal")
	public List<AnnualTitheTotalInterface> getAnnualPersonTitheTotal(@Argument(name = "personId") Long personId){
		return titheQueryService.getAnnualPersonTitheTotal(personId);
	}
	
	@QueryMapping(name = "getCurrentYearFamilyTitheTotal")
	public Double getCurrentYearFamilyTitheTotal(@Argument(name = "familyId") Long familyId){
		return titheQueryService.getCurrentYearFamilyTitheTotal(familyId);
	}
	
	@QueryMapping(name = "getAnnualFamilyTitheTotal")
	public List<AnnualTitheTotalInterface> getAnnualFamilyTitheTotal(@Argument(name = "familyId") Long familyId){
		return titheQueryService.getAnnualFamilyTitheTotal(familyId);
	}
	
	@QueryMapping(name = "getCurrentYearKoottaymaTitheTotal")
	public Double getCurrentYearKoottaymaTitheTotal(@Argument(name = "koottaymaId") Long koottaymaId){
		return titheQueryService.getCurrentYearKoottaymaTitheTotal(koottaymaId);
	}
	
	@QueryMapping(name = "getAnnualKoottaymaTitheTotal")
	public List<AnnualTitheTotalInterface> getAnnualKoottaymaTitheTotal(@Argument(name = "koottaymaId") Long koottaymaId){
		return titheQueryService.getAnnualKoottaymaTitheTotal(koottaymaId);
	}
	
	@QueryMapping(name = "getCurrentYearParishTitheTotal")
	public Double getCurrentYearParishTitheTotal(@Argument(name = "parishId") Long parishId){
		return titheQueryService.getCurrentYearParishTitheTotal(parishId);
	}
	
	@QueryMapping(name = "getAnnualParishTitheTotal")
	public List<AnnualTitheTotalInterface> getAnnualParishTitheTotal(@Argument(name = "parishId") Long parishId){
		return titheQueryService.getAnnualParishTitheTotal(parishId);
	}
	
	@QueryMapping(name = "getCurrentYearForaneTitheTotal")
	public Double getCurrentYearForaneTitheTotal(@Argument(name = "foraneId") Long foraneId){
		return titheQueryService.getCurrentYearForaneTitheTotal(foraneId);
	}
	
	@QueryMapping(name = "getAnnualForaneTitheTotal")
	public List<AnnualTitheTotalInterface> getAnnualForaneTitheTotal(@Argument(name = "foraneId") Long foraneId){
		return titheQueryService.getAnnualForaneTitheTotal(foraneId);
	}

}
