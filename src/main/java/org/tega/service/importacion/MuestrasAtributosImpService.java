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

import org.tega.domain.*;
import org.tega.domain.enumeration.*;
import org.tega.repository.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

@Service
public class MuestrasAtributosImpService {

	private static final String ERROR_SIN_CAMPO_DE_MUESTRA = "proyectoApp.muestra.importacion.error.sinCampoDeMuestra";

	private final Logger log = LoggerFactory.getLogger(MuestrasAtributosImpService.class);

	@Inject
    private MuestraRepository muestraRepository;	

	@Inject
    private AtributoRepository atributoRepository;

	@Inject
    private MuestraAtributoRepository muestraAtributoRepository;	

	@Inject
    private ConjuntoMuestrasRepository conjuntoMuestrasRepository;

	@Transactional
	public MuestrasAtributosImpResultado importar(List<String[]> lista) throws Exception{

		MuestrasAtributosImpResultado resultado = new MuestrasAtributosImpResultado();

		List<Atributo> atributosEntidad = atributoRepository.findByEntidad(Entidad.MUESTRA);

		if(StringUtils.equalsIgnoreCase(lista.get(0)[0],"conjuntoMuestras")){
			importarDesdeConjuntos(lista, resultado, atributosEntidad);
		}else{
			importarDesdeMuestra(lista, resultado, atributosEntidad);
		}

		return resultado;
	}



	private void importarDesdeMuestra(List<String[]> lista, MuestrasAtributosImpResultado resultado, 
									  List<Atributo> atributosEntidad)  throws Exception{
		
		if(StringUtils.equalsIgnoreCase(lista.get(0)[0],"id")){

			List<Long> muestrasID = new ArrayList<Long>();
			for(int i = 1; i < lista.size(); i++){
				muestrasID.add(new Long(lista.get(i)[0]));
			}

			List<Muestra> muestras = muestraRepository.findByIds(muestrasID);
			Map<Long, Muestra> mapMuestras = muestras.stream().collect(
                									Collectors.toMap(Muestra::getId, x -> x));

			for(int i = 1; i < lista.size(); i++){
				
				Muestra muestra = mapMuestras.get(new Long(lista.get(i)[0]));
				if(muestra != null){
					importarAtributos(lista.get(i), lista.get(0), muestra, resultado, 
								      atributosEntidad, i, 1);
				}else{
					resultado.incMuestrasNoIdentificadas(lista.get(i)[0]);
				}
			}
		}else if(StringUtils.equalsIgnoreCase(lista.get(0)[0],"codigoInterno")){
			List<String> muestrasCod = new ArrayList<String>();
			for(int i = 1; i < lista.size(); i++){
				muestrasCod.add(lista.get(i)[0].toLowerCase());
			}

			List<Muestra> muestras = muestraRepository.findByCodigosInternos(muestrasCod);

			for(int i = 1; i < lista.size(); i++){
				String codigo = lista.get(i)[0];
				Muestra muestra = muestras.stream()
												 .filter(x -> StringUtils.equalsIgnoreCase(x.getCodigoInterno(), codigo))
							 					 .findAny()
							 					 .orElse(null);
				if(muestra != null){
					importarAtributos(lista.get(i), lista.get(0), muestra, resultado, 
								      atributosEntidad, i, 1);
				}else{
					resultado.incMuestrasNoIdentificadas(codigo);
				}
			}
		}else{
			throw new Exception(ERROR_SIN_CAMPO_DE_MUESTRA);
		}
	}
	
	private void importarDesdeConjuntos(List<String[]> lista, MuestrasAtributosImpResultado resultado,
										List<Atributo> atributosEntidad) throws Exception{

		Map<String,ConjuntoMuestras> conjuntosMuestras = new HashMap<String,ConjuntoMuestras>(); 

		for(int i = 1; i < lista.size(); i++){

			String codigoConjunto = lista.get(i)[0].toLowerCase();
			if(!conjuntosMuestras.containsKey(codigoConjunto)){
				ConjuntoMuestras conjunto = conjuntoMuestrasRepository.findOneByCodigo(codigoConjunto);
				conjuntosMuestras.put(codigoConjunto, conjunto);
			}

			if(lista.get(i).length > 1){

				String codigoExt = lista.get(i)[1].toLowerCase();
			
				ConjuntoMuestras conjunto = conjuntosMuestras.get(codigoConjunto);

				if(conjunto != null){
					Muestra muestra = null;
					if(StringUtils.equalsIgnoreCase(lista.get(0)[1],"codigoExterno")){
						muestra = conjunto.getMuestras().stream()
													 .filter(x -> StringUtils.equalsIgnoreCase(x.getCodigoExterno(), codigoExt))
								 					 .findAny()
								 					 .orElse(null);
					}else if(StringUtils.equalsIgnoreCase(lista.get(0)[1],"codigoInterno")){
						muestra = conjunto.getMuestras().stream()
													 .filter(x -> StringUtils.equalsIgnoreCase(x.getCodigoInterno(), codigoExt))
								 					 .findAny()
								 					 .orElse(null);
					}else if(StringUtils.equalsIgnoreCase(lista.get(0)[1],"id")){
						Long id = new Long(codigoExt);
						muestra = conjunto.getMuestras().stream()
													 .filter(x -> x.getId().equals(id))
								 					 .findAny()
								 					 .orElse(null);
					}else{
						throw new Exception(ERROR_SIN_CAMPO_DE_MUESTRA);
					}

					if(muestra != null){
						importarAtributos(lista.get(i), lista.get(0), muestra, resultado, 
									      atributosEntidad, i, 2);
					}else{
						resultado.incMuestrasNoIdentificadas(codigoConjunto+"|"+codigoExt);
					}
				}else{
					resultado.incConjuntosMuestrasNoIdentificados(codigoConjunto);
				}
			}
		}

	}


	private void importarAtributos(String[] linea, String[] header, Muestra muestra, 
								MuestrasAtributosImpResultado resultado, 
								List<Atributo> atributosEntidad, int nroLinea, int colInicio){

		List<MuestraAtributo> atributosMuestra = muestraAtributoRepository.findByMuestra(muestra.getId());

		Map<Long, MuestraAtributo> mapAtributosMuestras = atributosMuestra.stream().collect(
                							Collectors.toMap(m -> m.getAtributo().getId(), x -> x));

		resultado.incMuestrasProcesadas ();

		for(int j = colInicio; j < linea.length; j++){
			String valor = linea[j].trim();
			String nomAtributo = header[j];

			Atributo atributo = atributosEntidad.stream()
								 .filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), nomAtributo))
			 					 .findAny()
			 					 .orElse(null);

			if(atributo != null){
				if(atributo.getTipo().formatoValido(valor)){
					valor = atributo.getTipo().convertir(valor);

					MuestraAtributo ma = mapAtributosMuestras.get(atributo.getId());

					if(atributo.getTipo().iguales(atributo.getValor(), valor)){
						if(ma != null)
							muestraAtributoRepository.delete(ma.getId());
					}else{
						
						if(ma != null){
							ma.setValor(valor);
							ma.setMuestra(muestra);
						}else{
							ma = new MuestraAtributo(muestra, atributo, valor);
						}

						muestraAtributoRepository.save(ma);
					}

					resultado.incAtributosValidos();	
				}else{
					resultado.incAtributosInvalidos(nomAtributo+"/"+(nroLinea+1));	
				}
			}else{
				resultado.incAtributosNoIdentificados(nomAtributo);				
			}
		}
	}

}
