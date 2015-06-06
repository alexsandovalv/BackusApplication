package edu.upc.backus.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A PlanSolucion.
 */
@Entity
@Table(name = "T_PLANSOLUCION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlanSolucion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dia_trabajada")
    private String diaTrabajada;

    @Column(name = "cantidad", precision=10, scale=2)
    private BigDecimal cantidad;

    @ManyToOne
    private ProgramaProduccion programaProduccion;

    @OneToMany(mappedBy = "planSolucion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CostoProduccion> costoProduccions = new HashSet<>();

    @ManyToOne
    private Maquina maquina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiaTrabajada() {
        return diaTrabajada;
    }

    public void setDiaTrabajada(String diaTrabajada) {
        this.diaTrabajada = diaTrabajada;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public ProgramaProduccion getProgramaProduccion() {
        return programaProduccion;
    }

    public void setProgramaProduccion(ProgramaProduccion programaProduccion) {
        this.programaProduccion = programaProduccion;
    }

    public Set<CostoProduccion> getCostoProduccions() {
        return costoProduccions;
    }

    public void setCostoProduccions(Set<CostoProduccion> costoProduccions) {
        this.costoProduccions = costoProduccions;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlanSolucion planSolucion = (PlanSolucion) o;

        if ( ! Objects.equals(id, planSolucion.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PlanSolucion{" +
                "id=" + id +
                ", diaTrabajada='" + diaTrabajada + "'" +
                ", cantidad='" + cantidad + "'" +
                '}';
    }
}
