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


package org.tega.repository.specifications;

import java.time.LocalDate;

public class MuestraCriterio{
	private LocalDate desdeRecoleccion;
	private LocalDate hastaRecoleccion;
	private String codigoInterno;
	private String codigoExterno;
	private String ubicacion;
	private Long idRegion;
	private Long idLocalidad;
	private Long idProfesional;
	private Long idInstitucion;
	private Long idEspecie;
	private Long idTejido;
	private Long idModoRecoleccion;
	private Long idConjuntoMuestras;
	private Long idProyecto;
	private Boolean publico;
	private Long id;

	public Long getId() {
	    return id;
	}

	public void setId(Long id) {
	    this.id = id;
	}
	public Boolean getPublico() {
	    return publico;
	}

	public void setPublico(Boolean publico) {
	    this.publico= publico;
	}	

	public LocalDate getDesdeRecoleccion() {
	    return desdeRecoleccion;
	}

	public void setDesdeRecoleccion(LocalDate desdeRecoleccion) {
	    this.desdeRecoleccion = desdeRecoleccion;
	}

	public LocalDate getHastaRecoleccion() {
	    return hastaRecoleccion;
	}

	public void setHastaRecoleccion(LocalDate hastaRecoleccion) {
	    this.hastaRecoleccion = hastaRecoleccion;
	}

	public String getCodigoInterno() {
	    return codigoInterno;
	}

	public void setCodigoInterno(String codigoInterno) {
	    this.codigoInterno = codigoInterno;
	}

	public String getCodigoExterno() {
	    return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
	    this.codigoExterno = codigoExterno;
	}

	public String getUbicacion() {
	    return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
	    this.ubicacion = ubicacion;
	}

	public Long getIdRegion() {
	    return idRegion;
	}

	public void setIdRegion(Long idRegion) {
	    this.idRegion = idRegion;
	}

	public Long getIdProfesional() {
	    return idProfesional;
	}

	public void setIdProfesional(Long idProfesional) {
	    this.idProfesional = idProfesional;
	}

	public Long getIdLocalidad() {
	    return idLocalidad;
	}

	public void setIdLocalidad(Long idLocalidad) {
	    this.idLocalidad = idLocalidad;
	}

	public Long getIdInstitucion() {
	    return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
	    this.idInstitucion = idInstitucion;
	}
	public Long getIdEspecie() {
	    return idEspecie;
	}

	public void setIdEspecie(Long idEspecie) {
	    this.idEspecie = idEspecie;
	}
	public Long getIdTejido() {
	    return idTejido;
	}

	public void setIdTejido(Long idTejido) {
	    this.idTejido = idTejido;
	}
	public Long getIdModoRecoleccion() {
	    return idModoRecoleccion;
	}

	public void setIdModoRecoleccion(Long idModoRecoleccion) {
	    this.idModoRecoleccion = idModoRecoleccion;
	}

	public Long getIdConjuntoMuestras() {
	    return idConjuntoMuestras;
	}

	public void setIdConjuntoMuestras(Long idConjuntoMuestras) {
	    this.idConjuntoMuestras = idConjuntoMuestras;
	}

	public Long getIdProyecto() {
	    return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
	    this.idProyecto = idProyecto;
	}

}
