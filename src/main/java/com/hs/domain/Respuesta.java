package com.hs.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.hs.domain.enumeration.ValoracionEnum;

import com.hs.domain.enumeration.EstadoEnum;

/**
 * A Respuesta.
 */
@Entity
@Table(name = "respuesta")
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "valoracion", nullable = false)
    private ValoracionEnum valoracion;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEnum estado;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Reporte reporte;

    @ManyToMany
    @NotNull
    @JoinTable(name = "respuesta_area_riesgo",
               joinColumns = @JoinColumn(name = "respuestas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "area_riesgos_id", referencedColumnName = "id"))
    private Set<AreaRiesgo> areaRiesgos = new HashSet<>();

    @ManyToMany
    @NotNull
    @JoinTable(name = "respuesta_tipo_riesgo",
               joinColumns = @JoinColumn(name = "respuestas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tipo_riesgos_id", referencedColumnName = "id"))
    private Set<TipoRiesgo> tipoRiesgos = new HashSet<>();

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

    public Respuesta valoracion(ValoracionEnum valoracion) {
        this.valoracion = valoracion;
        return this;
    }

    public void setValoracion(ValoracionEnum valoracion) {
        this.valoracion = valoracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Respuesta descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    public Respuesta estado(EstadoEnum estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public Respuesta reporte(Reporte reporte) {
        this.reporte = reporte;
        return this;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    public Set<AreaRiesgo> getAreaRiesgos() {
        return areaRiesgos;
    }

    public Respuesta areaRiesgos(Set<AreaRiesgo> areaRiesgos) {
        this.areaRiesgos = areaRiesgos;
        return this;
    }

    public Respuesta addAreaRiesgo(AreaRiesgo areaRiesgo) {
        this.areaRiesgos.add(areaRiesgo);
        areaRiesgo.getRespuestas().add(this);
        return this;
    }

    public Respuesta removeAreaRiesgo(AreaRiesgo areaRiesgo) {
        this.areaRiesgos.remove(areaRiesgo);
        areaRiesgo.getRespuestas().remove(this);
        return this;
    }

    public void setAreaRiesgos(Set<AreaRiesgo> areaRiesgos) {
        this.areaRiesgos = areaRiesgos;
    }

    public Set<TipoRiesgo> getTipoRiesgos() {
        return tipoRiesgos;
    }

    public Respuesta tipoRiesgos(Set<TipoRiesgo> tipoRiesgos) {
        this.tipoRiesgos = tipoRiesgos;
        return this;
    }

    public Respuesta addTipoRiesgo(TipoRiesgo tipoRiesgo) {
        this.tipoRiesgos.add(tipoRiesgo);
        tipoRiesgo.getRespuestas().add(this);
        return this;
    }

    public Respuesta removeTipoRiesgo(TipoRiesgo tipoRiesgo) {
        this.tipoRiesgos.remove(tipoRiesgo);
        tipoRiesgo.getRespuestas().remove(this);
        return this;
    }

    public void setTipoRiesgos(Set<TipoRiesgo> tipoRiesgos) {
        this.tipoRiesgos = tipoRiesgos;
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
        Respuesta respuesta = (Respuesta) o;
        if (respuesta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), respuesta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Respuesta{" +
            "id=" + getId() +
            ", valoracion='" + getValoracion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
