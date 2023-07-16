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
public class KoottaymaQueryFilter {
	
	String koottaymaName;
	Long parishId;
	Boolean active;

}
