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

import org.springframework.stereotype.Service;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import java.nio.charset.StandardCharsets;

import org.tega.service.ParametroTegaService;

@Service
public class ExpGenotipos {

	private static final String SEP_COL = "\t";
	private static final String SEP_FILA = "\n";
	private static final String EXP_REPLA = "[\t\n]";
	private static final String ARCHIVO_DESTINO = "genotipos.tsv";

	private static final Logger log = LoggerFactory.getLogger(ExpGenotipos.class);

	@Inject
	private ParametroTegaService parametroTegaService;

	public void exportar(AnalisisGenotipo ag, 
						 String pathDestino,
						 List<Alelo> alelos,
						 List<Locus> loci,
						 List<ConjuntoMuestras> conjuntosMuestras) 
		throws Exception {
					
		log.debug("ExpGenotipos - Inicio: {}",ag.getId());
		

		String valorNulo = parametroTegaService.getString("EXP_PROC_VALOR_NULO");
		String sepAlelos = parametroTegaService.getString("EXP_PROC_SEP_ALELOS");

		StringBuilder cadena = new StringBuilder();

		cadena.append("conjunto_id"	   +SEP_COL+
					  "conjunto_cod"   +SEP_COL+
					  "conjunto_det"   +SEP_COL+
					  "muestra_id"	   +SEP_COL+
					  "muestra_cod_int"+SEP_COL);

		for(Locus l : loci)
			cadena.append(l.getCodigo()+SEP_COL);

		cadena.deleteCharAt(cadena.length()-1);
		cadena.append(SEP_FILA);

		for(ConjuntoMuestras c : conjuntosMuestras){	

			for(Muestra e : c.getMuestras()){
				
				cadena.append(c.getId()			  		+SEP_COL+
							  expStr(c.getCodigo())		+SEP_COL+
							  expStr(c.getDetalle())	+SEP_COL+
							  e.getId()			  		+SEP_COL+
							  expStr(e.getCodigoInterno())+SEP_COL);	

				for(Locus l : loci){

					Integer ploidia = l.getPloidia();
					
					for(int i = 1; i <= ploidia; i++){
						String valor = valorNulo;

						Alelo alelo = BusquedaAlelo.buscar(alelos, e, l, i);
				
						if(alelo != null && alelo.getValor() != null)
							valor = alelo.getValor();
				
						cadena.append(valor+sepAlelos);
					}		
					
					cadena.deleteCharAt(cadena.length()-1);
					cadena.append(SEP_COL);
				}
				cadena.deleteCharAt(cadena.length()-1);
				cadena.append(SEP_FILA);
			}
		}

		cadena.deleteCharAt(cadena.length()-1);

		FileUtils.writeStringToFile(new File(pathDestino+ARCHIVO_DESTINO),cadena.toString(),StandardCharsets.UTF_8);		

		log.debug("ExpGenotipos- Fin: {}",ag.getId());
	}

	private String expStr(Object c){
		if(c == null)
			return "";

		return c.toString().trim().replaceAll(EXP_REPLA," ");
	}

}
