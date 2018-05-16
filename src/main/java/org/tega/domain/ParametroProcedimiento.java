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

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import org.tega.domain.enumeration.Tipo;
import org.tega.domain.util.BaseDeDatos;


@Entity
@Table(name = "parametros_procedimientos")
public class ParametroProcedimiento implements Serializable {

    private static final long serialVersionUID = 1L;

	public static final String VALOR_VERDADERO = "1";
	public static final String VALOR_FALSO = "0";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
	@Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @NotNull
    @Column(name = "detalle", nullable = false)
    private String detalle;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	private Tipo tipo;

    @Column(name = "valor",length=BaseDeDatos.VARCHAR_MAX_LENGTH) 
    private String valor;

	@ManyToOne(fetch=FetchType.LAZY)
    private Procedimiento procedimiento;

	@ManyToOne(fetch=FetchType.LAZY)
    private CategoriaParametroProcedimiento categoria;

    @Column(name = "no_editable")
    private Boolean noEditable;

    @OneToMany(mappedBy = "parametro")
    private Set<ParametroEjecucion> parametrosEjecucion = new HashSet<>();

	public ParametroProcedimiento(){};

	public ParametroProcedimiento(Long id, String codigo, String detalle){
		this.id = id;
		this.codigo = codigo;
		this.detalle = detalle;
	}


	public Boolean valorLogico() {
        return VALOR_VERDADERO.equals(this.valor);
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

    public Boolean getNoEditable() {
        return noEditable;
    }

    public void setNoEditable(Boolean noEditable) {
        this.noEditable = noEditable;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

	public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

	public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

	public Procedimiento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

	public CategoriaParametroProcedimiento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaParametroProcedimiento categoria) {
        this.categoria = categoria;
    }

	public Set<ParametroEjecucion> getParametrosEjecucion() {
        return parametrosEjecucion;
    }

    public void setParametrosEjecucion(Set<ParametroEjecucion> parametrosEjecucion) {
        this.parametrosEjecucion = parametrosEjecucion;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParametroProcedimiento parametro = (ParametroProcedimiento) o;
        if(parametro.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, parametro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParametroProcedimiento{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
			", detalle='" + detalle + "'" +
            '}';
    }
}
