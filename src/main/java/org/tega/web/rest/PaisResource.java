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
import org.tega.domain.Pais;

import org.tega.repository.PaisRepository;
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
import org.tega.repository.specifications.PaisCriterio;
import org.tega.repository.specifications.PaisSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;

/**
 * REST controller for managing Pais.
 */
@RestController
@RequestMapping("/api")
public class PaisResource {

    private final Logger log = LoggerFactory.getLogger(PaisResource.class);
        
    @Inject
    private PaisRepository paisRepository;

    /**
     * POST  /pais : Create a new pais.
     *
     * @param pais the pais to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pais, or with status 400 (Bad Request) if the pais has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/paises")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Pais> createPais(@Valid @RequestBody Pais pais) throws URISyntaxException {
        log.debug("REST request to save Pais : {}", pais);
        if (pais.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pais", "idexists", "A new pais cannot already have an ID")).body(null);
        }

		if(pais.getCodigo() != null){
			List<Pais> p = paisRepository.findByCodigoIgnoreCase(pais.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Pais result = paisRepository.save(pais);
        return ResponseEntity.created(new URI("/api/paises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pais", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pais : Updates an existing pais.
     *
     * @param pais the pais to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pais,
     * or with status 400 (Bad Request) if the pais is not valid,
     * or with status 500 (Internal Server Error) if the pais couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/paises")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Pais> updatePais(@Valid @RequestBody Pais pais) throws URISyntaxException {
        log.debug("REST request to update Pais : {}", pais);
        if (pais.getId() == null) {
            return createPais(pais);
        }

		if(pais.getCodigo() != null){
			List<Pais> p = paisRepository.findByCodigoModif(pais.getCodigo(), pais.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Pais result = paisRepository.save(pais);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pais", pais.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pais : get all the pais.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pais in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/paises")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Pais>> getAllPais(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Pais");
        Page<Pais> page = paisRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/paises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pais/:id : get the "id" pais.
     *
     * @param id the id of the pais to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pais, or with status 404 (Not Found)
     */
    @GetMapping("/paises/{id}")
    @Timed
    public ResponseEntity<Pais> getPais(@PathVariable Long id) {
        log.debug("REST request to get Pais : {}", id);
        Pais pais = paisRepository.findOne(id);
        return Optional.ofNullable(pais)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pais/:id : delete the "id" pais.
     *
     * @param id the id of the pais to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/paises/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deletePais(@PathVariable Long id) {
        log.debug("REST request to delete Pais : {}", id);
        paisRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pais", id.toString())).build();
    }

	@PutMapping("/paises-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to paises-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	paisRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("pais", ids.size())).build();
    }

	@GetMapping("/paises-filtered")
    @Timed
    public ResponseEntity<List<Pais>> getPaisQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of paises-filtered");

		PaisCriterio criterio =  MapperUtil.getObject(cadena, PaisCriterio.class);

		Page<Pais> page = paisRepository.findAll(PaisSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/paises-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/paises-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Pais>> getPaisItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of paises-items");

		PaisCriterio criterio =  MapperUtil.getObject(cadena, PaisCriterio.class);

		List<Pais> lista = paisRepository.findAll(PaisSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/paises-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Pais>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of paises-selector");

		
		List<Pais> lista = paisRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/paises-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of paises-selectorMin");

		List<Object[]> lista = paisRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
