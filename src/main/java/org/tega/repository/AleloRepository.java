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

import org.tega.domain.Alelo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data JPA repository for the Alelo entity.
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public interface AleloRepository extends JpaRepository<Alelo,Long>, JpaSpecificationExecutor<Alelo> {

	@Query("select alelo from Alelo alelo left join fetch alelo.muestra left join fetch alelo.locus where alelo.id =:id")
    Alelo findOneWithEagerRelationships(@Param("id") Long id);

	@Query("select a from Alelo a join fetch a.locus join fetch a.muestra e join e.conjuntosMuestras p join p.analisisGenotipos o where o.id =:id")
	List<Alelo> findByAnalisisGenotipo(@Param("id") Long id);

	@Query("select a.id, a.indice, a.valor, e.id, l.id from Alelo a join a.locus l join a.muestra e join e.conjuntosMuestras p join p.analisisGenotipos o where o.id =:id")
	List<Object[]> findByAnalisisGenotipoMin(@Param("id") Long id);

	@Query("select a.id, a.indice, a.valor, e.id, l.id from Alelo a join a.locus l join a.muestra e join e.conjuntosMuestras p join p.analisisGenotipos o where a.publico = TRUE and o.id =:id")
	List<Object[]> findByAnalisisGenotipoMinPublico(@Param("id") Long id);


	@Query("select a from Alelo a join a.muestra e join fetch a.locus l where e.id =:id")
	List<Alelo> findByMuestra(@Param("id") Long id);

	@Query("select a from Alelo a join a.muestra e join fetch a.locus l where a.publico = TRUE and e.id =:id")
	List<Alelo> findByMuestraPublico(@Param("id") Long id);

//	@Query("delete from Alelo a where a.id in (select ad.id from Alelo ad join ad.muestra e where e.id = :id)")
//	void deleteByMuestra(@Param("id") Long id);

	@Query("select a from Alelo a join fetch a.muestra e join fetch a.locus l where e.id =:id and l.id in :ids")
	List<Alelo> findByMuestraLoci(@Param("id") Long id, @Param("ids") List<Long> ids);

	@Query("select a from Alelo a join a.muestra e join fetch a.locus l where e.id =:idMuestra and l.id = :idLocus and a.indice = :indice")
	List<Alelo> findByMuestraLocusIndice(@Param("idMuestra") Long idMuestra, @Param("idLocus") Long idLocus, @Param("indice") Integer indice);

	@Modifying
	@Transactional
	@Query("delete from Alelo a where a.muestra.id = :id")
	void deleteByMuestra(@Param("id") Long id);

	@Modifying
	@Transactional
	void deleteByIdIn(List<Long> ids);
}
