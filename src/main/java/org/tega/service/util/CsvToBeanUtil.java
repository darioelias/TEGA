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


package org.tega.service.util;

import org.tega.domain.enumeration.Sexo;

import com.opencsv.exceptions.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import com.opencsv.bean.*;
import com.opencsv.*;
import org.tega.service.util.EnumUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileInputStream;
import org.apache.commons.lang3.StringUtils;

public class CsvToBeanUtil<T> extends CsvToBean<T>{

	public static <T> List<T> parsear(Class<T> clase, File archivoDestino, String separador, String codificacion)
		 throws IOException{

		separador = StringUtils.isBlank(separador) ? "," : separador;

	//	CSVReader reader = new CSVReader(new FileReader(archivoDestino), separador.charAt(0));

		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(archivoDestino),codificacion), separador.charAt(0));
		HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<T>();
		strategy.setType(clase);
		CsvToBeanUtil<T> csvToBean = new CsvToBeanUtil<T>();

		csvToBean.setFilter(new CsvToBeanFilter(){

			@Override
			public boolean allowLine(String[] line){
				boolean existe = false;				
				for(String s : line)
					existe = existe || !StringUtils.isBlank(s);
				return existe;
			}
		});

		return csvToBean.parse(strategy, reader);
	}

	@Override
	protected Object convertValue(String value, PropertyDescriptor prop) 
		throws InstantiationException,IllegalAccessException {

		if(value != null){
			if(prop.getPropertyType() == Sexo.class ){

				return Sexo.parsear(value);
			}

			if(prop.getPropertyType() == LocalDate.class){

				DateTimeFormatter dtf = null;

				if(value.contains("-")){
					dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				}else if(value.contains("/")){
					dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				}else{
					dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
				}

				return LocalDate.parse(value, dtf);
			}

		}

		return super.convertValue(value, prop);
	}

}
