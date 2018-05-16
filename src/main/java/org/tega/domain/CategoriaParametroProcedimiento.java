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


@Entity
@Table(name = "categorias_parametros_procedimientos")
public class CategoriaParametroProcedimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "codigo", unique = true)
    private String codigo;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "categoria")
    //@JsonIgnore
    private Set<ParametroProcedimiento> parametros = new HashSet<>();

	public CategoriaParametroProcedimiento(){};

	public CategoriaParametroProcedimiento(Long id, String codigo, String nombre){
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public CategoriaParametroProcedimiento(String codigo, String nombre){
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


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<ParametroProcedimiento> getParametros() {
        return parametros;
    }


    public void setParametros(Set<ParametroProcedimiento> parametros) {
        this.parametros = parametros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoriaParametroProcedimiento p = (CategoriaParametroProcedimiento) o;
        if(p.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CategoriaParametroProcedimiento{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", parametros='" + parametros + "'" +
            '}';
    }
}
