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
import org.tega.domain.Muestra;
import org.tega.domain.MuestraAtributo;
import org.tega.domain.enumeration.Sexo;
import org.tega.domain.enumeration.Entidad;
import org.tega.domain.Alelo;

import org.tega.service.AtributoService;
import org.tega.service.ArchivoService;
import org.tega.service.ArchivosEntidades;
import org.tega.service.importacion.MuestraImp;
import org.tega.service.importacion.MuestrasImpService;
import org.tega.service.importacion.MuestraImpResultado;
import org.tega.service.importacion.MuestrasAtributosImpService;
import org.tega.service.importacion.MuestrasAtributosImpResultado;

import org.tega.repository.MuestraRepository;
import org.tega.repository.AleloRepository;
import org.tega.repository.MuestraAtributoRepository;
import org.tega.repository.specifications.MuestraCriterio;
import org.tega.repository.specifications.MuestraSpecifications;

import org.tega.service.util.EnumUtil;
import org.tega.service.util.CsvToBeanUtil;
import org.tega.service.util.CsvReaderUtil;
import org.tega.service.util.MapperUtil;

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
import java.util.Set;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
import org.tega.security.SecurityUtils;

import com.opencsv.exceptions.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import com.opencsv.bean.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * REST controller for managing Muestra.
 */
@RestController
@RequestMapping("/api")
public class MuestraResource {

    private final Logger log = LoggerFactory.getLogger(MuestraResource.class);
        
    @Inject
    private MuestraRepository muestraRepository;

	@Inject
	private ArchivoService archivoService;

	@Inject
	private MuestrasImpService muestrasImpService;

	@Inject
	private MuestrasAtributosImpService muestrasAtributosImpService;

    @Inject
    private AleloRepository aleloRepository;

    @Inject
    private MuestraAtributoRepository muestraAtributoRepository;

	@Inject
    private AtributoService atributoService;


    /**
     * POST  /muestras : Create a new muestra.
     *
     * @param muestra the muestra to create
     * @return the ResponseEntity with status 201 (Created) and with body the new muestra, or with status 400 (Bad Request) if the muestra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/muestras")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Muestra> createMuestra(@Valid @RequestBody Muestra muestra) throws URISyntaxException {
        log.debug("REST request to save Muestra : {}", muestra);
        if (muestra.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("muestra", "idexists", "A new muestra cannot already have an ID")).body(null);
        }

		if(muestra.getCodigoInterno() != null){
			List<Muestra> p = muestraRepository.findByCodigoInternoIgnoreCase(muestra.getCodigoInterno());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		for(Alelo a : muestra.getAlelos())
			a.setMuestra(muestra);
		

		for(MuestraAtributo a : muestra.getAtributos())
			a.setMuestra(muestra);
		
        Muestra result = muestraRepository.save(muestra);

		for(Alelo a : result.getAlelos())
			a.setMuestra(null);
		

        return ResponseEntity.created(new URI("/api/muestras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("muestra", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /muestras : Updates an existing muestra.
     *
     * @param muestra the muestra to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated muestra,
     * or with status 400 (Bad Request) if the muestra is not valid,
     * or with status 500 (Internal Server Error) if the muestra couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/muestras")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Muestra> updateMuestra(@Valid @RequestBody final Muestra muestra) throws URISyntaxException {
        log.debug("REST request to update Muestra : {}", muestra);
        if (muestra.getId() == null) {
            return createMuestra(muestra);
        }

		if(muestra.getCodigoInterno() != null){
			List<Muestra> p = muestraRepository.findByCodigoInternoModif(muestra.getCodigoInterno(), muestra.getId());
			if(p.size() > 0){
				return ResponseEntity.badRequest().headers(HeaderUtil.codigoRepetido()).body(null);
			}
		}

		muestra.setAtributos(atributoService.filtrarValoresPorDefecto(muestra.getAtributos(), 
																	  Entidad.MUESTRA));

		muestra.getAtributos().forEach(a -> a.setMuestra(muestra));
		muestra.getAlelos().forEach(a -> a.setMuestra(muestra));
		muestra.getArchivos().forEach(a -> a.addMuestra(muestra));

        Muestra result = muestraRepository.save(muestra);

		result.getAlelos().forEach(a -> a.setMuestra(null));
		result.getArchivos().forEach(a -> a.setMuestras(null));

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("muestra", muestra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /muestras : get all the muestras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of muestras in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/muestras")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Muestra>> getAllMuestras(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Muestras");
        Page<Muestra> page = muestraRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/muestras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /muestras/:id : get the "id" muestra.
     *
     * @param id the id of the muestra to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the muestra, or with status 404 (Not Found)
     */
    @GetMapping("/muestras/{id}")
    @Timed
    public ResponseEntity<Muestra> getMuestra(@PathVariable Long id) {
        log.debug("REST request to get Muestra : {}", id);
        Muestra muestra = muestraRepository.findOneWithEagerRelationships(id);

		if(muestra != null &&
		  !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) &&
		  !muestra.getPublico())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return Optional.ofNullable(muestra)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /muestras/:id : delete the "id" muestra.
     *
     * @param id the id of the muestra to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/muestras/{id}")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteMuestra(@PathVariable Long id) throws IOException{
        log.debug("REST request to delete Muestra : {}", id);
        muestraRepository.delete(id);
		archivoService.eliminarCarpeta(ArchivosEntidades.MUESTRA, id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("muestra", id.toString())).build();
    }

	@PutMapping("/muestras-deleteByIdIn")
	@Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<Void> deleteByIdIn(@RequestBody List<Long> ids) throws IOException{
        log.debug("REST request to muestras-deleteByIdIn : {}", ids);
		if(ids.size() > 0){
        	muestraRepository.deleteByIdIn(ids);
			archivoService.eliminarCarpetas(ArchivosEntidades.MUESTRA, ids);
		}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionByIdInAlert("muestra", ids.size())).build();
    }

	private Page<Muestra> procesarQueryFiltered(Pageable pageable, String cadena)
			throws IOException{
		MuestraCriterio criterio =  MapperUtil.getObject(cadena, MuestraCriterio.class);

		if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			criterio.setPublico(true);

		return muestraRepository.findAll(MuestraSpecifications.findByCriteria(criterio),pageable);

	}

	@GetMapping("/muestras-filtered")
    @Timed
    public ResponseEntity<List<Muestra>> getMuestraQuery(Pageable pageable, @RequestParam(value="criterio") String cadena)
        throws URISyntaxException, java.io.IOException {
        log.debug("REST request to get a page of Muestras-filtered");

		Page<Muestra> page = procesarQueryFiltered(pageable, cadena);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/muestras-filtered");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	@GetMapping("/muestras-proyecto")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Muestra>> getMuestrasProyecto(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of muestras-proyecto");
		List<Muestra> lista = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			lista = muestraRepository.findByProyecto(id);
		else
			lista = muestraRepository.findByProyectoPublico(id);			

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/muestras-items")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Muestra>> getMuestraItems(@RequestParam(value="criterio") String cadena)
		throws java.io.IOException{
        log.debug("REST request to get a page of muestras-items");

		MuestraCriterio criterio =  MapperUtil.getObject(cadena, MuestraCriterio.class);

		List<Muestra> lista = muestraRepository.findAll(MuestraSpecifications.findByCriteria(criterio));

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/muestras-selector")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Muestra>> selector(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of muestras-selector");

		List<Muestra> lista = muestraRepository.selector(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

	@GetMapping("/muestras-selectorMin")
    @Timed
    public ResponseEntity<List<Object[]>> selectorMin(@RequestParam(value="texto") String texto){
        log.debug("REST request to get a page of muestras-selectorMin");

		List<Object[]> lista = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			lista = muestraRepository.selectorMin(texto, PaginationUtil.pageSelector());
		else
			lista = muestraRepository.selectorMinPublico(texto, PaginationUtil.pageSelector());

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


	@RequestMapping(value = "/muestras-importar")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<MuestraImpResultado> importar(@RequestParam("file") MultipartFile file,
														@RequestParam("sep") String sep) 
		throws IOException{

        log.debug("REST muestras-importar");
	
		ResponseEntity<MuestraImpResultado> response = null;

		File archivoDestino = File.createTempFile("muestras_imp_", ".csv");
		File archivoDestinoCodificado = null;

		try{
			archivoService.upload(file, archivoDestino);
			archivoDestinoCodificado = archivoService.cambiarCodificacion(archivoDestino);
			List<MuestraImp> lista = CsvToBeanUtil.parsear(MuestraImp.class, archivoDestinoCodificado, sep, archivoService.getCodificacion());
			MuestraImpResultado resultado = muestrasImpService.importar(lista);
			response = new ResponseEntity<>(resultado, HttpStatus.OK);
		}catch(Exception e){
			log.error("muestras-importar",e);
			response = ResponseEntity.badRequest().headers(HeaderUtil.importFailureAlert("muestra", "importacion", e.getMessage())).body(null);
		}

		archivoService.eliminar(archivoDestino);
		archivoService.eliminar(archivoDestinoCodificado);

		return response;
	}




	@RequestMapping(value = "/muestras-importar-atributos")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<MuestrasAtributosImpResultado> importarAtributos(@RequestParam("file") MultipartFile file,
																 		   @RequestParam("sep") String sep) 
		throws IOException{

        log.debug("REST muestras-importar-atributos");
	
		ResponseEntity<MuestrasAtributosImpResultado> response = null;

		File archivoDestino = File.createTempFile("muestras_imp_at_", ".csv");
		File archivoDestinoCodificado = null;

		try{
			archivoService.upload(file, archivoDestino);
			archivoDestinoCodificado = archivoService.cambiarCodificacion(archivoDestino);

			List<String[]> lista = CsvReaderUtil.parsear(archivoDestinoCodificado, sep, archivoService.getCodificacion());
			MuestrasAtributosImpResultado resultado = muestrasAtributosImpService.importar(lista);

			response = new ResponseEntity<>(resultado, HttpStatus.OK);
		}catch(Exception e){
			log.error("muestras-importar-atributos",e);
			response = ResponseEntity.badRequest().headers(HeaderUtil.importFailureAlert("muestra", "importacion", e.getMessage())).body(null);
		}

		archivoService.eliminar(archivoDestino);
		archivoService.eliminar(archivoDestinoCodificado);

		return response;
	}


	@GetMapping(value = "/muestras-exportar-atributos")
    @Timed
    public ResponseEntity<Void> exportarAtributos(Pageable pageable, 
								  @RequestParam(value="criterio") String cadena,
								  final HttpServletResponse response){

        log.debug("REST muestras-exportar-atributos");

		ResponseEntity<Void> respuesta = null;
		File archivo = null;
		try{
	
			Set<Long> ids = procesarQueryFiltered(pageable, cadena)
									.getContent()
									.stream()
									.map(Muestra::getId).collect(Collectors.toSet());

			archivo = atributoService.exportar(ids,
											   muestraAtributoRepository.findByIdsMuestras(ids),
											   Entidad.MUESTRA);

			archivoService.download(archivo, response, "muestras_atributos.tsv");

		}catch(Exception e){
			log.error(e.toString());
			respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorDownloadArchivo()).body(null);
		}finally{
			archivoService.eliminar(archivo);
		}

		return respuesta;

	}

	@GetMapping("/muestras-analisisGenotipo")
    @Timed
	@Secured(AuthoritiesConstants.ABM)
    public ResponseEntity<List<Muestra>> getMuestrasAnalisisGenotipo(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of muestras-analisisGenotipo");

		List<Muestra> muestras = muestraRepository.findByAnalisisGenotipo(id);

        return new ResponseEntity<>(muestras, HttpStatus.OK);
    }

	@GetMapping("/muestras-conjuntoMuestras")
    @Timed
	@Secured(AuthoritiesConstants.USER_EXTENDED)
    public ResponseEntity<List<Muestra>> getMuestrasConjuntoMuestras(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of muestras-conjuntoMuestras");

		List<Muestra> muestras = null;

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM))
			muestras = muestraRepository.findByConjuntoMuestras(id);
		else
			muestras = muestraRepository.findByConjuntoMuestrasPublico(id);

        return new ResponseEntity<>(muestras, HttpStatus.OK);
    }

	@GetMapping("/muestras-atributos")
    @Timed
    public ResponseEntity<List<MuestraAtributo>> getAtributos(@RequestParam(value="id") Long id){
        log.debug("REST request to get a page of muestras-atributos");

		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ABM) ||
		   Boolean.TRUE.equals(muestraRepository.esPublico(id)))
			return new ResponseEntity<List<MuestraAtributo>>(muestraAtributoRepository.findByMuestra(id), HttpStatus.OK);

		return ResponseEntity.badRequest().headers(HeaderUtil.errorPrivacidad()).body(null); 
    }

}
