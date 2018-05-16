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
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import org.tega.domain.util.BaseDeDatos;


@Entity
@Table(name = "procedimientos")
public class Procedimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "codigo", unique = true)
    private String codigo;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "procedimiento")
    @JsonIgnore
    private Set<ParametroProcedimiento> parametros = new HashSet<>();

    @Column(name = "detalle",length=BaseDeDatos.VARCHAR_MAX_LENGTH) 
    private String detalle;

    @Column(name = "comando_eje",length=BaseDeDatos.VARCHAR_MAX_LENGTH) 
    private String comandoEje;

    @Column(name = "comando_log",length=BaseDeDatos.VARCHAR_MAX_LENGTH) 
    private String comandoLog;

    @Column(name = "publico")
    private Boolean publico;

    @Column(name = "exportar_genotipos")
    private Boolean exportarGenotipos;

    @Column(name = "exportar_genotipos_structure")
    private Boolean exportarGenotiposStructure;

    @Column(name = "exportar_genotipos_genepop")
    private Boolean exportarGenotiposGenepop;

    @Column(name = "exportar_muestras")
    private Boolean exportarMuestras;

    @Column(name = "exportar_muestras_atributos")
    private Boolean exportarMuestrasAtributos;

    @Column(name = "exportar_conjuntos_muestras")
    private Boolean exportarConjuntosMuestras;

    @Column(name = "exportar_conjuntos_muestras_atributos")
    private Boolean exportarConjuntosMuestrasAtributos;

    @Column(name = "exportar_loci")
    private Boolean exportarLoci;

    @Column(name = "exportar_loci_atributos")
    private Boolean exportarLociAtributos;

    @Column(name = "exportar_cantidades")
    private Boolean exportarCantidades;

	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "procedimientos_archivos",
               joinColumns = @JoinColumn(name="procedimiento_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="archivo_id", referencedColumnName="ID"))
    private Set<Archivo> archivos = new HashSet<>();

    @OneToMany(mappedBy = "procedimiento")
    @JsonIgnore
    private Set<AnalisisGenotipo> analisisGenotipos = new HashSet<>();

    @OneToMany(mappedBy = "procedimiento")
    private Set<Ejecucion> ejecuciones = new HashSet<>();

	public Procedimiento(){};

	public Procedimiento(Long id){
		this.id = id;
	}

	public Procedimiento(Long id, String codigo, String nombre){
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public Procedimiento(String codigo, String nombre){
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

	public String getCodigo() {
        return codigo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<AnalisisGenotipo> getAnalisisGenotipos() {
        return analisisGenotipos;
    }

    public void setAnalisisGenotipos(Set<AnalisisGenotipo> analisisGenotipos) {
        this.analisisGenotipos = analisisGenotipos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getComandoEje() {
        return comandoEje;
    }

    public void setComandoEje(String comandoEje) {
        this.comandoEje = comandoEje;
    }

    public String getComandoLog() {
        return comandoLog;
    }

    public void setComandoLog(String comandoLog) {
        this.comandoLog = comandoLog;
    }

    public Boolean getExportarGenotipos() {
        return exportarGenotipos;
    }

    public void setExportarGenotipos(Boolean exportarGenotipos) {
        this.exportarGenotipos = exportarGenotipos;
    }

    public Boolean getExportarGenotiposStructure() {
        return exportarGenotiposStructure;
    }

    public void setExportarGenotiposStructure(Boolean exportarGenotiposStructure) {
        this.exportarGenotiposStructure = exportarGenotiposStructure;
    }

    public Boolean getExportarGenotiposGenepop() {
        return exportarGenotiposGenepop;
    }

    public void setExportarGenotiposGenepop(Boolean exportarGenotiposGenepop) {
        this.exportarGenotiposGenepop = exportarGenotiposGenepop;
    }

    public Boolean getExportarMuestras() {
        return exportarMuestras;
    }

    public void setExportarMuestras(Boolean exportarMuestras) {
        this.exportarMuestras = exportarMuestras;
    }

    public Boolean getExportarMuestrasAtributos() {
        return exportarMuestrasAtributos;
    }

    public void setExportarMuestrasAtributos(Boolean exportarMuestrasAtributos) {
        this.exportarMuestrasAtributos = exportarMuestrasAtributos;
    }

    public Boolean getExportarCantidades() {
        return exportarCantidades;
    }

    public void setExportarCantidades(Boolean exportarCantidades) {
        this.exportarCantidades = exportarCantidades;
    }

	public Boolean getExportarConjuntosMuestras() {
        return exportarConjuntosMuestras;
    }

    public void setExportarConjuntosMuestras(Boolean exportarConjuntosMuestras) {
        this.exportarConjuntosMuestras = exportarConjuntosMuestras;
    }

    public Boolean getExportarConjuntosMuestrasAtributos() {
        return exportarConjuntosMuestrasAtributos;
    }

    public void setExportarConjuntosMuestrasAtributos(Boolean exportarConjuntosMuestrasAtributos) {
        this.exportarConjuntosMuestrasAtributos = exportarConjuntosMuestrasAtributos;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public Set<ParametroProcedimiento> getParametros() {
        return parametros;
    }

    public void setParametros(Set<ParametroProcedimiento> parametros) {
        this.parametros = parametros;
    }

    public Set<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }

    public Boolean getExportarLoci() {
        return exportarLoci;
    }

    public void setExportarLoci(Boolean exportarLoci) {
        this.exportarLoci = exportarLoci;
    }

    public Boolean getExportarLociAtributos() {
        return exportarLociAtributos;
    }

    public void setExportarLociAtributos(Boolean exportarLociAtributos) {
        this.exportarLociAtributos = exportarLociAtributos;
    }

	public Set<Ejecucion> getEjecuciones() {
        return ejecuciones;
    }

    public void setEjecuciones(Set<Ejecucion> ejecuciones) {
        this.ejecuciones = ejecuciones;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Procedimiento p = (Procedimiento) o;
        if(p.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Procedimiento{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
