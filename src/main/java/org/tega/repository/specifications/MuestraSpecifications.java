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

import org.tega.domain.Muestra;
import org.tega.domain.Region;
import org.tega.domain.Profesional;
import org.tega.domain.Institucion;
import org.tega.domain.Especie;
import org.tega.domain.Tejido;
import org.tega.domain.ModoRecoleccion;
import org.tega.domain.Localidad;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.apache.commons.lang3.StringUtils;

public class MuestraSpecifications {


    public static Specification<Muestra> findByCriteria(final MuestraCriterio criterio){

		return new SpecificationCountAbstract<Muestra>(){			

			protected Predicate getPredicate(Root<Muestra> root, CriteriaQuery<?> query, 
												  CriteriaBuilder cb, Boolean fetch){
				
				Join region 			= getJoin(root,"region",fetch);
				Join profesional 		= getJoin(root,"profesional",fetch);
				Join localidad 			= getJoin(root,"localidad",fetch);
				Join institucion 		= getJoin(root,"institucion",fetch);
				Join especie 			= getJoin(root,"especie",fetch);
				Join tejido 			= getJoin(root,"tejido",fetch);
				Join modoRecoleccion 	= getJoin(root,"modoRecoleccion",fetch);

				Join conjuntosMuestras = getJoin(root,"conjuntosMuestras",false);
				Join proyectos = getJoin(root,"proyectos",false);
								
				List<Predicate> predicates = new ArrayList<Predicate>();

				if(criterio.getId() != null)
					predicates.add(cb.equal(root.<Long>get("id"), criterio.getId()));
		
				if(criterio.getDesdeRecoleccion() != null)
					predicates.add(cb.greaterThanOrEqualTo(root.<LocalDate>get("fechaRecoleccion"),criterio.getDesdeRecoleccion()));

				if(criterio.getHastaRecoleccion() != null)
					predicates.add(cb.lessThanOrEqualTo(root.<LocalDate>get("fechaRecoleccion"),criterio.getHastaRecoleccion()));

				if(!StringUtils.isBlank(criterio.getCodigoInterno()))
					predicates.add(cb.like(cb.lower(root.<String>get("codigoInterno")),"%"+criterio.getCodigoInterno().trim().toLowerCase()+"%"));

				if(!StringUtils.isBlank(criterio.getCodigoExterno()))
					predicates.add(cb.like(cb.lower(root.<String>get("codigoExterno")),"%"+criterio.getCodigoExterno().trim().toLowerCase()+"%"));

				if(!StringUtils.isBlank(criterio.getUbicacion()))
					predicates.add(cb.like(cb.lower(root.<String>get("ubicacion")),"%"+criterio.getUbicacion().trim().toLowerCase()+"%"));

				if(criterio.getIdRegion() != null)
					predicates.add(cb.equal(region.<Long>get("id"), criterio.getIdRegion()));
				
				if(criterio.getIdProfesional() != null)
					predicates.add(cb.equal(profesional.<Long>get("id"), criterio.getIdProfesional()));

				if(criterio.getIdLocalidad() != null)
					predicates.add(cb.equal(localidad.<Long>get("id"), criterio.getIdLocalidad()));

				if(criterio.getIdInstitucion() != null)
					predicates.add(cb.equal(institucion.<Long>get("id"), criterio.getIdInstitucion()));

				if(criterio.getIdEspecie() != null)
					predicates.add(cb.equal(especie.<Long>get("id"), criterio.getIdEspecie()));

				if(criterio.getIdTejido() != null)
					predicates.add(cb.equal(tejido.<Long>get("id"), criterio.getIdTejido()));
	
				if(criterio.getIdModoRecoleccion() != null)
					predicates.add(cb.equal(modoRecoleccion.<Long>get("id"), criterio.getIdModoRecoleccion()));

				if(criterio.getIdConjuntoMuestras() != null)
					predicates.add(cb.equal(conjuntosMuestras.<Long>get("id"), criterio.getIdConjuntoMuestras()));

				if(criterio.getIdProyecto() != null)
					predicates.add(cb.equal(proyectos.<Long>get("id"), criterio.getIdProyecto()));

				if(criterio.getPublico() != null){
					if(criterio.getPublico())
						predicates.add(cb.equal(root.<Boolean>get("publico"),criterio.getPublico()));
					else
						predicates.add(cb.or(cb.equal(root.<Boolean>get("publico"),criterio.getPublico()), 
											 cb.isNull(root.<Boolean>get("publico"))));
				}

				CriteriaQuery<Muestra> consulta = (CriteriaQuery<Muestra>) query;
				consulta.select(root).distinct(true);


				return cb.and(predicates.toArray(new Predicate[] {}));
			}

		};

	}

}
