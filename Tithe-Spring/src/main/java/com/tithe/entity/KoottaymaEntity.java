/**
 * 
 */
package com.tithe.entity;

import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "koottayma_table", uniqueConstraints = @UniqueConstraint(columnNames = {"koottaymaName", "parish_id"}))
public class KoottaymaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long koottaymaId;
	
	@NotBlank(message = "Name of Koottayma is empty or null")
	private String koottaymaName;
	
	@NotNull(message = "Parish does not exist")
	@ManyToOne
	@JoinColumn(name = "parish_id")
	private ParishEntity parish;
	
	@OneToMany(mappedBy = "koottayma")
	private List<FamilyEntity> families;
	
	@OneToMany(mappedBy = "koottayma")
	private List<TitheEntity> tithes;

	private Boolean active = true;
	
}
