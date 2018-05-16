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
import org.tega.domain.util.BaseDeDatos;


@Entity
@Table(name = "parametros_ejecuciones")
public class ParametroEjecucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
    private Ejecucion ejecucion;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
    private ParametroProcedimiento parametro;

    @Column(name = "valor",length=BaseDeDatos.VARCHAR_MAX_LENGTH) 
    private String valor;


	public ParametroEjecucion(){}

	public ParametroEjecucion(Ejecucion ejecucion, ParametroProcedimiento parametro, String valor){
		this.ejecucion = ejecucion;
		this.parametro = parametro;
		this.valor = valor;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

	public ParametroProcedimiento getParametro() {
        return parametro;
    }

    public void setParametro(ParametroProcedimiento parametro) {
        this.parametro = parametro;
    }

	public void setEjecucion(Ejecucion ejecucion) {
        this.ejecucion = ejecucion;
    }

	public Ejecucion getEjecucion() {
        return ejecucion;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParametroEjecucion parametro = (ParametroEjecucion) o;
        if(parametro.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, parametro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParametroEjecucion{" +
            "id=" + id +
            ", valor='" + valor + "'" +
            '}';
    }
}
