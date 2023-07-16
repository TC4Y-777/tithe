/**
 * 
 */
package com.tithe.model;

import java.time.LocalDate;
import java.util.List;

import com.tithe.entity.EducationEntity;
import com.tithe.entity.OccupationEntity;
import com.tithe.entity.PersonEntity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@NoArgsConstructor
@Data
public class PersonQueryFilter {
	
	String baptismName;
	String personName;
//	When startDate is set, automatically set the endDate as same, in frontend
//	LocalDate startDate;
//	LocalDate endDate;
	
	LocalDate dob;
	
	String phone;
	GenderEnum gender;
	Boolean active;

}
