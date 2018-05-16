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


package org.tega.domain.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * The Sexo enumeration.
 */
public enum Sexo {
    FEMENINO,MASCULINO,INDEFINIDO;

	public static Sexo parsear(String cadena){

		if(StringUtils.isBlank(cadena))
			return null;

		cadena = cadena.trim();

		if( StringUtils.equalsIgnoreCase(Sexo.FEMENINO.toString(),cadena) ||
			StringUtils.equalsIgnoreCase("F",cadena) ||
			StringUtils.equalsIgnoreCase("FEMALE",cadena))
			return Sexo.FEMENINO;

		if( StringUtils.equalsIgnoreCase(Sexo.MASCULINO.toString(),cadena) ||
			StringUtils.equalsIgnoreCase("M",cadena) ||
			StringUtils.equalsIgnoreCase("MALE",cadena))
			return Sexo.MASCULINO;
			
		if( StringUtils.equalsIgnoreCase(Sexo.INDEFINIDO.toString(),cadena) ||
			StringUtils.equalsIgnoreCase("I",cadena) ||
			StringUtils.equalsIgnoreCase("UNDEFINED",cadena) ||
			StringUtils.equalsIgnoreCase("U",cadena))
			return Sexo.INDEFINIDO;

		throw new IllegalArgumentException(String.format(
		    "There is no value with name '%s' in Sexo",cadena));
	}
}
