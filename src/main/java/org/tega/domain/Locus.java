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
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Locus.
 */
@Entity
@Table(name = "loci")
public class Locus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "ploidia")
    private Integer ploidia;

	@Column(name = "publico")
    private Boolean publico;

    @OneToMany(mappedBy = "locus")
    @JsonIgnore
    private Set<Alelo> alelos = new HashSet<>();

    @ManyToMany(mappedBy = "loci")
    @JsonIgnore
    private Set<AnalisisGenotipo> analisisGenotipos = new HashSet<>();

    @OneToMany(mappedBy = "locus", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<LocusAtributo> atributos = new HashSet<>();

	public Locus(){}

	public Locus(Long id){
		this.id = id;
	}

	public Locus(Long id, String codigo, String detalle){
		this.id = id;
		this.codigo = codigo;
		this.detalle = detalle;
	}

	public Locus(String codigo, String detalle, Integer ploidia, Boolean publico){
		this.codigo = codigo;
		this.detalle = detalle;
		this.ploidia = ploidia;
		this.publico = publico;
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

    public String getCodigo() {
        return codigo;
    }

    public Locus codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return detalle;
    }

    public Locus detalle(String detalle) {
        this.detalle = detalle;
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getPloidia() {
        return ploidia;
    }

    public Locus ploidia(Integer ploidia) {
        this.ploidia = ploidia;
        return this;
    }

    public void setPloidia(Integer ploidia) {
        this.ploidia = ploidia;
    }

    public Set<Alelo> getAlelos() {
        return alelos;
    }

    public Locus alelos(Set<Alelo> alelos) {
        this.alelos = alelos;
        return this;
    }

    public Locus addAlelos(Alelo alelo) {
        alelos.add(alelo);
        alelo.setLocus(this);
        return this;
    }

    public Locus removeAlelos(Alelo alelo) {
        alelos.remove(alelo);
        alelo.setLocus(null);
        return this;
    }

    public void setAlelos(Set<Alelo> alelos) {
        this.alelos = alelos;
    }

    public Set<AnalisisGenotipo> getAnalisisGenotipos() {
        return analisisGenotipos;
    }

    public Locus analisisGenotipos(Set<AnalisisGenotipo> analisisGenotipos) {
        this.analisisGenotipos = analisisGenotipos;
        return this;
    }

    public Locus addAnalisisGenotipo(AnalisisGenotipo analisisGenotipo) {
        analisisGenotipos.add(analisisGenotipo);
        analisisGenotipo.getLoci().add(this);
        return this;
    }

    public Locus removeAnalisisGenotipo(AnalisisGenotipo analisisGenotipo) {
        analisisGenotipos.remove(analisisGenotipo);
        analisisGenotipo.getLoci().remove(this);
        return this;
    }

    public void setAnalisisGenotipos(Set<AnalisisGenotipo> analisisGenotipos) {
        this.analisisGenotipos = analisisGenotipos;
    }

	public Set<LocusAtributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(Set<LocusAtributo> atributos) {
        this.atributos = atributos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Locus locus = (Locus) o;
        if(locus.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, locus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Locus{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            ", detalle='" + detalle + "'" +
            ", ploidia='" + ploidia + "'" +
            '}';
    }
}
