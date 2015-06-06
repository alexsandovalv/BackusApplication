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
 * A CostoProduccion.
 */
@Entity
@Table(name = "T_COSTOPRODUCCION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CostoProduccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "mano_obra", precision=10, scale=2, nullable = false)
    private BigDecimal manoObra;

    @NotNull
    @Column(name = "hora_maquina", precision=10, scale=2, nullable = false)
    private BigDecimal horaMaquina;

    @Column(name = "total", precision=10, scale=2, nullable = false)
    private BigDecimal total;

    @ManyToOne
    private PlanSolucion planSolucion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getManoObra() {
        return manoObra;
    }

    public void setManoObra(BigDecimal manoObra) {
        this.manoObra = manoObra;
    }

    public BigDecimal getHoraMaquina() {
        return horaMaquina;
    }

    public void setHoraMaquina(BigDecimal horaMaquina) {
        this.horaMaquina = horaMaquina;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public PlanSolucion getPlanSolucion() {
        return planSolucion;
    }

    public void setPlanSolucion(PlanSolucion planSolucion) {
        this.planSolucion = planSolucion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CostoProduccion costoProduccion = (CostoProduccion) o;

        if ( ! Objects.equals(id, costoProduccion.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CostoProduccion{" +
                "id=" + id +
                ", manoObra='" + manoObra + "'" +
                ", horaMaquina='" + horaMaquina + "'" +
                ", total='" + total + "'" +
                '}';
    }
}
