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
import org.tega.domain.ParametroTega;

import org.tega.repository.ParametroTegaRepository;
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
import org.tega.repository.specifications.ParametroTegaCriterio;
import org.tega.repository.specifications.ParametroTegaSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
/**
 * REST controller for managing ParametroTega.
 */
@RestController
@RequestMapping("/api")
public class ParametroTegaResource {

    private final Logger log = LoggerFactory.getLogger(ParametroTegaResource.class);
        
    @Inject
    private ParametroTegaRepository parametroTegaRepository;


    /**
     * POST  /ParametroTega : Create a new ParametroTega.
     *
     * @param ParametroTega the ParametroTega to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ParametroTega, or with status 400 (Bad Request) if the parametro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametros-tega")
    @Timed
	@Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<ParametroTega> createParametroTega(@Valid @RequestBody ParametroTega parametro) throws URISyntaxException {
        log.debug("REST request to save ParametroTega : {}", parametro);
        if (parametro.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("parametroTega", "idexists", "A new parametro cannot already have an ID")).body(null);
        }

		if(parametro.getCodigo() != null){
			List<ParametroTega> p = parametroTegaRepository.findByCodigoIgnoreCase(parametro.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        ParametroTega result = parametroTegaRepository.save(parametro);
        return ResponseEntity.created(new URI("/api/parametros-tega/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("parametroTega", result.getId().toString()))
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
    @PutMapping("/parametros-tega")
    @Timed
	@Secured(AuthoritiesConstants.MANAGER)
    public ResponseEntity<ParametroTega> updateParametroTega(@Valid @RequestBody ParametroTega parametro) throws URISyntaxException {
        log.debug("REST request to update ParametroTega : {}", parametro);
        if (parametro.getId() == null) {
            return createParametroTega(parametro);
        }

		if(parametro.getCodigo() != null){
			List<ParametroTega> p = parametroTegaRepository.findByCodigoModif(parametro.getCodigo(), parametro.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

        ParametroTega result = parametroTegaRepository.save(parametro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("parametroTega", parametro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametro : get all the parametro.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parametro in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/parametros-tega")
    @Timed
	@Secured(AuthoritiesConstants.MANAGER)
    public ResponseEntity<List<ParametroTega>> getAllParametroTega(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ParametroTega");
        Page<ParametroTega> page = parametroTegaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametros-tega");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parametro/:id : get the "id" parametro.
     *
     * @param id the id of the parametro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametro, or with status 404 (Not Found)
     */
    @GetMapping("/parametros-tega/{id}")
    @Timed
	@Secured(AuthoritiesConstants.MANAGER)
    public ResponseEntity<ParametroTega> getParametroTega(@PathVariable Long id) {
        log.debug("REST request to get ParametroTega : {}", id);
        ParametroTega parametro = parametroTegaRepository.findOne(id);
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
    @DeleteMapping("/parametros-tega/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteParametroTega(@PathVariable Long id) {
        log.debug("REST request to delete ParametroTega : {}", id);
        parametroTegaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("parametroTega", id.toString())).build();
    }

	@PutMapping("/parametros-tega-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to parametros-tega-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	parametroTegaRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("parametroTega", ids.size())).build();
    }

	@GetMapping("/parametros-tega-filtered")
    @Timed
	@Secured(AuthoritiesConstants.MANAGER)
    public ResponseEntity<List<ParametroTega>> getParametroTegaQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of parametros-tega-filtered");

		ParametroTegaCriterio criterio =  MapperUtil.getObject(cadena, ParametroTegaCriterio.class);

		Page<ParametroTega> page = parametroTegaRepository.findAll(ParametroTegaSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametros-tega-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/parametros-tega-items")
    @Timed
	@Secured(AuthoritiesConstants.MANAGER)
    public ResponseEntity<List<ParametroTega>> getParametroTegaItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of parametros-tega-items");

		ParametroTegaCriterio criterio =  MapperUtil.getObject(cadena, ParametroTegaCriterio.class);

		List<ParametroTega> lista = parametroTegaRepository.findAll(ParametroTegaSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/parametros-tega-selector")
    @Timed
	@Secured(AuthoritiesConstants.MANAGER)
    public ResponseEntity<List<ParametroTega>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of parametros-tega-selector");

		
		List<ParametroTega> lista = parametroTegaRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/parametros-tega-selectorMin")
    @Timed
	@Secured(AuthoritiesConstants.MANAGER)
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of parametros-tega-selectorMin");

		List<Object[]> lista = parametroTegaRepository.selectorMin(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/parametros-tega-codigo")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<ParametroTega> getParametroTegaCodigo(@RequestParam(value="codigo") String codigo) {
        log.debug("REST request to get ParametroTega : {}", codigo);
        ParametroTega parametro = parametroTegaRepository.buscarPorCodigo(codigo);
        return Optional.ofNullable(parametro)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/parametros-tega-codigo-publico")
    @Timed
    public ResponseEntity<ParametroTega> getParametroTegaCodigoPublico(@RequestParam(value="codigo") String codigo) {
        log.debug("REST request to get ParametroTega Público : {}", codigo);
        ParametroTega parametro = parametroTegaRepository.buscarPorCodigoPublico(codigo);
        return Optional.ofNullable(parametro)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
