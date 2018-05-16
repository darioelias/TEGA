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
import org.tega.domain.enumeration.Sexo;
import org.tega.repository.*;

import java.util.function.Predicate;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AlelosImpService {

	private static final String ERROR_SIN_CAMPO_DE_MUESTRA = "proyectoApp.muestra.importacion.error.sinCampoDeMuestra";

	@Inject
    private MuestraRepository muestraRepository;

	@Inject
    private ConjuntoMuestrasRepository conjuntoMuestrasRepository;

	@Inject
    private AleloRepository aleloRepository;	

	@Inject
    private LocusRepository locusRepository;

	@Transactional
	public AleloImpResultado importar(List<AleloImp> lista){

		AleloImpResultadoLista resultado = new AleloImpResultadoLista();

		List<Muestra> muestrasBD = null;
		List<Long> muestrasID = lista.stream()
									  .filter(a -> a.getIdMuestra() != null)
									  .map(AleloImp::getIdMuestra)
									  .distinct()
									  .collect(Collectors.toList());
		Map<Long, Muestra> mapMuestrasID = null;
		if(muestrasID.size() > 0){
			muestrasBD = muestraRepository.findByIds(muestrasID);
			mapMuestrasID = muestrasBD.stream().collect(Collectors.toMap(Muestra::getId, x -> x));
		}else{
			mapMuestrasID = new HashMap<Long, Muestra>();
		}

		List<String> muestrasCod = lista.stream()
										 .filter(a -> a.getCodigoInternoMuestra() != null)
										 .map(AleloImp::getCodigoInternoMuestra)
										 .distinct()
										 .collect(Collectors.toList());
		muestrasCod.replaceAll(String::toLowerCase);
		Map<String, Muestra> mapMuestrasCod = null;
		if(muestrasCod.size() > 0){
			muestrasBD = muestraRepository.findByCodigosInternos(muestrasCod);
			mapMuestrasCod = muestrasBD.stream().collect(Collectors.toMap(Muestra::getCodigoInterno, x -> x));
		}else{
			mapMuestrasCod = new HashMap<String, Muestra>();
		}

		List<Locus> lociBD = null;
		List<Long> lociID = lista.stream()
								  .filter(a -> a.getIdLocus() != null)
								  .map(AleloImp::getIdLocus)
								  .distinct()
								  .collect(Collectors.toList());
		Map<Long, Locus> mapLociID = null;
		if(lociID.size() > 0){
			lociBD = locusRepository.findByIds(lociID);
			mapLociID = lociBD.stream().collect(Collectors.toMap(Locus::getId, x -> x));
		}else{
			mapLociID = new HashMap<Long, Locus>();
		}

		List<String> lociCod = lista.stream()
									 .filter(a -> a.getCodigoLocus() != null)
									 .map(AleloImp::getCodigoLocus)
									 .distinct()
									 .collect(Collectors.toList());
		lociCod.replaceAll(String::toLowerCase);
		Map<String, Locus> mapLociCod = null;
		if(lociCod.size() > 0){
			lociBD = locusRepository.findByCodigos(lociCod);
			mapLociCod = lociBD.stream().collect(Collectors.toMap(Locus::getCodigo, x -> x));
		}else{
			mapLociCod = new HashMap<String, Locus>();					
		}

		Set<Long> lociCreadosSet = new HashSet<Long>();

		Integer nroLinea = 0;
		for(AleloImp aleloImp : lista){
				nroLinea++;
				Locus locus = null;
				Muestra muestra = null;
				
				if(aleloImp.getIdLocus() != null){
					locus = mapLociID.get(aleloImp.getIdLocus());
				}else{
					if(aleloImp.getCodigoLocus() != null){
						locus = mapLociCod.get(aleloImp.getCodigoLocus());
						if(locus == null && aleloImp.getIndice() != null){
							locus = new Locus(aleloImp.getCodigoLocus(), aleloImp.getCodigoLocus(), aleloImp.getIndice(), 
											  aleloImp.getPublico() == null ? false : aleloImp.getPublico());
							locus = locusRepository.save(locus);
							resultado.incLociCreados();
							mapLociCod.put(locus.getCodigo(),locus);
							mapLociID.put(locus.getId(),locus);
							lociCreadosSet.add(locus.getId());
						}
					}
				}

				if(aleloImp.getIdMuestra() != null){
					muestra = mapMuestrasID.get(aleloImp.getIdMuestra());
				}else{
					if(aleloImp.getCodigoInternoMuestra() != null){
						muestra = mapMuestrasCod.get(aleloImp.getCodigoInternoMuestra());
					}
				}

				if(muestra == null){
					resultado.incMuestrasNoIdentificadasLinea(nroLinea);
				}else if(locus == null){
						resultado.incLociNoIdentificadosLinea(nroLinea);
					}else{
						Alelo alelo = null;

						if(aleloImp.getId() != null){
							alelo = aleloRepository.findOne(aleloImp.getId());
						}else{
							if(aleloImp.getIndice() != null){
								List<Alelo> listAux = aleloRepository.findByMuestraLocusIndice(muestra.getId(), locus.getId(), aleloImp.getIndice());
								if(listAux.size() > 0){
									alelo = listAux.get(0);
								}else{
									aleloImp.setPublico(aleloImp.getPublico() == null ? false : aleloImp.getPublico());	
									aleloRepository.save(new Alelo(muestra,locus,aleloImp.getValor(),aleloImp.getIndice(),aleloImp.getPublico()));
									resultado.incAlelosCreados();
									if(aleloImp.getIndice() > locus.getPloidia()){
										locus.setPloidia(aleloImp.getIndice());
										locus = locusRepository.save(locus);
										mapLociCod.replace(locus.getCodigo(),locus);
										mapLociID.replace(locus.getId(),locus);
										if(!lociCreadosSet.contains(locus.getId()))
											resultado.incLociModificados();
									}							
								}
							}
						}

						if(alelo != null){
							alelo.setMuestra(muestra);
							alelo.setLocus(locus);

							if(aleloImp.getPublico() != null)
								alelo.setPublico(aleloImp.getPublico());

							if(aleloImp.getValor() != null)
								alelo.setValor(aleloImp.getValor());

							if(aleloImp.getIndice() != null)
								alelo.setIndice(aleloImp.getIndice());
						

							Boolean actualizar = true;
							List<Alelo> listAux = aleloRepository.findByMuestraLocusIndice(muestra.getId(), locus.getId(), alelo.getIndice());
							if(listAux.size() > 0){
								Alelo aleloAux = listAux.get(0);
								if(!aleloAux.equals(alelo)){
									resultado.incAlelosRepetidosLinea(nroLinea);
									actualizar = false;
								}
							}
			
							if(actualizar){

								if(alelo.getIndice() > locus.getPloidia()){
									locus.setPloidia(aleloImp.getIndice());
									locus = locusRepository.save(locus);
									mapLociCod.replace(locus.getCodigo(),locus);
									mapLociID.replace(locus.getId(),locus);
									if(!lociCreadosSet.contains(locus.getId()))
										resultado.incLociModificados();
								}	

								aleloRepository.save(alelo);
								resultado.incAlelosModificados();
							}

						}

				}
				

		}


		return resultado;

	}

	@Transactional
	public AleloImpResultado importar(List<String[]> lista, String nulo, Boolean publico)
			throws Exception{

		AleloImpResultadoMatriz resultado = new AleloImpResultadoMatriz();

		Map<String,Locus> lociImp = new HashMap<String,Locus>(); 
		Map<String,Locus> loci = new HashMap<String,Locus>(); 
		List<Long> lociID = new ArrayList<Long>();


		for(String s : lista.get(0)){

			if(!StringUtils.equalsIgnoreCase(s,"conjuntoMuestras") &&
			   !StringUtils.equalsIgnoreCase(s,"codigoExterno") &&
			   !StringUtils.equalsIgnoreCase(s,"codigoInterno") &&
			   !StringUtils.equalsIgnoreCase(s,"codigo") &&
			   !StringUtils.equalsIgnoreCase(s,"id")){

				int pos = s.lastIndexOf("_");

				if(pos > 0){

					String codigoOrig = s.substring(0, pos).trim();
					String codigo = codigoOrig.toLowerCase();

					try{
						String ploidiaS = s.substring(pos+1).trim();
						Integer ploidia = new Integer(ploidiaS);

						Locus l = lociImp.get(codigo);
						if(l != null){
							if(l.getPloidia() < ploidia)
								l.setPloidia(ploidia);
						}else{
							lociImp.put(codigo, new Locus(codigoOrig, codigoOrig, ploidia, publico));
						}
					}catch(Exception e){
						resultado.incLociNoIdentificados(s);
					}

				}else{
					resultado.incLociNoIdentificados(s);
				}
			}
		}


		List<Locus> lociBD = locusRepository.findByCodigos(new ArrayList<String>(lociImp.keySet()));

		for(String codigo : lociImp.keySet()){

			Locus locusImp = lociImp.get(codigo);
			Locus locusBD = lociBD.stream()
							 .filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), codigo))
		 					 .findAny()
		 					 .orElse(null);

			if(locusBD != null){

				if(locusBD.getPloidia() < locusImp.getPloidia()){
					locusBD.setPloidia(locusImp.getPloidia());
					locusBD = locusRepository.save(locusBD);
					resultado.incLociModificados();
				}

			}else{
				locusBD = locusRepository.save(locusImp);
				resultado.incLociCreados();
			}

			loci.put(locusBD.getCodigo().toLowerCase(), locusBD);
			lociID.add(locusBD.getId());
		}

		if(StringUtils.equalsIgnoreCase(lista.get(0)[0],"conjuntoMuestras")){
			importarDesdeConjuntos(lista, lociID, loci, resultado, publico, nulo);
		}else{
			importarDesdeMuestra(lista, lociID, loci, resultado, publico, nulo);
		}
		

		return resultado;
	}

	private void importarDesdeMuestra(List<String[]> lista, List<Long> lociID, Map<String,Locus> loci, 
									  AleloImpResultadoMatriz resultado, Boolean publico, String nulo)
			throws Exception{
		
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
					importarAlelos(lista.get(i), lista.get(0), muestra, lociID, loci, resultado, publico, nulo, 1);
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
					importarAlelos(lista.get(i), lista.get(0), muestra, lociID, loci, resultado, publico, nulo, 1);
				}else{
					resultado.incMuestrasNoIdentificadas(codigo);
				}
			}
		}else{
			throw new Exception(ERROR_SIN_CAMPO_DE_MUESTRA);
		}

//		else if(StringUtils.equalsIgnoreCase(lista.get(0)[0],"codigoExterno")){
//			List<String> muestrasCod = new ArrayList<String>();
//			for(int i = 1; i < lista.size(); i++){
//				muestrasCod.add(lista.get(i)[0].toLowerCase());
//			}

//			List<Muestra> muestras = muestraRepository.findByCodigosExternos(muestrasCod);

//			for(int i = 1; i < lista.size(); i++){
//				String codigo = lista.get(i)[0];
//				Muestra muestra = muestras.stream()
//												 .filter(x -> StringUtils.equalsIgnoreCase(x.getCodigoExterno(), codigo))
//							 					 .findAny()
//							 					 .orElse(null);
//				if(muestra != null){
//					importarAlelos(lista.get(i), lista.get(0), muestra, lociID, loci, resultado, publico, nulo, 1);
//				}else{
//					resultado.incMuestrasNoIdentificadas(codigo);
//				}
//			}
//		}
	}
	
	private void importarDesdeConjuntos(List<String[]> lista, List<Long> lociID, Map<String,Locus> loci,
									    AleloImpResultadoMatriz resultado, Boolean publico, String nulo)
			throws Exception{

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
						importarAlelos(lista.get(i), lista.get(0), muestra, lociID, loci, resultado, publico, nulo, 2);
					}else{
						resultado.incMuestrasNoIdentificadas(codigoConjunto+"|"+codigoExt);
					}
				}else{
					resultado.incConjuntosMuestrasNoIdentificados(codigoConjunto);
				}
			}
		}

	}


	private void importarAlelos(String[] linea, String[] header, Muestra muestra, 
							    List<Long> lociID, Map<String,Locus> loci, 
								AleloImpResultadoMatriz resultado, Boolean publico, String nulo, int colInicio){

		List<Alelo> alelos = aleloRepository.findByMuestraLoci(muestra.getId(),lociID);

		for(int j = colInicio; j < linea.length; j++){
			String valor = linea[j].trim();
			if(!StringUtils.equalsIgnoreCase(valor,nulo)){

				String campos = header[j];
				int pos = campos.lastIndexOf("_");
				if(pos > 0){

					boolean continuar = true;
					Integer indiceAux = null;
					String codigo = null;

					try{
						indiceAux = new Integer(campos.substring(pos+1));
						codigo = campos.substring(0,pos).toLowerCase();
					}catch(Exception e){
						continuar = false;
					}

					if(continuar){
						String codigoLocus = codigo;
						Integer indice = indiceAux;

						Locus locus = loci.get(codigoLocus);
						
						Alelo alelo = alelos.stream()
											 .filter(x -> x.getLocus().getId().equals(locus.getId()) && x.getIndice().equals(indice))
						 					 .findAny()
						 					 .orElse(null);

						if(alelo != null){
							alelo.setValor(valor);
							alelo.setPublico(publico);
							aleloRepository.save(alelo);
							resultado.incAlelosModificados();
						}else{							
							aleloRepository.save(new Alelo(muestra, locus, valor, indice, publico));
							resultado.incAlelosCreados();
						}	
					}
				}
			}else{
				resultado.incAlelosNulos();
			}
		}
	}

}
