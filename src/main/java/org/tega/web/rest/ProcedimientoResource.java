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
import org.tega.domain.Procedimiento;

import org.tega.repository.ProcedimientoRepository;
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
import org.tega.repository.specifications.ProcedimientoCriterio;
import org.tega.repository.specifications.ProcedimientoSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
import org.tega.security.SecurityUtils;

import org.tega.service.ArchivosEntidades;
import org.tega.service.ArchivoService;

/**
 * REST controller for managing Procedimiento.
 */
@RestController
@RequestMapping("/api")
public class ProcedimientoResource {

    private final Logger log = LoggerFactory.getLogger(ProcedimientoResource.class);
        
    @Inject
    private ProcedimientoRepository procedimientoRepository;

	@Inject
	private ArchivoService archivoService;

    /**
     * POST  /procedimiento : Create a new procedimiento.
     *
     * @param procedimiento the procedimiento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedimiento, or with status 400 (Bad Request) if the procedimiento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procedimientos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Procedimiento> createProcedimiento(@Valid @RequestBody Procedimiento procedimiento) throws URISyntaxException {
        log.debug("REST request to save Procedimiento : {}", procedimiento);
        if (procedimiento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("procedimiento", "idexists", "A new procedimiento cannot already have an ID")).body(null);
        }

		if(procedimiento.getCodigo() != null){
			List<Procedimiento> p = procedimientoRepository.findByCodigoIgnoreCase(procedimiento.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        Procedimiento result = procedimientoRepository.save(procedimiento);
        return ResponseEntity.created(new URI("/api/procedimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("procedimiento", result.getId().toString()))
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
    @PutMapping("/procedimientos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Procedimiento> updateProcedimiento(@Valid @RequestBody Procedimiento procedimiento) throws URISyntaxException {
        log.debug("REST request to update Procedimiento : {}", procedimiento);
        if (procedimiento.getId() == null) {
            return createProcedimiento(procedimiento);
        }

		if(procedimiento.getCodigo() != null){
			List<Procedimiento> p = procedimientoRepository.findByCodigoModif(procedimiento.getCodigo(), procedimiento.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		if(procedimiento.getArchivos() != null){
			procedimiento.getArchivos().forEach(a -> a.addProcedimiento(procedimiento));
		}

        Procedimiento result = procedimientoRepository.save(procedimiento);

		if(result.getArchivos() != null){
			result.getArchivos().forEach(a -> a.setProcedimientos(null));
		}

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("procedimiento", procedimiento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedimiento : get all the procedimiento.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of procedimiento in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/procedimientos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Procedimiento>> getAllProcedimiento(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Procedimiento");
        Page<Procedimiento> page = procedimientoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/procedimientos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /procedimiento/:id : get the "id" procedimiento.
     *
     * @param id the id of the procedimiento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedimiento, or with status 404 (Not Found)
     */
    @GetMapping("/procedimientos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<Procedimiento> getProcedimiento(@PathVariable Long id) {
        log.debug("REST request to get Procedimiento : {}", id);
        Procedimiento procedimiento = procedimientoRepository.findOne(id);
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
    @DeleteMapping("/procedimientos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteProcedimiento(@PathVariable Long id) {
        log.debug("REST request to delete Procedimiento : {}", id);
        procedimientoRepository.delete(id);
		archivoService.eliminarCarpeta(ArchivosEntidades.PROCEDIMIENTO, id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("procedimiento", id.toString())).build();
    }

	@PutMapping("/procedimientos-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to procedimientos-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	procedimientoRepository.deleteByIdIn(ids);
			archivoService.eliminarCarpetas(ArchivosEntidades.PROCEDIMIENTO, ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("procedimiento", ids.size())).build();
    }

	@GetMapping("/procedimientos-filtered")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Procedimiento>> getProcedimientoQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of procedimientos-filtered");

		ProcedimientoCriterio criterio =  MapperUtil.getObject(cadena, ProcedimientoCriterio.class);

		if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			criterio.setPublico(true);

		Page<Procedimiento> page = procedimientoRepository.findAll(ProcedimientoSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/procedimientos-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/procedimientos-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Procedimiento>> getProcedimientoItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of procedimientos-items");

		ProcedimientoCriterio criterio =  MapperUtil.getObject(cadena, ProcedimientoCriterio.class);

		List<Procedimiento> lista = procedimientoRepository.findAll(ProcedimientoSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/procedimientos-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Procedimiento>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of procedimientos-selector");
		
		List<Procedimiento> lista = procedimientoRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/procedimientos-selectorMin")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of procedimientos-selectorMin");

		List<Object[]> lista = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			lista = procedimientoRepository.selectorMin(texto, PaginationUtil.pageSelector());
		else
			lista = procedimientoRepository.selectorMinPublico(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
