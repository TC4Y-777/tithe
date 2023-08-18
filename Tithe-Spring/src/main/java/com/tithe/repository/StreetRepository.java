/**
 *
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.StreetEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface StreetRepository extends JpaRepository<StreetEntity, Long> {

}
