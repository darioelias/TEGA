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
import org.tega.domain.Region;

import org.tega.repository.RegionRepository;
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
import org.tega.repository.specifications.RegionCriterio;
import org.tega.repository.specifications.RegionSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
/**
 * REST controller for managing Region.
 */
@RestController
@RequestMapping("/api")
public class RegionResource {

    private final Logger log = LoggerFactory.getLogger(RegionResource.class);
        
    @Inject
    private RegionRepository regionRepository;

    /**
     * POST  /regiones : Create a new region.
     *
     * @param region the region to create
     * @return the ResponseEntity with status 201 (Created) and with body the new region, or with status 400 (Bad Request) if the region has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regiones")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Region> createRegion(@Valid @RequestBody Region region) throws URISyntaxException {
        log.debug("REST request to save Region : {}", region);
        if (region.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("region", "idexists", "A new region cannot already have an ID")).body(null);
        }

		if(region.getCodigo() != null){
			List<Region> p = regionRepository.findByCodigoIgnoreCase(region.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Region result = regionRepository.save(region);
        return ResponseEntity.created(new URI("/api/regiones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("region", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regiones : Updates an existing region.
     *
     * @param region the region to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated region,
     * or with status 400 (Bad Request) if the region is not valid,
     * or with status 500 (Internal Server Error) if the region couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regiones")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Region> updateRegion(@Valid @RequestBody Region region) throws URISyntaxException {
        log.debug("REST request to update Region : {}", region);
        if (region.getId() == null) {
            return createRegion(region);
        }

		if(region.getCodigo() != null){
			List<Region> p = regionRepository.findByCodigoModif(region.getCodigo(), region.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Region result = regionRepository.save(region);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("region", region.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regiones : get all the regiones.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of regiones in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/regiones")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Region>> getAllRegions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Regions");
        Page<Region> page = regionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/regiones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /regiones/:id : get the "id" region.
     *
     * @param id the id of the region to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the region, or with status 404 (Not Found)
     */
    @GetMapping("/regiones/{id}")
    @Timed
    public ResponseEntity<Region> getRegion(@PathVariable Long id) {
        log.debug("REST request to get Region : {}", id);
        Region region = regionRepository.findOne(id);
        return Optional.ofNullable(region)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /regiones/:id : delete the "id" region.
     *
     * @param id the id of the region to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regiones/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        log.debug("REST request to delete Region : {}", id);
        regionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("region", id.toString())).build();
    }

	@PutMapping("/regiones-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to regiones-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	regionRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("region", ids.size())).build();
    }

	@GetMapping("/regiones-filtered")
    @Timed
    public ResponseEntity<List<Region>> getRegionQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of regiones-filtered");

		RegionCriterio criterio =  MapperUtil.getObject(cadena, RegionCriterio.class);

		Page<Region> page = regionRepository.findAll(RegionSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/regiones-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/regiones-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Region>> getRegionItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of regiones-items");

		RegionCriterio criterio =  MapperUtil.getObject(cadena, RegionCriterio.class);

		List<Region> lista = regionRepository.findAll(RegionSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/regiones-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Region>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of regiones-selector");

		List<Region> lista = regionRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/regiones-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of regiones-selectorMin");

		List<Object[]> lista = regionRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
