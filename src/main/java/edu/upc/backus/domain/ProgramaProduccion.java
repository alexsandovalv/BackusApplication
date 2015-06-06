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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import edu.upc.backus.domain.util.CustomDateTimeDeserializer;
import edu.upc.backus.domain.util.CustomDateTimeSerializer;

/**
 * A ProgramaProduccion.
 */
@Entity
@Table(name = "T_PROGRAMAPRODUCCION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProgramaProduccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "cantidad", precision=10, scale=2, nullable = false)
    private BigDecimal cantidad;

    @NotNull
    @Column(name = "dia_trabajada", nullable = false)
    private String diaTrabajada;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fecha_programada", nullable = false)
    private DateTime fechaProgramada;

    @ManyToOne
    private MedidasLts medidasLts;

    @ManyToOne
    private Producto producto;

    @OneToMany(mappedBy = "programaProduccion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanSolucion> planSolucions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getDiaTrabajada() {
        return diaTrabajada;
    }

    public void setDiaTrabajada(String diaTrabajada) {
        this.diaTrabajada = diaTrabajada;
    }

    public DateTime getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(DateTime fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
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

    public Set<PlanSolucion> getPlanSolucions() {
        return planSolucions;
    }

    public void setPlanSolucions(Set<PlanSolucion> planSolucions) {
        this.planSolucions = planSolucions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgramaProduccion programaProduccion = (ProgramaProduccion) o;

        if ( ! Objects.equals(id, programaProduccion.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProgramaProduccion{" +
                "id=" + id +
                ", cantidad='" + cantidad + "'" +
                ", diaTrabajada='" + diaTrabajada + "'" +
                ", fechaProgramada='" + fechaProgramada + "'" +
                '}';
    }
}
