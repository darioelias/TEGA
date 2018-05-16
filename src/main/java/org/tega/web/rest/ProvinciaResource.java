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
import org.tega.domain.Provincia;

import org.tega.repository.ProvinciaRepository;
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
import org.tega.repository.specifications.ProvinciaCriterio;
import org.tega.repository.specifications.ProvinciaSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
/**
 * REST controller for managing Provincia.
 */
@RestController
@RequestMapping("/api")
public class ProvinciaResource {

    private final Logger log = LoggerFactory.getLogger(ProvinciaResource.class);
        
    @Inject
    private ProvinciaRepository provinciaRepository;

    /**
     * POST  /provincias : Create a new provincia.
     *
     * @param provincia the provincia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new provincia, or with status 400 (Bad Request) if the provincia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provincias")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Provincia> createProvincia(@Valid @RequestBody Provincia provincia) throws URISyntaxException {
        log.debug("REST request to save Provincia : {}", provincia);
        if (provincia.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("provincia", "idexists", "A new provincia cannot already have an ID")).body(null);
        }

		if(provincia.getCodigo() != null){
			List<Provincia> p = provinciaRepository.findByCodigoIgnoreCase(provincia.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Provincia result = provinciaRepository.save(provincia);
        return ResponseEntity.created(new URI("/api/provincias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("provincia", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provincias : Updates an existing provincia.
     *
     * @param provincia the provincia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated provincia,
     * or with status 400 (Bad Request) if the provincia is not valid,
     * or with status 500 (Internal Server Error) if the provincia couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provincias")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Provincia> updateProvincia(@Valid @RequestBody Provincia provincia) throws URISyntaxException {
        log.debug("REST request to update Provincia : {}", provincia);
        if (provincia.getId() == null) {
            return createProvincia(provincia);
        }

		if(provincia.getCodigo() != null){
			List<Provincia> p = provinciaRepository.findByCodigoModif(provincia.getCodigo(), provincia.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Provincia result = provinciaRepository.save(provincia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("provincia", provincia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provincias : get all the provincias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of provincias in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/provincias")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Provincia>> getAllProvincias(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Provincias");
        Page<Provincia> page = provinciaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provincias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /provincias/:id : get the "id" provincia.
     *
     * @param id the id of the provincia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the provincia, or with status 404 (Not Found)
     */
    @GetMapping("/provincias/{id}")
    @Timed
    public ResponseEntity<Provincia> getProvincia(@PathVariable Long id) {
        log.debug("REST request to get Provincia : {}", id);
        Provincia provincia = provinciaRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(provincia)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /provincias/:id : delete the "id" provincia.
     *
     * @param id the id of the provincia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provincias/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteProvincia(@PathVariable Long id) {
        log.debug("REST request to delete Provincia : {}", id);
        provinciaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("provincia", id.toString())).build();
    }

	@PutMapping("/provincias-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to provincias-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	provinciaRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("provincia", ids.size())).build();
    }

	@GetMapping("/provincias-filtered")
    @Timed
    public ResponseEntity<List<Provincia>> getProvinciaQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of provincias-filtered");

		ProvinciaCriterio criterio =  MapperUtil.getObject(cadena, ProvinciaCriterio.class);

		Page<Provincia> page = provinciaRepository.findAll(ProvinciaSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provincias-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/provincias-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Provincia>> getProvinciaItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of provincias-items");

		ProvinciaCriterio criterio =  MapperUtil.getObject(cadena, ProvinciaCriterio.class);

		List<Provincia> lista = provinciaRepository.findAll(ProvinciaSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/provincias-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Provincia>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of provincias-selector");

		List<Provincia> lista = provinciaRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/provincias-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of provincias-selectorMin");

		List<Object[]> lista = provinciaRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
