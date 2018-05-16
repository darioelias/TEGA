/**
 *  This file is part of TEGA (Tools for Evolutionary and Genetic Analysis)
 *  TEGA website: https://github.com/darioelias/TEGA
 *
 *  Copyright (C) 2018 Dario E. Elias & Eva C. Rueda
 *
 *  TEGA is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TEGA is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *  
 *  Additional permission under GNU AGPL version 3 section 7
 *  
 *  If you modify TEGA, or any covered work, by linking or combining it with
 *  STRUCTURE, DISTRUCT or CLUMPP (or a modified version of those programs),
 *  the licensors of TEGA grant you additional permission to convey the resulting work.
 */


/**
 * @author Dario E. Elias
 * @version 1.0 
 */


package org.tega.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.tega.domain.enumeration.EstadoAnalisisGenotipo;

/**
 * A AnalisisGenotipo.
 */
@Entity
@Table(name = "analisis_genotipos")
public class AnalisisGenotipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codigo", unique = true)
    private String codigo;

    @NotNull
    @Column(name = "detalle", nullable = false)
    private String detalle;

    @Column(name = "fecha")
    private LocalDate fecha;

	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "analisis_genotipos_archivos",
               joinColumns = @JoinColumn(name="analisis_genotipo_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="archivo_id", referencedColumnName="ID"))
    private Set<Archivo> archivos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "analisis_genotipos_loci",
               joinColumns = @JoinColumn(name="analisis_genotipo_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="locus_id", referencedColumnName="ID"))
    private Set<Locus> loci = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "analisis_genotipos_conjuntos_muestras",
               joinColumns = @JoinColumn(name="analisis_genotipo_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="conjunto_muestras_id", referencedColumnName="ID"))
    private Set<ConjuntoMuestras> conjuntosMuestras = new HashSet<>();

    @ManyToOne(fetch=FetchType.LAZY)
    private Proyecto proyecto;

    @ManyToOne(fetch=FetchType.LAZY)
    private Procedimiento procedimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoAnalisisGenotipo estado;

	@Column(name = "publico")
    private Boolean publico;

    @OneToMany(mappedBy = "analisisGenotipo", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<AnalisisGenotipoAtributo> atributos = new HashSet<>();

    @OneToMany(mappedBy = "analisisGenotipo", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<Ejecucion> ejecuciones = new HashSet<>();

	public AnalisisGenotipo(){}

	public AnalisisGenotipo(Long id){
		this.id = id;
	}

	public AnalisisGenotipo(Long id, String codigo, String detalle){
		this.id = id;
		this.codigo = codigo;
		this.detalle = detalle;
	}

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoAnalisisGenotipo getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnalisisGenotipo estado) {
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }


    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Procedimiento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public AnalisisGenotipo fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Set<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }

    public Set<Locus> getLoci() {
        return loci;
    }

    public AnalisisGenotipo loci(Set<Locus> loci) {
        this.loci = loci;
        return this;
    }

    public AnalisisGenotipo addLocus(Locus locus) {
        loci.add(locus);
        locus.getAnalisisGenotipos().add(this);
        return this;
    }

    public AnalisisGenotipo removeLocus(Locus locus) {
        loci.remove(locus);
        locus.getAnalisisGenotipos().remove(this);
        return this;
    }

    public void setLoci(Set<Locus> loci) {
        this.loci = loci;
    }

    public Set<ConjuntoMuestras> getConjuntosMuestras() {
        return conjuntosMuestras;
    }

    public AnalisisGenotipo conjuntosMuestras(Set<ConjuntoMuestras> conjuntosMuestras) {
        this.conjuntosMuestras = conjuntosMuestras;
        return this;
    }

    public AnalisisGenotipo addConjuntosMuestras(ConjuntoMuestras conjuntoMuestras) {
        conjuntosMuestras.add(conjuntoMuestras);
        conjuntoMuestras.getAnalisisGenotipos().add(this);
        return this;
    }

    public AnalisisGenotipo removeConjuntosMuestras(ConjuntoMuestras conjuntoMuestras) {
        conjuntosMuestras.remove(conjuntoMuestras);
        conjuntoMuestras.getAnalisisGenotipos().remove(this);
        return this;
    }

    public void setConjuntosMuestras(Set<ConjuntoMuestras> conjuntosMuestras) {
        this.conjuntosMuestras = conjuntosMuestras;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public AnalisisGenotipo proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

	public Set<AnalisisGenotipoAtributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(Set<AnalisisGenotipoAtributo> atributos) {
        this.atributos = atributos;
    }

	public Set<Ejecucion> getEjecuciones() {
        return ejecuciones;
    }

    public void setEjecuciones(Set<Ejecucion> ejecuciones) {
        this.ejecuciones = ejecuciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnalisisGenotipo analisisGenotipo = (AnalisisGenotipo) o;
        if(analisisGenotipo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, analisisGenotipo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AnalisisGenotipo{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            ", detalle='" + detalle + "'" +
            ", fecha='" + fecha + "'" +
            '}';
    }
}
