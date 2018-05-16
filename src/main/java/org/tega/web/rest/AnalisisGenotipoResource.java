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


package org.tega.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.tega.domain.*;
import org.tega.domain.enumeration.*;

import org.tega.repository.AnalisisGenotipoRepository;
import org.tega.repository.EjecucionRepository;
import org.tega.repository.ArchivoRepository;

import org.tega.domain.enumeration.EstadoAnalisisGenotipo;
import org.tega.web.rest.util.HeaderUtil;
import org.tega.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.File;

import org.tega.service.util.MapperUtil;
import org.tega.repository.specifications.AnalisisGenotipoCriterio;
import org.tega.repository.specifications.AnalisisGenotipoSpecifications;

import org.tega.service.AtributoService;
import org.tega.service.ArchivosEntidades;
import org.tega.service.ArchivoService;

import java.io.IOException;

import org.tega.service.proc.*;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
import org.tega.security.SecurityUtils;
/**
 * REST controller for managing AnalisisGenotipo.
 */
@RestController
@RequestMapping("/api")
public class AnalisisGenotipoResource {

    private final Logger log = LoggerFactory.getLogger(AnalisisGenotipoResource.class);
        
    @Inject
    private AnalisisGenotipoRepository analisisGenotipoRepository;

    @Inject
    private EjecucionRepository ejecucionRepository;

    @Inject
    private ArchivoRepository archivoRepository;

	@Inject
	private ArchivoService archivoService;

	@Inject
	private EjecProcedimiento ejecProcedimiento;

	@Inject
    private AtributoService atributoService;

	private ResponseEntity validarEstadoDisponible(Long id){

		AnalisisGenotipo analisisBD = analisisGenotipoRepository.findOne(id);
		if(analisisBD.getEstado() != null)
			if(!analisisBD.getEstado().equals(EstadoAnalisisGenotipo.DISPONIBLE))
				return ResponseEntity.badRequest().headers(HeaderUtil.errorModelo("proyectoApp.analisisGenotipo.error.no-disponible")).body(null);
		
		return null;
	}

    /**
     * POST  /analisis-genotipos : Create a new analisisGenotipo.
     *
     * @param analisisGenotipo the analisisGenotipo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new analisisGenotipo, or with status 400 (Bad Request) if the analisisGenotipo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/analisis-genotipos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<AnalisisGenotipo> createAnalisisGenotipo(@Valid @RequestBody AnalisisGenotipo analisisGenotipo) throws URISyntaxException {
        log.debug("REST request to save AnalisisGenotipo : {}", analisisGenotipo);
        if (analisisGenotipo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("analisisGenotipo", "idexists", "A new analisisGenotipo cannot already have an ID")).body(null);
        }

		if(analisisGenotipo.getCodigo() != null){
			List<AnalisisGenotipo> p = analisisGenotipoRepository.findByCodigoIgnoreCase(analisisGenotipo.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		analisisGenotipo.setEstado(EstadoAnalisisGenotipo.DISPONIBLE);

		for(AnalisisGenotipoAtributo a : analisisGenotipo.getAtributos())
			a.setAnalisisGenotipo(analisisGenotipo);

        AnalisisGenotipo result = analisisGenotipoRepository.save(analisisGenotipo);
        return ResponseEntity.created(new URI("/api/analisis-genotipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("analisisGenotipo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /analisis-genotipos : Updates an existing analisisGenotipo.
     *
     * @param analisisGenotipo the analisisGenotipo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated analisisGenotipo,
     * or with status 400 (Bad Request) if the analisisGenotipo is not valid,
     * or with status 500 (Internal Server Error) if the analisisGenotipo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/analisis-genotipos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<AnalisisGenotipo> updateAnalisisGenotipo(@Valid @RequestBody AnalisisGenotipo analisisGenotipo) throws URISyntaxException {
        log.debug("REST request to update AnalisisGenotipo : {}", analisisGenotipo);
        if (analisisGenotipo.getId() == null) {
            return createAnalisisGenotipo(analisisGenotipo);
        }

		if(analisisGenotipo.getCodigo() != null){
			List<AnalisisGenotipo> p = analisisGenotipoRepository.findByCodigoModif(analisisGenotipo.getCodigo(), analisisGenotipo.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		ResponseEntity responseValidar = validarEstadoDisponible(analisisGenotipo.getId());
		if(responseValidar != null)
			return responseValidar;

		analisisGenotipo.setAtributos(atributoService.filtrarValoresPorDefecto(analisisGenotipo.getAtributos(), 
																	  		   Entidad.ANALISIS_GENOTIPO));

		analisisGenotipo.getAtributos().forEach(a -> a.setAnalisisGenotipo(analisisGenotipo));
		analisisGenotipo.getArchivos().forEach(a -> a.addAnalisisGenotipo(analisisGenotipo));

		for(Ejecucion e : analisisGenotipo.getEjecuciones()){
			if(e.getArchivos() == null){
				e.setArchivos(archivoRepository.findByEjecucion(e.getId()));
				e.getArchivos().forEach(a -> a.setEjecuciones(null));
			}
			e.getArchivos().forEach(a -> a.addEjecucion(e));

			e.setParametros(ejecucionRepository.findParametrosEjecucionUpdate(e.getId()));
			e.getParametros().forEach(p -> p.setEjecucion(e));
			
			e.setAnalisisGenotipo(analisisGenotipo);
		}

		List<Ejecucion> ejeBD = ejecucionRepository.findByAnalisisGenotipo(analisisGenotipo.getId());
		
        AnalisisGenotipo result = analisisGenotipoRepository.save(analisisGenotipo);

		List<Long> idsEje = analisisGenotipo.getEjecuciones().stream().map(Ejecucion::getId).collect(Collectors.toList());
		ejeBD.forEach(e -> {
			if(!idsEje.contains(e.getId()))
				archivoService.eliminarCarpeta(e.getDireccion());
		});

//		result.getArchivos().forEach(a -> a.setAnalisisGenotipos(null));

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("analisisGenotipo", analisisGenotipo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /analisis-genotipos : get all the analisisGenotipos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of analisisGenotipos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/analisis-genotipos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<AnalisisGenotipo>> getAllAnalisisGenotipos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of AnalisisGenotipos");
        Page<AnalisisGenotipo> page = analisisGenotipoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/analisis-genotipos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /analisis-genotipos/:id : get the "id" analisisGenotipo.
     *
     * @param id the id of the analisisGenotipo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the analisisGenotipo, or with status 404 (Not Found)
     */
    @GetMapping("/analisis-genotipos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<AnalisisGenotipo> getAnalisisGenotipo(@PathVariable Long id) {
        log.debug("REST request to get AnalisisGenotipo : {}", id);
        AnalisisGenotipo analisisGenotipo = analisisGenotipoRepository.findOneWithEagerRelationships(id);

		if(analisisGenotipo != null &&
		  !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) &&
		  !analisisGenotipo.getPublico())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return Optional.ofNullable(analisisGenotipo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /analisis-genotipos/:id : delete the "id" analisisGenotipo.
     *
     * @param id the id of the analisisGenotipo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/analisis-genotipos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteAnalisisGenotipo(@PathVariable Long id) throws IOException{
        log.debug("REST request to delete AnalisisGenotipo : {}", id);

		ResponseEntity responseValidar = validarEstadoDisponible(id);
		if(responseValidar != null)
			return responseValidar;

        analisisGenotipoRepository.delete(id);
		archivoService.eliminarCarpeta(ArchivosEntidades.ANALISIS_GENOTIPO, id);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("analisisGenotipo", id.toString())).build();
    }

	@PutMapping("/analisis-genotipos-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids) throws IOException{
        log.debug("REST request to analisis-genotipos-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	analisisGenotipoRepository.deleteByIdIn(ids);
			archivoService.eliminarCarpetas(ArchivosEntidades.ANALISIS_GENOTIPO, ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("analisisGenotipo", ids.size())).build();
    }

	private Page<AnalisisGenotipo> procesarQueryFiltered(Pageable pageable, String cadena)
			throws IOException{
				AnalisisGenotipoCriterio criterio =  MapperUtil.getObject(cadena, AnalisisGenotipoCriterio.class);

		if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			criterio.setPublico(true);

		return analisisGenotipoRepository.findAll(AnalisisGenotipoSpecifications.findByCriteria(criterio),pageable);

	}

	@GetMapping("/analisis-genotipos-filtered")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<AnalisisGenotipo>> getAnalisisGenotipoQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of Analisis-Genotipos-Filtered");

		Page<AnalisisGenotipo> page = procesarQueryFiltered(pageable, cadena);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/analisis-genotipos-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


	@GetMapping("/analisis-genotipos-ejecutar-procedimiento")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> ejecutarProcedimiento(@RequestParam(value="parametros") String parametros) 
		throws Exception{
		log.debug("REST request to get a page of analisis-genotipos-ejecutar-procedimiento");

		ParametrosEjecucionProcedimiento p = MapperUtil.getObject(parametros, ParametrosEjecucionProcedimiento.class);

		ResponseEntity responseValidar = validarEstadoDisponible(p.getIdAnalisis());
		if(responseValidar != null)
			return responseValidar;

		ejecProcedimiento.ejecutar(p);

		return ResponseEntity.ok().build();
    }

	@GetMapping(value="/analisis-genotipos-ver-log-procedimiento",produces="text/plain")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<String> verLogProcedimiento(@RequestParam(value="id") Long idAnalisis){
		log.debug("REST request to get a page of analisis-genotipos-ver-log-procedimiento");

		ResponseEntity<String> respuesta = null;

		try{
			
			respuesta = new ResponseEntity<>(ejecProcedimiento.verLog(idAnalisis), HttpStatus.OK);

		}catch(Exception e){
			log.error(e.toString());
			respuesta = ResponseEntity.badRequest().headers(HeaderUtil.error(e.toString())).body(null);
		}

		return respuesta;
    }

	@GetMapping("/analisis-genotipos-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<AnalisisGenotipo>> getAnalisisGenotipoItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of analisis-genotipos-items");

		AnalisisGenotipoCriterio criterio =  MapperUtil.getObject(cadena, AnalisisGenotipoCriterio.class);

		List<AnalisisGenotipo> lista = analisisGenotipoRepository.findAll(AnalisisGenotipoSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/analisis-genotipos-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<AnalisisGenotipo>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of analisis-genotipos-selector");

		List<AnalisisGenotipo> lista = analisisGenotipoRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/analisis-genotipos-selectorMin")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of analisis-genotipos-selectorMin");

		List<Object[]> lista = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			lista = analisisGenotipoRepository.selectorMin(texto, PaginationUtil.pageSelector());
		else
			lista = analisisGenotipoRepository.selectorMinPublico(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/analisis-genotipos-atributos")
    @Timed
    public ResponseEntity<List<AnalisisGenotipoAtributo>> getAtributos(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of analisis-genotipos-atributos");

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) ||
		   Boolean.TRUE.equals(analisisGenotipoRepository.esPublico(id)))
			return new ResponseEntity<List<AnalisisGenotipoAtributo>>(analisisGenotipoRepository.findAtributos(id), HttpStatus.OK);

		return ResponseEntity.badRequest().headers(HeaderUtil.errorPrivacidad()).body(null);   
	}

	@GetMapping(value = "/analisis-genotipos-exportar-atributos")
    @Timed
    public ResponseEntity<Void> exportarAtributos(Pageable pageable, 
								  @RequestParam(value="criterio") String cadena,
								  final HttpServletResponse response){

        log.debug("REST analisis-genotipos-exportar-atributos");

		ResponseEntity<Void> respuesta = null;
		File archivo = null;
		try{
	
			Set<Long> ids = procesarQueryFiltered(pageable, cadena)
									.getContent()
									.stream()
									.map(AnalisisGenotipo::getId).collect(Collectors.toSet());

			archivo = atributoService.exportar(ids,
											   analisisGenotipoRepository.findAtributosByIdsObj(ids),
											   Entidad.ANALISIS_GENOTIPO);

			archivoService.download(archivo, response, "analisis_genotipos_atributos.tsv");

		}catch(Exception e){
			log.error(e.toString());
			respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorDownloadArchivo()).body(null);
		}finally{
			archivoService.eliminar(archivo);
		}

		return respuesta;

	}

	@GetMapping("/analisis-genotipos-parametros-ejecucion")
    @Timed
    public ResponseEntity<List<ParametroEjecucion>> getParametrosEjecucion(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of analisis-genotipos-parametros-ejecucion");

		List<ParametroEjecucion> lista = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			lista = ejecucionRepository.findParametrosEjecucion(id);
		else
			lista = ejecucionRepository.findParametrosEjecucionPublico(id);

		return new ResponseEntity<List<ParametroEjecucion>>(lista, HttpStatus.OK);
 
	}

	@GetMapping("/analisis-genotipos-ejecuciones")
    @Timed
    public ResponseEntity<List<Ejecucion>> getEjecuciones(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of analisis-genotipos-ejecuciones");

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) ||
		   Boolean.TRUE.equals(analisisGenotipoRepository.esPublico(id)))
			return new ResponseEntity<List<Ejecucion>>(ejecucionRepository.findByAnalisisGenotipo(id), HttpStatus.OK);

		return ResponseEntity.badRequest().headers(HeaderUtil.errorPrivacidad()).body(null); 
 
	}


}
