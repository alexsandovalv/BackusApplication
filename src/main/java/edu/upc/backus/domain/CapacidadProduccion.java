package edu.upc.backus.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CapacidadProduccion.
 */
@Entity
@Table(name = "T_CAPACIDADPRODUCCION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CapacidadProduccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "capacidad", precision=10, scale=2, nullable = false)
    private BigDecimal capacidad;

    @NotNull
    @Column(name = "velocidad", precision=10, scale=2, nullable = false)
    private BigDecimal velocidad;

    @ManyToOne
    private Maquina maquina;

    @ManyToOne
    private MedidasLts medidasLts;

    @ManyToOne
    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(BigDecimal capacidad) {
        this.capacidad = capacidad;
    }

    public BigDecimal getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(BigDecimal velocidad) {
        this.velocidad = velocidad;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public MedidasLts getMedidasLts() {
        return medidasLts;
    }

    public void setMedidasLts(MedidasLts medidasLts) {
        this.medidasLts = medidasLts;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CapacidadProduccion capacidadProduccion = (CapacidadProduccion) o;

        if ( ! Objects.equals(id, capacidadProduccion.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CapacidadProduccion{" +
                "id=" + id +
                ", capacidad='" + capacidad + "'" +
                ", velocidad='" + velocidad + "'" +
                '}';
    }
}
