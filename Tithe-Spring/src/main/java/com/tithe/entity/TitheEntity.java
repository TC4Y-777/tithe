/**
 * 
 */
package com.tithe.entity;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TitheEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long titheId;
	
	private Double titheAmount;
	
//TODO	When the below two annotations were not used, an error occured. Find out why that error occured.
	@ManyToOne
	@JoinColumn(name = "person_id")
	private PersonEntity person;
	
	@ManyToOne
	@JoinColumn(name = "family_id")
	private FamilyEntity family;
	
	@ManyToOne
	@JoinColumn(name = "koottayma_id")
	private KoottaymaEntity koottayma;
	
	@ManyToOne
	@JoinColumn(name = "parish_id")
	private ParishEntity parish;
	
	@ManyToOne
	@JoinColumn(name = "forane_id")
	private ForaneEntity forane;
	
	private LocalDate timeStamp;

}
