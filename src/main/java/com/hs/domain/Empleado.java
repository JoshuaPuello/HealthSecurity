package com.hs.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.hs.domain.enumeration.TipoDocumentoEnum;

/**
 * A Empleado.
 */
@Entity
@Table(name = "empleado")
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumentoEnum tipo_documento;

    @NotNull
    @Column(name = "documento", nullable = false)
    private String documento;

    @NotNull
    @Column(name = "primer_nombre", nullable = false)
    private String primer_nombre;

    @Column(name = "segundo_nombre")
    private String segundo_nombre;

    @NotNull
    @Column(name = "primer_apellido", nullable = false)
    private String primer_apellido;

    @NotNull
    @Column(name = "segundo_apellido", nullable = false)
    private String segundo_apellido;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "nacimiento")
    private LocalDate nacimiento;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumentoEnum getTipo_documento() {
        return tipo_documento;
    }

    public Empleado tipo_documento(TipoDocumentoEnum tipo_documento) {
        this.tipo_documento = tipo_documento;
        return this;
    }

    public void setTipo_documento(TipoDocumentoEnum tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getDocumento() {
        return documento;
    }

    public Empleado documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPrimer_nombre() {
        return primer_nombre;
    }

    public Empleado primer_nombre(String primer_nombre) {
        this.primer_nombre = primer_nombre;
        return this;
    }

    public void setPrimer_nombre(String primer_nombre) {
        this.primer_nombre = primer_nombre;
    }

    public String getSegundo_nombre() {
        return segundo_nombre;
    }

    public Empleado segundo_nombre(String segundo_nombre) {
        this.segundo_nombre = segundo_nombre;
        return this;
    }

    public void setSegundo_nombre(String segundo_nombre) {
        this.segundo_nombre = segundo_nombre;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public Empleado primer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
        return this;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public Empleado segundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
        return this;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public Empleado direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Empleado telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public Empleado nacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
        return this;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public User getUser() {
        return user;
    }

    public Empleado user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Empleado empleado = (Empleado) o;
        if (empleado.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empleado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Empleado{" +
            "id=" + getId() +
            ", tipo_documento='" + getTipo_documento() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", primer_nombre='" + getPrimer_nombre() + "'" +
            ", segundo_nombre='" + getSegundo_nombre() + "'" +
            ", primer_apellido='" + getPrimer_apellido() + "'" +
            ", segundo_apellido='" + getSegundo_apellido() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", nacimiento='" + getNacimiento() + "'" +
            "}";
    }
}
