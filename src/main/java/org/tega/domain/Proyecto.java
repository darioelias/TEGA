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

/**
 * A Proyecto.
 */
@Entity
@Table(name = "proyectos")
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "publico")
    private Boolean publico;

    @OneToMany(mappedBy = "proyecto")
    @JsonIgnore
    private Set<AnalisisGenotipo> analisisGenotipos = new HashSet<>();

	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "proyectos_archivos",
               joinColumns = @JoinColumn(name="proyecto_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="archivo_id", referencedColumnName="ID"))
    private Set<Archivo> archivos = new HashSet<>();


	@ManyToMany(mappedBy = "proyectos")
    @JsonIgnore
    private Set<Muestra> muestras = new HashSet<>();

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<ProyectoAtributo> atributos = new HashSet<>();

	public Proyecto(){}


	public Proyecto(Long id){
		this.id = id;
	}

	public Proyecto(Long id, String codigo, String detalle){
		this.id = id;
		this.codigo = codigo;
		this.detalle = detalle;
	}

	public Proyecto(String codigo, String detalle){
		this.codigo = codigo;
		this.detalle = detalle;
	}


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
        this.publico = publico;
    }

    public String getCodigo() {
        return codigo;
    }

    public Proyecto codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Proyecto fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public Proyecto detalle(String detalle) {
        this.detalle = detalle;
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Set<AnalisisGenotipo> getAnalisisGenotipos() {
        return analisisGenotipos;
    }

    public void setAnalisisGenotipos(Set<AnalisisGenotipo> analisisGenotipos) {
        this.analisisGenotipos = analisisGenotipos;
    }

    public Set<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }

    public Set<ProyectoAtributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(Set<ProyectoAtributo> atributos) {
        this.atributos = atributos;
    }


 	public Set<Muestra> getMuestras() {
        return muestras;
    }

    public void setMuestras(Set<Muestra> muestras) {
        this.muestras = muestras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Proyecto proyecto = (Proyecto) o;
        if(proyecto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, proyecto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            ", fecha='" + fecha + "'" +
            ", detalle='" + detalle + "'" +
            '}';
    }
}
