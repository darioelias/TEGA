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
 * A Profesional.
 */
@Entity
@Table(name = "profesionales")
public class Profesional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

	@Column(name = "codigo", unique = true)
    private String codigo;

    @OneToMany(mappedBy = "profesional")
    @JsonIgnore
    private Set<Muestra> muestras = new HashSet<>();

    @ManyToMany()
	@JoinTable(name = "instituciones_profesionales",
               joinColumns = @JoinColumn(name="profesional_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="institucion_id", referencedColumnName="ID"))
    private Set<Institucion> instituciones = new HashSet<>();

	public Profesional(){};

	public Profesional(Long id, String codigo, String nombre){
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public Profesional(String codigo, String nombre){
		this.codigo = codigo;
		this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public Profesional nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Muestra> getMuestras() {
        return muestras;
    }

    public Profesional muestras(Set<Muestra> muestras) {
        this.muestras = muestras;
        return this;
    }

    public Profesional addMuestras(Muestra muestra) {
        muestras.add(muestra);
        muestra.setProfesional(this);
        return this;
    }

    public Profesional removeMuestras(Muestra muestra) {
        muestras.remove(muestra);
        muestra.setProfesional(null);
        return this;
    }

    public void setMuestras(Set<Muestra> muestras) {
        this.muestras = muestras;
    }


    public Set<Institucion> getInstituciones() {
        return instituciones;
    }

    public Profesional instituciones(Set<Institucion> institucions) {
        this.instituciones = institucions;
        return this;
    }

    public Profesional addInstituciones(Institucion institucion) {
        instituciones.add(institucion);
        institucion.getProfesionales().add(this);
        return this;
    }

    public Profesional removeInstituciones(Institucion institucion) {
        instituciones.remove(institucion);
        institucion.getProfesionales().remove(this);
        return this;
    }

    public void setInstituciones(Set<Institucion> institucions) {
        this.instituciones = institucions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profesional profesional = (Profesional) o;
        if(profesional.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, profesional.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Profesional{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
