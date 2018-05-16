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


package org.tega.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Date;
import java.util.Arrays;

import org.tega.domain.Archivo;

import org.tega.repository.ArchivoRepository;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.io.comparator.LastModifiedFileComparator;

@Service
public class ArchivoService {

	private static long TEMP_DIAS = 1L;

	private final Logger log = LoggerFactory.getLogger(ArchivoService.class);

	private static final String PARAMETRO_DIRECTORIO_ENTIDADES = "DIRECTORIO_ENTIDADES";
	private static final String DIRECTORIO_ENTIDADES_POR_DEFECTO = "archivos";
	private static final String PARAMETRO_CODIFICACION_ARCHIVOS = "CODIFICACION_ARCHIVOS";
	private static final String PARAMETRO_CODIFICACION_ARCHIVOS_ORIGEN = "CODIFICACION_ARCHIVOS_ORIGEN";
	private static final String CODIFICACION_POR_DEFECTO = "UTF-8";
	private static final String PATH_SEPERADOR = File.separator;

	@Inject
	private ArchivoRepository archivoRepository;

	@Inject
	private ParametroTegaService parametroTegaService;

	public String getCodificacion(){
		return parametroTegaService.getString(PARAMETRO_CODIFICACION_ARCHIVOS, CODIFICACION_POR_DEFECTO);
	}

	public String getCodificacionOrigen(){
		return parametroTegaService.getString(PARAMETRO_CODIFICACION_ARCHIVOS_ORIGEN, CODIFICACION_POR_DEFECTO);
	}

	public String getPathArchivosEntidades(){
		return parametroTegaService.getString(PARAMETRO_DIRECTORIO_ENTIDADES, DIRECTORIO_ENTIDADES_POR_DEFECTO); 
	}

	public String getPathArchivosEntidades(ArchivosEntidades archivoEnum){
		return getPathArchivosEntidades() + PATH_SEPERADOR + archivoEnum.getPath();
	}

	public String getPathArchivosEntidades(ArchivosEntidades archivoEnum, Long idPadre){
		return getPathArchivosEntidades(archivoEnum) + PATH_SEPERADOR + idPadre;
	}

	public String getPathAbsolutoArchivosEntidades(ArchivosEntidades archivoEnum, Long idPadre) throws Exception{
		return (new File(getPathArchivosEntidades(archivoEnum,idPadre))).getAbsolutePath();
	}

	@Transactional
	public void crearArchivos(ArchivosEntidades arcEnt, Long idPadre, File[] archivos){
		log.debug("ArchivoService.crearArchivos: {}",idPadre);

		try{

			for(File f : archivos){
				Archivo archivo = new Archivo();
				archivo.setDireccion(f.getPath());
				archivo.setDetalle(f.getParentFile().getName()+f.separator+f.getName());
				arcEnt.setPadre(archivo, idPadre);
				archivoRepository.save(archivo);
			}
			
		}catch(Exception e){
			log.error("ArchivoService",e);
		}

	}



	public void eliminar(String nomArchivo){

		try{
			File file = new File (nomArchivo);
			file.delete();
		}catch(Exception e){
			log.error("ArchivoService",e);
		}

	}

	public void eliminar(File file){

		try{
			if(file != null)
				file.delete();
		}catch(Exception e){
			log.error("ArchivoService",e);
		}

	}

	public void eliminarCarpeta(String carpeta){
		try{
			File file = new File(carpeta);
			if(file.exists()){
				FileUtils.deleteDirectory(file);
			}
		}catch(Exception e){
			log.error("ArchivoService",e);
		}
	}

	public void eliminarCarpeta(ArchivosEntidades archivoEntidad, Long idPadre){
		eliminarCarpeta(getPathArchivosEntidades(archivoEntidad, idPadre));
	}

	public void eliminarCarpetas(ArchivosEntidades archivoEntidad, List<Long> idsPadre){
		for(Long id : idsPadre)
			eliminarCarpeta(getPathArchivosEntidades(archivoEntidad, id));
	}

    public void download(String nomArchivo, HttpServletResponse response) throws IOException {

        File file = new File (nomArchivo);
       	download(file, response, file.getName());
    }

    public void download(File file, HttpServletResponse response) throws IOException {
       	download(file, response, file.getName());
    }


	 public void download(String nomArchivo, HttpServletResponse response, String nomArchivoDestino) throws IOException {
		
        File file = new File (nomArchivo);
       	download(file, response, nomArchivoDestino);

    }

	public void download(File file, HttpServletResponse response, String nomArchivoDestino) throws IOException {
       
        InputStream fileInputStream = new FileInputStream(file);
        OutputStream output = response.getOutputStream();

        response.reset();

        response.setContentType("application/octet-stream");
        response.setContentLength((int) (file.length()));

		response.setHeader("filename", nomArchivoDestino);

        IOUtils.copyLarge(fileInputStream, output);
        output.flush();
    }

	public File crearCarpeta(String direccion) throws IOException{
		File dir = new File(direccion);
		if(!dir.exists()){
			dir.mkdirs();
		}	

		return dir;	
	}

	public File crearCarpetaNumerada(String pathBase) throws IOException{
			
		File dir;
		String path;
		int t = 0;
		do{
			t++;
			path = pathBase+t;
			dir = new File(path);				
		}while(dir.exists());

		dir.mkdirs();

		return dir;
	}

	public Archivo upload(MultipartFile archivoOrigen, String detalle, Long idPadre, 
					  ArchivosEntidades archivoEntidad, Boolean publico){

		Archivo archivo = null;
		try{
			if (!archivoOrigen.isEmpty()) {
		
				String direccion = getPathArchivosEntidades(archivoEntidad, idPadre);
				crearCarpeta(direccion);

				direccion = direccion + PATH_SEPERADOR + archivoOrigen.getOriginalFilename();

				File archivoDestino = new File(direccion);

				if(!archivoDestino.exists()){
					upload(archivoOrigen, archivoDestino);

					archivo = new Archivo();
					archivo.setDetalle(detalle);
					archivo.setDireccion(direccion);
					archivo.setPublico(publico);

					archivoEntidad.setPadre(archivo, idPadre);

					archivo = archivoRepository.save(archivo);
				}
		    }
		}catch(Exception e){
			log.error("ArchivoService",e);
		}

		return archivo;
	}


	public void upload(MultipartFile archivoOrigen, String archivoDestino) throws IOException{
		upload(archivoOrigen, new File(archivoDestino));
	}

	public void upload(MultipartFile archivoOrigen, File archivoDestino) throws IOException{

		byte[] bytes = archivoOrigen.getBytes();
	    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(archivoDestino));
	    stream.write(bytes);
	    stream.close();

	}

	public void eliminarArchivosTemp(String path){
		File dir = new File(path);

		long time = new Date().getTime();

		for(File f : dir.listFiles()){
				
			long diff = time - f.lastModified();

			if (diff > TEMP_DIAS * 24 * 60 * 60 * 1000) {
				try{
					f.delete();
				}catch(Exception e){}
			}

		}

	}


	public File cambiarCodificacion(File entrada) throws IOException{

		File salida = File.createTempFile("codificado_",".codif");

		String codificacionOrigen = getCodificacionOrigen();
		String codificacion = getCodificacion();

        Reader in = new BufferedReader(new InputStreamReader(new FileInputStream(entrada), codificacionOrigen));
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(salida), codificacion));
		
		char[] buffer = new char[16384];
        int ch;
        while ((ch = in.read(buffer)) > -1) {
            out.write(buffer, 0, ch);
        }

        out.close();
        in.close();

		return salida;
	}

	public File getArchivoMasReciente(String directorio, String filtro) throws IOException{

		File dir = new File(directorio);
		FileFilter fileFilter = new WildcardFileFilter(filtro);
		File[] files = dir.listFiles(fileFilter);
		File archivoMasReciente = null;

		if (files.length > 0) {
		    Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		    archivoMasReciente = files[0];
   		}

		return archivoMasReciente;
	}
}
