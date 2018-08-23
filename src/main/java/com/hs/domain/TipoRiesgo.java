package com.hs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TipoRiesgo.
 */
@Entity
@Table(name = "tipo_riesgo")
public class TipoRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "tipoRiesgos")
    @JsonIgnore
    private Set<Respuesta> respuestas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoRiesgo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoRiesgo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Respuesta> getRespuestas() {
        return respuestas;
    }

    public TipoRiesgo respuestas(Set<Respuesta> respuestas) {
        this.respuestas = respuestas;
        return this;
    }

    public TipoRiesgo addRespuesta(Respuesta respuesta) {
        this.respuestas.add(respuesta);
        respuesta.getTipoRiesgos().add(this);
        return this;
    }

    public TipoRiesgo removeRespuesta(Respuesta respuesta) {
        this.respuestas.remove(respuesta);
        respuesta.getTipoRiesgos().remove(this);
        return this;
    }

    public void setRespuestas(Set<Respuesta> respuestas) {
        this.respuestas = respuestas;
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
        TipoRiesgo tipoRiesgo = (TipoRiesgo) o;
        if (tipoRiesgo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoRiesgo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoRiesgo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
