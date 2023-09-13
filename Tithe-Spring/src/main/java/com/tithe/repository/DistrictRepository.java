/**
 *
 */
package com.tithe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.DistrictEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
	
	List<DistrictEntity> findByDistrictNameContainingIgnoringCase(String districtName);

}
