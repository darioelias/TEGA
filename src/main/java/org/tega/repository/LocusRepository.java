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

import org.tega.domain.Locus;
import org.tega.domain.LocusAtributo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
/**
 * Spring Data JPA repository for the Locus entity.
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public interface LocusRepository extends JpaRepository<Locus,Long>, JpaSpecificationExecutor<Locus> {

	@Query("select distinct l from Locus l join l.analisisGenotipos o where o.id =:id")
	List<Locus> findByAnalisisGenotipo(@Param("id") Long id);

	@Query("select distinct l from Locus l join l.analisisGenotipos o where l.publico = TRUE and o.id =:id")
	List<Locus> findByAnalisisGenotipoPublico(@Param("id") Long id);
		
	@Query("select NEW org.tega.domain.Locus(p.id, p.codigo, p.detalle) from Locus p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<Locus> selector(@Param("texto") String texto, Pageable pageable);

	@Query("select p.id, p.codigo, p.detalle from Locus p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<Object[]> selectorMin(@Param("texto") String texto, Pageable pageable);

	@Query("select p from Locus p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<Locus> selectorCompleto(@Param("texto") String texto, Pageable pageable);


	@Query(	"select distinct p.id, p.codigo, p.detalle "+
			"from Locus p "+
			"where p.publico = TRUE AND (lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or "+
										"lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%'))) "+
			"ORDER BY lower(p.codigo)")
    List<Object[]> selectorMinPublico(@Param("texto") String texto, Pageable pageable);

	@Query("select a from Locus a where lower(a.codigo) in :codigos")
	List<Locus> findByCodigos(@Param("codigos") List<String> codigos);

	List<Locus> findByCodigoIgnoreCase(String codigo);

	@Query("select distinct p from Locus p where lower(p.codigo) = lower(:codigo) and p.id <> :id")
	List<Locus> findByCodigoModif(@Param("codigo") String codigo, @Param("id") Long id);

	@Query("select m from Locus m where m.id in :ids")
	List<Locus> findByIds(@Param("ids") List<Long> ids);

	@Modifying
	@Transactional
	void deleteByIdIn(List<Long> ids);

	@Query("select p.publico from Locus p where p.id = :id")
	Boolean esPublico(@Param("id") Long id);

	@Query("select m from LocusAtributo m join fetch m.atributo a where m.locus.id = :id")
	List<LocusAtributo> findAtributos(@Param("id") Long id);

	@Query("select m from LocusAtributo m join fetch m.atributo a join fetch m.locus u where u.id in :ids")
	List<LocusAtributo> findAtributosByIdsObj(@Param("ids") Set<Long> ids);

}
