package com.hs.repository;

import com.hs.domain.Reporte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Reporte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    @Query("select reporte from Reporte reporte where reporte.user.login = ?#{principal.username}")
    List<Reporte> findByUserIsCurrentUser();

}
