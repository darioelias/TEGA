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
import org.tega.domain.Especie;

import org.tega.repository.EspecieRepository;
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
import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.tega.service.util.MapperUtil;
import org.tega.repository.specifications.EspecieCriterio;
import org.tega.repository.specifications.EspecieSpecifications;

/**
 * REST controller for managing Especie.
 */
@RestController
@RequestMapping("/api")
public class EspecieResource {

    private final Logger log = LoggerFactory.getLogger(EspecieResource.class);
        
    @Inject
    private EspecieRepository especieRepository;

    /**
     * POST  /especies : Create a new especie.
     *
     * @param especie the especie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new especie, or with status 400 (Bad Request) if the especie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/especies")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Especie> createEspecie(@Valid @RequestBody Especie especie) throws URISyntaxException {
        log.debug("REST request to save Especie : {}", especie);
        if (especie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("especie", "idexists", "A new especie cannot already have an ID")).body(null);
        }

		if(especie.getCodigo() != null){
			List<Especie> p = especieRepository.findByCodigoIgnoreCase(especie.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Especie result = especieRepository.save(especie);
        return ResponseEntity.created(new URI("/api/especies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("especie", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /especies : Updates an existing especie.
     *
     * @param especie the especie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated especie,
     * or with status 400 (Bad Request) if the especie is not valid,
     * or with status 500 (Internal Server Error) if the especie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/especies")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Especie> updateEspecie(@Valid @RequestBody Especie especie) throws URISyntaxException {
        log.debug("REST request to update Especie : {}", especie);
        if (especie.getId() == null) {
            return createEspecie(especie);
        }

		if(especie.getCodigo() != null){
			List<Especie> p = especieRepository.findByCodigoModif(especie.getCodigo(), especie.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Especie result = especieRepository.save(especie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("especie", especie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /especies : get all the especies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of especies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/especies")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Especie>> getAllEspecies(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Especies");
        Page<Especie> page = especieRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/especies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /especies/:id : get the "id" especie.
     *
     * @param id the id of the especie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the especie, or with status 404 (Not Found)
     */
    @GetMapping("/especies/{id}")
    @Timed
    public ResponseEntity<Especie> getEspecie(@PathVariable Long id) {
        log.debug("REST request to get Especie : {}", id);
        Especie especie = especieRepository.findOne(id);
        return Optional.ofNullable(especie)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /especies/:id : delete the "id" especie.
     *
     * @param id the id of the especie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/especies/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteEspecie(@PathVariable Long id) {
        log.debug("REST request to delete Especie : {}", id);
        especieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("especie", id.toString())).build();
    }

	@PutMapping("/especies-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to especies-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	especieRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("especie", ids.size())).build();
    }

	@GetMapping("/especies-filtered")
    @Timed
    public ResponseEntity<List<Especie>> getEspecieQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of especies-filtered");

		EspecieCriterio criterio =  MapperUtil.getObject(cadena, EspecieCriterio.class);

		Page<Especie> page = especieRepository.findAll(EspecieSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/especies-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/especies-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Especie>> getEspecieItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of especies-items");

		EspecieCriterio criterio =  MapperUtil.getObject(cadena, EspecieCriterio.class);

		List<Especie> lista = especieRepository.findAll(EspecieSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/especies-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Especie>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of especies-selector");

		List<Especie> lista = especieRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/especies-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of especies-selectorMin");

		List<Object[]> lista = especieRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
