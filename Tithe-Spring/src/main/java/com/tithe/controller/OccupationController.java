package com.tithe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tithe.entity.OccupationEntity;
import com.tithe.repository.OccupationRepository;

@RestController
public class OccupationController {
	
	@Autowired
	private OccupationRepository occupationRepository;
	
	@GetMapping("/occupation")
	public void PutSomeValue(@RequestParam String name) {
		OccupationEntity occupationEntity = new OccupationEntity();
		occupationEntity.setOccupationName(name);
		occupationRepository.save(occupationEntity);
	}

}
