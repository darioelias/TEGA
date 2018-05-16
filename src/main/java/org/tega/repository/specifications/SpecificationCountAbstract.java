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



public abstract class SpecificationCountAbstract<T> implements SpecificationCount<T>  {

	protected <T,M> Join<T,M> getJoin(Root<T> root, String campo, Boolean fetch){
		if(fetch)
			return (Join<T, M>)root.<T, M>fetch(campo,JoinType.LEFT);
		else
			return root.<T, M>join(campo,JoinType.LEFT);
	}
	

	protected <T,M> void putJoin(Map<String,Join> joins, Root<T> root, String campo, Boolean fetch){
		if(fetch)
			joins.put(campo,(Join<T, M>)root.<T, M>fetch(campo,JoinType.LEFT));
		else
			joins.put(campo,root.<T, M>join(campo,JoinType.LEFT));
	}

	protected abstract Predicate getPredicate(Root<T> root, CriteriaQuery<?> query, 
										      CriteriaBuilder cb, Boolean fetch);


	public Predicate toPredicateCount(
				Root<T> root,
				CriteriaQuery<?> query, CriteriaBuilder cb) {
	
		return getPredicate(root, query, cb, false);
	}		

	public Predicate toPredicate(
				Root<T> root,
				CriteriaQuery<?> query, CriteriaBuilder cb) {
	
		return getPredicate(root, query, cb, true);
	}

}
