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


package org.tega.repository;

import org.tega.domain.MuestraAtributo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the Muestra entity.
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public interface MuestraAtributoRepository extends JpaRepository<MuestraAtributo,Long>, JpaSpecificationExecutor<MuestraAtributo> {

	@Query("select m from MuestraAtributo m join fetch m.atributo a where m.muestra.id = :id")
	List<MuestraAtributo> findByMuestra(@Param("id") Long id);

//	@Query("select m from MuestraAtributo m join fetch m.atributo a join fetch m.muestra u where u.id = :id")
//	List<MuestraAtributo> findByMuestraCompletos(@Param("id") Long id);

	@Query("select distinct m from MuestraAtributo m "+
		   "join fetch m.atributo a "+
		   "join fetch m.muestra u "+
		   "join u.conjuntosMuestras c "+
		   "join c.analisisGenotipos n "+
		   "where n.id = :id")
	List<MuestraAtributo> findByAnalisisGenotipo(@Param("id") Long id);

	@Query("select m from MuestraAtributo m join fetch m.atributo a join fetch m.muestra u where u.id in :ids")
	List<MuestraAtributo> findByIdsMuestras(@Param("ids") Set<Long> ids);

}
