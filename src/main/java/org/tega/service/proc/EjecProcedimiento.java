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
import org.apache.commons.io.FileUtils;
import java.nio.charset.StandardCharsets;

import org.tega.service.*;
import org.tega.repository.*;
import org.tega.domain.*;
import org.tega.domain.enumeration.*;

import org.springframework.scheduling.annotation.Async;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EjecProcedimiento {
	
	private static final String SEP_COL = "\t";
	private static final String SEP_FILA = "\n";
	private static final String EXP_REPLA = "[\t\n]";
	private static final String SEP_FILE = File.separator;

	private static final String CATEGORIA_TEGA = "TEGA";

	private static final String ARCHIVO_LOG = "tega.log";
	private static final String ARCHIVO_PARAMETROS = "parametros.tsv";
	private static final String ARCHIVO_CANTIDADES = "cantidades.tsv";
	private static final String ARCHIVO_CONJUNTOS_MUESTRAS = "conjuntos_muestras.tsv";
	private static final String ARCHIVO_MUESTRAS = "muestras.tsv";
	private static final String ARCHIVO_LOCI = "loci.tsv";
	private static final String ARCHIVO_AT_MUESTRAS = "muestras_atributos.tsv";
	private static final String ARCHIVO_AT_CONJUNTOS_MUESTRAS = "conjuntos_muestras_atributos.tsv";
	private static final String ARCHIVO_AT_LOCI = "loci_atributos.tsv";

	private static final String ERROR_SIN_EJECUCION = "error.sinProcedimientoEjec";

	private final Logger log = LoggerFactory.getLogger(EjecProcedimiento.class);

	@Inject
	private ParametroTegaService parametroTegaService;

	@Inject
	private ParametroProcedimientoRepository parametroProcedimientoRepository;

	@Inject
	private ProcedimientoRepository procedimientoRepository;

	@Inject
	private ArchivoService archivoService;

	@Inject
	private AnalisisGenotipoRepository analisisGenotipoRepository;

	@Inject
	private ConjuntoMuestrasRepository conjuntoMuestrasRepository;

	@Inject
	private MuestraRepository muestraRepository;

	@Inject
	private AleloRepository aleloRepository;

	@Inject
	private LocusRepository locusRepository;

	@Inject
	private ParametroTegaRepository parametroTegaRepository;

	@Inject
	private MuestraAtributoRepository muestraAtributoRepository;

	@Inject
	private EjecucionRepository ejecucionRepository;

	@Inject
	private ExpGenotipos expGenotipos;

	@Inject
	private ExpGenepop expGenepop;

	@Inject
	private ExpStructure expStructure;

	@Inject
    private AtributoService atributoService;


	public String verLog(Long idAnalisis) throws Exception{
	
		List<Ejecucion> ejes = ejecucionRepository.findByAnalisisGenotipoEnEjecucion(idAnalisis);

		if(ejes.isEmpty())
			return ERROR_SIN_EJECUCION;

		Ejecucion e = ejes.get(ejes.size()-1);
		log.debug(e.getId().toString());
		String logCmd = ejecutar(idAnalisis, 
								e.getProcedimiento().getId(), 
								e.getProcedimiento().getComandoLog(), 
								e.getDireccion());		
		
		return logCmd;
	}


	@Async
	public void ejecutar(ParametrosEjecucionProcedimiento p) throws Exception{

		log.debug("EjecProcedimiento - Inicio: {}",p.getIdAnalisis());

		Procedimiento procedimiento = procedimientoRepository.findOne(p.getIdProcedimiento());	
		analisisGenotipoRepository.setEstado(p.getIdAnalisis(), procedimiento, EstadoAnalisisGenotipo.EJECUTANDO);

		AnalisisGenotipo ag = analisisGenotipoRepository.findOne(p.getIdAnalisis());

		File dirDestino = null;
		Ejecucion eje = null;
		try{
			
			eje = new Ejecucion();
			eje.setInicio(LocalDateTime.now());
			eje.setAnalisisGenotipo(ag);
			eje.setProcedimiento(procedimiento);

			Set<ParametroEjecucion> pe = new HashSet<>();
			for(CategoriaParametroProcedimiento c : p.getCategorias())
				for(ParametroProcedimiento pa : c.getParametros())
					pe.add(new ParametroEjecucion(eje, pa, pa.getValor()));

			eje.setParametros(pe);

			eje = ejecucionRepository.save(eje);

			String nombreCarpeta = procedimiento.getCodigo().trim().replaceAll("[^a-zA-Z0-9\\.\\-]","_");
			String pathAnalisis = archivoService.getPathArchivosEntidades(ArchivosEntidades.ANALISIS_GENOTIPO, p.getIdAnalisis());
			dirDestino = archivoService.crearCarpeta(pathAnalisis+SEP_FILE+nombreCarpeta+"_"+eje.getId());
			eje.setDireccion(dirDestino.getPath());
			eje = ejecucionRepository.save(eje);

			String pathDestino = dirDestino.getPath()+SEP_FILE;

			exportarParametros(p, procedimiento, pathDestino);

			List<Alelo> alelos = aleloRepository.findByAnalisisGenotipo(ag.getId());
			List<ConjuntoMuestras> conjuntosMuestras = conjuntoMuestrasRepository.findByAnalisisGenotipo(ag.getId());
			List<Locus> loci = locusRepository.findByAnalisisGenotipo(ag.getId());

			Set<Long> idsConjuntosMuestras = conjuntosMuestras.stream().map(ConjuntoMuestras::getId).collect(Collectors.toSet());
			Set<Long> idsLoci = loci.stream().map(Locus::getId).collect(Collectors.toSet());
			Set<Long> idsMuestras = muestraRepository.findIdsByAnalisisGenotipo(ag.getId());

			if(Boolean.TRUE.equals(procedimiento.getExportarGenotipos()))
				expGenotipos.exportar(ag, pathDestino, alelos, loci, conjuntosMuestras);
 
			if(Boolean.TRUE.equals(procedimiento.getExportarGenotiposGenepop()))
				expGenepop.exportar(ag, pathDestino, alelos, loci, conjuntosMuestras);

			if(Boolean.TRUE.equals(procedimiento.getExportarGenotiposStructure()))
				expStructure.exportar(ag,pathDestino, alelos, loci, conjuntosMuestras);

			if(Boolean.TRUE.equals(procedimiento.getExportarCantidades()))
				exportarCantidades(pathDestino, alelos, loci, conjuntosMuestras);

			if(Boolean.TRUE.equals(procedimiento.getExportarConjuntosMuestras()))
				exportarConjuntosMuestras(pathDestino, conjuntosMuestras);

			if(Boolean.TRUE.equals(procedimiento.getExportarMuestras()))
				exportarMuestras(ag, pathDestino);

			if(Boolean.TRUE.equals(procedimiento.getExportarLoci()))
				exportarLoci(loci, pathDestino);

			if(Boolean.TRUE.equals(procedimiento.getExportarMuestrasAtributos()))
				atributoService.exportar(idsMuestras,
									  muestraAtributoRepository.findByAnalisisGenotipo(ag.getId()),
									  Entidad.MUESTRA, new File(pathDestino+ARCHIVO_AT_MUESTRAS));

			if(Boolean.TRUE.equals(procedimiento.getExportarConjuntosMuestrasAtributos()))
				atributoService.exportar(idsConjuntosMuestras, 
									  conjuntoMuestrasRepository.findAtributosByIdsObj(idsConjuntosMuestras),
									  Entidad.CONJUNTO_MUESTRAS, 
									  new File(pathDestino+ARCHIVO_AT_CONJUNTOS_MUESTRAS));

			if(Boolean.TRUE.equals(procedimiento.getExportarLociAtributos()))
				atributoService.exportar(idsLoci, 
									  locusRepository.findAtributosByIdsObj(idsLoci),
									  Entidad.LOCUS, 
									  new File(pathDestino+ARCHIVO_AT_LOCI));

			String logCmd = ejecutar(p.getIdAnalisis(), 
									procedimiento.getId(), 
									procedimiento.getComandoEje(), 
									dirDestino.getPath());

			FileUtils.writeStringToFile(new File(pathDestino+ARCHIVO_LOG),
										logCmd, StandardCharsets.UTF_8);

		
		}catch(Exception e){
			log.error("EjecProcedimiento",e);
		}finally{
			finEjecucion(p.getIdAnalisis(), eje, dirDestino);
		}

		log.debug("EjecProcedimiento - Fin: {}",p.getIdAnalisis());
        
	}

	@Transactional
	private void finEjecucion(Long idAnalisis, Ejecucion eje, File dirDestino){
		
		analisisGenotipoRepository.setEstado(idAnalisis, null, EstadoAnalisisGenotipo.DISPONIBLE);

		if(eje != null && eje.getId() != null){
			eje.setFin(LocalDateTime.now());

			eje.setArchivos(new HashSet<Archivo>());
			for(File f : dirDestino.listFiles()){
				Archivo archivo = new Archivo();
				archivo.setDireccion(f.getPath());
				archivo.setDetalle(f.getName());
				eje.getArchivos().add(archivo);
			}

			ejecucionRepository.save(eje);
		}

	}

	private void exportarMuestras(AnalisisGenotipo ag, String pathDestino) throws Exception{

		List<Muestra> muestras = muestraRepository.findByAnalisisGenotipoEager(ag.getId());

		StringBuilder cadena  = new StringBuilder();

		cadena.append("id"						+SEP_COL+
					  "codigoInterno"			+SEP_COL+
					  "codigoExterno"			+SEP_COL+
 					  "fechaRecoleccion"		+SEP_COL+
					  "sexo"					+SEP_COL+
					  "latitud"					+SEP_COL+
					  "longitud"				+SEP_COL+
					  "ubicacion"				+SEP_COL+
					  "detalle"					+SEP_COL+
					  "especie_codigo"			+SEP_COL+
					  "tejido_codigo"			+SEP_COL+
					  "region_codigo"			+SEP_COL+
					  "localidad_codigo"		+SEP_COL+
					  "profesional_codigo"		+SEP_COL+
					  "institucion_codigo"		+SEP_COL+
					  "modoRecoleccion_codigo"	+SEP_FILA);

		for(Muestra c : muestras){
			cadena.append(c.getId()	+SEP_COL);
			cadena.append(expStr(c.getCodigoInterno()) 		+SEP_COL);
			cadena.append(expStr(c.getCodigoExterno())		+SEP_COL);
			cadena.append(expStr(c.getFechaRecoleccion())	+SEP_COL);
			cadena.append(expStr(c.getSexo())				+SEP_COL);
			cadena.append(expStr(c.getLatitud())			+SEP_COL);
			cadena.append(expStr(c.getLongitud())			+SEP_COL);
			cadena.append(expStr(c.getUbicacion())			+SEP_COL);
			cadena.append(expStr(c.getDetalle())			+SEP_COL);
			cadena.append(expStr(c.getEspecie() == null ? "" : c.getEspecie().getCodigo())		+SEP_COL);
			cadena.append(expStr(c.getTejido() == null ? "" : c.getTejido().getCodigo())		+SEP_COL);
			cadena.append(expStr(c.getRegion() == null ? "" : c.getRegion().getCodigo())		+SEP_COL);
			cadena.append(expStr(c.getLocalidad() == null ? "" : c.getLocalidad().getCodigo())	+SEP_COL);
			cadena.append(expStr(c.getProfesional() == null ? "" : c.getProfesional().getCodigo())+SEP_COL);
			cadena.append(expStr(c.getInstitucion() == null ? "" : c.getInstitucion().getCodigo())+SEP_COL);
			cadena.append(expStr(c.getModoRecoleccion() == null ? "" : c.getModoRecoleccion().getCodigo())+SEP_FILA);
		}

		FileUtils.writeStringToFile(new File(pathDestino+ARCHIVO_MUESTRAS),
									cadena.toString(), StandardCharsets.UTF_8);

	}

	private void exportarLoci(List<Locus> loci, String pathDestino) throws Exception{

		StringBuilder cadena  = new StringBuilder();

		cadena.append("id"		+SEP_COL+
					  "codigo"	+SEP_COL+
					  "ploidia"	+SEP_COL+
					  "detalle"	+SEP_FILA);

		for(Locus c : loci){
			cadena.append(c.getId()		+SEP_COL+
					  	  expStr(c.getCodigo())	+SEP_COL+
					  	  expStr(c.getPloidia())+SEP_COL+
					  	  expStr(c.getDetalle())+SEP_FILA);
		}

		FileUtils.writeStringToFile(new File(pathDestino+ARCHIVO_LOCI),
									cadena.toString(), StandardCharsets.UTF_8);

	}

	private void exportarCantidades(String pathDestino,
									 List<Alelo> alelos,
									 List<Locus> loci,
									 List<ConjuntoMuestras> conjuntosMuestras) throws Exception{

		Integer cantMuestras = conjuntosMuestras.stream()
									.map(c -> c.getMuestras() == null ? 0 : c.getMuestras().size())
									.reduce(0, Integer::sum);

		String cadena = "entidad" +SEP_COL+
						"cantidad"+SEP_FILA;

		cadena += "conjuntos_muestras"+SEP_COL+conjuntosMuestras.size()+SEP_FILA+
			      "muestras"		  +SEP_COL+cantMuestras+SEP_FILA+
			      "loci"			  +SEP_COL+loci.size()+SEP_FILA+
			      "alelos"			  +SEP_COL+alelos.size();

		FileUtils.writeStringToFile(new File(pathDestino+ARCHIVO_CANTIDADES),
									cadena, StandardCharsets.UTF_8);
	}

	private void exportarConjuntosMuestras(String pathDestino,
								   List<ConjuntoMuestras> conjuntosMuestras) throws Exception{

		StringBuilder cadena  = new StringBuilder();

		cadena.append("id"		+SEP_COL+
					  "codigo"	+SEP_COL+
					  "detalle"	+SEP_FILA);

		for(ConjuntoMuestras c : conjuntosMuestras){
			cadena.append(c.getId()		+SEP_COL+
					  	  expStr(c.getCodigo())	+SEP_COL+
					  	  expStr(c.getDetalle())+SEP_FILA);
		}

		FileUtils.writeStringToFile(new File(pathDestino+ARCHIVO_CONJUNTOS_MUESTRAS),
									cadena.toString(), StandardCharsets.UTF_8);
	}


	private void exportarParametros(ParametrosEjecucionProcedimiento p, 
									Procedimiento procedimiento,
									String pathDestino) throws Exception{
		
		for(CategoriaParametroProcedimiento c : p.getCategorias()){
			List<ParametroProcedimiento> params = parametroProcedimientoRepository
													.findByProcedimientoCategoriaNoEditable(p.getIdProcedimiento(), 
																						    c.getId());
			c.getParametros().addAll(params);
		}

		StringBuilder parametros = new StringBuilder();

		parametros.append("categoria"	+SEP_COL+
						  "parametro"	+SEP_COL+
						  "tipo"		+SEP_COL+
						  "valor"		+SEP_FILA);

		for(CategoriaParametroProcedimiento c : p.getCategorias()){
			for(ParametroProcedimiento pa : c.getParametros()){

				parametros.append(expStr(c.getCodigo())	+SEP_COL+
								  expStr(pa.getCodigo())+SEP_COL+
								  expStr(pa.getTipo())	+SEP_COL+
								  expStr(pa.getValor())	+SEP_FILA);

			}
		}

		if(Boolean.TRUE.equals(procedimiento.getExportarGenotipos()) || 
		   Boolean.TRUE.equals(procedimiento.getExportarGenotiposStructure())){
			ParametroTega pa = parametroTegaRepository.buscarPorCodigo("EXP_PROC_VALOR_NULO");
			parametros.append(CATEGORIA_TEGA	+SEP_COL+
							  expStr(pa.getCodigo())	+SEP_COL+
							  expStr(pa.getTipo())		+SEP_COL+
							  expStr(pa.getValor())		+SEP_FILA);	
		}	

		if(Boolean.TRUE.equals(procedimiento.getExportarGenotipos())){
			ParametroTega pa = parametroTegaRepository.buscarPorCodigo("EXP_PROC_SEP_ALELOS");
			parametros.append(CATEGORIA_TEGA	+SEP_COL+
							  expStr(pa.getCodigo())	+SEP_COL+
							  expStr(pa.getTipo())		+SEP_COL+
							  expStr(pa.getValor())		+SEP_FILA);	
		}	

		if(parametros.length() > 0)
			FileUtils.writeStringToFile(new File(pathDestino+ARCHIVO_PARAMETROS),
									    parametros.toString(), StandardCharsets.UTF_8);		
	}

	private String expStr(Object c){
		if(c == null)
			return "";

		return c.toString().trim().replaceAll(EXP_REPLA," ");
	}


	private String ejecutar(Long idAnalisis, Long idProc, String comandoProc, String pathDest) throws Exception{

		String pathProcAbs = archivoService.getPathAbsolutoArchivosEntidades(ArchivosEntidades.PROCEDIMIENTO, idProc);

		String comandoEje = parametroTegaService.getString("COMANDO_PROCEDIMIENTOS");
		String usuario = parametroTegaService.getString("USUARIO_PROCEDIMIENTOS");
		String clave = parametroTegaService.getString("CLAVE_USUARIO_PROCEDIMIENTOS");

		String logCmd = "";

		try{
			logCmd = EjecComando.ejecutarUnificarOutErr(comandoEje, 
														 comandoProc.trim(),
														 usuario,
														 clave,
														 idAnalisis.toString(),
														 pathProcAbs, 
														 (new File(pathDest)).getAbsolutePath());
		}catch(Exception ex){
			logCmd = ex.getMessage();
			log.error("EjecProcedimiento",ex);
		}

		return logCmd;
	}

}
