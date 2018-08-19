package com.hs.repository;

import com.hs.domain.AreaRiesgo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AreaRiesgo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AreaRiesgoRepository extends JpaRepository<AreaRiesgo, Long> {

}
