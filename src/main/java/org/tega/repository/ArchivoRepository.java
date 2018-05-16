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

import org.tega.domain.Archivo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Archivo entity.
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public interface ArchivoRepository extends JpaRepository<Archivo,Long> {

	@Query("select distinct a from Archivo a join a.analisisGenotipos o where o.id =:id")
	List<Archivo> findByAnalisisGenotipo(@Param("id") Long id);

	@Query("select distinct a from Archivo a join a.analisisGenotipos o where a.publico = TRUE and o.id =:id")
	List<Archivo> findByAnalisisGenotipoPublico(@Param("id") Long id);

	@Query("select distinct a from Archivo a join a.muestras o where o.id =:id")
	List<Archivo> findByMuestra(@Param("id") Long id);

	@Query("select distinct a from Archivo a join a.muestras o where a.publico = TRUE and o.id =:id")
	List<Archivo> findByMuestraPublico(@Param("id") Long id);

	@Query("select distinct a from Archivo a join a.proyectos o where o.id =:id")
	List<Archivo> findByProyecto(@Param("id") Long id);

	@Query("select distinct a from Archivo a join a.proyectos o where a.publico = TRUE and o.id =:id")
	List<Archivo> findByProyectoPublico(@Param("id") Long id);

	@Query("select distinct a from Archivo a join a.procedimientos o where o.id =:id")
	List<Archivo> findByProcedimiento(@Param("id") Long id);

	@Query("select distinct a from Archivo a join a.procedimientos o where a.publico = TRUE and o.id =:id")
	List<Archivo> findByProcedimientoPublico(@Param("id") Long id);

	@Query("select distinct a from Archivo a join a.ejecuciones o where o.id =:id")
	Set<Archivo> findByEjecucion(@Param("id") Long id);

	@Query("select distinct a from Archivo a join a.ejecuciones o where a.publico = TRUE and o.id =:id")
	Set<Archivo> findByEjecucionPublico(@Param("id") Long id);

	@Modifying
	@Transactional
	void deleteByIdIn(List<Long> ids);
}
