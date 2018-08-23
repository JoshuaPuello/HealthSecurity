package com.hs.repository;

import com.hs.domain.Reporte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Reporte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

}
