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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.io.File;
import org.tega.service.ArchivoService;
import org.tega.service.ParametroTegaService;
import java.util.regex.*;

@Service
public class Backup {
	
	private final Logger log = LoggerFactory.getLogger(Backup.class);

	@Inject
	private ParametroTegaService parametroTegaService;

	@Inject
	private ArchivoService archivoService;

	public ResultadoComando crearBackup() throws Exception{

		String comando 		 = parametroTegaService.getString("COMANDO_BACKUP");
		String archivoConfig = parametroTegaService.getString("ARCHIVO_CONFIG_BACKUP");
		
		ResultadoComando resultado = EjecComando.ejecutar(comando,"MANUAL", archivoConfig);

        Matcher m = Pattern.compile(".*ERROR.*" , Pattern.CASE_INSENSITIVE).matcher(resultado.getStdOut());

        while (m.find()){
        	resultado.setStdErr("\n"+resultado.getStdErr().concat(m.group()+"\n"));
        }

		return resultado;
        
	}

	public File buscarUltimo() throws Exception{

		String directorio 	= parametroTegaService.getString("DIRECTORIO_BACKUP"); 
		String filtro 		= parametroTegaService.getString("FILTRO_ARCHIVOS_BACKUP"); 

		return archivoService.getArchivoMasReciente(directorio, filtro);		
	}
}
