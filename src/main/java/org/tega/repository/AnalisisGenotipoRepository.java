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

import org.tega.domain.AnalisisGenotipo;
import org.tega.domain.Procedimiento;
import org.tega.domain.enumeration.EstadoAnalisisGenotipo;
import org.tega.domain.AnalisisGenotipoAtributo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the AnalisisGenotipo entity.
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public interface AnalisisGenotipoRepository extends JpaRepository<AnalisisGenotipo,Long>, JpaSpecificationExecutor<AnalisisGenotipo> {

    @Query("select a from AnalisisGenotipo a "+
		   "left join fetch a.proyecto "+
		   "left join fetch a.procedimiento "+
		   "where a.id =:id")
    AnalisisGenotipo findOneWithEagerRelationships(@Param("id") Long id);

	@Query("select NEW org.tega.domain.AnalisisGenotipo(p.id, p.codigo, p.detalle) from AnalisisGenotipo p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<AnalisisGenotipo> selector(@Param("texto") String texto, Pageable pageable);

	@Query("select p.id, p.codigo, p.detalle from AnalisisGenotipo p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<Object[]> selectorMin(@Param("texto") String texto, Pageable pageable);

	@Query("select p.id, p.codigo, p.detalle from AnalisisGenotipo p where p.publico = TRUE and (lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%'))) ORDER BY lower(p.codigo)")
    List<Object[]> selectorMinPublico(@Param("texto") String texto, Pageable pageable);

	List<AnalisisGenotipo> findByCodigoIgnoreCase(String codigo);

	@Query("select distinct p from AnalisisGenotipo p where lower(p.codigo) = lower(:codigo) and p.id <> :id")
	List<AnalisisGenotipo> findByCodigoModif(@Param("codigo") String codigo, @Param("id") Long id);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update AnalisisGenotipo set procedimiento = :p, estado = :estado where id = :id")
	int setEstado(@Param("id") Long id, @Param("p") Procedimiento p, @Param("estado") EstadoAnalisisGenotipo estado);

	@Modifying
	@Transactional
	void deleteByIdIn(List<Long> ids);

	@Query("select p.publico from AnalisisGenotipo p where p.id = :id")
	Boolean esPublico(@Param("id") Long id);

	@Query("select m from AnalisisGenotipoAtributo m join fetch m.atributo a where m.analisisGenotipo.id = :id")
	List<AnalisisGenotipoAtributo> findAtributos(@Param("id") Long id);

	@Query("select m from AnalisisGenotipoAtributo m join fetch m.atributo a join fetch m.analisisGenotipo u where u.id in :ids")
	List<AnalisisGenotipoAtributo> findAtributosByIdsObj(@Param("ids") Set<Long> ids);
}
