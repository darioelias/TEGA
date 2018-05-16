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

import java.io.Serializable;

public class MuestraImpResultado implements Serializable {

	private Integer muestrasCreadas = 0;
	private Integer muestrasModificadas = 0;

	private Integer proyectosCreados = 0;
	private Integer regionesCreadas = 0;
	private Integer especiesCreadas = 0;
	private Integer localidadesCreadas = 0;
	private Integer provinciasCreadas = 0;
	private Integer paisesCreados = 0;
	private Integer profesionalesCreados = 0;
	private Integer institucionesCreadas = 0;
	private Integer tejidosCreados = 0;
	private Integer modosRecoleccionCreados = 0;
	private Integer conjuntoMuestrasCreados = 0;

	public void incMuestrasCreadas(){muestrasCreadas++;}
	public void incMuestrasModificadas(){muestrasModificadas++;}
	public void incProyectosCreados(){proyectosCreados++;}
	public void incRegionesCreadas(){regionesCreadas++;}
	public void incEspeciesCreadas(){especiesCreadas++;}
	public void incLocalidadesCreadas(){localidadesCreadas++;}
	public void incProvinciasCreadas(){provinciasCreadas++;}
	public void incPaisesCreados(){paisesCreados++;}
	public void incProfesionalesCreados(){profesionalesCreados++;}
	public void incInstitucionesCreadas(){institucionesCreadas++;}
	public void incTejidosCreados(){tejidosCreados++;}
	public void incModosRecoleccionCreados(){modosRecoleccionCreados++;}
	public void incConjuntoMuestrasCreados(){conjuntoMuestrasCreados++;}



	public Integer getMuestrasCreadas(){return muestrasCreadas;}
	public Integer getMuestrasModificadas(){return muestrasModificadas;}
	public Integer getProyectosCreados(){return proyectosCreados;}
	public Integer getRegionesCreadas(){return regionesCreadas;}
	public Integer getEspeciesCreadas(){return especiesCreadas;}
	public Integer getLocalidadesCreadas(){return localidadesCreadas;}
	public Integer getProvinciasCreadas(){return provinciasCreadas;}
	public Integer getPaisesCreados(){return paisesCreados;}
	public Integer getProfesionalesCreados(){return profesionalesCreados;}
	public Integer getInstitucionesCreadas(){return institucionesCreadas;}
	public Integer getTejidosCreados(){return tejidosCreados;}
	public Integer getModosRecoleccionCreados(){return modosRecoleccionCreados;}
	public Integer getConjuntoMuestrasCreados(){return conjuntoMuestrasCreados;}

	public void setMuestrasCreadas(Integer muestrasCreadas){this.muestrasCreadas = muestrasCreadas;}
	public void setMuestrasModificadas(Integer muestrasModificadas){this.muestrasModificadas = muestrasModificadas;}
	public void setProyectosCreados(Integer proyectosCreados){this.proyectosCreados = proyectosCreados;}
	public void setRegionesCreadas(Integer regionesCreadas){this.regionesCreadas = regionesCreadas;}
	public void setEspeciesCreadas(Integer especiesCreadas){this.especiesCreadas = especiesCreadas;}
	public void setLocalidadesCreadas(Integer localidadesCreadas){this.localidadesCreadas = localidadesCreadas;}
	public void setProvinciasCreadas(Integer provinciasCreadas){this.provinciasCreadas = provinciasCreadas;}
	public void setPaisesCreados(Integer paisesCreados){this.paisesCreados = paisesCreados;}
	public void setProfesionalesCreados(Integer profesionalesCreados){this.profesionalesCreados = profesionalesCreados;}
	public void setInstitucionesCreadas(Integer institucionesCreadas){this.institucionesCreadas = institucionesCreadas;}
	public void setTejidosCreados(Integer tejidosCreados){this.tejidosCreados = tejidosCreados;}
	public void setModosRecoleccionCreados(Integer modosRecoleccionCreados){this.modosRecoleccionCreados = modosRecoleccionCreados;}
	public void setConjuntoMuestrasCreados(Integer conjuntoMuestrasCreados){this.conjuntoMuestrasCreados = conjuntoMuestrasCreados;}

}
