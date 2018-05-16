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
import java.util.Set;
import java.util.HashSet;

public class AleloImpResultadoLista extends AleloImpResultado {

	private Set<Integer> lociNoIdentificadosLinea = new HashSet<Integer>();
	private Set<Integer> muestrasNoIdentificadasLinea = new HashSet<Integer>();
	private Set<Integer> alelosRepetidosLinea = new HashSet<Integer>();

	public void incLociNoIdentificadosLinea(Integer s){lociNoIdentificadosLinea.add(s);}
	public void incMuestrasNoIdentificadasLinea(Integer s){muestrasNoIdentificadasLinea.add(s);}
	public void incAlelosRepetidosLinea(Integer s){alelosRepetidosLinea.add(s);}



	public Set<Integer> getLociNoIdentificadosLinea(){return lociNoIdentificadosLinea;}
	public Set<Integer> getMuestrasNoIdentificadasLinea(){return muestrasNoIdentificadasLinea ;}
	public Set<Integer> getAlelosRepetidosLinea(){return alelosRepetidosLinea;}


	public void setLociNoIdentificadosLinea(Set<Integer> l){this.lociNoIdentificadosLinea = l;}
	public void setMuestrasNoIdentificadaLinea(Set<Integer> l){this.muestrasNoIdentificadasLinea = l;}
	public void setAlelosRepetidosLinea(Set<Integer> l){this.alelosRepetidosLinea = l;}
}
