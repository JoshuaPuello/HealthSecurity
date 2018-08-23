package com.hs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.hs.domain.enumeration.ValoracionEnum;

import com.hs.domain.enumeration.LugarEventoEnum;

import com.hs.domain.enumeration.TipoEventoEnum;

/**
 * A Reporte.
 */
@Entity
@Table(name = "reporte")
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "valoracion", nullable = false)
    private ValoracionEnum valoracion;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "accinones_realizadas")
    private String accinones_realizadas;

    @Lob
    @Column(name = "evidencia")
    private byte[] evidencia;

    @Column(name = "evidencia_content_type")
    private String evidenciaContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "lugar_evento", nullable = false)
    private LugarEventoEnum lugar_evento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evento", nullable = false)
    private TipoEventoEnum tipo_evento;

    @NotNull
    @Column(name = "b_labores", nullable = false)
    private Boolean bLabores;

    @NotNull
    @Column(name = "b_reportado", nullable = false)
    private Boolean bReportado;

    @OneToOne(mappedBy = "reporte")
    @JsonIgnore
    private Respuesta respuesta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ValoracionEnum getValoracion() {
        return valoracion;
    }

    public Reporte valoracion(ValoracionEnum valoracion) {
        this.valoracion = valoracion;
        return this;
    }

    public void setValoracion(ValoracionEnum valoracion) {
        this.valoracion = valoracion;
    }

    public String getNombre() {
        return nombre;
    }

    public Reporte nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Reporte descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAccinones_realizadas() {
        return accinones_realizadas;
    }

    public Reporte accinones_realizadas(String accinones_realizadas) {
        this.accinones_realizadas = accinones_realizadas;
        return this;
    }

    public void setAccinones_realizadas(String accinones_realizadas) {
        this.accinones_realizadas = accinones_realizadas;
    }

    public byte[] getEvidencia() {
        return evidencia;
    }

    public Reporte evidencia(byte[] evidencia) {
        this.evidencia = evidencia;
        return this;
    }

    public void setEvidencia(byte[] evidencia) {
        this.evidencia = evidencia;
    }

    public String getEvidenciaContentType() {
        return evidenciaContentType;
    }

    public Reporte evidenciaContentType(String evidenciaContentType) {
        this.evidenciaContentType = evidenciaContentType;
        return this;
    }

    public void setEvidenciaContentType(String evidenciaContentType) {
        this.evidenciaContentType = evidenciaContentType;
    }

    public LugarEventoEnum getLugar_evento() {
        return lugar_evento;
    }

    public Reporte lugar_evento(LugarEventoEnum lugar_evento) {
        this.lugar_evento = lugar_evento;
        return this;
    }

    public void setLugar_evento(LugarEventoEnum lugar_evento) {
        this.lugar_evento = lugar_evento;
    }

    public TipoEventoEnum getTipo_evento() {
        return tipo_evento;
    }

    public Reporte tipo_evento(TipoEventoEnum tipo_evento) {
        this.tipo_evento = tipo_evento;
        return this;
    }

    public void setTipo_evento(TipoEventoEnum tipo_evento) {
        this.tipo_evento = tipo_evento;
    }

    public Boolean isbLabores() {
        return bLabores;
    }

    public Reporte bLabores(Boolean bLabores) {
        this.bLabores = bLabores;
        return this;
    }

    public void setbLabores(Boolean bLabores) {
        this.bLabores = bLabores;
    }

    public Boolean isbReportado() {
        return bReportado;
    }

    public Reporte bReportado(Boolean bReportado) {
        this.bReportado = bReportado;
        return this;
    }

    public void setbReportado(Boolean bReportado) {
        this.bReportado = bReportado;
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public Reporte respuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
        return this;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reporte reporte = (Reporte) o;
        if (reporte.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reporte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reporte{" +
            "id=" + getId() +
            ", valoracion='" + getValoracion() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", accinones_realizadas='" + getAccinones_realizadas() + "'" +
            ", evidencia='" + getEvidencia() + "'" +
            ", evidenciaContentType='" + getEvidenciaContentType() + "'" +
            ", lugar_evento='" + getLugar_evento() + "'" +
            ", tipo_evento='" + getTipo_evento() + "'" +
            ", bLabores='" + isbLabores() + "'" +
            ", bReportado='" + isbReportado() + "'" +
            "}";
    }
}
