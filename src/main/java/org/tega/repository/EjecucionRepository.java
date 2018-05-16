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

import org.tega.domain.Ejecucion;
import org.tega.domain.ParametroEjecucion;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
/**
 * Spring Data JPA repository for the Especie entity.
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public interface EjecucionRepository extends JpaRepository<Ejecucion,Long>, JpaSpecificationExecutor<Ejecucion> {

	@Query("select distinct e from Ejecucion e join fetch e.procedimiento p where e.analisisGenotipo.id =:id")
	List<Ejecucion> findByAnalisisGenotipo(@Param("id") Long id);

	@Query("select distinct pe from ParametroEjecucion pe join fetch pe.parametro p join fetch p.categoria where pe.ejecucion.id =:id")
	List<ParametroEjecucion> findParametrosEjecucion(@Param("id") Long id);

	@Query("select distinct pe from ParametroEjecucion pe join fetch pe.parametro p join fetch p.categoria join pe.ejecucion e join e.analisisGenotipo a where pe.ejecucion.id =:id and a.publico = TRUE")
	List<ParametroEjecucion> findParametrosEjecucionPublico(@Param("id") Long id);

	@Query("select distinct pe from ParametroEjecucion pe join fetch pe.parametro p where pe.ejecucion.id =:id")
	Set<ParametroEjecucion> findParametrosEjecucionUpdate(@Param("id") Long id);

	@Query("select distinct e from Ejecucion e join fetch e.procedimiento p where e.analisisGenotipo.id =:id and e.fin IS NULL ORDER BY e.id")
	List<Ejecucion> findByAnalisisGenotipoEnEjecucion(@Param("id") Long id);	

}
