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
import java.time.LocalDateTime;
/**
 * A Archivo.
 */
@Entity
@Table(name = "ejecuciones")
public class Ejecucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "direccion")
    private String direccion;

	@NotNull
    @Column(name = "inicio")
    private LocalDateTime inicio;

    @Column(name = "fin")
    private LocalDateTime fin;

	@NotNull
    @ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
    private AnalisisGenotipo analisisGenotipo;

	@NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    private Procedimiento procedimiento;

	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "ejecuciones_archivos",
               joinColumns = @JoinColumn(name="ejecucion_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="archivo_id", referencedColumnName="ID"))
    private Set<Archivo> archivos = new HashSet<>();

    @OneToMany(mappedBy = "ejecucion", cascade = CascadeType.ALL, orphanRemoval=true)
	@JsonIgnore
    private Set<ParametroEjecucion> parametros = new HashSet<>();


	public Ejecucion(){}

	public Ejecucion(Long id){
		this.id = id;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }

    public Set<Archivo> getArchivos() {
        return archivos;
    }

 	public AnalisisGenotipo getAnalisisGenotipo() {
        return analisisGenotipo;
    }

    public void setAnalisisGenotipo(AnalisisGenotipo analisisGenotipo) {
        this.analisisGenotipo = analisisGenotipo;
    }

	public Procedimiento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    public Set<ParametroEjecucion> getParametros() {
        return parametros;
    }

    public void setParametros(Set<ParametroEjecucion> parametros) {
        this.parametros = parametros;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ejecucion e = (Ejecucion) o;
        if(e.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, e.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ejecucion{" +
            "id=" + id +
            ", direccion='" + direccion + "'" +
            '}';
    }
}
