package com.hs.repository;

import com.hs.domain.TipoRiesgo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoRiesgo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoRiesgoRepository extends JpaRepository<TipoRiesgo, Long> {

}
