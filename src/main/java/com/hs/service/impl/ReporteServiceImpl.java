package com.hs.service.impl;

import com.hs.service.ReporteService;
import com.hs.web.rest.util.PdfGeneratorUtil;
import com.hs.domain.Reporte;
import com.hs.repository.ReporteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Service Implementation for managing Reporte.
 */
@Service
@Transactional
public class ReporteServiceImpl implements ReporteService {

    private final Logger log = LoggerFactory.getLogger(ReporteServiceImpl.class);

    private final ReporteRepository reporteRepository;

    @Autowired
    private final PdfGeneratorUtil pdfGenaratorUtil;
    
    @PersistenceContext
    private EntityManager em;

    public ReporteServiceImpl(ReporteRepository reporteRepository, PdfGeneratorUtil pdfGenaratorUtil) {
        this.reporteRepository = reporteRepository;
        this.pdfGenaratorUtil = pdfGenaratorUtil;
    }

    /**
     * Save a reporte.
     *
     * @param reporte the entity to save
     * @return the persisted entity
     */
    @Override
    public Reporte save(Reporte reporte) {
        log.debug("Request to save Reporte : {}", reporte);        return reporteRepository.save(reporte);
    }

    /**
     * Get all the reportes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reporte> findAll(Pageable pageable) {
        log.debug("Request to get all Reportes");
        return reporteRepository.findAll(pageable);
    }



    /**
     *  get all the reportes where Respuesta is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Reporte> findAllWhereRespuestaIsNull() {
        log.debug("Request to get all reportes where Respuesta is null");
        return StreamSupport
            .stream(reporteRepository.findAll().spliterator(), false)
            .filter(reporte -> reporte.getRespuesta() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one reporte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Reporte> findOne(Long id) {
        log.debug("Request to get Reporte : {}", id);
        return reporteRepository.findById(id);
    }

    /**
     * Delete the reporte by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reporte : {}", id);
        reporteRepository.deleteById(id);
    }

    public String generatePDF(){
    	
    	LocalDate date = LocalDate.now();
    	Map<String,String> data = new HashMap<String,String>();
        
    	Query queryRecibidos = em.createNativeQuery("SELECT count(*) as recibidos FROM hsplatfo_database.reporte;");

    	int recibidos = Integer.parseInt(queryRecibidos.getResultList().get(0).toString());
    	
    	System.out.println("Recibidos: " + recibidos);
    	
    	Query queryAprobados = em.createNativeQuery("SELECT count(*) aprobados FROM hsplatfo_database.respuesta where estado = 'ACEPTADO';");
    	int aprobados = Integer.parseInt(queryAprobados.getResultList().get(0).toString());
    	
    	Query queryRechazados = em.createNativeQuery("SELECT count(*) rechazados FROM hsplatfo_database.respuesta where estado = 'RECHAZADO';");
    	int rechazados = Integer.parseInt(queryRechazados.getResultList().get(0).toString());
    	
    	Query queryPendientes = em.createNativeQuery("SELECT count(*) pendientes FROM hsplatfo_database.respuesta where estado = 'PENDIENTE';");
    	int pendientes = Integer.parseInt(queryPendientes.getResultList().get(0).toString());
    	
    	Query queryIncidentes = em.createNativeQuery("SELECT count(*) incidentes FROM hsplatfo_database.reporte where tipo_evento = 'INCIDENTE';");
    	int incidentes = Integer.parseInt(queryIncidentes.getResultList().get(0).toString());
    	
    	Query queryAccidentes = em.createNativeQuery("SELECT count(*) accidentes FROM hsplatfo_database.reporte where tipo_evento = 'ACCIDENTE';");
    	int accidentes = Integer.parseInt(queryAccidentes.getResultList().get(0).toString());
    	
    	Query queryIncidentesLeve = em.createNativeQuery("SELECT count(*) incidentesLeve FROM hsplatfo_database.reporte where tipo_evento = 'INCIDENTE' and valoracion = 'LEVE';");
    	int incidentesLeves = Integer.parseInt(queryIncidentesLeve.getResultList().get(0).toString());
    	
    	Query queryIncidentesModerado = em.createNativeQuery("SELECT count(*) incidentesModerado FROM hsplatfo_database.reporte where tipo_evento = 'INCIDENTE' and valoracion = 'MODERADO';");
    	int incidentesModerado = Integer.parseInt(queryIncidentesModerado.getResultList().get(0).toString());
    	
    	Query queryIncidentesCritico = em.createNativeQuery("SELECT count(*) incidentesCritico FROM hsplatfo_database.reporte where tipo_evento = 'INCIDENTE' and valoracion = 'CRITICO';");
    	int incidentesCritico = Integer.parseInt(queryIncidentesCritico.getResultList().get(0).toString());
    	
    	Query queryAccidentesLeve = em.createNativeQuery("SELECT count(*) accidentesLeve FROM hsplatfo_database.reporte where tipo_evento = 'ACCIDENTE' and valoracion = 'LEVE';");
    	int accidentesLeve = Integer.parseInt(queryAccidentesLeve.getResultList().get(0).toString());
    	
    	Query queryAccidentesModerado = em.createNativeQuery("SELECT count(*) accidentesModerado FROM hsplatfo_database.reporte where tipo_evento = 'ACCIDENTE' and valoracion = 'MODERADO';");
    	int accidentesModerado = Integer.parseInt(queryAccidentesModerado.getResultList().get(0).toString());
    	
    	Query queryAccidentesCritico = em.createNativeQuery("SELECT count(*) accidentesCritico FROM hsplatfo_database.reporte where tipo_evento = 'ACCIDENTE' and valoracion = 'CRITICO';");
    	int accidentesCritico = Integer.parseInt(queryAccidentesCritico.getResultList().get(0).toString());
    	
    	double porcentajeIncidentes = incidentes / recibidos * 100;
    	double porcentajeAccidentes = accidentes / recibidos * 100;
    	
    	data.put("fecha", date.toString());
        data.put("recibidos", recibidos + "");
        data.put("aprobados", aprobados + "");
        data.put("rechazados", rechazados + "");
        data.put("pendientes", pendientes + "");
        data.put("incidentes", incidentes + "");
        data.put("accidentes", accidentes + "");
        data.put("porcentajeIncidentes", porcentajeIncidentes + "");
        data.put("porcentajeAccidentes", porcentajeAccidentes + "");
        data.put("incidentesLeve", incidentesLeves + "");
        data.put("incidentesModerado", incidentesModerado + "");
        data.put("incidentesCritico", incidentesCritico + "");
        
        data.put("accidentesLeve", accidentesLeve + "");
        data.put("accidentesModerado", accidentesModerado + "");
        data.put("accidentesCritico", accidentesCritico + "");

        try {
			return this.pdfGenaratorUtil.createPdf("pdfTemplate", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}
