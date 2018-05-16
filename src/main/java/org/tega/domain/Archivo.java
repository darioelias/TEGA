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
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
/**
 * A Archivo.
 */
@Entity
@Table(name = "archivos")
public class Archivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "detalle", nullable = false)
    private String detalle;

    @Column(name = "direccion")
    private String direccion;

	@Column(name = "publico")
    private Boolean publico;


	@ManyToMany
    @JoinTable(name = "muestras_archivos",
               joinColumns = @JoinColumn(name="archivo_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="muestra_id", referencedColumnName="ID"))
	@JsonIgnore
    private Set<Muestra> muestras = new HashSet<>();

	@ManyToMany
    @JoinTable(name = "analisis_genotipos_archivos",
               joinColumns = @JoinColumn(name="archivo_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="analisis_genotipo_id", referencedColumnName="ID"))
	@JsonIgnore
    private Set<AnalisisGenotipo> analisisGenotipos = new HashSet<>();

	@ManyToMany
    @JoinTable(name = "proyectos_archivos",
               joinColumns = @JoinColumn(name="archivo_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="proyecto_id", referencedColumnName="ID"))
	@JsonIgnore
    private Set<Proyecto> proyectos = new HashSet<>();

	@ManyToMany
    @JoinTable(name = "procedimientos_archivos",
               joinColumns = @JoinColumn(name="archivo_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="procedimiento_id", referencedColumnName="ID"))
	@JsonIgnore
    private Set<Procedimiento> procedimientos = new HashSet<>();


	@ManyToMany
    @JoinTable(name = "ejecuciones_archivos",
               joinColumns = @JoinColumn(name="archivo_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="ejecucion_id", referencedColumnName="ID"))
	@JsonIgnore
    private Set<Ejecucion> ejecuciones = new HashSet<>();

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

    public String getDetalle() {
        return detalle;
    }

    public Archivo detalle(String detalle) {
        this.detalle = detalle;
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDireccion() {
        return direccion;
    }

    public Archivo direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public Set<Muestra> getMuestras() {
        return muestras;
    }


    public void setMuestras(Set<Muestra> muestras) {
        this.muestras = muestras;
    }

    public void addMuestra(Muestra muestra) {
        if(this.muestras == null)
			this.muestras = new HashSet<Muestra>();

		this.muestras.add(muestra);
    }


    public Set<AnalisisGenotipo> getAnalisisGenotipos() {
        return analisisGenotipos;
    }

    public void setAnalisisGenotipos(Set<AnalisisGenotipo> analisisGenotipos) {
        this.analisisGenotipos = analisisGenotipos;
    }

    public void addAnalisisGenotipo(AnalisisGenotipo analisisGenotipo) {
        if(this.analisisGenotipos == null)
			this.analisisGenotipos = new HashSet<AnalisisGenotipo>();

		this.analisisGenotipos.add(analisisGenotipo);
    }


    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public void addProyecto(Proyecto proyecto) {
        if(this.proyectos == null)
			this.proyectos = new HashSet<Proyecto>();

		this.proyectos.add(proyecto);
    }


    public Set<Procedimiento> getProcedimientos() {
        return procedimientos;
    }

    public void setProcedimientos(Set<Procedimiento> procedimientos) {
        this.procedimientos = procedimientos;
    }

    public void addProcedimiento(Procedimiento procedimiento) {
        if(this.procedimientos == null)
			this.procedimientos = new HashSet<Procedimiento>();

		this.procedimientos.add(procedimiento);
    }

    public Set<Ejecucion> getEjecuciones() {
        return ejecuciones;
    }

    public void setEjecuciones(Set<Ejecucion> ejecuciones) {
        this.ejecuciones = ejecuciones;
    }

    public void addEjecucion(Ejecucion ejecucion) {
        if(this.ejecuciones == null)
			this.ejecuciones = new HashSet<Ejecucion>();

		this.ejecuciones.add(ejecucion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Archivo archivo = (Archivo) o;
        if(archivo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, archivo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Archivo{" +
            "id=" + id +
            ", detalle='" + detalle + "'" +
            ", direccion='" + direccion + "'" +
            '}';
    }
}
