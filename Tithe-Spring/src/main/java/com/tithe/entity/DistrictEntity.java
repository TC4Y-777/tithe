/**
 * 
 */
package com.tithe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DistrictEntity {
	
	@Id
	private Long districtId;
	
	@Column(unique = true)
	private String districtName;

}
