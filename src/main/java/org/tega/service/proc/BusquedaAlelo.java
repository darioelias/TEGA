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


package org.tega.service.proc;

import java.util.List;

import org.tega.domain.AnalisisGenotipo;
import org.tega.domain.Archivo;
import org.tega.domain.ConjuntoMuestras;
import org.tega.domain.Muestra;
import org.tega.domain.Locus;
import org.tega.domain.Alelo;

public class BusquedaAlelo {

	public static Alelo buscar(List<Alelo> alelos, final Muestra e, final Locus l, final int indice){
		
		return alelos.stream()
					 .filter(a -> a.getMuestra().getId().equals(e.getId()) && 
			  					  a.getLocus().getId().equals(l.getId()) &&
			   					  a.getIndice().equals(indice))
					 .findAny()
					 .orElse(null);
	}

}
