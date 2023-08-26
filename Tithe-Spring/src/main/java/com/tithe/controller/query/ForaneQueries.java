/**
 *
 */
package com.tithe.controller.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.ForaneEntity;
import com.tithe.model.ForaneQueryFilter;
import com.tithe.service.query.ForaneQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class ForaneQueries {

	@Autowired
	private ForaneQueryService foraneQueryService;

	@QueryMapping(name = "getOneForane")
	public ForaneEntity getOneForane(@Argument Long id) {
		return foraneQueryService.getOneForane(id);
	}

	@QueryMapping(name = "getManyForanes")
	public List<ForaneEntity> getManyForanes(@Argument(name = "filter") ForaneQueryFilter foraneQueryFilter) {
		return foraneQueryService.getManyForanes(foraneQueryFilter);
	}

	@QueryMapping(name = "getAllForanes")
	public List<ForaneEntity> getAllForanes() {
		return foraneQueryService.getAllForanes();
	}

	@QueryMapping(name = "getForaneCount")
	public Long getForaneCount() {
		return foraneQueryService.getForaneCount();
	}

}
