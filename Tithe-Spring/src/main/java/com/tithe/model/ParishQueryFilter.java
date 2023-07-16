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
public class ParishQueryFilter {
	
	String parishName;
	String phone;
	Long foraneId;
	Boolean active;

}
