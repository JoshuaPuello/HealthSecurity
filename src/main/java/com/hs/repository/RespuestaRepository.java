package com.hs.repository;

import com.hs.domain.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Respuesta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    @Query(value = "select distinct respuesta from Respuesta respuesta left join fetch respuesta.areaRiesgos left join fetch respuesta.tipoRiesgos",
        countQuery = "select count(distinct respuesta) from Respuesta respuesta")
    Page<Respuesta> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct respuesta from Respuesta respuesta left join fetch respuesta.areaRiesgos left join fetch respuesta.tipoRiesgos")
    List<Respuesta> findAllWithEagerRelationships();

    @Query("select respuesta from Respuesta respuesta left join fetch respuesta.areaRiesgos left join fetch respuesta.tipoRiesgos where respuesta.id =:id")
    Optional<Respuesta> findOneWithEagerRelationships(@Param("id") Long id);

}
