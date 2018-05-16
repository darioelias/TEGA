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
import org.tega.domain.Profesional;

import org.tega.repository.ProfesionalRepository;
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
import org.tega.repository.specifications.ProfesionalCriterio;
import org.tega.repository.specifications.ProfesionalSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
/**
 * REST controller for managing Profesional.
 */
@RestController
@RequestMapping("/api")
public class ProfesionalResource {

    private final Logger log = LoggerFactory.getLogger(ProfesionalResource.class);
        
    @Inject
    private ProfesionalRepository profesionalRepository;

    /**
     * POST  /profesionals : Create a new profesional.
     *
     * @param profesional the profesional to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profesional, or with status 400 (Bad Request) if the profesional has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profesionales")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Profesional> createProfesional(@Valid @RequestBody Profesional profesional) throws URISyntaxException {
        log.debug("REST request to save Profesional : {}", profesional);
        if (profesional.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("profesional", "idexists", "A new profesional cannot already have an ID")).body(null);
        }

		if(profesional.getCodigo() != null){
			List<Profesional> p = profesionalRepository.findByCodigoIgnoreCase(profesional.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Profesional result = profesionalRepository.save(profesional);
        return ResponseEntity.created(new URI("/api/profesionales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("profesional", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profesionals : Updates an existing profesional.
     *
     * @param profesional the profesional to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profesional,
     * or with status 400 (Bad Request) if the profesional is not valid,
     * or with status 500 (Internal Server Error) if the profesional couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profesionales")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Profesional> updateProfesional(@Valid @RequestBody Profesional profesional) throws URISyntaxException {
        log.debug("REST request to update Profesional : {}", profesional);
        if (profesional.getId() == null) {
            return createProfesional(profesional);
        }

		if(profesional.getCodigo() != null){
			List<Profesional> p = profesionalRepository.findByCodigoModif(profesional.getCodigo(), profesional.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Profesional result = profesionalRepository.save(profesional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("profesional", profesional.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profesionals : get all the profesionals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of profesionals in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/profesionales")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Profesional>> getAllProfesionals(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Profesionals");
        Page<Profesional> page = profesionalRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profesionales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /profesionals/:id : get the "id" profesional.
     *
     * @param id the id of the profesional to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profesional, or with status 404 (Not Found)
     */
    @GetMapping("/profesionales/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Profesional> getProfesional(@PathVariable Long id) {
        log.debug("REST request to get Profesional : {}", id);
        Profesional profesional = profesionalRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(profesional)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /profesionals/:id : delete the "id" profesional.
     *
     * @param id the id of the profesional to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profesionales/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteProfesional(@PathVariable Long id) {
        log.debug("REST request to delete Profesional : {}", id);
        profesionalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("profesional", id.toString())).build();
    }

	@PutMapping("/profesionales-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to profesionales-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	profesionalRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("profesional", ids.size())).build();
    }

	@GetMapping("/profesionales-filtered")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Profesional>> getProfesionalQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of Profesionales-filtered");

		ProfesionalCriterio criterio =  MapperUtil.getObject(cadena, ProfesionalCriterio.class);

		Page<Profesional> page = profesionalRepository.findAll(ProfesionalSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/Profesionales-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/profesionales-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Profesional>> getProfesionalItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of profesionales-items");

		ProfesionalCriterio criterio =  MapperUtil.getObject(cadena, ProfesionalCriterio.class);

		List<Profesional> lista = profesionalRepository.findAll(ProfesionalSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/profesionales-selector")
    @Timed
    public ResponseEntity<List<Profesional>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of poblaciones-selector");

		List<Profesional> lista = profesionalRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/profesionales-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of poblaciones-selectorMin");

		List<Object[]> lista = profesionalRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
