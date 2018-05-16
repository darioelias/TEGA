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
import org.tega.domain.Institucion;

import org.tega.repository.InstitucionRepository;
import org.tega.web.rest.util.HeaderUtil;
import org.tega.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.tega.service.util.MapperUtil;
import org.tega.repository.specifications.InstitucionCriterio;
import org.tega.repository.specifications.InstitucionSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
/**
 * REST controller for managing Institucion.
 */
@RestController
@RequestMapping("/api")
public class InstitucionResource {

    private final Logger log = LoggerFactory.getLogger(InstitucionResource.class);
        
    @Inject
    private InstitucionRepository institucionRepository;

    /**
     * POST  /institucions : Create a new institucion.
     *
     * @param institucion the institucion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new institucion, or with status 400 (Bad Request) if the institucion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/instituciones")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Institucion> createInstitucion(@Valid @RequestBody Institucion institucion) throws URISyntaxException {
        log.debug("REST request to save Institucion : {}", institucion);
        if (institucion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("institucion", "idexists", "A new institucion cannot already have an ID")).body(null);
        }

		if(institucion.getCodigo() != null){
			List<Institucion> p = institucionRepository.findByCodigoIgnoreCase(institucion.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Institucion result = institucionRepository.save(institucion);
        return ResponseEntity.created(new URI("/api/instituciones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("institucion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /institucions : Updates an existing institucion.
     *
     * @param institucion the institucion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated institucion,
     * or with status 400 (Bad Request) if the institucion is not valid,
     * or with status 500 (Internal Server Error) if the institucion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/instituciones")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Institucion> updateInstitucion(@Valid @RequestBody Institucion institucion) throws URISyntaxException {
        log.debug("REST request to update Institucion : {}", institucion);
        if (institucion.getId() == null) {
            return createInstitucion(institucion);
        }

		if(institucion.getCodigo() != null){
			List<Institucion> p = institucionRepository.findByCodigoModif(institucion.getCodigo(), institucion.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Institucion result = institucionRepository.save(institucion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("institucion", institucion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /institucions : get all the institucions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of institucions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/instituciones")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Institucion>> getAllInstitucions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Institucions");
        Page<Institucion> page = institucionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituciones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /institucions/:id : get the "id" institucion.
     *
     * @param id the id of the institucion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the institucion, or with status 404 (Not Found)
     */
    @GetMapping("/instituciones/{id}")
    @Timed
    public ResponseEntity<Institucion> getInstitucion(@PathVariable Long id) {
        log.debug("REST request to get Institucion : {}", id);
        Institucion institucion = institucionRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(institucion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /institucions/:id : delete the "id" institucion.
     *
     * @param id the id of the institucion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/instituciones/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteInstitucion(@PathVariable Long id) {
        log.debug("REST request to delete Institucion : {}", id);
        institucionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("institucion", id.toString())).build();
    }

	@PutMapping("/instituciones-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to instituciones-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	institucionRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("institucion", ids.size())).build();
    }

	@GetMapping("/instituciones-filtered")
    @Timed
    public ResponseEntity<List<Institucion>> getInstitucionQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of Instituciones-filtered");

		InstitucionCriterio criterio =  MapperUtil.getObject(cadena, InstitucionCriterio.class);

		Page<Institucion> page = institucionRepository.findAll(InstitucionSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituciones-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/instituciones-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Institucion>> getInstitucionItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of instituciones-items");

		InstitucionCriterio criterio =  MapperUtil.getObject(cadena, InstitucionCriterio.class);

		List<Institucion> lista = institucionRepository.findAll(InstitucionSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/instituciones-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Institucion>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of instituciones-selector");

		List<Institucion> lista = institucionRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/instituciones-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of instituciones-selectorMin");

		List<Object[]> lista = institucionRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
