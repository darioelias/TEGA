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

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

@Service
public class MuestrasImpService {

	private final Logger log = LoggerFactory.getLogger(MuestrasImpService.class);

	@Inject
    private MuestraRepository muestraRepository;

	@Inject
    private ProyectoRepository proyectoRepository;

	@Inject
    private RegionRepository regionRepository;

	@Inject
    private EspecieRepository especieRepository;

	@Inject
    private LocalidadRepository localidadRepository;	

	@Inject
    private ProfesionalRepository profesionalRepository;	

	@Inject
    private InstitucionRepository institucionRepository;	

	@Inject
    private TejidoRepository tejidoRepository;	

	@Inject
    private ModoRecoleccionRepository modoRecoleccionRepository;	

	@Inject
    private ConjuntoMuestrasRepository conjuntoMuestrasRepository;

	@Inject
    private PaisRepository paisRepository;	

	@Inject
    private ProvinciaRepository provinciaRepository;	

	@Transactional
	public MuestraImpResultado importar(List<MuestraImp> lista){

		LocalDateTime currentDateTime = LocalDateTime.now();

		MuestraImpResultado resultado = new MuestraImpResultado();

		List<Proyecto> proyectos 		= proyectoRepository.findAll();
		List<Region> regiones			= regionRepository.findAll();
		List<Especie> especies 			= especieRepository.findAll();
		List<Localidad> localidades 	= localidadRepository.findAll();
		List<Profesional> profesionales = profesionalRepository.findAll();
		List<Institucion> instituciones = institucionRepository.findAll();
		List<Tejido> tejidos 			= tejidoRepository.findAll();
		List<Pais> paises 				= paisRepository.findAll();
		List<Provincia> provincias 		= provinciaRepository.findAll();
		List<ModoRecoleccion> modosRecoleccion 			 = modoRecoleccionRepository.findAll();
		List<ConjuntoMuestras> conjuntosMuestras = conjuntoMuestrasRepository.findAll();

		for(MuestraImp muestraImp : lista){

			Muestra muestra = null;

			if(muestraImp.getId() != null){
				muestra = muestraRepository.findOne(muestraImp.getId());
			}else if(muestraImp.getCodigoInterno() != null){
				List<Muestra> listAux = muestraRepository.findByCodigoInternoIgnoreCase(muestraImp.getCodigoInterno());
				if(listAux.size() > 0)
					muestra = listAux.get(0);
			}else if(muestraImp.getCodigoExterno() != null && muestraImp.getConjuntoMuestras() != null){
				List<Muestra> listAux = muestraRepository.findByCodigoExternoConjunto(muestraImp.getCodigoExterno(), muestraImp.getConjuntoMuestras());
				if(listAux.size() > 0)
					muestra = listAux.get(0);
			}


			if(muestra == null){
				muestra = new Muestra();

				if(!StringUtils.isBlank(muestraImp.getCodigoInterno()))
					muestra.setCodigoInterno(muestraImp.getCodigoInterno());

				resultado.incMuestrasCreadas();
			}else{
				resultado.incMuestrasModificadas();
			}

			if(!StringUtils.isBlank(muestraImp.getProyecto())){
				Proyecto proyecto = proyectos.stream()
											 .filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getProyecto()))
						 					 .findAny()
						 					 .orElse(null);
				if(proyecto == null){
						proyecto = proyectos.stream()
									 		 .filter(x -> StringUtils.equalsIgnoreCase(x.getDetalle(), muestraImp.getProyecto()))
				 					 		 .findAny()
				 					 		 .orElse(null);		

						if(proyecto == null){
							proyecto = proyectoRepository.save(new Proyecto(muestraImp.getProyecto(), muestraImp.getProyecto()));
							proyectos.add(proyecto);	
							resultado.incProyectosCreados();
						}	
				}

				if(muestra.getId() != null){
					List<Proyecto> proyectosMuestra = proyectoRepository.findByMuestra(muestra.getId());
					muestra.setProyectos(new HashSet<Proyecto>(proyectosMuestra));
				}

				muestra.getProyectos().add(proyecto);
			}

			if(!StringUtils.isBlank(muestraImp.getRegion())){
				Region region = regiones.stream()
							 .filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getRegion()))
		 					 .findAny()
		 					 .orElse(null);

				if(region == null){
						region = regiones.stream()
						 		 .filter(x -> StringUtils.equalsIgnoreCase(x.getNombre(), muestraImp.getRegion()))
	 					 		 .findAny()
	 					 		 .orElse(null);			

						if(region == null){
							region = regionRepository.save(new Region(muestraImp.getRegion(), muestraImp.getRegion()));
							regiones.add(region);	
							resultado.incRegionesCreadas();
						}
				}

				muestra.setRegion(region);
			}
			

			if(!StringUtils.isBlank(muestraImp.getEspecie())){
				Especie especie = especies.stream()
							 			.filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getEspecie()))
		 					 			.findAny()
		 					 			.orElse(null);

				if(especie == null){
						especie = especies.stream()
						 		 	.filter(x -> StringUtils.equalsIgnoreCase(x.getNombre(), muestraImp.getEspecie()))
	 					 		 	.findAny()
	 					 		 	.orElse(null);		

						if(especie == null){
							especie = especieRepository.save(new Especie(muestraImp.getEspecie(), muestraImp.getEspecie()));
							especies.add(especie);	
							resultado.incEspeciesCreadas();
						}	
				}

				muestra.setEspecie(especie);
			}


			if(!StringUtils.isBlank(muestraImp.getLocalidad())){
				Localidad localidad = localidades.stream()
							 			.filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getLocalidad()))
		 					 			.findAny()
		 					 			.orElse(null);

				if(localidad == null){
						localidad = localidades.stream()
						 		 	.filter(x -> StringUtils.equalsIgnoreCase(x.getNombre(), muestraImp.getLocalidad()))
	 					 		 	.findAny()
	 					 		 	.orElse(null);		

						if(localidad == null){

							Provincia provincia = provincias.stream()
							 					.filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getProvincia()))
		 					 					.findAny()
		 					 					.orElse(null);

							if(provincia == null){
								provincia = provincias.stream()
						 		 				.filter(x -> StringUtils.equalsIgnoreCase(x.getNombre(), muestraImp.getProvincia()))
	 					 		 				.findAny()
	 					 		 				.orElse(null);	

								if(provincia == null){
									
									Pais pais = paises.stream()
									 					.filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getPais()))
				 					 					.findAny()
				 					 					.orElse(null);

									if(pais == null){
										pais = paises.stream()
						 		 				.filter(x -> StringUtils.equalsIgnoreCase(x.getNombre(), muestraImp.getPais()))
	 					 		 				.findAny()
	 					 		 				.orElse(null);	

										if(pais == null){
											pais = paisRepository.save(new Pais(muestraImp.getPais(), muestraImp.getPais()));
											paises.add(pais);
											resultado.incPaisesCreados();
										}
									}

									provincia = new Provincia(muestraImp.getProvincia(),muestraImp.getProvincia());
									provincia.setPais(pais);
									provincia = provinciaRepository.save(provincia);
									provincias.add(provincia);
									resultado.incProvinciasCreadas();

								}

							}

							localidad = new Localidad(muestraImp.getLocalidad(), muestraImp.getLocalidad());
							localidad.setProvincia(provincia);
							localidad = localidadRepository.save(localidad);
							localidades.add(localidad);	
							resultado.incLocalidadesCreadas();
						}	
				}

				muestra.setLocalidad(localidad);
			}

			if(!StringUtils.isBlank(muestraImp.getProfesional())){
				Profesional profesional = profesionales.stream()
							 			.filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getProfesional()))
		 					 			.findAny()
		 					 			.orElse(null);

				if(profesional == null){
						profesional = profesionales.stream()
						 		 	.filter(x -> StringUtils.equalsIgnoreCase(x.getNombre(), muestraImp.getProfesional()))
	 					 		 	.findAny()
	 					 		 	.orElse(null);		

						if(profesional == null){
							profesional = profesionalRepository.save(new Profesional(muestraImp.getProfesional(), muestraImp.getProfesional()));
							profesionales.add(profesional);	
							resultado.incProfesionalesCreados();
						}	
				}

				muestra.setProfesional(profesional);
			}

			if(!StringUtils.isBlank(muestraImp.getInstitucion())){
				Institucion institucion = instituciones.stream()
							 			.filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getInstitucion()))
		 					 			.findAny()
		 					 			.orElse(null);

				if(institucion == null){
						institucion = instituciones.stream()
						 		 	.filter(x -> StringUtils.equalsIgnoreCase(x.getNombre(), muestraImp.getInstitucion()))
	 					 		 	.findAny()
	 					 		 	.orElse(null);		

						if(institucion == null){
							institucion = institucionRepository.save(new Institucion(muestraImp.getInstitucion(), muestraImp.getInstitucion()));
							instituciones.add(institucion);	
							resultado.incInstitucionesCreadas();
						}	
				}

				muestra.setInstitucion(institucion);
			}

			if(!StringUtils.isBlank(muestraImp.getTejido())){
				Tejido tejido = tejidos.stream()
							 			.filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getTejido()))
		 					 			.findAny()
		 					 			.orElse(null);

				if(tejido == null){
						tejido = tejidos.stream()
						 		 	.filter(x -> StringUtils.equalsIgnoreCase(x.getNombre(), muestraImp.getTejido()))
	 					 		 	.findAny()
	 					 		 	.orElse(null);		

						if(tejido == null){
							tejido = tejidoRepository.save(new Tejido(muestraImp.getTejido(), muestraImp.getTejido()));
							tejidos.add(tejido);
							resultado.incTejidosCreados();	
						}	
				}

				muestra.setTejido(tejido);
			}


			if(!StringUtils.isBlank(muestraImp.getModoRecoleccion())){
				ModoRecoleccion modoRecoleccion = modosRecoleccion.stream()
							 			.filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getModoRecoleccion()))
		 					 			.findAny()
		 					 			.orElse(null);

				if(modoRecoleccion == null){
						modoRecoleccion = modosRecoleccion.stream()
						 		 	.filter(x -> StringUtils.equalsIgnoreCase(x.getNombre(), muestraImp.getModoRecoleccion()))
	 					 		 	.findAny()
	 					 		 	.orElse(null);		

						if(modoRecoleccion == null){
							modoRecoleccion = modoRecoleccionRepository.save(new ModoRecoleccion(muestraImp.getModoRecoleccion(), muestraImp.getModoRecoleccion()));
							modosRecoleccion.add(modoRecoleccion);	
							resultado.incModosRecoleccionCreados();
						}	
				}

				muestra.setModoRecoleccion(modoRecoleccion);
			}

			if(!StringUtils.isBlank(muestraImp.getConjuntoMuestras())){
				ConjuntoMuestras conjuntoMuestras = conjuntosMuestras.stream()
							 			.filter(x -> StringUtils.equalsIgnoreCase(x.getCodigo(), muestraImp.getConjuntoMuestras()))
		 					 			.findAny()
		 					 			.orElse(null);

				if(conjuntoMuestras == null){
						conjuntoMuestras = conjuntosMuestras.stream()
						 		 	.filter(x -> StringUtils.equalsIgnoreCase(x.getDetalle(), muestraImp.getConjuntoMuestras()))
	 					 		 	.findAny()
	 					 		 	.orElse(null);		

						if(conjuntoMuestras == null){
							conjuntoMuestras = conjuntoMuestrasRepository.save(new ConjuntoMuestras(muestraImp.getConjuntoMuestras(), muestraImp.getConjuntoMuestras()));
							conjuntosMuestras.add(conjuntoMuestras);	
							resultado.incConjuntoMuestrasCreados();
						}	
				}

				if(muestra.getId() != null){
					List<ConjuntoMuestras> conjuntosMuestrasExt = conjuntoMuestrasRepository.findByMuestra(muestra.getId());
					muestra.setConjuntosMuestras(new HashSet<ConjuntoMuestras>(conjuntosMuestrasExt));
				}

				muestra.getConjuntosMuestras().add(conjuntoMuestras);
			}


				
			if(muestraImp.getCodigoExterno() != null)
				muestra.setCodigoExterno(muestraImp.getCodigoExterno());

			if(muestraImp.getFechaRecoleccion() != null)
				muestra.setFechaRecoleccion(muestraImp.getFechaRecoleccion());

			if(muestraImp.getSexo() != null)
				muestra.setSexo(muestraImp.getSexo());

			if(muestraImp.getLatitud() != null)
				muestra.setLatitud(muestraImp.getLatitud());

			if(muestraImp.getLongitud() != null)
				muestra.setLongitud(muestraImp.getLongitud());

			if(muestraImp.getUbicacion() != null)
				muestra.setUbicacion(muestraImp.getUbicacion());

			if(muestraImp.getPublico() != null)
				muestra.setPublico(muestraImp.getPublico());

			if(muestraImp.getDetalle() != null)
				muestra.setDetalle(muestraImp.getDetalle());
			
//			if(muestra.getId() == null)
//				muestra.setDetalle((muestraImp.getDetalle() == null ? "" : muestraImp.getDetalle()) + " IMP.: "+currentDateTime);

			muestra = muestraRepository.save(muestra);

			if(StringUtils.isBlank(muestra.getCodigoInterno())){
	
				String codigoInterno = "";

				if(!StringUtils.isBlank(muestraImp.getConjuntoMuestras()))
					codigoInterno += muestraImp.getConjuntoMuestras();
				
				codigoInterno += "|";

				if(!StringUtils.isBlank(muestraImp.getCodigoExterno()))
					codigoInterno += muestraImp.getCodigoExterno();

				codigoInterno += "|";
				codigoInterno += muestra.getId();

				muestra.setCodigoInterno(codigoInterno);	
				muestra = muestraRepository.save(muestra);		
			}

		}

		return resultado;
	}


}
