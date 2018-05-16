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
import org.tega.domain.Proyecto;
import org.tega.domain.ProyectoAtributo;
import org.tega.domain.enumeration.Entidad;

import org.tega.repository.ProyectoRepository;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.tega.service.util.MapperUtil;
import org.tega.repository.specifications.ProyectoCriterio;
import org.tega.repository.specifications.ProyectoSpecifications;

import org.tega.service.AtributoService;
import org.tega.service.ArchivosEntidades;
import org.tega.service.ArchivoService;
import java.io.IOException;
import java.io.File;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
import org.tega.security.SecurityUtils;
/**
 * REST controller for managing Proyecto.
 */
@RestController
@RequestMapping("/api")
public class ProyectoResource {

    private final Logger log = LoggerFactory.getLogger(ProyectoResource.class);
        
    @Inject
    private ProyectoRepository proyectoRepository;

    @Inject
    private ArchivoService archivoService;

	@Inject
    private AtributoService atributoService;

    /**
     * POST  /proyectos : Create a new proyecto.
     *
     * @param proyecto the proyecto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new proyecto, or with status 400 (Bad Request) if the proyecto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proyectos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Proyecto> createProyecto(@Valid @RequestBody Proyecto proyecto) throws URISyntaxException {
        log.debug("REST request to save Proyecto : {}", proyecto);
        if (proyecto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("proyecto", "idexists", "A new proyecto cannot already have an ID")).body(null);
        }

		if(proyecto.getCodigo() != null){
			List<Proyecto> p = proyectoRepository.findByCodigoIgnoreCase(proyecto.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		for(ProyectoAtributo a : proyecto.getAtributos())
			a.setProyecto(proyecto);

        Proyecto result = proyectoRepository.save(proyecto);
        return ResponseEntity.created(new URI("/api/proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("proyecto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proyectos : Updates an existing proyecto.
     *
     * @param proyecto the proyecto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated proyecto,
     * or with status 400 (Bad Request) if the proyecto is not valid,
     * or with status 500 (Internal Server Error) if the proyecto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proyectos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Proyecto> updateProyecto(@Valid @RequestBody Proyecto proyecto) throws URISyntaxException {
        log.debug("REST request to update Proyecto : {}", proyecto);
        if (proyecto.getId() == null) {
            return createProyecto(proyecto);
        }

		if(proyecto.getCodigo() != null){
			List<Proyecto> p = proyectoRepository.findByCodigoModif(proyecto.getCodigo(), proyecto.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		Proyecto proyectoBD = proyectoRepository.findOne(proyecto.getId());
		
		Boolean difPublico = proyectoBD.getPublico() != proyecto.getPublico();

		proyecto.setAtributos(atributoService.filtrarValoresPorDefecto(proyecto.getAtributos(), 
																	  Entidad.PROYECTO));
		proyecto.getAtributos().forEach(a -> a.setProyecto(proyecto));

		proyecto.getArchivos().forEach(a -> a.addProyecto(proyecto));
		

        Proyecto result = proyectoRepository.save(proyecto);

		if(difPublico)
			proyectoRepository.actualizarProyectoPublico(proyecto.getId());


		result.getArchivos().forEach(a -> a.setProyectos(null));


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("proyecto", proyecto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proyectos : get all the proyectos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of proyectos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/proyectos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Proyecto>> getAllProyectos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Proyectos");
        Page<Proyecto> page = proyectoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proyectos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /proyectos/:id : get the "id" proyecto.
     *
     * @param id the id of the proyecto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the proyecto, or with status 404 (Not Found)
     */
    @GetMapping("/proyectos/{id}")
    @Timed
    public ResponseEntity<Proyecto> getProyecto(@PathVariable Long id) {
        log.debug("REST request to get Proyecto : {}", id);
        Proyecto proyecto = proyectoRepository.findOneWithEagerRelationships(id);

		if(proyecto != null &&
		  !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) &&
		  !proyecto.getPublico())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return Optional.ofNullable(proyecto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /proyectos/:id : delete the "id" proyecto.
     *
     * @param id the id of the proyecto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proyectos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteProyecto(@PathVariable Long id) throws IOException{
        log.debug("REST request to delete Proyecto : {}", id);
        proyectoRepository.delete(id);
		archivoService.eliminarCarpeta(ArchivosEntidades.PROYECTO, id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proyecto", id.toString())).build();
    }

	@PutMapping("/proyectos-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids) throws IOException{
        log.debug("REST request to proyectos-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	proyectoRepository.deleteByIdIn(ids);
			archivoService.eliminarCarpetas(ArchivosEntidades.PROYECTO, ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("proyecto", ids.size())).build();
    }

	private Page<Proyecto> procesarQueryFiltered(Pageable pageable, String cadena)
			throws IOException{
		ProyectoCriterio criterio =  MapperUtil.getObject(cadena, ProyectoCriterio.class);

		if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			criterio.setPublico(true);

		return proyectoRepository.findAll(ProyectoSpecifications.findByCriteria(criterio),pageable);

	}

	@GetMapping("/proyectos-filtered")
    @Timed
    public ResponseEntity<List<Proyecto>> getProyectosQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of Proyectos-Filtered");

		Page<Proyecto> page = procesarQueryFiltered(pageable, cadena);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proyectos-filtered");
    	return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

        
    }

	@GetMapping("/proyectos-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Proyecto>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of proyectos-selector");

		List<Proyecto> lista = proyectoRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/proyectos-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of proyectos-selectorMin");

		List<Object[]> lista = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			lista = proyectoRepository.selectorMin(texto, PaginationUtil.pageSelector());
		else
			lista = proyectoRepository.selectorMinPublico(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/proyectos-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Proyecto>> getProyectoItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of proyectos-items");

		ProyectoCriterio criterio =  MapperUtil.getObject(cadena, ProyectoCriterio.class);

		List<Proyecto> lista = proyectoRepository.findAll(ProyectoSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/proyectos-muestra")
    @Timed
    public ResponseEntity<List<Proyecto>> getProyectosMuestra(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of proyectos-muestra");

		List<Proyecto> lista = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			lista = proyectoRepository.findByMuestra(id);
		else
			lista = proyectoRepository.findByMuestraPublico(id);

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/proyectos-atributos")
    @Timed
    public ResponseEntity<List<ProyectoAtributo>> getAtributos(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of proyectos-atributos");

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) ||
		   Boolean.TRUE.equals(proyectoRepository.esPublico(id)))
			return new ResponseEntity<List<ProyectoAtributo>>(proyectoRepository.findAtributos(id), HttpStatus.OK);

		return ResponseEntity.badRequest().headers(HeaderUtil.errorPrivacidad()).body(null);   
	}

	@GetMapping(value = "/proyectos-exportar-atributos")
    @Timed
    public ResponseEntity<Void> exportarAtributos(Pageable pageable, 
										  @RequestParam(value="criterio") String cadena,
										  final HttpServletResponse response) {

        log.debug("REST proyectos-exportar-atributos");

		ResponseEntity<Void> respuesta = null;
		File archivo = null;
		try{
	
			Set<Long> ids = procesarQueryFiltered(pageable, cadena)
									.getContent()
									.stream()
									.map(Proyecto::getId).collect(Collectors.toSet());

			archivo = atributoService.exportar(ids,
											   proyectoRepository.findAtributosByIdsObj(ids),
											   Entidad.PROYECTO);

			archivoService.download(archivo, response, "proyectos_atributos.tsv");

		}catch(Exception e){
			log.error(e.toString());
			respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorDownloadArchivo()).body(null);
		}finally{
			archivoService.eliminar(archivo);
		}

		return respuesta;

	}

}
