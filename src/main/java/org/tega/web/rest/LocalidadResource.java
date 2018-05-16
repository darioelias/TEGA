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
import org.tega.domain.Localidad;

import org.tega.repository.LocalidadRepository;
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
import org.tega.repository.specifications.LocalidadCriterio;
import org.tega.repository.specifications.LocalidadSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
/**
 * REST controller for managing Localidad.
 */
@RestController
@RequestMapping("/api")
public class LocalidadResource {

    private final Logger log = LoggerFactory.getLogger(LocalidadResource.class);
        
    @Inject
    private LocalidadRepository localidadRepository;

    /**
     * POST  /localidads : Create a new localidad.
     *
     * @param localidad the localidad to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localidad, or with status 400 (Bad Request) if the localidad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/localidades")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Localidad> createLocalidad(@Valid @RequestBody Localidad localidad) throws URISyntaxException {
        log.debug("REST request to save Localidad : {}", localidad);
        if (localidad.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("localidad", "idexists", "A new localidad cannot already have an ID")).body(null);
        }

		if(localidad.getCodigo() != null){
			List<Localidad> p = localidadRepository.findByCodigoIgnoreCase(localidad.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Localidad result = localidadRepository.save(localidad);
        return ResponseEntity.created(new URI("/api/localidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("localidad", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /localidads : Updates an existing localidad.
     *
     * @param localidad the localidad to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localidad,
     * or with status 400 (Bad Request) if the localidad is not valid,
     * or with status 500 (Internal Server Error) if the localidad couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/localidades")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Localidad> updateLocalidad(@Valid @RequestBody Localidad localidad) throws URISyntaxException {
        log.debug("REST request to update Localidad : {}", localidad);
        if (localidad.getId() == null) {
            return createLocalidad(localidad);
        }

		if(localidad.getCodigo() != null){
			List<Localidad> p = localidadRepository.findByCodigoModif(localidad.getCodigo(), localidad.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Localidad result = localidadRepository.save(localidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("localidad", localidad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /localidads : get all the localidads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localidads in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/localidades")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Localidad>> getAllLocalidads(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Localidads");
        Page<Localidad> page = localidadRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/localidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /localidads/:id : get the "id" localidad.
     *
     * @param id the id of the localidad to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localidad, or with status 404 (Not Found)
     */
    @GetMapping("/localidades/{id}")
    @Timed
    public ResponseEntity<Localidad> getLocalidad(@PathVariable Long id) {
        log.debug("REST request to get Localidad : {}", id);
        Localidad localidad = localidadRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(localidad)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /localidads/:id : delete the "id" localidad.
     *
     * @param id the id of the localidad to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/localidades/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteLocalidad(@PathVariable Long id) {
        log.debug("REST request to delete Localidad : {}", id);
        localidadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("localidad", id.toString())).build();
    }

	@PutMapping("/localidades-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to localidades-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	localidadRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("localidad", ids.size())).build();
    }

	@GetMapping("/localidades-filtered")
    @Timed
    public ResponseEntity<List<Localidad>> getLocalidadQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of Localidades-filtered");

		LocalidadCriterio criterio =  MapperUtil.getObject(cadena, LocalidadCriterio.class);

		Page<Localidad> page = localidadRepository.findAll(LocalidadSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/Localidades-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/localidades-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Localidad>> getLocalidadItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of localidades-items");

		LocalidadCriterio criterio =  MapperUtil.getObject(cadena, LocalidadCriterio.class);

		List<Localidad> lista = localidadRepository.findAll(LocalidadSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/localidades-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Localidad>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of localidades-selector");

		List<Localidad> lista = localidadRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/localidades-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of localidades-selectorMin");

		List<Object[]> lista = localidadRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
