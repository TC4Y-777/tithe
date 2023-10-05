/**
 *
 */
package com.tithe.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tithe.entity.TitheEntity;
import com.tithe.model.AnnualTitheTotalInterface;


/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface TitheRepository extends JpaRepository<TitheEntity, Long> {

	@Query(value = "SELECT SUM(tithe_amount) AS titheTotal FROM tithe_table tt "
			+ "WHERE tt.person_id = :personId AND "
			+ "EXTRACT(YEAR FROM tt.time_stamp) = EXTRACT(YEAR FROM NOW())", nativeQuery = true)
	Double findCurrentYearPersonTitheTotal(@Param("personId") Long personId);

	@Query(value = "SELECT EXTRACT(YEAR FROM time_stamp) AS year, "
			+ "SUM(tithe_amount) AS titheTotal FROM tithe_table tt "
			+ "WHERE tt.person_id = :personId GROUP BY EXTRACT(YEAR FROM time_stamp) "
			+ "ORDER BY EXTRACT(YEAR FROM time_stamp)", nativeQuery = true)
	List<AnnualTitheTotalInterface> findAnnualPersonTitheTotal(@Param("personId") Long personId);

	@Query(value = "SELECT SUM(tithe_amount) AS titheTotal FROM tithe_table tt "
			+ "WHERE tt.family_id = :familyId AND "
			+ "EXTRACT(YEAR FROM tt.time_stamp) = EXTRACT(YEAR FROM NOW())", nativeQuery = true)
	Double findCurrentYearFamilyTitheTotal(@Param("familyId") Long familyId);

	@Query(value = "SELECT EXTRACT(YEAR FROM time_stamp) AS year, "
			+ "SUM(tithe_amount) AS titheTotal FROM tithe_table tt "
			+ "WHERE tt.family_id = :familyId GROUP BY EXTRACT(YEAR FROM time_stamp) "
			+ "ORDER BY EXTRACT(YEAR FROM time_stamp)", nativeQuery = true)
	List<AnnualTitheTotalInterface> findAnnualFamilyTitheTotal(@Param("familyId") Long familyId);

	@Query(value = "SELECT SUM(tithe_amount) AS titheTotal FROM tithe_table tt "
			+ "WHERE tt.koottayma_id = :koottaymaId AND "
			+ "EXTRACT(YEAR FROM tt.time_stamp) = EXTRACT(YEAR FROM NOW())", nativeQuery = true)
	Double findCurrentYearKoottaymaTitheTotal(@Param("koottaymaId") Long koottaymaId);

	@Query(value = "SELECT EXTRACT(YEAR FROM time_stamp) AS year, "
			+ "SUM(tithe_amount) AS titheTotal FROM tithe_table tt "
			+ "WHERE tt.koottayma_id = :koottaymaId GROUP BY EXTRACT(YEAR FROM time_stamp) "
			+ "ORDER BY EXTRACT(YEAR FROM time_stamp)", nativeQuery = true)
	List<AnnualTitheTotalInterface> findAnnualKoottaymaTitheTotal(
			@Param("koottaymaId") Long koottaymaId);

	@Query(value = "SELECT SUM(tithe_amount) AS titheTotal FROM tithe_table tt "
			+ "WHERE tt.parish_id = :parishId AND "
			+ "EXTRACT(YEAR FROM tt.time_stamp) = EXTRACT(YEAR FROM NOW())", nativeQuery = true)
	Double findCurrentYearParishTitheTotal(@Param("parishId") Long parishId);

	@Query(value = "SELECT EXTRACT(YEAR FROM time_stamp) AS year, "
			+ "SUM(tithe_amount) AS titheTotal FROM tithe_table tt "
			+ "WHERE tt.parish_id = :parishId GROUP BY EXTRACT(YEAR FROM time_stamp) "
			+ "ORDER BY EXTRACT(YEAR FROM time_stamp)", nativeQuery = true)
	List<AnnualTitheTotalInterface> findAnnualParishTitheTotal(@Param("parishId") Long parishId);

	@Query(value = "SELECT SUM(tithe_amount) AS titheTotal FROM tithe_table tt "
			+ "WHERE tt.forane_id = :foraneId AND "
			+ "EXTRACT(YEAR FROM tt.time_stamp) = EXTRACT(YEAR FROM NOW())", nativeQuery = true)
	Double findCurrentYearForaneTitheTotal(@Param("foraneId") Long foraneId);

	@Query(value = "SELECT EXTRACT(YEAR FROM time_stamp) AS year, "
			+ "SUM(tithe_amount) AS titheTotal FROM tithe_table tt "
			+ "WHERE tt.forane_id = :foraneId GROUP BY EXTRACT(YEAR FROM time_stamp) "
			+ "ORDER BY EXTRACT(YEAR FROM time_stamp)", nativeQuery = true)
	List<AnnualTitheTotalInterface> findAnnualForaneTitheTotal(@Param("foraneId") Long foraneId);

}
