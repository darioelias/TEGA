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
 * A ConjuntoMuestras.
 */
@Entity
@Table(name = "conjuntos_muestras")
public class ConjuntoMuestras implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "detalle")
    private String detalle;

    @ManyToMany
    @JoinTable(name = "conjuntos_muestras_muestras",
               joinColumns = @JoinColumn(name="conjunto_muestras_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="muestra_id", referencedColumnName="ID"))
    private Set<Muestra> muestras = new HashSet<>();

    @ManyToMany(mappedBy = "conjuntosMuestras")
    @JsonIgnore
    private Set<AnalisisGenotipo> analisisGenotipos = new HashSet<>();

    @OneToMany(mappedBy = "conjuntoMuestras", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<ConjuntoMuestrasAtributo> atributos = new HashSet<>();

	@Column(name = "publico")
    private Boolean publico;


	public ConjuntoMuestras(){}

	public ConjuntoMuestras(Long id){
		this.id = id;
	}

	public ConjuntoMuestras(Long id, String codigo, String detalle){
		this.id = id;
		this.codigo = codigo;
		this.detalle = detalle;
	}

	public ConjuntoMuestras(String codigo, String detalle){
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

    public String getCodigo() {
        return codigo;
    }

    public ConjuntoMuestras codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return detalle;
    }

    public ConjuntoMuestras detalle(String detalle) {
        this.detalle = detalle;
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Set<Muestra> getMuestras() {
        return muestras;
    }

    public ConjuntoMuestras muestras(Set<Muestra> muestras) {
        this.muestras = muestras;
        return this;
    }

    public ConjuntoMuestras addMuestras(Muestra muestra) {
        muestras.add(muestra);
        muestra.getConjuntosMuestras().add(this);
        return this;
    }

    public ConjuntoMuestras removeMuestras(Muestra muestra) {
        muestras.remove(muestra);
        muestra.getConjuntosMuestras().remove(this);
        return this;
    }

    public void setMuestras(Set<Muestra> muestras) {
        this.muestras = muestras;
    }

    public Set<AnalisisGenotipo> getAnalisisGenotipos() {
        return analisisGenotipos;
    }

    public ConjuntoMuestras analisisGenotipos(Set<AnalisisGenotipo> analisisGenotipos) {
        this.analisisGenotipos = analisisGenotipos;
        return this;
    }

    public ConjuntoMuestras addAnalisisGenotipo(AnalisisGenotipo analisisGenotipo) {
        analisisGenotipos.add(analisisGenotipo);
        analisisGenotipo.getConjuntosMuestras().add(this);
        return this;
    }

    public ConjuntoMuestras removeAnalisisGenotipo(AnalisisGenotipo analisisGenotipo) {
        analisisGenotipos.remove(analisisGenotipo);
        analisisGenotipo.getConjuntosMuestras().remove(this);
        return this;
    }

    public void setAnalisisGenotipos(Set<AnalisisGenotipo> analisisGenotipos) {
        this.analisisGenotipos = analisisGenotipos;
    }

	public Set<ConjuntoMuestrasAtributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(Set<ConjuntoMuestrasAtributo> atributos) {
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
        ConjuntoMuestras conjuntoMuestras = (ConjuntoMuestras) o;
        if(conjuntoMuestras.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, conjuntoMuestras.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConjuntoMuestras{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            ", detalle='" + detalle + "'" +
            '}';
    }
}
