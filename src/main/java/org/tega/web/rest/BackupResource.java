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

import javax.servlet.http.HttpServletResponse;
import org.tega.service.util.MapperUtil;
import org.tega.service.proc.Backup;
import org.tega.service.proc.ResultadoComando;
import org.tega.service.ArchivoService;
import org.springframework.security.access.annotation.Secured;
import org.tega.security.AuthoritiesConstants;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/api")
public class BackupResource {

    private final Logger log = LoggerFactory.getLogger(BackupResource.class);
  
	@Inject
	private Backup backup;

	@Inject
	private ArchivoService archivoService;

	@GetMapping("/backup-crear")
	@Secured({AuthoritiesConstants.MANAGER})
    public ResponseEntity<ResultadoComando> crearBackup(final HttpServletResponse response) {
    	
		ResponseEntity<ResultadoComando> respuesta = null;

		try{
			ResultadoComando result = backup.crearBackup();
			respuesta = ResponseEntity.ok().body(result);
		}catch(Exception e){
			log.error(e.toString());
			respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorBackup(e.toString())).body(null);
		}

		return respuesta;
	}

	@GetMapping("/backup-descargar")
	@Secured({AuthoritiesConstants.MANAGER})
    public ResponseEntity<Void> descargar(final HttpServletResponse response) throws Exception{
		
		ResponseEntity<Void> respuesta = null;

		try{
			File archivo = backup.buscarUltimo();

			if(archivo != null)
				archivoService.download(archivo, response);
	   		else
				respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorBackup("backup.error-descarga")).body(null);
			   		
		}catch(Exception e){
			log.error(e.toString());
			respuesta = ResponseEntity.badRequest().headers(HeaderUtil.errorBackup(e.toString())).body(null);
		}

		return respuesta;
	}

}
