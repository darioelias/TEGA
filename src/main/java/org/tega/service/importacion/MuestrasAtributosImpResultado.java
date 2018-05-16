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

public class MuestrasAtributosImpResultado implements Serializable {

	private Integer muestrasProcesadas = 0;
	private Integer atributosValidos = 0;
	private Set<String> atributosInvalidos = new HashSet<String>();
	private Set<String> conjuntosMuestrasNoIdentificados = new HashSet<String>();
	private Set<String> muestrasNoIdentificadas = new HashSet<String>();
	private Set<String> atributosNoIdentificados = new HashSet<String>();


	public void incMuestrasProcesadas(){muestrasProcesadas++;}
	public void incAtributosValidos(){atributosValidos++;}
	public void incAtributosInvalidos(String s){atributosInvalidos.add(s);}
	public void incMuestrasNoIdentificadas(String s){muestrasNoIdentificadas.add(s);}
	public void incConjuntosMuestrasNoIdentificados(String s){conjuntosMuestrasNoIdentificados.add(s);}
	public void incAtributosNoIdentificados(String s){atributosNoIdentificados.add(s);}


	public Integer getMuestrasProcesadas(){return muestrasProcesadas ;}
	public Integer getAtributosValidos(){return atributosValidos ;}
	public Set<String> getAtributosInvalidos(){return atributosInvalidos ;}
	public Set<String> getMuestrasNoIdentificadas(){return muestrasNoIdentificadas ;}
	public Set<String> getConjuntosMuestrasNoIdentificados(){return conjuntosMuestrasNoIdentificados;}
	public Set<String> getAtributosNoIdentificados(){return atributosNoIdentificados ;}


	public void setMuestrasProcesadas(Integer l){this.muestrasProcesadas = l;}
	public void setAtributosValidos(Integer l){this.atributosValidos = l;}
	public void setAtributosInvalidos(Set<String> l){this.atributosInvalidos = l;}
	public void setMuestrasNoIdentificada(Set<String> l){this.muestrasNoIdentificadas = l;}
	public void setConjuntosMuestrasNoIdentificado(Set<String> l){this.conjuntosMuestrasNoIdentificados = l;}
	public void setAtributosNoIdentificados(Set<String> l){this.atributosNoIdentificados = l;}



}
