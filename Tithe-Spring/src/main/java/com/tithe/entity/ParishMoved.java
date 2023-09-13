/**
 * 
 */
package com.tithe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "parish_moved_table")
public class ParishMoved {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long parishMovedId;
	
	@OneToOne
	@JoinColumn(name = "parish_id")
	private ParishEntity parish;
	
	@OneToOne
	@JoinColumn(name = "old_forane_id")
	private ForaneEntity oldForane;
	
	@OneToOne
	@JoinColumn(name = "new_forane_id")
	private ForaneEntity newForane;

}
