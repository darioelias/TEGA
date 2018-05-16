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
import org.tega.domain.Alelo;

import org.tega.repository.AleloRepository;
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

import org.tega.service.importacion.AleloImp;
import org.tega.service.importacion.AlelosImpService;
import org.tega.service.importacion.AleloImpResultado;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.io.File;

import org.tega.service.util.MapperUtil;
import org.tega.repository.specifications.AleloCriterio;
import org.tega.repository.specifications.AleloSpecifications;

import org.springframework.web.multipart.MultipartFile;
import org.tega.service.ArchivoService;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
import org.tega.security.SecurityUtils;
import org.tega.service.util.CsvReaderUtil;
import org.tega.service.ArchivoService;
import org.tega.service.util.CsvToBeanUtil;
import org.springframework.transaction.annotation.Transactional;
/**
 * REST controller for managing Alelo.
 */
@RestController
@RequestMapping("/api")
public class AleloResource {

    private final Logger log = LoggerFactory.getLogger(AleloResource.class);
	private final static String TIPO_IMPORTACION_MATRIZ = "MATRIZ";
        
    @Inject
    private AleloRepository aleloRepository;

	@Inject
	private ArchivoService archivoService;

	@Inject
	private AlelosImpService alelosImpService;



    /**
     * POST  /alelos : Create a new alelo.
     *
     * @param alelo the alelo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alelo, or with status 400 (Bad Request) if the alelo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alelos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Alelo> createAlelo(@RequestBody Alelo alelo) throws URISyntaxException {
        log.debug("REST request to save Alelo : {}", alelo);
        if (alelo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("alelo", "idexists", "A new alelo cannot already have an ID")).body(null);
        }
        Alelo result = aleloRepository.save(alelo);
        return ResponseEntity.created(new URI("/api/alelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("alelo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alelos : Updates an existing alelo.
     *
     * @param alelo the alelo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alelo,
     * or with status 400 (Bad Request) if the alelo is not valid,
     * or with status 500 (Internal Server Error) if the alelo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alelos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Alelo> updateAlelo(@RequestBody Alelo alelo) throws URISyntaxException {
        log.debug("REST request to update Alelo : {}", alelo);
        if (alelo.getId() == null) {
            return createAlelo(alelo);
        }
        Alelo result = aleloRepository.save(alelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("alelo", alelo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alelos : get all the alelos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of alelos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/alelos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Alelo>> getAllAlelos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Alelos");
        Page<Alelo> page = aleloRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/alelos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /alelos/:id : get the "id" alelo.
     *
     * @param id the id of the alelo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alelo, or with status 404 (Not Found)
     */
    @GetMapping("/alelos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<Alelo> getAlelo(@PathVariable Long id) {
        log.debug("REST request to get Alelo : {}", id);
        Alelo alelo = aleloRepository.findOneWithEagerRelationships(id);

		if(alelo != null &&
		  !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) &&
		  !alelo.getPublico())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return Optional.ofNullable(alelo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alelos/:id : delete the "id" alelo.
     *
     * @param id the id of the alelo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alelos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteAlelo(@PathVariable Long id) {
        log.debug("REST request to delete Alelo : {}", id);
        aleloRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("alelo", id.toString())).build();
    }

	@PutMapping("/alelos-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids){
        log.debug("REST request to alelos-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	aleloRepository.deleteByIdIn(ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("alelo", ids.size())).build();
    }

	@GetMapping("/alelos-filtered")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Alelo>> getAleloQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of Alelos-filtered");

		AleloCriterio criterio =  MapperUtil.getObject(cadena, AleloCriterio.class);

		if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			criterio.setPublico(true);

		Page<Alelo> page = aleloRepository.findAll(AleloSpecifications.findByCriteria(criterio),pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/Alelos-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/alelos-analisis-genotipo")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public List<Object[]> findByAnalisisGenotipo(@RequestParam(value="id") Long id) {
        log.debug("REST request to get Alelos by Analisis Genotipo");
		
		List<Object[]> alelos = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
        	alelos = aleloRepository.findByAnalisisGenotipoMin(id);
		else
			alelos = aleloRepository.findByAnalisisGenotipoMinPublico(id);

        return alelos;
    }


	@GetMapping("/alelos-muestra")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Alelo>> findByMuestra(@RequestParam(value="id") Long id) {
        log.debug("REST request to get Alelos by Muestra");

        List<Alelo> alelos = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
        	alelos = aleloRepository.findByMuestra(id);
		else
        	alelos = aleloRepository.findByMuestraPublico(id);			

        return new ResponseEntity<>(alelos, HttpStatus.OK);
    }

	@PutMapping("/alelos-masivo")
    @Timed
	@ResponseStatus(value = HttpStatus.OK)
	@Secured(AuthoritiesConstants.ABM)
	@Transactional
    public void createUpdateAlelos(@RequestBody List<Alelo> alelos) throws URISyntaxException {
        log.debug("REST request to update Alelo Masivo : {}", alelos);
	
		for(Alelo alelo : alelos){
			if (alelo.getId() != null ){
				if(alelo.getValor() == null || alelo.getValor().trim().length() == 0) 
			    	aleloRepository.delete(alelo.getId());
				else
					aleloRepository.save(alelo);
			}else{
				aleloRepository.save(alelo);
			}
		}

    }

	@GetMapping("/alelos-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Alelo>> getAleloItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of alelos-items");

		AleloCriterio criterio =  MapperUtil.getObject(cadena, AleloCriterio.class);

		List<Alelo> lista = aleloRepository.findAll(AleloSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@RequestMapping(value = "/alelos-importar")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<AleloImpResultado> importar(@RequestParam("file") MultipartFile file,
														@RequestParam("tipo") String tipo,
														@RequestParam("sep") String sep,
														@RequestParam("nulo") String nulo,
														@RequestParam("publico") Boolean publico) 
		throws IOException{

        log.debug("REST alelos-importar");
	
		ResponseEntity<AleloImpResultado> response = null;

		File archivoDestino = File.createTempFile("alelos_imp_", ".csv");
		File archivoDestinoCodificado = null;

		try{
			archivoService.upload(file, archivoDestino);
			archivoDestinoCodificado = archivoService.cambiarCodificacion(archivoDestino);
			AleloImpResultado resultado = null;
			if(TIPO_IMPORTACION_MATRIZ.equals(tipo)){
				List<String[]> lista = CsvReaderUtil.parsear(archivoDestinoCodificado, sep, archivoService.getCodificacion());
				resultado = alelosImpService.importar(lista, nulo, publico);
			}else{
				List<AleloImp> lista = CsvToBeanUtil.parsear(AleloImp.class, archivoDestinoCodificado, sep, archivoService.getCodificacion());
				resultado = alelosImpService.importar(lista);
			}
			response = new ResponseEntity<>(resultado, HttpStatus.OK);
		}catch(Exception e){
			log.error("alelos-importar",e);
			response = ResponseEntity.badRequest().headers(HeaderUtil.importFailureAlert("alelo", "importacion", e.getMessage())).body(null);
		}

		archivoService.eliminar(archivoDestino);
		archivoService.eliminar(archivoDestinoCodificado);

		return response;
	}

}
