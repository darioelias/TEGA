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
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.tega.domain.enumeration.Entidad;
import org.tega.domain.util.BaseDeDatos;


@Entity
@Table(name = "muestras_atributos",
	   uniqueConstraints=@UniqueConstraint(columnNames={"muestra_id", "atributo_id"}))
public class MuestraAtributo implements ValorAtributo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
    private Muestra muestra;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
    private Atributo atributo;

	@NotNull
	@Column(name = "valor",length=BaseDeDatos.VARCHAR_MAX_LENGTH) 
    private String valor;


	public MuestraAtributo(){};

	public MuestraAtributo(Muestra m, Atributo a, String valor){
		this.muestra = m;
		this.atributo = a;
		this.valor = valor;
	};
	
	@Override
	@JsonIgnore
	public Long getIdObjeto() {
        return muestra.getId();
    }

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	@Override
	public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Muestra getMuestra() {
        return muestra;
    }

    public void setMuestra(Muestra muestra) {
        this.muestra = muestra;
    }

	@Override
    public Atributo getAtributo() {
        return atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MuestraAtributo muestra = (MuestraAtributo) o;
        if(muestra.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, muestra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MuestraAtributo{" +
            ", id='" + id + "'" +
            ", valor='" + valor + "'" +
			'}';
    }
}



