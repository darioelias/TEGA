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

import java.io.File;
import java.util.List;
import java.util.Iterator;

import org.tega.domain.AnalisisGenotipo;
import org.tega.domain.Archivo;
import org.tega.domain.ConjuntoMuestras;
import org.tega.domain.Muestra;
import org.tega.domain.Locus;
import org.tega.domain.Alelo;

import org.tega.service.ArchivosEntidades;
import org.tega.service.ArchivoService;

import org.tega.repository.AnalisisGenotipoRepository;
import org.tega.repository.AleloRepository;
import org.tega.repository.MuestraRepository;
import org.tega.repository.ConjuntoMuestrasRepository;
import org.tega.repository.LocusRepository;

import org.springframework.stereotype.Service;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import java.nio.charset.StandardCharsets;

import org.tega.service.ParametroTegaService;

@Service
public class ExpGenepop {

	private static final Integer VALOR_PERDIDO = 0;
	private static final String SEP_COL = " ";
	private static final String SEP_FILA = "\n";
	private static final String EXP_REPLA = "[ \n]";
	private static final String LINEA_POP = "Pop"; 
	private static final String SEP_IND_ALELOS = ",";
	private static final String FORMATO_VALOR = "%03d"; //Formato de valores (3 dígitos completando con 0)
	private static final String ARCHIVO_DESTINO = "genotipos.gen";

	private static final Logger log = LoggerFactory.getLogger(ExpGenepop.class);

	public void exportar(AnalisisGenotipo ag, 
						 String pathDestino,
						 List<Alelo> alelos,
						 List<Locus> loci,
						 List<ConjuntoMuestras> conjuntosMuestras) 
		throws Exception {

		log.debug("ExpGenepop - Inicio: {}",ag.getId());

		StringBuilder cadena = new StringBuilder();
		
		if(alelos.size() > 0){

			cadena.append(ag.getCodigo()+": "+ag.getDetalle()+SEP_FILA);

			for(Locus l : loci)
				cadena.append(expStr(l.getCodigo())+SEP_FILA);

			for(ConjuntoMuestras ce : conjuntosMuestras){	

				cadena.append(LINEA_POP+SEP_FILA);	
	 		
				for(Muestra e : ce.getMuestras()){

					cadena.append(e.getId()+SEP_COL+SEP_COL);
					cadena.append(ce.getId()+SEP_IND_ALELOS+SEP_COL);

					for(Locus l : loci){

						Integer ploidia = l.getPloidia();

						for(Integer i = 1; i <= ploidia; i++){
							Integer valor = VALOR_PERDIDO;

							Alelo alelo = BusquedaAlelo.buscar(alelos, e, l, i);
				
							if(alelo != null && alelo.getValor() != null){
								try{
									valor = Integer.valueOf(alelo.getValor().trim());
								}catch(Exception ex){
									valor = VALOR_PERDIDO;
									log.debug(ex.toString());
								}
							}

							cadena.append(String.format(FORMATO_VALOR, valor));
						}	

						cadena.append(SEP_COL);				
					}
					cadena.deleteCharAt(cadena.length()-1);
					cadena.append(SEP_FILA);
				}
			}

			cadena.deleteCharAt(cadena.length()-1);
		}

		FileUtils.writeStringToFile(new File(pathDestino+ARCHIVO_DESTINO),cadena.toString(),StandardCharsets.UTF_8);

		log.debug("ExpGenepop - Fin: {}",ag.getId());
	}

	private String expStr(Object c){
		if(c == null)
			return "";

		return c.toString().trim().replaceAll(EXP_REPLA,"_");
	}

}
