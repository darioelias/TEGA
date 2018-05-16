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
import org.tega.domain.CategoriaParametroProcedimiento;

import org.tega.repository.CategoriaParametroProcedimientoRepository;
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
import org.tega.repository.specifications.CategoriaParametroProcedimientoCriterio;
import org.tega.repository.specifications.CategoriaParametroProcedimientoSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;

/**
 * REST controller for managing CategoriaParametroProcedimiento.
 */
@RestController
@RequestMapping("/api")
public class CategoriaParametroProcedimientoResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaParametroProcedimientoResource.class);
        
    @Inject
    private CategoriaParametroProcedimientoRepository categoriaParametroProcedimientoRepository;

    /**
     * POST  /procedimiento : Create a new procedimiento.
     *
     * @param procedimiento the procedimiento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedimiento, or with status 400 (Bad Request) if the procedimiento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categorias-parametro-procedimiento")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<CategoriaParametroProcedimiento> createCategoriaParametroProcedimiento(@Valid @RequestBody CategoriaParametroProcedimiento procedimiento) throws URISyntaxException {
        log.debug("REST request to save CategoriaParametroProcedimiento : {}", procedimiento);
        if (procedimiento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("categoriaParametroProcedimiento", "idexists", "A new procedimiento cannot already have an ID")).body(null);
        }

		if(procedimiento.getCodigo() != null){
			List<CategoriaParametroProcedimiento> p = categoriaParametroProcedimientoRepository.findByCodigoIgnoreCase(procedimiento.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        CategoriaParametroProcedimiento result = categoriaParametroProcedimientoRepository.save(procedimiento);
        return ResponseEntity.created(new URI("/api/categorias-parametro-procedimiento/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("categoriaParametroProcedimiento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procedimiento : Updates an existing procedimiento.
     *
     * @param procedimiento the procedimiento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procedimiento,
     * or with status 400 (Bad Request) if the procedimiento is not valid,
     * or with status 500 (Internal Server Error) if the procedimiento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categorias-parametro-procedimiento")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<CategoriaParametroProcedimiento> updateCategoriaParametroProcedimiento(@Valid @RequestBody CategoriaParametroProcedimiento procedimiento) throws URISyntaxException {
        log.debug("REST request to update CategoriaParametroProcedimiento : {}", procedimiento);
        if (procedimiento.getId() == null) {
            return createCategoriaParametroProcedimiento(procedimiento);
        }

		if(procedimiento.getCodigo() != null){
			List<CategoriaParametroProcedimiento> p = categoriaParametroProcedimientoRepository.findByCodigoModif(procedimiento.getCodigo(), procedimiento.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        CategoriaParametroProcedimiento result = categoriaParametroProcedimientoRepository.save(procedimiento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("categoriaParametroProcedimiento", procedimiento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedimiento : get all the procedimiento.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of procedimiento in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/categorias-parametro-procedimiento")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<CategoriaParametroProcedimiento>> getAllCategoriaParametroProcedimiento(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CategoriaParametroProcedimiento");
        Page<CategoriaParametroProcedimiento> page = categoriaParametroProcedimientoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categorias-parametro-procedimiento");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /procedimiento/:id : get the "id" procedimiento.
     *
     * @param id the id of the procedimiento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedimiento, or with status 404 (Not Found)
     */
    @GetMapping("/categorias-parametro-procedimiento/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<CategoriaParametroProcedimiento> getCategoriaParametroProcedimiento(@PathVariable Long id) {
        log.debug("REST request to get CategoriaParametroProcedimiento : {}", id);
        CategoriaParametroProcedimiento procedimiento = categoriaParametroProcedimientoRepository.findOne(id);
        return Optional.ofNullable(procedimiento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /procedimiento/:id : delete the "id" procedimiento.
     *
     * @param id the id of the procedimiento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categorias-parametro-procedimiento/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteCategoriaParametroProcedimiento(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaParametroProcedimiento : {}", id);
        categoriaParametroProcedimientoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("categoriaParametroProcedimiento", id.toString())).build();
    }

	@PutMapping("/categorias-parametro-procedimiento-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to procedimientos-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	categoriaParametroProcedimientoRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("categoriaParametroProcedimiento", ids.size())).build();
    }

	@GetMapping("/categorias-parametro-procedimiento-filtered")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<CategoriaParametroProcedimiento>> getCategoriaParametroProcedimientoQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of procedimientos-filtered");

		CategoriaParametroProcedimientoCriterio criterio =  MapperUtil.getObject(cadena, CategoriaParametroProcedimientoCriterio.class);

		Page<CategoriaParametroProcedimiento> page = categoriaParametroProcedimientoRepository.findAll(CategoriaParametroProcedimientoSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categorias-parametro-procedimiento-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/categorias-parametro-procedimiento-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<CategoriaParametroProcedimiento>> getCategoriaParametroProcedimientoItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of procedimientos-items");

		CategoriaParametroProcedimientoCriterio criterio =  MapperUtil.getObject(cadena, CategoriaParametroProcedimientoCriterio.class);

		List<CategoriaParametroProcedimiento> lista = categoriaParametroProcedimientoRepository.findAll(CategoriaParametroProcedimientoSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/categorias-parametro-procedimiento-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<CategoriaParametroProcedimiento>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of procedimientos-selector");

		
		List<CategoriaParametroProcedimiento> lista = categoriaParametroProcedimientoRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/categorias-parametro-procedimiento-selectorMin")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of procedimientos-selectorMin");

		List<Object[]> lista = categoriaParametroProcedimientoRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
