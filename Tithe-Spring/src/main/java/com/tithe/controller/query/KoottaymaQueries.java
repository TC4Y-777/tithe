/**
 * 
 */
package com.tithe.controller.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.KoottaymaEntity;
import com.tithe.model.KoottaymaQueryFilter;
import com.tithe.service.query.KoottaymaQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class KoottaymaQueries {
	
	@Autowired
	private KoottaymaQueryService koottaymaQueryService;
	
	@QueryMapping(name = "getOneKoottayma")
	public KoottaymaEntity getOneKoottayma(@Argument Long id) {
		return koottaymaQueryService.getOneKoottayma(id);
	}
	
	@QueryMapping(name = "getManyKoottaymas")
	public List<KoottaymaEntity> getManyKoottaymas(@Argument KoottaymaQueryFilter koottaymaQueryFilter){
		return koottaymaQueryService.getManyKoottaymas(koottaymaQueryFilter);
	}
	
	@QueryMapping(name = "getAllKoottaymas")
	public List<KoottaymaEntity> getAllKoottaymas() {
		return koottaymaQueryService.getAllKoottaymas();
	}

}
