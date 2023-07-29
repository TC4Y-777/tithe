/**
 * 
 */
package com.tithe.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "district_table")
public class DistrictEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long districtId;
	
	@Column(unique = true)
	private String districtName;

}
