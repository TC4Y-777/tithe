/**
 * 
 */
package com.tithe.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.tithe.model.TitheMutationInput;


/**
 * @author Ashish Sam T George
 *
 */
public class TitheBuilder {

	private Long titheId;
	private Double titheAmount;
	private PersonEntity person;
	private FamilyEntity family;
	private KoottaymaEntity koottayma;
	private ParishEntity parish;
	private ForaneEntity forane;
	private LocalDate timeStamp;

	public TitheBuilder titheId(Long titheId) {
		this.titheId = titheId;
		return this;
	}

	public TitheBuilder titheAmount(Double titheAmount) {
		this.titheAmount = titheAmount;
		return this;
	}

	public TitheBuilder person(PersonEntity person) {
		this.person = person;
		return this;
	}

	public TitheBuilder family(FamilyEntity family) {
		this.family = family;
		return this;
	}

	public TitheBuilder koottayma(KoottaymaEntity koottayma) {
		this.koottayma = koottayma;
		return this;
	}

	public TitheBuilder parish(ParishEntity parish) {
		this.parish = parish;
		return this;
	}

	public TitheBuilder forane(ForaneEntity forane) {
		this.forane = forane;
		return this;
	}

	public TitheBuilder timeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}

	public TitheEntity build() {
		TitheEntity tithe = new TitheEntity(titheId, titheAmount, person, family, koottayma, parish,
				forane, timeStamp);
		return tithe;
	}

	public List<TitheEntity> buildTithe(PersonEntity person,
			List<TitheMutationInput> titheMutationInputs) {
		List<TitheEntity> tithes = new ArrayList<>();
		if (titheMutationInputs != null && titheMutationInputs.size() != 0) {
//			objectValidation.validateObjects(titheMutationInputs);

			for (TitheMutationInput titheMutationInput : titheMutationInputs) {
//				New Builder object for each tithe
				TitheBuilder titheBuilder = new TitheBuilder();
				TitheEntity tithe = titheBuilder.titheAmount(titheMutationInput.getTitheAmount())
						.person(person)
						.family(person.getFamily())
						.koottayma(person.getFamily().getKoottayma())
						.parish(person.getFamily().getKoottayma().getParish())
						.forane(person.getFamily().getKoottayma().getParish().getForane())
						.timeStamp(titheMutationInput.getTimeStamp())
						.build();
				tithes.add(tithe);
			}
		}
		return tithes;
	}

}
