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

import org.tega.domain.ParametroProcedimiento;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Spring Data JPA repository for the ParametroProcedimiento entity.
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public interface ParametroProcedimientoRepository extends JpaRepository<ParametroProcedimiento,Long>, JpaSpecificationExecutor<ParametroProcedimiento> {

	@Query(	"select p "+ 
		   	"from ParametroProcedimiento p "+
			"left join fetch p.categoria "+
			"left join fetch p.procedimiento "+
			"where p.id =:id")
    ParametroProcedimiento findOneWithEagerRelationships(@Param("id") Long id);

	@Query("select NEW org.tega.domain.ParametroProcedimiento(p.id, p.codigo, p.detalle) from ParametroProcedimiento p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<ParametroProcedimiento> selector(@Param("texto") String texto, Pageable pageable);

	@Query("select p.id, p.codigo, p.detalle from ParametroProcedimiento p where lower(p.codigo) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigo)")
    List<Object[]> selectorMin(@Param("texto") String texto, Pageable pageable);

	@Query("select p from ParametroProcedimiento p where lower(p.codigo) = lower(:codigo)")
	ParametroProcedimiento buscarPorCodigo(@Param("codigo") String codigo);

	List<ParametroProcedimiento> findByCodigoIgnoreCase(String codigo);

	@Query("select distinct p from ParametroProcedimiento p where lower(p.codigo) = lower(:codigo) and p.id <> :id")
	List<ParametroProcedimiento> findByCodigoModif(@Param("codigo") String codigo, @Param("id") Long id);

	@Query(	"select p "+ 
		   	"from ParametroProcedimiento p "+
			"left join fetch p.categoria "+
			"left join p.procedimiento r "+
			"where (p.noEditable IS NULL or p.noEditable = FALSE) and r.id =:id ")
    List<ParametroProcedimiento> findByProcedimientoEditable(@Param("id") Long id);

	@Query(	"select p "+ 
		   	"from ParametroProcedimiento p "+
			"left join p.categoria c "+
			"left join p.procedimiento r "+
			"where p.noEditable = TRUE and r.id =:idProc and c.id =:idCate")
    List<ParametroProcedimiento> findByProcedimientoCategoriaNoEditable(@Param("idProc") Long idProc, @Param("idCate") Long idCate);

	@Modifying
	@Transactional
	void deleteByIdIn(List<Long> ids);
	
}
