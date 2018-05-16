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


package org.tega.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

import org.tega.domain.Atributo;
import org.tega.domain.ValorAtributo;
import org.tega.domain.enumeration.Entidad;

import org.springframework.stereotype.Service;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import java.nio.charset.StandardCharsets;

import org.tega.repository.AtributoRepository;

@Service
public class AtributoService {

	private static final String SEP_COL = "\t";
	private static final String SEP_FILA = "\n";
	private static final String EXP_REPLA = "[\t\n]";
	private static final String CAMPO_ID = "id";

	private static final Logger log = LoggerFactory.getLogger(AtributoService.class);

	@Inject
    private AtributoRepository atributoRepository;

	public <T extends ValorAtributo> void exportar(final Set<Long> listaIDEnt,
												 final List<T> listaAtt,	 
												 final Entidad entidad,
												 final File archivoDestino) 
		throws Exception {
					
		log.debug("ExpAtributos - Inicio");
		
		List<Atributo> atributosEntidad = atributoRepository.findByEntidad(entidad);

		final StringBuilder cadena = new StringBuilder();

		cadena.append(CAMPO_ID+SEP_COL);

		atributosEntidad.forEach(a -> cadena.append(a.getCodigo().trim().replaceAll(EXP_REPLA," ")+SEP_COL));
		
		cadena.deleteCharAt(cadena.length()-1);
		cadena.append(SEP_FILA);
		

		listaIDEnt.forEach(id -> {
			
			cadena.append(id.toString()+SEP_COL);

			Map<Long, T> mapFilt = listaAtt.stream().filter(a -> a.getIdObjeto().equals(id))
													  .collect(Collectors.toMap(k -> k.getAtributo().getId(), x -> x)); 

			atributosEntidad.forEach(a -> {

				T ma = mapFilt.get(a.getId());
				if(ma == null)
					cadena.append(a.getValor().replaceAll(EXP_REPLA," ")+SEP_COL);
				else
					cadena.append(ma.getValor().replaceAll(EXP_REPLA," ")+SEP_COL);
			});
			
			cadena.deleteCharAt(cadena.length()-1);
			cadena.append(SEP_FILA);
		});

		FileUtils.writeStringToFile(archivoDestino, cadena.toString(), StandardCharsets.UTF_8);		

		log.debug("ExpAtributos - Fin");
	}

	public <T extends ValorAtributo> File exportar(final Set<Long> listaIDEnt,
												   final List<T> listaAtt,	 
												   final Entidad entidad) throws Exception {
		File tmp = File.createTempFile("atributos",".tsv");
		exportar(listaIDEnt, listaAtt, entidad, tmp);
		return tmp;
	}


	public <T extends ValorAtributo> Set<T> filtrarValoresPorDefecto(Set<T> atributosObj, Entidad entidad){
		
		final Map<Long, Atributo> atributosEntidad = atributoRepository.findByEntidad(entidad)
														.stream()
														.collect(Collectors.toMap(Atributo::getId, x -> x));

		Set<T> atributosFilt = atributosObj.stream() 
								.filter(a -> {
									Atributo at = atributosEntidad.get(a.getAtributo().getId());
									return at != null && !at.getTipo().iguales(at.getValor(), a.getValor());})
								.collect(Collectors.toSet());

		return atributosFilt;
	}

}
