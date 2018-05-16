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


import com.opencsv.exceptions.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import com.opencsv.*;
import org.tega.service.util.EnumUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.stream.Collectors;

public class CsvReaderUtil{

	public static List<String[]> parsear(File archivoDestino, String separador, String codificacion)
		 throws IOException{

		separador = StringUtils.isBlank(separador) ? "," : separador;

		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(archivoDestino),codificacion), separador.charAt(0));

		return reader.readAll();
	}

}
