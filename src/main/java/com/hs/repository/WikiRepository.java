package com.hs.repository;

import com.hs.domain.Wiki;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Wiki entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WikiRepository extends JpaRepository<Wiki, Long> {

}
