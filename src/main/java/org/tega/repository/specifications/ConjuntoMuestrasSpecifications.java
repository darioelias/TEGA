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

import org.tega.domain.ConjuntoMuestras;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.apache.commons.lang3.StringUtils;

public class ConjuntoMuestrasSpecifications {


    public static Specification<ConjuntoMuestras> findByCriteria(final ConjuntoMuestrasCriterio criterio){

		return new SpecificationCountAbstract<ConjuntoMuestras>(){
			
            protected Predicate getPredicate(
                Root<ConjuntoMuestras> root,
                CriteriaQuery<?> query, CriteriaBuilder cb, Boolean fetch) {

				Join analisisGenotipos = getJoin(root,"analisisGenotipos",false);
				//Join analisisSecuencias = getJoin(root,"analisisSecuencias",false);
//				Join analisisSecuencias = getJoin(root,"muestras",false)
//								   .join("secuencias",JoinType.LEFT)
//								   .join("analisisSecuencias",JoinType.LEFT);

				List<Predicate> predicates = new ArrayList<Predicate>();

				if(criterio.getId() != null)
					predicates.add(cb.equal(root.<Long>get("id"), criterio.getId()));
		
				if(!StringUtils.isBlank(criterio.getCodigo()))
					predicates.add(cb.like(cb.lower(root.<String>get("codigo")),"%"+criterio.getCodigo().trim().toLowerCase()+"%"));

				if(criterio.getIdAnalisisGenotipos() != null)
					predicates.add(cb.equal(analisisGenotipos.<Long>get("id"), criterio.getIdAnalisisGenotipos()));

//				if(criterio.getIdAnalisisSecuencias() != null)
//					predicates.add(cb.equal(analisisSecuencias.<Long>get("id"), criterio.getIdAnalisisSecuencias()));

				if(criterio.getPublico() != null){
					if(criterio.getPublico())
						predicates.add(cb.equal(root.<Boolean>get("publico"),criterio.getPublico()));
					else
						predicates.add(cb.or(cb.equal(root.<Boolean>get("publico"),criterio.getPublico()), 
											 cb.isNull(root.<Boolean>get("publico"))));
				}

				CriteriaQuery<ConjuntoMuestras> consulta = (CriteriaQuery<ConjuntoMuestras>) query;
				consulta.select(root).distinct(true);

				return cb.and(predicates.toArray(new Predicate[] {}));

			}
		};

	}

}
