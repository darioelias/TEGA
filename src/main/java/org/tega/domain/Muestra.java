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


package org.tega.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.tega.domain.enumeration.Sexo;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch; 

/**
 * A Muestra.
 */
@Entity
@Table(name = "muestras")
public class Muestra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_interno", unique = true)
    private String codigoInterno;

    @Column(name = "codigo_externo")
    private String codigoExterno;

	@Column(name = "fecha_recoleccion")
    private LocalDate fechaRecoleccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "detalle")
    private String detalle;

	@Column(name = "publico")
    private Boolean publico;

	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "muestras_archivos",
               joinColumns = @JoinColumn(name="muestra_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="archivo_id", referencedColumnName="ID"))
    private Set<Archivo> archivos = new HashSet<>();

    @ManyToOne(fetch=FetchType.LAZY)
    private Region region;

    @ManyToOne(fetch=FetchType.LAZY)
    private Localidad localidad;

    @ManyToOne(fetch=FetchType.LAZY)
    private Profesional profesional;

    @ManyToOne(fetch=FetchType.LAZY)
    private Institucion institucion;

    @ManyToOne(fetch=FetchType.LAZY)
    private Especie especie;

    @ManyToOne(fetch=FetchType.LAZY)
    private Tejido tejido;

    @ManyToOne(fetch=FetchType.LAZY)
    private ModoRecoleccion modoRecoleccion;

	@ManyToMany
    @JoinTable(name = "proyectos_muestras",
               joinColumns = @JoinColumn(name="muestra_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="proyecto_id", referencedColumnName="ID"))
    private Set<Proyecto> proyectos = new HashSet<>();

	@ManyToMany
    @JoinTable(name = "conjuntos_muestras_muestras",
               joinColumns = @JoinColumn(name="muestra_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="conjunto_muestras_id", referencedColumnName="ID"))
    private Set<ConjuntoMuestras> conjuntosMuestras = new HashSet<>();

    @OneToMany(mappedBy = "muestra", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<Alelo> alelos = new HashSet<>();

    @OneToMany(mappedBy = "muestra", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<MuestraAtributo> atributos = new HashSet<>();

	public Muestra(){}

	public Muestra(Long id){
		this.id = id;
	}

	public Muestra(Long id, String codigoInterno, String detalle){
		this.id = id;
		this.codigoInterno = codigoInterno;
		this.detalle = detalle;
	}

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

    public Muestra codigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
        return this;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getCodigoExterno() {
        return codigoExterno;
    }

    public Muestra codigoExterno(String codigoExterno) {
        this.codigoExterno = codigoExterno;
        return this;
    }

    public void setCodigoExterno(String codigoExterno) {
        this.codigoExterno = codigoExterno;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Muestra sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
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


    public Set<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }

    public Region getRegion() {
        return region;
    }


    public void setRegion(Region region) {
        this.region = region;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public Muestra localidad(Localidad localidad) {
        this.localidad = localidad;
        return this;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public Muestra profesional(Profesional profesional) {
        this.profesional = profesional;
        return this;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public Especie getEspecie() {
        return especie;
    }


    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Tejido getTejido() {
        return tejido;
    }

    public void setTejido(Tejido tejido) {
        this.tejido = tejido;
    }

    public ModoRecoleccion getModoRecoleccion() {
        return modoRecoleccion;
    }

    public void setModoRecoleccion(ModoRecoleccion modoRecoleccion) {
        this.modoRecoleccion = modoRecoleccion;
    }

 	public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }


	public Set<ConjuntoMuestras> getConjuntosMuestras() {
        return conjuntosMuestras;
    }

    public void setConjuntosMuestras(Set<ConjuntoMuestras> conjuntoMuestras) {
        this.conjuntosMuestras = conjuntoMuestras;
    }

	public Set<Alelo> getAlelos() {
        return alelos;
    }

    public void setAlelos(Set<Alelo> alelos) {
        this.alelos = alelos;
    }

	public Set<MuestraAtributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(Set<MuestraAtributo> atributos) {
        this.atributos = atributos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Muestra muestra = (Muestra) o;
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
        return "Muestra{" +
            "id=" + id +
            ", codigoInterno='" + codigoInterno + "'" +
            ", codigoExterno='" + codigoExterno + "'" +
            '}';
    }
}
