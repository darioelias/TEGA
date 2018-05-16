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
import org.tega.domain.Locus;
import org.tega.domain.LocusAtributo;
import org.tega.domain.enumeration.Entidad;

import org.tega.repository.LocusRepository;
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

import org.tega.service.AtributoService;
import org.tega.service.ArchivoService;

import org.tega.service.util.MapperUtil;
import org.tega.repository.specifications.LocusCriterio;
import org.tega.repository.specifications.LocusSpecifications;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
import org.tega.security.SecurityUtils;
/**
 * REST controller for managing Locus.
 */
@RestController
@RequestMapping("/api")
public class LocusResource {

    private final Logger log = LoggerFactory.getLogger(LocusResource.class);
        
    @Inject
    private LocusRepository locusRepository;

	@Inject
	private ArchivoService archivoService;

	@Inject
    private AtributoService atributoService;


    /**
     * POST  /loci : Create a new locus.
     *
     * @param locus the locus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locus, or with status 400 (Bad Request) if the locus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loci")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Locus> createLocus(@Valid @RequestBody Locus locus) throws URISyntaxException {
        log.debug("REST request to save Locus : {}", locus);
        if (locus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("locus", "idexists", "A new locus cannot already have an ID")).body(null);
        }

		if(locus.getCodigo() != null){
			List<Locus> p = locusRepository.findByCodigoIgnoreCase(locus.getCodigo());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		for(LocusAtributo a : locus.getAtributos())
			a.setLocus(locus);

        Locus result = locusRepository.save(locus);
        return ResponseEntity.created(new URI("/api/loci/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("locus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loci : Updates an existing locus.
     *
     * @param locus the locus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locus,
     * or with status 400 (Bad Request) if the locus is not valid,
     * or with status 500 (Internal Server Error) if the locus couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loci")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Locus> updateLocus(@Valid @RequestBody Locus locus) throws URISyntaxException {
        log.debug("REST request to update Locus : {}", locus);
        if (locus.getId() == null) {
            return createLocus(locus);
        }

		if(locus.getCodigo() != null){
			List<Locus> p = locusRepository.findByCodigoModif(locus.getCodigo(), locus.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		locus.setAtributos(atributoService.filtrarValoresPorDefecto(locus.getAtributos(), 
																	Entidad.LOCUS));
		locus.getAtributos().forEach(a -> a.setLocus(locus));

        Locus result = locusRepository.save(locus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("locus", locus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loci : get all the loci.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of loci in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/loci")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Locus>> getAllLoci(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Loci");
        Page<Locus> page = locusRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/loci");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /loci/:id : get the "id" locus.
     *
     * @param id the id of the locus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locus, or with status 404 (Not Found)
     */
    @GetMapping("/loci/{id}")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<Locus> getLocus(@PathVariable Long id) {
        log.debug("REST request to get Locus : {}", id);
        Locus locus = locusRepository.findOne(id);

		if(locus != null &&
		  !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) &&
		  !locus.getPublico())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return Optional.ofNullable(locus)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /loci/:id : delete the "id" locus.
     *
     * @param id the id of the locus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loci/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteLocus(@PathVariable Long id) {
        log.debug("REST request to delete Locus : {}", id);
        locusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("locus", id.toString())).build();
    }

	@PutMapping("/loci-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to loci-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	locusRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("locus", ids.size())).build();
    }

	private Page<Locus> procesarQueryFiltered(Pageable pageable, String cadena)
			throws IOException{
		LocusCriterio criterio =  MapperUtil.getObject(cadena, LocusCriterio.class);

		if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			criterio.setPublico(true);

		return locusRepository.findAll(LocusSpecifications.findByCriteria(criterio),pageable);

	}

	@GetMapping("/loci-filtered")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Locus>> getLocusQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of loci-filtered");

		Page<Locus> page  = procesarQueryFiltered(pageable, cadena);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/loci-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/loci-analisisGenotipo")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Locus>> getLocusAnalisisGenotipo(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of loci-analisisGenotipo");

		List<Locus> loci = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			loci = locusRepository.findByAnalisisGenotipo(id);
		else
			loci = locusRepository.findByAnalisisGenotipoPublico(id);

        return new ResponseEntity<>(loci, HttpStatus.OK);
    }


	@GetMapping("/loci-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Locus>> getLocusItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of loci-items");

		LocusCriterio criterio =  MapperUtil.getObject(cadena, LocusCriterio.class);

		List<Locus> loci = locusRepository.findAll(LocusSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(loci, HttpStatus.OK);
    }

	@GetMapping("/loci-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Locus>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of loci-selector");

		List<Locus> lista = locusRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/loci-selectorMin")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of loci-selectorMin");

		List<Object[]> lista = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			lista = locusRepository.selectorMin(texto, PaginationUtil.pageSelector());
		else
			lista = locusRepository.selectorMinPublico(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


	@GetMapping("/loci-selectorCompleto")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Locus>> selectorCompleto(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of loci-selectorCompleto");

		List<Locus> lista = locusRepository.selectorCompleto(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/loci-atributos")
    @Timed
    public ResponseEntity<List<LocusAtributo>> getAtributos(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of loci-atributos");

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) ||
		   Boolean.TRUE.equals(locusRepository.esPublico(id)))
			return new ResponseEntity<List<LocusAtributo>>(locusRepository.findAtributos(id), HttpStatus.OK);

		return ResponseEntity.badRequest().headers(HeaderUtil.errorPrivacidad()).body(null);   
	}

	@GetMapping(value = "/loci-exportar-atributos")
    @Timed
    public ResponseEntity<Void> exportarAtributos(Pageable pageable, 
										  @RequestParam(value="criterio") String cadena,
										  final HttpServletResponse response) {

        log.debug("REST loci-exportar-atributos");

		ResponseEntity<Void> respuesta = null;
		File archivo = null;
		try{
	
			Set<Long> ids = procesarQueryFiltered(pageable, cadena)
									.getContent()
									.stream()
									.map(Locus::getId).collect(Collectors.toSet());

			archivo = atributoService.exportar(ids,
											   locusRepository.findAtributosByIdsObj(ids),
											   Entidad.LOCUS);

			archivoService.download(archivo, response, "loci_atributos.tsv");

		}catch(Exception e){
			log.error(e.toString());
			respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorDownloadArchivo()).body(null);
		}finally{
			archivoService.eliminar(archivo);
		}

		return respuesta;

	}

}
