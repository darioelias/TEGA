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


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public enum Tipo {

	NUMERICO {
		protected boolean validar(String valor){
			boolean valido = false;
			try{
				Double.valueOf(valor);
				valido = true;
			}catch(Exception e){}

			return valido;
		}

		protected boolean comparar(String valorA, String valorB){
			boolean iguales = false;
			try{
				iguales = Double.valueOf(valorA).equals(Double.valueOf(valorB));
			}catch(Exception e){}

			return iguales;
		}
	},
	ENTERO {
		protected boolean validar(String valor){
			boolean valido = false;
			try{
				Integer.valueOf(valor);
				valido = true;
			}catch(Exception e){}

			return valido;
		}

		protected boolean comparar(String valorA, String valorB){
			boolean iguales = false;
			try{
				iguales = Integer.valueOf(valorA).equals(Integer.valueOf(valorB));
			}catch(Exception e){}

			return iguales;
		}
	},
	CARACTER {
		protected boolean validar(String valor){
			return true;
		}

		protected boolean comparar(String valorA, String valorB){
			return valorA.equals(valorB);
		}
	},
	LOGICO {
		protected boolean validar(String valor){
			return valor.equals("1") || valor.equals("0");
		}

		protected boolean comparar(String valorA, String valorB){
			return validar(valorA) && valorA.equals(valorB);
		}
	},
	FECHA {
		protected boolean validar(String valor){
			boolean valido = false;
			try{
				getDate(valor);
				valido = true;
			}catch(Exception e){}
			return valido;
		}

		protected boolean comparar(String valorA, String valorB){
			boolean iguales = false;
			try{
				iguales = getDate(valorA).equals(getDate(valorB));
			}catch(Exception e){}
			return iguales;
		}

		public String convertir(String valor){
			if(valor == null)
				return null;			

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return getDate(valor).format(dtf);
		}

		private LocalDate getDate(String valor){
			DateTimeFormatter dtf = null;

			if(valor.contains("-")){
				dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			}else if(valor.contains("/")){
				dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			}else{
				dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			}

			return LocalDate.parse(valor, dtf);
		}
	};


	protected abstract boolean validar(String valor); 
	protected abstract boolean comparar(String valorA, String valorB); 

	public boolean formatoValido(String valor){
		if(valor == null)
			return true;
		return validar(valor);
	} 


	public boolean iguales(String valorA, String valorB){
		if(valorA == null && valorB == null)
			return true;

		if(valorA != null && valorB != null)
			return comparar(valorA, valorB);

		return false;
	}

	public String convertir(String valor){
		return valor;
	} 

}
