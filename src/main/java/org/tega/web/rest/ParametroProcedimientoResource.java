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
import org.tega.domain.ParametroProcedimiento;

import org.tega.repository.ParametroProcedimientoRepository;
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
import org.tega.repository.specifications.ParametroProcedimientoCriterio;
import org.tega.repository.specifications.ParametroProcedimientoSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
/**
 * REST controller for managing ParametroProcedimiento.
 */
@RestController
@RequestMapping("/api")
public class ParametroProcedimientoResource {

    private final Logger log = LoggerFactory.getLogger(ParametroProcedimientoResource.class);
        
    @Inject
    private ParametroProcedimientoRepository parametroProcedimientoRepository;


    /**
     * POST  /ParametroProcedimiento : Create a new ParametroProcedimiento.
     *
     * @param ParametroProcedimiento the ParametroProcedimiento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ParametroProcedimiento, or with status 400 (Bad Request) if the parametro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametros-procedimiento")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<ParametroProcedimiento> createParametroProcedimiento(@Valid @RequestBody ParametroProcedimiento parametro) throws URISyntaxException {
        log.debug("REST request to save ParametroProcedimiento : {}", parametro);
        if (parametro.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("parametroProcedimiento", "idexists", "A new parametro cannot already have an ID")).body(null);
        }

		if(parametro.getCodigo() != null){
			List<ParametroProcedimiento> p = parametroProcedimientoRepository.findByCodigoIgnoreCase(parametro.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        ParametroProcedimiento result = parametroProcedimientoRepository.save(parametro);
        return ResponseEntity.created(new URI("/api/parametros-procedimiento/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("parametroProcedimiento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parametro : Updates an existing parametro.
     *
     * @param parametro the parametro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parametro,
     * or with status 400 (Bad Request) if the parametro is not valid,
     * or with status 500 (Internal Server Error) if the parametro couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parametros-procedimiento")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<ParametroProcedimiento> updateParametroProcedimiento(@Valid @RequestBody ParametroProcedimiento parametro) throws URISyntaxException {
        log.debug("REST request to update ParametroProcedimiento : {}", parametro);
        if (parametro.getId() == null) {
            return createParametroProcedimiento(parametro);
        }

		if(parametro.getCodigo() != null){
			List<ParametroProcedimiento> p = parametroProcedimientoRepository.findByCodigoModif(parametro.getCodigo(), parametro.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        ParametroProcedimiento result = parametroProcedimientoRepository.save(parametro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("parametroProcedimiento", parametro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametro : get all the parametro.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parametro in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/parametros-procedimiento")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ParametroProcedimiento>> getAllParametroProcedimiento(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ParametroProcedimiento");
        Page<ParametroProcedimiento> page = parametroProcedimientoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametros-procedimiento");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parametro/:id : get the "id" parametro.
     *
     * @param id the id of the parametro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametro, or with status 404 (Not Found)
     */
    @GetMapping("/parametros-procedimiento/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<ParametroProcedimiento> getParametroProcedimiento(@PathVariable Long id) {
        log.debug("REST request to get ParametroProcedimiento : {}", id);
        ParametroProcedimiento parametro = parametroProcedimientoRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(parametro)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /parametro/:id : delete the "id" parametro.
     *
     * @param id the id of the parametro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parametros-procedimiento/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteParametroProcedimiento(@PathVariable Long id) {
        log.debug("REST request to delete ParametroProcedimiento : {}", id);
        parametroProcedimientoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("parametroProcedimiento", id.toString())).build();
    }

	@PutMapping("/parametros-procedimiento-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to parametros-procedimiento-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	parametroProcedimientoRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("parametroProcedimiento", ids.size())).build();
    }

	@GetMapping("/parametros-procedimiento-filtered")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ParametroProcedimiento>> getParametroProcedimientoQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of parametros-procedimiento-filtered");

		ParametroProcedimientoCriterio criterio =  MapperUtil.getObject(cadena, ParametroProcedimientoCriterio.class);

		Page<ParametroProcedimiento> page = parametroProcedimientoRepository.findAll(ParametroProcedimientoSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametros-procedimiento-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/parametros-procedimiento-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ParametroProcedimiento>> getParametroProcedimientoItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of parametros-procedimiento-items");

		ParametroProcedimientoCriterio criterio =  MapperUtil.getObject(cadena, ParametroProcedimientoCriterio.class);

		List<ParametroProcedimiento> lista = parametroProcedimientoRepository.findAll(ParametroProcedimientoSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/parametros-procedimiento-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ParametroProcedimiento>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of parametros-procedimiento-selector");

		
		List<ParametroProcedimiento> lista = parametroProcedimientoRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/parametros-procedimiento-selectorMin")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of parametros-procedimiento-selectorMin");

		List<Object[]> lista = parametroProcedimientoRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


	@GetMapping("/parametros-procedimiento-procedimiento-ejec")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ParametroProcedimiento>> getParametroProcedimientoProcedimientoEjec(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of parametros-procedimiento-procedimiento");

		List<ParametroProcedimiento> lista = parametroProcedimientoRepository.findByProcedimientoEditable(id);

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
