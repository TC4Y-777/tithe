/**
 *
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.CityEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

}
