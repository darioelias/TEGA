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


package org.tega.service.importacion;

import java.io.Serializable;
import java.util.Objects;


/**
 * A Muestra.
 */
public class AleloImp implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long 		id;
    private Integer		indice;
    private String 		valor;
    private Boolean 	publico;

	private Long 		idMuestra;
	private String 		codigoInternoMuestra;
	private Long 		idLocus;
	private String 		codigoLocus;


	public AleloImp(){}


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


    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Long getIdMuestra() {
        return idMuestra;
    }

    public void setIdMuestra(Long idMuestra) {
        this.idMuestra = idMuestra;
    }

    public String getCodigoInternoMuestra() {
        return codigoInternoMuestra;
    }

    public void setCodigoInternoMuestra(String codigoInternoMuestra) {
        this.codigoInternoMuestra = codigoInternoMuestra;
    }

    public Long getIdLocus() {
        return idLocus;
    }

    public void setIdLocus(Long idLocus) {
        this.idLocus = idLocus;
    }

    public String getCodigoLocus() {
        return codigoLocus;
    }

    public void setCodigoLocus(String codigoLocus) {
        this.codigoLocus = codigoLocus;
    }

   	

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AleloImp alelo = (AleloImp) o;
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
        return "AleloImp{" +
            "id="+id+
			"valor="+valor+
			"indice="+indice+
			"idMuestra="+idMuestra+
			"codigoInternoMuestra="+codigoInternoMuestra+
			"idLocus="+idLocus+
			"codigoLocus="+codigoLocus+
			"publico="+publico+
		'}';
    }
}
