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


package org.tega.service.importacion;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.tega.domain.enumeration.Sexo;

/**
 * A Muestra.
 */
public class MuestraImp implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long 		id;
    private LocalDate 	fechaRecoleccion;
    private String 		codigoInterno;
    private String 		codigoExterno;
    private Sexo 		sexo;
    private Double 		latitud;
    private Double 		longitud;
    private String 		ubicacion;
    private String 		detalle;
    private Boolean 	publico;

	private String 		proyecto;
	private String 		region;
	private String 		localidad;
	private String 		profesional;
	private String 		institucion;
	private String 		tejido;
	private String 		especie;
	private String 		modoRecoleccion;

	private String 		provincia;
	private String 		pais;

	private String 		conjuntoMuestras;

	public MuestraImp(){}


	public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public LocalDate getFechaRecoleccion() {
        return fechaRecoleccion;
    }

    public void setFechaRecoleccion(LocalDate fechaRecoleccion) {
        this.fechaRecoleccion = fechaRecoleccion;
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }


    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getUbicacion() {
        return ubicacion;
    }


    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDetalle() {
        return detalle;
    }


    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

	public String getRegion() {
        return region;
    }


    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocalidad() {
        return localidad;
    }


    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProfesional() {
        return profesional;
    }


    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getInstitucion() {
        return institucion;
    }


    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getEspecie() {
        return especie;
    }


    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getTejido() {
        return tejido;
    }

    public void setTejido(String tejido) {
        this.tejido = tejido;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getModoRecoleccion() {
        return modoRecoleccion;
    }


    public void setModoRecoleccion(String modoRecoleccion) {
        this.modoRecoleccion = modoRecoleccion;
    }


   

   	public String getConjuntoMuestras() {
        return conjuntoMuestras;
    }

    public void setConjuntoMuestras(String conjuntoMuestras) {
        this.conjuntoMuestras = conjuntoMuestras;
    }

   	

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MuestraImp muestra = (MuestraImp) o;
        if(muestra.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, muestra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MuestraImp{" +
            "id="+id+
			"codigoInterno="+codigoInterno+
			"codigoExterno="+codigoExterno+
		'}';
    }
}
