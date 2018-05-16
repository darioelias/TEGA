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
import java.io.Serializable;
import java.util.Objects;

/**
 * A Alelo.
 */
@Entity
@Table(name = "alelos")
public class Alelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private String valor;

    @Column(name = "indice")
    private Integer indice;

    @ManyToOne(fetch=FetchType.LAZY)
    private Locus locus;

    @ManyToOne(fetch=FetchType.LAZY)
    private Muestra muestra;

	@Column(name = "publico")
    private Boolean publico;


	public Alelo(){}

	public Alelo(Muestra muestra, Locus locus, String valor, Integer indice, Boolean publico){
		this.muestra = muestra;
		this.locus = locus;
		this.valor = valor;
		this.indice = indice;
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

    public String getValor() {
        return valor;
    }

    public Alelo valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getIndice() {
        return indice;
    }

    public Alelo indice(Integer indice) {
        this.indice = indice;
        return this;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public Locus getLocus() {
        return locus;
    }

    public Alelo locus(Locus locus) {
        this.locus = locus;
        return this;
    }

    public void setLocus(Locus locus) {
        this.locus = locus;
    }

    public Muestra getMuestra() {
        return muestra;
    }

    public Alelo muestra(Muestra muestra) {
        this.muestra = muestra;
        return this;
    }

    public void setMuestra(Muestra muestra) {
        this.muestra = muestra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alelo alelo = (Alelo) o;
        if(alelo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alelo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Alelo{" +
            "id=" + id +
            ", valor='" + valor + "'" +
            ", indice='" + indice + "'" +
            '}';
    }
}
