/**
 *
 */
package com.tithe.model;

import java.time.LocalDate;

import lombok.Data;
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

//TODO	When startDate is set, automatically set the endDate as same, in frontend
//	LocalDate startDate;
//	LocalDate endDate;

	LocalDate dob;

	String phone;
	GenderEnum gender;
	Boolean active;

}
