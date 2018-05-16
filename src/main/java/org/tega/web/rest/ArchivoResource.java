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
import org.tega.domain.AnalisisGenotipo;
import org.tega.domain.Archivo;
import org.tega.domain.Proyecto;

import org.tega.service.ArchivosEntidades;
import org.tega.service.ArchivoService;
import org.tega.repository.ArchivoRepository;
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
import java.util.Set;
import java.util.Optional;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;

import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
import org.tega.security.SecurityUtils;

/**
 * REST controller for managing Archivo.
 */
@RestController
@RequestMapping("/api")
public class ArchivoResource {

    private final Logger log = LoggerFactory.getLogger(ArchivoResource.class);
        
    @Inject
    private ArchivoRepository archivoRepository;

	@Inject
    private ArchivoService archivoService;

    /**
     * POST  /archivos : Create a new archivo.
     *
     * @param archivo the archivo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new archivo, or with status 400 (Bad Request) if the archivo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/archivos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Archivo> createArchivo(@Valid @RequestBody Archivo archivo) throws URISyntaxException {
        log.debug("REST request to save Archivo : {}", archivo);
        if (archivo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("archivo", "idexists", "A new archivo cannot already have an ID")).body(null);
        }
        Archivo result = archivoRepository.save(archivo);
        return ResponseEntity.created(new URI("/api/archivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("archivo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /archivos : Updates an existing archivo.
     *
     * @param archivo the archivo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated archivo,
     * or with status 400 (Bad Request) if the archivo is not valid,
     * or with status 500 (Internal Server Error) if the archivo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/archivos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Archivo> updateArchivo(@Valid @RequestBody Archivo archivo) throws URISyntaxException {
        log.debug("REST request to update Archivo : {}", archivo);
        if (archivo.getId() == null) {
            return createArchivo(archivo);
        }
        Archivo result = archivoRepository.save(archivo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("archivo", archivo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /archivos : get all the archivos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of archivos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/archivos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Archivo>> getAllArchivos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Archivos");
        Page<Archivo> page = archivoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/archivos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /archivos/:id : get the "id" archivo.
     *
     * @param id the id of the archivo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the archivo, or with status 404 (Not Found)
     */
    @GetMapping("/archivos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Archivo> getArchivo(@PathVariable Long id) {
        log.debug("REST request to get Archivo : {}", id);
        Archivo archivo = archivoRepository.findOne(id);
        return Optional.ofNullable(archivo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /archivos/:id : delete the "id" archivo.
     *
     * @param id the id of the archivo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/archivos/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteArchivo(@PathVariable Long id) {
        log.debug("REST request to delete Archivo : {}", id);

		try{
			Archivo archivo = archivoRepository.findOne(id);
			archivoService.eliminar(archivo.getDireccion());
		}catch(Exception e){}

        archivoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("archivo", id.toString())).build();
    }


	@RequestMapping(value = "/archivo-upload")
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Archivo> upload( @RequestParam("file") MultipartFile file, 
										   @RequestParam("detalle") String detalle, 
										   @RequestParam("idPadre") Long idPadre,
										   @RequestParam("tipoPadre") ArchivosEntidades tipoPadre,
										   @RequestParam("publico") Boolean publico) 
		throws IOException {

		log.debug("REST archivo-upload");

		Archivo archivo = archivoService.upload(file, detalle, idPadre, tipoPadre, publico);

		if(archivo != null)
			return ResponseEntity.ok()
						.headers(HeaderUtil.createEntityUpdateAlert("archivo", archivo.getId().toString()))
						.body(archivo);
		
		return ResponseEntity.badRequest().headers(HeaderUtil.errorUploadArchivo()).body(null);

    }


	@GetMapping(value = "/archivo-download")
	@Secured({AuthoritiesConstants.ABM, AuthoritiesConstants.USER_EXTENDED})
    public ResponseEntity<Void> download(@RequestParam ("id") Long id, final HttpServletResponse response) throws IOException {
       
		ResponseEntity<Void> respuesta = null;

		try{
			Archivo archivo = archivoRepository.findOne(id);

			if(archivo != null){
				if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) || archivo.getPublico())
					archivoService.download(archivo.getDireccion(), response);
				else
					respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorDownloadArchivo()).body(null);
			}else{
				respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorDownloadArchivo()).body(null);
			}
		}catch(Exception e){
			log.error(e.toString());
			respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorDownloadArchivo()).body(null);
		}

		return respuesta;
    }


	@GetMapping("/archivos-analisisGenotipo")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Archivo>> getArchivosAnalisisGenotipo(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of archivo-analisisGenotipo");
	
		List<Archivo> archivos =  null;
	
		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			archivos = archivoRepository.findByAnalisisGenotipo(id);
		else
			archivos = archivoRepository.findByAnalisisGenotipoPublico(id);


        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }



	@GetMapping("/archivos-muestra")
    @Timed
	@Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<Archivo>> getArchivosMuestra(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of archivo-muestra");

		List<Archivo> archivos =  null;
	
		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			archivos = archivoRepository.findByMuestra(id);
		else
			archivos = archivoRepository.findByMuestraPublico(id);

        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }

	@GetMapping("/archivos-proyecto")
    @Timed
	@Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<Archivo>> getArchivosProyecto(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of archivo-proyecto");

		List<Archivo> archivos =  null;
	
		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			archivos = archivoRepository.findByProyecto(id);
		else
			archivos = archivoRepository.findByProyectoPublico(id);

        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }

	@GetMapping("/archivos-procedimiento")
    @Timed
	@Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<Archivo>> getArchivosProcedimiento(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of archivo-procedimiento");

		List<Archivo> archivos =  null;
	
		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			archivos = archivoRepository.findByProcedimiento(id);
		else
			archivos = archivoRepository.findByProcedimientoPublico(id);

        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }

	@GetMapping("/archivos-ejecucion")
    @Timed
	@Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Set<Archivo>> getArchivosEjecucion(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of archivo-ejecucion");

		Set<Archivo> archivos =  null;
	
		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			archivos = archivoRepository.findByEjecucion(id);
		else
			archivos = archivoRepository.findByEjecucionPublico(id);

        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }


//	@GetMapping("/archivos-items")
//    @Timed
//    public ResponseEntity<List<Archivo>> getArchivoItems(@RequestParam(value="criterio") String cadena)
//		throws java.io.IOException{
//        log.debug("REST request to get a page of archivos-items");

//		ArchivoCriterio criterio =  MapperUtil.getObject(cadena, ArchivoCriterio.class);

//		List<Archivo> lista = archivoRepository.findAll(ArchivoSpecifications.findByCriteria(criterio));

//        return new ResponseEntity<>(lista, HttpStatus.OK);
//    }

}
