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
@Table(name = "city_table")
public class CityEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cityId;
	
	@Column(unique = true)
	private String cityName;

}
