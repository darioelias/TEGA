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

import org.tega.domain.Muestra;

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
public interface MuestraRepository extends JpaRepository<Muestra,Long>, JpaSpecificationExecutor<Muestra> {
	
	@Query(	"select muestra "+ 
		   	"from Muestra muestra "+
			"left join fetch muestra.region "+
			"left join fetch muestra.localidad "+
			"left join fetch muestra.profesional "+
			"left join fetch muestra.institucion "+
			"left join fetch muestra.tejido "+
			"left join fetch muestra.modoRecoleccion "+
			"left join fetch muestra.especie "+
			"where muestra.id =:id")
    Muestra findOneWithEagerRelationships(@Param("id") Long id);


	@Query(	"select distinct muestra "+ 
		   	"from Muestra muestra "+
			"left join fetch muestra.region "+
			"left join fetch muestra.localidad "+
			"left join fetch muestra.profesional "+
			"left join fetch muestra.institucion "+
			"left join fetch muestra.tejido "+
			"left join fetch muestra.modoRecoleccion "+
			"left join fetch muestra.especie "+
			"join muestra.conjuntosMuestras c "+
			"join c.analisisGenotipos o "+
			"where o.id in :id")
    List<Muestra> findByAnalisisGenotipoEager(@Param("id") Long id);

	@Query("select NEW org.tega.domain.Muestra(p.id, p.codigoInterno, p.detalle) from Muestra p where lower(p.codigoInterno) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigoInterno)")
    List<Muestra> selector(@Param("texto") String texto, Pageable pageable);

	@Query("select p.id, p.codigoInterno, p.detalle from Muestra p where lower(p.codigoInterno) LIKE lower(CONCAT('%',:texto,'%')) or lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%')) ORDER BY lower(p.codigoInterno)")
    List<Object[]> selectorMin(@Param("texto") String texto, Pageable pageable);

	@Query(	"select distinct p.id, p.codigoInterno, p.detalle "+
			"from Muestra p "+
			"where p.publico = TRUE and (lower(p.codigoInterno) LIKE lower(CONCAT('%',:texto,'%')) or "+
										"lower(p.detalle) LIKE lower(CONCAT('%',:texto,'%'))) "+
			"ORDER BY lower(p.codigoInterno)")
    List<Object[]> selectorMinPublico(@Param("texto") String texto, Pageable pageable);

	@Query("select distinct m from Muestra m join m.proyectos p where p.id =:id")
	List<Muestra> findByProyecto(@Param("id") Long id);

	@Query("select distinct m from Muestra m join m.proyectos p where m.publico = TRUE and p.id =:id")
	List<Muestra> findByProyectoPublico(@Param("id") Long id);

	@Query("select m from Muestra m where m.id in :ids")
	List<Muestra> findByIds(@Param("ids") List<Long> ids);

	@Query("select distinct m from Muestra m join m.conjuntosMuestras c where lower(m.codigoExterno) = lower(:codigoExterno) and lower(c.codigo) = lower(:codigoConjunto)")
	List<Muestra> findByCodigoExternoConjunto(@Param("codigoExterno") String codigoExterno, @Param("codigoConjunto") String codigoConjunto);

	List<Muestra> findByCodigoInternoIgnoreCase(String codigo);

	@Query("select distinct p from Muestra p where lower(p.codigoInterno) = lower(:codigo) and p.id <> :id")
	List<Muestra> findByCodigoInternoModif(@Param("codigo") String codigo, @Param("id") Long id);

	@Query("select distinct e from Muestra e join fetch e.conjuntosMuestras p join p.analisisGenotipos o where o.id =:id")
	List<Muestra> findByAnalisisGenotipo(@Param("id") Long id);

	@Query("select distinct e.id from Muestra e join e.conjuntosMuestras p join p.analisisGenotipos o where o.id =:id")
	Set<Long> findIdsByAnalisisGenotipo(@Param("id") Long id);

	@Query("select distinct e from Muestra e join e.conjuntosMuestras p where p.id =:id")
	List<Muestra> findByConjuntoMuestras(@Param("id") Long id);

	@Query("select distinct e from Muestra e join e.conjuntosMuestras p where e.publico = TRUE and p.id =:id")
	List<Muestra> findByConjuntoMuestrasPublico(@Param("id") Long id);

	@Query("select m from Muestra m where lower(m.codigoInterno) in :codigos")
	List<Muestra> findByCodigosInternos(@Param("codigos") List<String> codigos);

	@Query("select m from Muestra m where lower(m.codigoExterno) in :codigos")
	List<Muestra> findByCodigosExternos(@Param("codigos") List<String> codigos);

	@Query("select p.publico from Muestra p where p.id = :id")
	Boolean esPublico(@Param("id") Long id);

	@Modifying
	@Transactional
	void deleteByIdIn(List<Long> ids);

}
