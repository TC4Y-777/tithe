/**
 *
 */
package com.tithe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.PincodeEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface PincodeRepository extends JpaRepository<PincodeEntity, Long> {
	
	List<PincodeEntity> findByPincodeContainingIgnoringCase(String pincode);

}
