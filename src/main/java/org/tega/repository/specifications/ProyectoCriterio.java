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


package org.tega.repository.specifications;

import java.time.LocalDate;

public class ProyectoCriterio{
	private LocalDate desde;
	private LocalDate hasta;
	private String codigo;
	private Boolean publico;
	private Long id;

	public Long getId() {
	    return id;
	}

	public void setId(Long id) {
	    this.id = id;
	}
	public Boolean getPublico() {
	    return publico;
	}

	public void setPublico(Boolean publico) {
	    this.publico= publico;
	}	

	public LocalDate getDesde() {
	    return desde;
	}

	public void setDesde(LocalDate desde) {
	    this.desde = desde;
	}

	public LocalDate getHasta() {
	    return hasta;
	}

	public void setHasta(LocalDate hasta) {
	    this.hasta = hasta;
	}

	public String getCodigo() {
	    return codigo;
	}

	public void setCodigo(String codigo) {
	    this.codigo = codigo;
	}

}
