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
import org.tega.domain.Atributo;
import org.tega.domain.enumeration.Entidad;

import org.tega.repository.AtributoRepository;
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
import org.tega.repository.specifications.AtributoCriterio;
import org.tega.repository.specifications.AtributoSpecifications;

/**
 * REST controller for managing Atributo.
 */
@RestController
@RequestMapping("/api")
public class AtributoResource {

    private final Logger log = LoggerFactory.getLogger(AtributoResource.class);
        
    @Inject
    private AtributoRepository atributoRepository;

    /**
     * POST  /atributos : Create a new atributo.
     *
     * @param atributo the atributo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new atributo, or with status 400 (Bad Request) if the atributo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/atributos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Atributo> createAtributo(@Valid @RequestBody Atributo atributo) throws URISyntaxException {
        log.debug("REST request to save Atributo : {}", atributo);
        if (atributo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("atributo", "idexists", "A new atributo cannot already have an ID")).body(null);
        }

		if(atributo.getCodigo() != null){
			List<Atributo> p = atributoRepository.findByCodigoIgnoreCase(atributo.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Atributo result = atributoRepository.save(atributo);
        return ResponseEntity.created(new URI("/api/atributos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("atributo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /atributos : Updates an existing atributo.
     *
     * @param atributo the atributo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated atributo,
     * or with status 400 (Bad Request) if the atributo is not valid,
     * or with status 500 (Internal Server Error) if the atributo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/atributos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Atributo> updateAtributo(@Valid @RequestBody Atributo atributo) throws URISyntaxException {
        log.debug("REST request to update Atributo : {}", atributo);
        if (atributo.getId() == null) {
            return createAtributo(atributo);
        }

		if(atributo.getCodigo() != null){
			List<Atributo> p = atributoRepository.findByCodigoModif(atributo.getCodigo(), atributo.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Atributo result = atributoRepository.save(atributo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("atributo", atributo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /atributos : get all the atributos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of atributos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/atributos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Atributo>> getAllAtributos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Atributos");
        Page<Atributo> page = atributoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/atributos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /atributos/:id : get the "id" atributo.
     *
     * @param id the id of the atributo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the atributo, or with status 404 (Not Found)
     */
    @GetMapping("/atributos/{id}")
    @Timed
    public ResponseEntity<Atributo> getAtributo(@PathVariable Long id) {
        log.debug("REST request to get Atributo : {}", id);
        Atributo atributo = atributoRepository.findOne(id);
        return Optional.ofNullable(atributo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /atributos/:id : delete the "id" atributo.
     *
     * @param id the id of the atributo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/atributos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteAtributo(@PathVariable Long id) {
        log.debug("REST request to delete Atributo : {}", id);
        atributoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("atributo", id.toString())).build();
    }

	@PutMapping("/atributos-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to atributos-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	atributoRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("atributo", ids.size())).build();
    }

	@GetMapping("/atributos-filtered")
    @Timed
    public ResponseEntity<List<Atributo>> getAtributoQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of atributos-filtered");	

		AtributoCriterio criterio =  MapperUtil.getObject(cadena, AtributoCriterio.class);

		Page<Atributo> page = atributoRepository.findAll(AtributoSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/atributos-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/atributos-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Atributo>> getAtributoItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of atributos-items");

		AtributoCriterio criterio =  MapperUtil.getObject(cadena, AtributoCriterio.class);

		List<Atributo> lista = atributoRepository.findAll(AtributoSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/atributos-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Atributo>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of atributos-selector");

		List<Atributo> lista = atributoRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/atributos-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of atributos-selectorMin");

		List<Object[]> lista = atributoRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


	@GetMapping("/atributos-entidad")
    @Timed
    public ResponseEntity<List<Atributo>> getAtributosPorEntidad(@RequestParam(value="entidad") Entidad entidad)
		throws java.io.IOException{
        log.debug("REST request to get a page of atributos-entidad");

		List<Atributo> lista = atributoRepository.findByEntidad(entidad);

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
