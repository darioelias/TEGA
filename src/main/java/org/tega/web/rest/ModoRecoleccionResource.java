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
import org.tega.domain.ModoRecoleccion;

import org.tega.repository.ModoRecoleccionRepository;
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
import org.tega.repository.specifications.ModoRecoleccionCriterio;
import org.tega.repository.specifications.ModoRecoleccionSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
/**
 * REST controller for managing ModoRecoleccion.
 */
@RestController
@RequestMapping("/api")
public class ModoRecoleccionResource {

    private final Logger log = LoggerFactory.getLogger(ModoRecoleccionResource.class);
        
    @Inject
    private ModoRecoleccionRepository modoRecoleccionRepository;

    /**
     * POST  /modo-recoleccions : Create a new modoRecoleccion.
     *
     * @param modoRecoleccion the modoRecoleccion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modoRecoleccion, or with status 400 (Bad Request) if the modoRecoleccion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/modos-recoleccion")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<ModoRecoleccion> createModoRecoleccion(@Valid @RequestBody ModoRecoleccion modoRecoleccion) throws URISyntaxException {
        log.debug("REST request to save ModoRecoleccion : {}", modoRecoleccion);
        if (modoRecoleccion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("modoRecoleccion", "idexists", "A new modoRecoleccion cannot already have an ID")).body(null);
        }

		if(modoRecoleccion.getCodigo() != null){
			List<ModoRecoleccion> p = modoRecoleccionRepository.findByCodigoIgnoreCase(modoRecoleccion.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        ModoRecoleccion result = modoRecoleccionRepository.save(modoRecoleccion);
        return ResponseEntity.created(new URI("/api/modos-recoleccion/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("modoRecoleccion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /modo-recoleccions : Updates an existing modoRecoleccion.
     *
     * @param modoRecoleccion the modoRecoleccion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modoRecoleccion,
     * or with status 400 (Bad Request) if the modoRecoleccion is not valid,
     * or with status 500 (Internal Server Error) if the modoRecoleccion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/modos-recoleccion")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<ModoRecoleccion> updateModoRecoleccion(@Valid @RequestBody ModoRecoleccion modoRecoleccion) throws URISyntaxException {
        log.debug("REST request to update ModoRecoleccion : {}", modoRecoleccion);
        if (modoRecoleccion.getId() == null) {
            return createModoRecoleccion(modoRecoleccion);
        }

		if(modoRecoleccion.getCodigo() != null){
			List<ModoRecoleccion> p = modoRecoleccionRepository.findByCodigoModif(modoRecoleccion.getCodigo(), modoRecoleccion.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        ModoRecoleccion result = modoRecoleccionRepository.save(modoRecoleccion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("modoRecoleccion", modoRecoleccion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /modo-recoleccions : get all the modoRecoleccions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of modoRecoleccions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/modos-recoleccion")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ModoRecoleccion>> getAllModoRecoleccions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ModoRecoleccions");
        Page<ModoRecoleccion> page = modoRecoleccionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modos-recoleccion");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /modo-recoleccions/:id : get the "id" modoRecoleccion.
     *
     * @param id the id of the modoRecoleccion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modoRecoleccion, or with status 404 (Not Found)
     */
    @GetMapping("/modos-recoleccion/{id}")
    @Timed
    public ResponseEntity<ModoRecoleccion> getModoRecoleccion(@PathVariable Long id) {
        log.debug("REST request to get ModoRecoleccion : {}", id);
        ModoRecoleccion modoRecoleccion = modoRecoleccionRepository.findOne(id);
        return Optional.ofNullable(modoRecoleccion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /modo-recoleccions/:id : delete the "id" modoRecoleccion.
     *
     * @param id the id of the modoRecoleccion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/modos-recoleccion/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteModoRecoleccion(@PathVariable Long id) {
        log.debug("REST request to delete ModoRecoleccion : {}", id);
        modoRecoleccionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("modoRecoleccion", id.toString())).build();
    }

	@PutMapping("/modos-recoleccion-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to modos-recoleccion-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	modoRecoleccionRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("modoRecoleccion", ids.size())).build();
    }

	@GetMapping("/modos-recoleccion-filtered")
    @Timed
    public ResponseEntity<List<ModoRecoleccion>> getModoRecoleccionQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of modos-recoleccion-filtered");

		ModoRecoleccionCriterio criterio =  MapperUtil.getObject(cadena, ModoRecoleccionCriterio.class);

		Page<ModoRecoleccion> page = modoRecoleccionRepository.findAll(ModoRecoleccionSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modos-recoleccion-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/modos-recoleccion-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ModoRecoleccion>> getModoRecoleccionItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of modos-recoleccion-items");

		ModoRecoleccionCriterio criterio =  MapperUtil.getObject(cadena, ModoRecoleccionCriterio.class);

		List<ModoRecoleccion> lista = modoRecoleccionRepository.findAll(ModoRecoleccionSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/modos-recoleccion-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ModoRecoleccion>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of modos-recoleccion-selector");

		List<ModoRecoleccion> lista = modoRecoleccionRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/modos-recoleccion-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of modos-recoleccion-selectorMin");

		List<Object[]> lista = modoRecoleccionRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
