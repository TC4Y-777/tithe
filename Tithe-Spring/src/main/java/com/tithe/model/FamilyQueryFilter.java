/**
 *
 */
package com.tithe.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@NoArgsConstructor
@Data
public class FamilyQueryFilter {

	String familyName;
	String phone;
	Long koottaymaId;
	Boolean active;

}
