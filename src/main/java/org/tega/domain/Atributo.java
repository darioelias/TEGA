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

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import org.tega.domain.enumeration.Tipo;
import org.tega.domain.enumeration.Entidad;
import org.tega.domain.util.BaseDeDatos;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "atributos")
public class Atributo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
	@Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @NotNull
    @Column(name = "detalle", nullable = false)
    private String detalle;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	private Tipo tipo;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "entidad", nullable = false)
	private Entidad entidad;

	@NotNull
    @Column(name = "valor",length=BaseDeDatos.VARCHAR_MAX_LENGTH) 
    private String valor;

    @OneToMany(mappedBy = "atributo", orphanRemoval=true)
	@JsonIgnore
    private Set<MuestraAtributo> muestraAtributos = new HashSet<>();

    @OneToMany(mappedBy = "atributo", orphanRemoval=true)
	@JsonIgnore
    private Set<ProyectoAtributo> proyectoAtributos = new HashSet<>();

    @OneToMany(mappedBy = "atributo", orphanRemoval=true)
	@JsonIgnore
    private Set<ConjuntoMuestrasAtributo> conjuntoMuestrasAtributos = new HashSet<>();

    @OneToMany(mappedBy = "atributo", orphanRemoval=true)
	@JsonIgnore
    private Set<AnalisisGenotipoAtributo> analisisGenotipoAtributos = new HashSet<>();

    @OneToMany(mappedBy = "atributo", orphanRemoval=true)
	@JsonIgnore
    private Set<LocusAtributo> locusAtributos = new HashSet<>();

	public Atributo(){};

	public Atributo(Long id){
		this.id = id;
	}

	public Atributo(Long id, String codigo, String detalle){
		this.id = id;
		this.codigo = codigo;
		this.detalle = detalle;
	}

	public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

	public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

	public String getCodigo() {
        return codigo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

	public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

	public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

	public Set<MuestraAtributo> getMuestraAtributos() {
        return muestraAtributos;
    }

    public void setMuestraAtributos(Set<MuestraAtributo> muestraAtributos) {
        this.muestraAtributos = muestraAtributos;
    }

	public Set<ProyectoAtributo> getProyectoAtributos() {
        return proyectoAtributos;
    }

    public void setProyectoAtributos(Set<ProyectoAtributo> proyectoAtributos) {
        this.proyectoAtributos = proyectoAtributos;
    }

	public Set<ConjuntoMuestrasAtributo> getConjuntoMuestrasAtributos() {
        return conjuntoMuestrasAtributos;
    }

    public void setConjuntoMuestrasAtributos(Set<ConjuntoMuestrasAtributo> conjuntoMuestrasAtributos) {
        this.conjuntoMuestrasAtributos = conjuntoMuestrasAtributos;
    }

	public Set<AnalisisGenotipoAtributo> getAnalisisGenotipoAtributos() {
        return analisisGenotipoAtributos;
    }

    public void setAnalisisGenotipoAtributos(Set<AnalisisGenotipoAtributo> analisisGenotipoAtributos) {
        this.analisisGenotipoAtributos = analisisGenotipoAtributos;
    }

	public Set<LocusAtributo> getLocusAtributos() {
        return locusAtributos;
    }

    public void setLocusAtributos(Set<LocusAtributo> locusAtributos) {
        this.locusAtributos = locusAtributos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Atributo a = (Atributo) o;
        if(a.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, a.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Atributo{" +
            "id=" + id +
            '}';
    }
}
