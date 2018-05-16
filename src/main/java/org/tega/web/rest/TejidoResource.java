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
import org.tega.domain.Tejido;

import org.tega.repository.TejidoRepository;
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
import org.tega.repository.specifications.TejidoCriterio;
import org.tega.repository.specifications.TejidoSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
/**
 * REST controller for managing Tejido.
 */
@RestController
@RequestMapping("/api")
public class TejidoResource {

    private final Logger log = LoggerFactory.getLogger(TejidoResource.class);
        
    @Inject
    private TejidoRepository tejidoRepository;

    /**
     * POST  /tejidos : Create a new tejido.
     *
     * @param tejido the tejido to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tejido, or with status 400 (Bad Request) if the tejido has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tejidos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Tejido> createTejido(@Valid @RequestBody Tejido tejido) throws URISyntaxException {
        log.debug("REST request to save Tejido : {}", tejido);
        if (tejido.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tejido", "idexists", "A new tejido cannot already have an ID")).body(null);
        }

		if(tejido.getCodigo() != null){
			List<Tejido> p = tejidoRepository.findByCodigoIgnoreCase(tejido.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Tejido result = tejidoRepository.save(tejido);
        return ResponseEntity.created(new URI("/api/tejidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tejido", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tejidos : Updates an existing tejido.
     *
     * @param tejido the tejido to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tejido,
     * or with status 400 (Bad Request) if the tejido is not valid,
     * or with status 500 (Internal Server Error) if the tejido couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tejidos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Tejido> updateTejido(@Valid @RequestBody Tejido tejido) throws URISyntaxException {
        log.debug("REST request to update Tejido : {}", tejido);
        if (tejido.getId() == null) {
            return createTejido(tejido);
        }

		if(tejido.getCodigo() != null){
			List<Tejido> p = tejidoRepository.findByCodigoModif(tejido.getCodigo(), tejido.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Tejido result = tejidoRepository.save(tejido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tejido", tejido.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tejidos : get all the tejidos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tejidos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tejidos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Tejido>> getAllTejidos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tejidos");
        Page<Tejido> page = tejidoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tejidos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tejidos/:id : get the "id" tejido.
     *
     * @param id the id of the tejido to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tejido, or with status 404 (Not Found)
     */
    @GetMapping("/tejidos/{id}")
    @Timed
    public ResponseEntity<Tejido> getTejido(@PathVariable Long id) {
        log.debug("REST request to get Tejido : {}", id);
        Tejido tejido = tejidoRepository.findOne(id);
        return Optional.ofNullable(tejido)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tejidos/:id : delete the "id" tejido.
     *
     * @param id the id of the tejido to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tejidos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteTejido(@PathVariable Long id) {
        log.debug("REST request to delete Tejido : {}", id);
        tejidoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tejido", id.toString())).build();
    }

	@PutMapping("/tejidos-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to tejidos-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	tejidoRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("tejido", ids.size())).build();
    }

	@GetMapping("/tejidos-filtered")
    @Timed
    public ResponseEntity<List<Tejido>> getTejidoQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of tejidos-filtered");

		TejidoCriterio criterio =  MapperUtil.getObject(cadena, TejidoCriterio.class);

		Page<Tejido> page = tejidoRepository.findAll(TejidoSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tejidos-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


	@GetMapping("/tejidos-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Tejido>> getTejidoItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of tejidos-items");

		TejidoCriterio criterio =  MapperUtil.getObject(cadena, TejidoCriterio.class);

		List<Tejido> lista = tejidoRepository.findAll(TejidoSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/tejidos-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Tejido>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of tejidos-selector");

		List<Tejido> lista = tejidoRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/tejidos-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of tejidos-selectorMin");

		List<Object[]> lista = tejidoRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
