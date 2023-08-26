/**
 *
 */
package com.tithe.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "parish_table",
uniqueConstraints = @UniqueConstraint(columnNames = {
		"parishName",
		"address_id",
		"phone",
		"forane_id"
}))
public class ParishEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long parishId;

	@NotBlank(message = "Name of Parish is empty or null")
	private String parishName;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "address_id")
	private AddressEntity address;

	private String phone;

	@NotNull(message = "Forane does not exist")
	@ManyToOne
	@JoinColumn(name = "forane_id")
	private ForaneEntity forane;

	@OneToMany(mappedBy = "parish")
	private List<KoottaymaEntity> koottaymas;

	@OneToMany(mappedBy = "parish")
	private List<TitheEntity> tithes;

	private Boolean active = true;

}
