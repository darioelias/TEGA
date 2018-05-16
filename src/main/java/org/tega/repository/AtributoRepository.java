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

import org.tega.domain.Atributo;
import org.tega.domain.enumeration.Entidad;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
/**
 * Spring Data JPA repository for the Atributo entity.
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public interface AtributoRepository extends JpaRepository<Atributo,Long>, JpaSpecificationExecutor<Atributo> {

	@Query("select NEW org.tega.domain.Atributo(p.id, p.codigo, p.detalle) from Atributo p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<Atributo> selector(@Param("texto") String texto, Pageable pageable);

	@Query("select p.id, p.codigo, p.detalle from Atributo p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<Object[]> selectorMin(@Param("texto") String texto, Pageable pageable);


	List<Atributo> findByCodigoIgnoreCase(String codigo);

	List<Atributo> findByEntidad(Entidad entidad);

	@Query("select distinct p from Atributo p where lower(p.codigo) = lower(:codigo) and p.id <> :id")
	List<Atributo> findByCodigoModif(@Param("codigo") String codigo, @Param("id") Long id);

	@Modifying
	@Transactional
	void deleteByIdIn(List<Long> ids);
}
