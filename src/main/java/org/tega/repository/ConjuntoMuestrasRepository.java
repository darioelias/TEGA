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

import org.tega.domain.ConjuntoMuestras;
import org.tega.domain.ConjuntoMuestrasAtributo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
/**
 * Spring Data JPA repository for the ConjuntoMuestras entity.
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public interface ConjuntoMuestrasRepository extends JpaRepository<ConjuntoMuestras,Long>, JpaSpecificationExecutor<ConjuntoMuestras> {

    @Query("select distinct p from ConjuntoMuestras p")
    List<ConjuntoMuestras> findAllWithEagerRelationships();

    @Query("select p from ConjuntoMuestras p where p.id =:id")
    ConjuntoMuestras findOneWithEagerRelationships(@Param("id") Long id);

	@Query("select distinct p from ConjuntoMuestras p left join fetch p.muestras join p.analisisGenotipos o where o.id =:id")
	List<ConjuntoMuestras> findByAnalisisGenotipo(@Param("id") Long id);

	@Query("select distinct p from ConjuntoMuestras p left join fetch p.muestras e join p.analisisGenotipos o where p.publico = TRUE and e.publico = TRUE and o.id =:id")
	List<ConjuntoMuestras> findByAnalisisGenotipoPublico(@Param("id") Long id);

	@Query("select distinct p from ConjuntoMuestras p join p.muestras e where e.id =:id")
	List<ConjuntoMuestras> findByMuestra(@Param("id") Long id);

	@Query("select distinct p from ConjuntoMuestras p join p.muestras e where p.publico = TRUE and e.id =:id")
	List<ConjuntoMuestras> findByMuestraPublico(@Param("id") Long id);

	@Query("select NEW org.tega.domain.ConjuntoMuestras(p.id, p.codigo, p.detalle) from ConjuntoMuestras p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<ConjuntoMuestras> selector(@Param("texto") String texto, Pageable pageable);

	@Query("select p.id, p.codigo, p.detalle from ConjuntoMuestras p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<Object[]> selectorMin(@Param("texto") String texto, Pageable pageable);

	@Query("select p.id, p.codigo, p.detalle from ConjuntoMuestras p where p.publico = TRUE and (lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%'))) ORDER BY lower(p.codigo)")
    List<Object[]> selectorMinPublico(@Param("texto") String texto, Pageable pageable);

    @Query("select p from ConjuntoMuestras p join fetch p.muestras e where lower(p.codigo) = lower(:codigo)")
    ConjuntoMuestras findOneByCodigo(@Param("codigo") String codigo);

	List<ConjuntoMuestras> findByCodigoIgnoreCase(String codigo);

	@Query("select distinct p from ConjuntoMuestras p where lower(p.codigo) = lower(:codigo) and p.id <> :id")
	List<ConjuntoMuestras> findByCodigoModif(@Param("codigo") String codigo, @Param("id") Long id);

	@Modifying
	@Transactional
	void deleteByIdIn(List<Long> ids);


	@Query("select p.publico from ConjuntoMuestras p where p.id = :id")
	Boolean esPublico(@Param("id") Long id);

	@Query("select m from ConjuntoMuestrasAtributo m join fetch m.atributo a where m.conjuntoMuestras.id = :id")
	List<ConjuntoMuestrasAtributo> findAtributos(@Param("id") Long id);

	@Query("select m from ConjuntoMuestrasAtributo m join fetch m.atributo a join fetch m.conjuntoMuestras u where u.id in :ids")
	List<ConjuntoMuestrasAtributo> findAtributosByIdsObj(@Param("ids") Set<Long> ids);
}
