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
import org.tega.domain.ConjuntoMuestras;
import org.tega.domain.ConjuntoMuestrasAtributo;
import org.tega.domain.enumeration.Entidad;

import org.tega.service.AtributoService;
import org.tega.service.ArchivoService;

import org.tega.repository.ConjuntoMuestrasRepository;
import org.tega.web.rest.util.HeaderUtil;
import org.tega.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.File;

import org.tega.repository.specifications.ConjuntoMuestrasSpecifications;
import org.tega.repository.specifications.ConjuntoMuestrasCriterio;
import org.tega.service.util.MapperUtil;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
import org.tega.security.SecurityUtils;
/**
 * REST controller for managing ConjuntoMuestras.
 */
@RestController
@RequestMapping("/api")
public class ConjuntoMuestrasResource {

    private final Logger log = LoggerFactory.getLogger(ConjuntoMuestrasResource.class);
        
    @Inject
    private ConjuntoMuestrasRepository conjuntoMuestrasRepository;

	@Inject
	private ArchivoService archivoService;

	@Inject
    private AtributoService atributoService;

    /**
     * POST  /conjuntoMuestrass : Create a new conjuntoMuestras.
     *
     * @param conjuntoMuestras the conjuntoMuestras to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conjuntoMuestras, or with status 400 (Bad Request) if the conjuntoMuestras has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conjuntos-muestras")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<ConjuntoMuestras> createConjuntoMuestras(@Valid @RequestBody ConjuntoMuestras conjuntoMuestras) throws URISyntaxException {
        log.debug("REST request to save ConjuntoMuestras : {}", conjuntoMuestras);
        if (conjuntoMuestras.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("conjuntoMuestras", "idexists", "A new conjuntoMuestras cannot already have an ID")).body(null);
        }

		if(conjuntoMuestras.getCodigo() != null){
			List<ConjuntoMuestras> p = conjuntoMuestrasRepository.findByCodigoIgnoreCase(conjuntoMuestras.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		conjuntoMuestras.getAtributos().forEach(a -> a.setConjuntoMuestras(conjuntoMuestras));

        ConjuntoMuestras result = conjuntoMuestrasRepository.save(conjuntoMuestras);
        return ResponseEntity.created(new URI("/api/conjuntos-muestras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("conjuntoMuestras", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conjuntoMuestrass : Updates an existing conjuntoMuestras.
     *
     * @param conjuntoMuestras the conjuntoMuestras to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conjuntoMuestras,
     * or with status 400 (Bad Request) if the conjuntoMuestras is not valid,
     * or with status 500 (Internal Server Error) if the conjuntoMuestras couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conjuntos-muestras")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<ConjuntoMuestras> updateConjuntoMuestras(@Valid @RequestBody ConjuntoMuestras conjuntoMuestras) throws URISyntaxException {
        log.debug("REST request to update ConjuntoMuestras : {}", conjuntoMuestras);
        if (conjuntoMuestras.getId() == null) {
            return createConjuntoMuestras(conjuntoMuestras);
        }

		if(conjuntoMuestras.getCodigo() != null){
			List<ConjuntoMuestras> p = conjuntoMuestrasRepository.findByCodigoModif(conjuntoMuestras.getCodigo(), conjuntoMuestras.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		conjuntoMuestras.setAtributos(atributoService.filtrarValoresPorDefecto(conjuntoMuestras.getAtributos(), 
																	  Entidad.CONJUNTO_MUESTRAS));
		conjuntoMuestras.getAtributos().forEach(a -> a.setConjuntoMuestras(conjuntoMuestras));

        ConjuntoMuestras result = conjuntoMuestrasRepository.save(conjuntoMuestras);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("conjuntoMuestras", conjuntoMuestras.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conjuntoMuestrass : get all the conjuntoMuestrass.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of conjuntoMuestrass in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/conjuntos-muestras")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ConjuntoMuestras>> getAllConjuntosMuestras(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ConjuntosMuestras");
        Page<ConjuntoMuestras> page = conjuntoMuestrasRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/conjuntos-muestras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /conjuntoMuestrass/:id : get the "id" conjuntoMuestras.
     *
     * @param id the id of the conjuntoMuestras to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conjuntoMuestras, or with status 404 (Not Found)
     */
    @GetMapping("/conjuntos-muestras/{id}")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<ConjuntoMuestras> getConjuntoMuestras(@PathVariable Long id) {
        log.debug("REST request to get ConjuntoMuestras : {}", id);
        ConjuntoMuestras conjuntoMuestras = conjuntoMuestrasRepository.findOneWithEagerRelationships(id);

		if(conjuntoMuestras != null &&
		  !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) &&
		  !conjuntoMuestras.getPublico())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return Optional.ofNullable(conjuntoMuestras)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /conjuntoMuestrass/:id : delete the "id" conjuntoMuestras.
     *
     * @param id the id of the conjuntoMuestras to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conjuntos-muestras/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteConjuntoMuestras(@PathVariable Long id) {
        log.debug("REST request to delete ConjuntoMuestras : {}", id);
        conjuntoMuestrasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("conjuntoMuestras", id.toString())).build();
    }

	@PutMapping("/conjuntos-muestras-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to conjuntos-muestras-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	conjuntoMuestrasRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("conjuntoMuestras", ids.size())).build();
    }


	private Page<ConjuntoMuestras> procesarQueryFiltered(Pageable pageable, String cadena)
			throws IOException{
		ConjuntoMuestrasCriterio criterio =  MapperUtil.getObject(cadena, ConjuntoMuestrasCriterio.class);

		if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			criterio.setPublico(true);

		return conjuntoMuestrasRepository.findAll(ConjuntoMuestrasSpecifications.findByCriteria(criterio),pageable);


	}

	@GetMapping("/conjuntos-muestras-filtered")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<ConjuntoMuestras>> getConjuntosMuestrasQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of conjuntos-muestras-filtered");

		Page<ConjuntoMuestras> page = procesarQueryFiltered(pageable, cadena);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/conjuntos-muestras-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


	@GetMapping("/conjuntos-muestras-analisisGenotipo")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<ConjuntoMuestras>> getConjuntosMuestrasAnalisisGenotipo(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of conjuntos-muestras-analisisGenotipo");

		List<ConjuntoMuestras> conjuntosMuestras = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			conjuntosMuestras = conjuntoMuestrasRepository.findByAnalisisGenotipo(id);
		else
			conjuntosMuestras = conjuntoMuestrasRepository.findByAnalisisGenotipoPublico(id);	
		
        return new ResponseEntity<>(conjuntosMuestras, HttpStatus.OK);
    }

	@GetMapping("/conjuntos-muestras-muestra")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<ConjuntoMuestras>> getConjuntosMuestrasMuestra(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of conjuntos-muestras-muestra");

		List<ConjuntoMuestras> conjuntosMuestras = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			conjuntosMuestras = conjuntoMuestrasRepository.findByMuestra(id);
		else
			conjuntosMuestras = conjuntoMuestrasRepository.findByMuestraPublico(id);

        return new ResponseEntity<>(conjuntosMuestras, HttpStatus.OK);
    }

	@GetMapping("/conjuntos-muestras-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ConjuntoMuestras>> getConjuntoMuestrasItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of conjuntos-muestras-items");

		ConjuntoMuestrasCriterio criterio =  MapperUtil.getObject(cadena, ConjuntoMuestrasCriterio.class);

		List<ConjuntoMuestras> lista = conjuntoMuestrasRepository.findAll(ConjuntoMuestrasSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/conjuntos-muestras-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<ConjuntoMuestras>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of conjuntos-muestras-selector");

		List<ConjuntoMuestras> lista = conjuntoMuestrasRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/conjuntos-muestras-selectorMin")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of conjuntos-muestras-selectorMin");

		List<Object[]> lista = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			lista = conjuntoMuestrasRepository.selectorMin(texto, PaginationUtil.pageSelector());
		else
			lista = conjuntoMuestrasRepository.selectorMinPublico(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/conjuntos-muestras-atributos")
    @Timed
    public ResponseEntity<List<ConjuntoMuestrasAtributo>> getAtributos(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of conjuntos-muestras-atributos");

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) ||
		   Boolean.TRUE.equals(conjuntoMuestrasRepository.esPublico(id)))
			return new ResponseEntity<List<ConjuntoMuestrasAtributo>>(conjuntoMuestrasRepository.findAtributos(id), HttpStatus.OK);

		return ResponseEntity.badRequest().headers(HeaderUtil.errorPrivacidad()).body(null);   
	}


	@GetMapping(value = "/conjuntos-muestras-exportar-atributos")
    @Timed
    public ResponseEntity<Void> exportarAtributos(Pageable pageable, 
										  @RequestParam(value="criterio") String cadena,
										  final HttpServletResponse response) {

        log.debug("REST conjuntos-muestras-exportar-atributos");

		ResponseEntity<Void> respuesta = null;
		File archivo = null;
		try{
	
			Set<Long> ids = procesarQueryFiltered(pageable, cadena)
									.getContent()
									.stream()
									.map(ConjuntoMuestras::getId).collect(Collectors.toSet());

			archivo = atributoService.exportar(ids,
											   conjuntoMuestrasRepository.findAtributosByIdsObj(ids),
											   Entidad.CONJUNTO_MUESTRAS);

			archivoService.download(archivo, response, "conjuntos_muestras_atributos.tsv");
	
		}catch(Exception e){
			log.error(e.toString());
			respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorDownloadArchivo()).body(null);
		}finally{
			archivoService.eliminar(archivo);
		}

		return respuesta;

	}

}
