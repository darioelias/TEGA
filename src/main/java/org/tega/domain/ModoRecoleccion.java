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
 * A ModoRecoleccion.
 */
@Entity
@Table(name = "modos_recoleccion")
public class ModoRecoleccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

	@Column(name = "codigo", unique = true)
    private String codigo;

    @OneToMany(mappedBy = "modoRecoleccion")
    @JsonIgnore
    private Set<Muestra> muestras = new HashSet<>();

	public ModoRecoleccion(){};

	public ModoRecoleccion(Long id, String codigo, String nombre){
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public ModoRecoleccion(String codigo, String nombre){
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

    public ModoRecoleccion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Muestra> getMuestras() {
        return muestras;
    }

    public ModoRecoleccion muestras(Set<Muestra> muestras) {
        this.muestras = muestras;
        return this;
    }

    public ModoRecoleccion addMuestras(Muestra muestra) {
        muestras.add(muestra);
        muestra.setModoRecoleccion(this);
        return this;
    }

    public ModoRecoleccion removeMuestras(Muestra muestra) {
        muestras.remove(muestra);
        muestra.setModoRecoleccion(null);
        return this;
    }

    public void setMuestras(Set<Muestra> muestras) {
        this.muestras = muestras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModoRecoleccion modoRecoleccion = (ModoRecoleccion) o;
        if(modoRecoleccion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, modoRecoleccion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ModoRecoleccion{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
