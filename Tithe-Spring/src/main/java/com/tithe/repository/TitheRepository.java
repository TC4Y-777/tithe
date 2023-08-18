/**
 *
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.TitheEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface TitheRepository extends JpaRepository<TitheEntity, Long> {

}
