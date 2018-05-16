CREATE OR REPLACE FUNCTION ActualizarProyectoPublico(IN _idProyecto bigint) 
RETURNS varchar(2) AS $$
DECLARE
_publico boolean;
BEGIN
	
	SET TRANSACTION READ WRITE;

	_publico := publico from proyectos where id = _idProyecto;

	DROP TABLE IF EXISTS MuestrasTmp ;
    DROP TABLE IF EXISTS AlelosTmp ;
    DROP TABLE IF EXISTS AlelosTmpPub ;
	DROP TABLE IF EXISTS LocusTmp ;
	DROP TABLE IF EXISTS ArchivosProyectosTmp ;    
	DROP TABLE IF EXISTS ArchivosMuestrasTmp ; 
	DROP TABLE IF EXISTS ArchivosAnalisisGenotiposTmp ;
	DROP TABLE IF EXISTS ArchivosEjecucionesTmp;
    DROP TABLE IF EXISTS ConjuntosMuestrasTmp ; 
    DROP TABLE IF EXISTS ConjuntosMuestrasAntTmp ; 

	   
	CREATE TEMP TABLE MuestrasTmp 
	AS (
	   select distinct pe.muestra_id 
	   from proyectos_muestras pe
	   left join proyectos_muestras pex on pe.muestra_id = pex.muestra_id and pex.proyecto_id <> _idProyecto
	   left join proyectos p on p.id = pex.proyecto_id and p.publico 
	   where pe.proyecto_id = _idProyecto and  (p.id is null or _publico )
	);

	CREATE TEMP TABLE ConjuntosMuestrasAntTmp 
	AS (
	   select distinct ce.id
	   from conjuntos_muestras ce
	   join analisis_genotipos_conjuntos_muestras agc on agc.conjunto_muestras_id = ce.id
	   join analisis_genotipos ag on ag.id = agc.analisis_genotipo_id
	   where ag.proyecto_id <> _idProyecto and ag.publico
	);

	CREATE TEMP TABLE ConjuntosMuestrasTmp 
	AS (
	   select distinct ce.id
	   from conjuntos_muestras ce
	   join analisis_genotipos_conjuntos_muestras agc on agc.conjunto_muestras_id = ce.id
	   join analisis_genotipos ag on ag.id = agc.analisis_genotipo_id
	   left join ConjuntosMuestrasAntTmp cea on cea.id = ce.id
	   where ag.proyecto_id = _idProyecto and (cea.id is null or _publico)
	);

	CREATE TEMP TABLE AlelosTmp 
	AS (
		select a.id, a.locus_id
		from alelos a
		join MuestrasTmp e on a.muestra_id = e.muestra_id
	);

	CREATE TEMP TABLE AlelosTmpPub 
	AS (
		select distinct a.locus_id
		from alelos a
		join AlelosTmp atm on atm.locus_id = a.locus_id
		where a.publico and a.id not in (select distinct id from AlelosTmp)
	);

	CREATE TEMP TABLE LocusTmp 
	AS (
		select distinct l.id
		from loci l
		join AlelosTmp a on a.locus_id = l.id
		left join AlelosTmpPub ax on ax.locus_id = l.id 
		where ax.locus_id is null or _publico
	);

	CREATE TEMP TABLE ArchivosProyectosTmp 
	AS (
		select distinct a.id
		from archivos a
		join proyectos_archivos ma on ma.archivo_id = a.id
		where ma.proyecto_id = _idProyecto
	);

	CREATE TEMP TABLE ArchivosMuestrasTmp 
	AS (
		select distinct a.id
		from archivos a
		join muestras_archivos ma on ma.archivo_id = a.id
		join MuestrasTmp m on m.muestra_id = ma.muestra_id 
	);

	CREATE TEMP TABLE ArchivosAnalisisGenotiposTmp 
	AS (
		select distinct a.id
		from archivos a
		join analisis_genotipos_archivos ma on ma.archivo_id = a.id
		join analisis_genotipos ag on ag.id = ma.analisis_genotipo_id
		where ag.proyecto_id = _idProyecto
	);

	CREATE TEMP TABLE ArchivosEjecucionesTmp 
	AS (
		select distinct a.id
		from archivos a
		join ejecuciones_archivos ea on ea.archivo_id = a.id
		join ejecuciones e on e.id = ea.ejecucion_id
		join analisis_genotipos ag on ag.id = e.analisis_genotipo_id
		where ag.proyecto_id = _idProyecto
	);




	update muestras 	SET publico = _publico WHERE id in (select muestra_id from MuestrasTmp);
	update alelos 		SET publico = _publico WHERE id in (select id from AlelosTmp);
	update loci 		SET publico = _publico WHERE id in (select id from LocusTmp); 	
	update conjuntos_muestras SET publico = _publico WHERE id in (select id from ConjuntosMuestrasTmp);
	update analisis_genotipos SET publico = _publico WHERE proyecto_id = _idProyecto;

	update archivos		SET publico = _publico WHERE id in (select id from ArchivosProyectosTmp 
															union 
															select id from ArchivosMuestrasTmp
															union
															select id from ArchivosAnalisisGenotiposTmp
															union
															select id from ArchivosEjecucionesTmp); 

	DROP TABLE IF EXISTS MuestrasTmp ;
	DROP TABLE IF EXISTS AlelosTmp ;
    DROP TABLE IF EXISTS AlelosTmpPub ;
	DROP TABLE IF EXISTS LocusTmp ;
	DROP TABLE IF EXISTS ArchivosProyectosTmp ;    
	DROP TABLE IF EXISTS ArchivosMuestrasTmp ; 
	DROP TABLE IF EXISTS ArchivosAnalisisGenotiposTmp ; 
	DROP TABLE IF EXISTS ArchivosEjecucionesTmp;
    DROP TABLE IF EXISTS ConjuntosMuestrasTmp ; 
    DROP TABLE IF EXISTS ConjuntosMuestrasAntTmp ;
    
	RETURN 'OK';
END;
$$ LANGUAGE plpgsql;


