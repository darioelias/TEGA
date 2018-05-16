INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (1, 'CODIFICACION_ARCHIVOS', 'Coding used in the import from files', 'CARACTER', 'ISO-8859-1',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (2, 'CODIFICACION_ARCHIVOS_ORIGEN', 'Source coding that have the files that will be imported', 'CARACTER', 'UTF-8',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (3, 'SEPARADOR_IMP_EXP_ARCHIVOS', 'Default separator in file import/export', 'CARACTER', ',',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (4, 'ALELOS_IMP_EXP_VALOR_NULO', 'Default value in the import/export of Alleles', 'CARACTER', '?',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (5, 'NOMBRE_PLATAFORMA', 'Name of the platform', 'CARACTER', 'NOMBRE_PLATAFORMA',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (6, 'HTML_HOME_ES', 'HTML of the home page (Spanish)', 'CARACTER', '<div class="row row-centered"><img src="../content/images/logo.png" class="logo-tega img-responsive"></div><div class="row"><br/>    <h1><b>Bienvenid@ a TEGA: Nombre</b></h1><p class="lead"><br/><b><a href="https://github.com/darioelias/TEGA">TEGA</a></b> (Tools for Evolutionary and Genetic Analysis) es una plataforma WEB libre abocada a la gestión y análisis de datos biológicos vinculados a estudios de genética de poblaciones.<br/> <br/><b>Nombre</b> es una implementación de TEGA en el <a href="http://...">Instituto</a>. Descrición...</p><br/></div>',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (7, 'HTML_HOME_EN', 'HTML of the homepage (English)', 'CARACTER', '<div class="row row-centered"><img src="../content/images/logo.png" class="logo-tega img-responsive"></div><div class="row"><br/>    <h1><b>Welcome to TEGA: Name</b></h1><p class="lead"><br/><b><a href="https://github.com/darioelias/TEGA">TEGA</a></b> (Tools for Evolutionary and Genetic Analysis) is a free WEB platform dedicated to the management and analysis of biological data of population genetics studies.<br/> <br/><b>Name</b> is a implementation of TEGA at <a href="http://">Institute</a>. Description...</p><br/></div>',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (8, 'HTML_FOOTER_ES', 'HTML of the footer (Spanish)', 'CARACTER', '<div class="row"><div class="col-xs-4 col-md-4 col-sm-4"><h5><p class="text-center small"><b>TEGA</b><br/>Copyright (C) 2018 Dario E. Elias & Eva C. Rueda.<br/>TEGA se encuentra licenciada bajo <a rel="license" href="https://www.gnu.org/licenses/agpl-3.0.en.html">GNU AGPL v3</a><br/>.<a href="https://github.com/darioelias/TEGA">Documentación y código fuente de TEGA</a>.</p></h5></div><div class="col-xs-4 col-md-4 col-sm-4"><h5><p class="text-center small"><b>Nombre</b><br/>Copyright (C) Año Instituto<br/>El contenido (datos) público de Nombre se encuentra licenciado bajo <a rel="license" href="http://...">Licencia</a>.</p></h5></div><div class="col-xs-4 col-md-4 col-sm-4"><h5><p class="text-center small"><b>Contactar a Nombre</b><br/>email@contacto.com<br/><a href="http://...">Link Instituto</a></p></h5></div></div>',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (9, 'HTML_FOOTER_EN', 'HTML of the footer (English)', 'CARACTER', '<div class="row"><div class="col-xs-4 col-md-4 col-sm-4"><h5><p class="text-center small"><b>TEGA</b><br/>Copyright (C) 2018 Dario E. Elias & Eva C. Rueda.<br/>TEGA is licensed under <a rel="license" href="https://www.gnu.org/licenses/agpl-3.0.en.html">GNU AGPL v3</a><br/>.<a href="https://github.com/darioelias/TEGA">TEGA documentation and source code</a><br/>.</p></h5></div><div class="col-xs-4 col-md-4 col-sm-4"><h5><p class="text-center small"><b>Name</b><br/>Copyright (C) Year Institute.<br/>The public content (data) of Name is licensed under <a rel="license" href="http://...">License</a>.</p></h5></div><div class="col-xs-4 col-md-4 col-sm-4"><h5><p class="text-center small"><b>Contact Name</b><br/>email@contact.com<br/><a href="http://...">Link Institute</a></p></h5></div></div>',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (10, 'URL_LOGO_ICO', 'URL for the platform icon (favicon.ico)', 'CARACTER', 'favicon.ico',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (11, 'URL_LOGO_MENU', 'URL for the menu logo', 'CARACTER', '../content/images/logo_min.png',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (12, 'MAPA_MUESTRAS_LATITUD', 'Map of Samples: initial latitude (decimal)', 'NUMERICO', '-31.1',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (13, 'MAPA_MUESTRAS_LONGITUD', 'Map of Samples: initial longitude (decimal)', 'NUMERICO', '-59.1',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (14, 'ALELOS_IMP_TIPO_IMPORTACION', 'Import type by default of Alleles [MATRIZ, LISTA]', 'CARACTER', 'MATRIZ',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (15, 'MAPA_MUESTRAS_ICONO', 'Map of Samples: icon used in the map of samples (base64)', 'CARACTER', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABUAAAAcCAYAAACOGPReAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAwcgAAMHIBPY7h2AAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAOlSURBVEiJrZVPSGNXFMa/ex0pTwmpttpgRaxCJ+hQxICB5Eaqi2ThxkFoIMwsanXVFHdSVzMlXUy3QnGhtOhEXChmJyaUUvJHDDow1KeGYYp21FCdjiZ9tfnnvNOFMdj4ktGhHzx49577/e7lcs49jIigJavVeocxZmWMmQCYAHwM4BmAJ0T0hIgikUhE1vKyYqjD4ahOpVKPiOhLAExzx3MRY+x7SZK+9vv9pyWhQggbY+xHAK2VlZVnAwMDt9ra2tDY2AiDwYCjoyPs7u4iFovB5/OpmUyGA/iNiD4Ph8OhK9A88BcAam9v7x8jIyONtbW1JY95fHyMiYmJ0+XlZQkAiOjTApiIYLfbq4UQz4UQNDs7+zvdQD6fLy2EICHEc7vdXk1E4ACQSqUeAWhtaWlZcblcTWXu8Yr6+/vfMZlMLwC05jlgFovlDuf8V8bYnt/vr5Ak6cObQPOHor6+vsNcLveBqqqfcMaYFQAzGo1/lgOm0+mSUEmSmMViIQCMMWbl+TxEV1dXTSmTx+OBw+GAx+MpCTabzQacU00c54mNjo6O97UWJ5NJBAIBqKqKQCCAZDKpCTUajRc5beIAbgNAU1OTTmuxXq+H0+mETqeD0+mEXq/XhDY3NwMAAbh9C8AeAOPOzg7q6+s1DW63G263WzN2oYODA+C8Avc4gG0AkGXNMr62tre3C7+ciFYAYGNj43+BEtEK55xPA8jKsgxFUd4KqKoq1tbWwBg745xP82Aw+BLAYiaTweLi4ltBQ6EQ9vf3UVVVtRQMBl/y/E7fcc5pYWEBmUzmxlCv14uKigooivIAwHntRyKRp3V1dT8kEgksLS3dCLi+vo5YLIbOzs6fIpHI0wIUAOLx+Fc1NTV/zc3NIZfLXRs6MzMDg8GQ0+v1A4VJIip8w8PDnwkhaHx8/FrP3vz8PAkhaHJy8tvLnP9AiQgul+tnm81Gq6urZYFbW1vU09NDbrdbJiJ2mXGlR5nN5vckSXqm0+nenZ6e5lqvv6IoGBwcpJOTk78VRfkoGo2+uhznxYZoNPrq7OysP5FIvB4bG/unOHdPT08xOjqqHB4evk6n033FQECjm16ou7v7HhE9bmhowNDQENrb27G5uYmpqSmKx+OMMXY/GAx6tbwloQBgs9keAHioEXoYCoW+KeUrCwUAIcRjxti9izERecPh8P1ynit3WqxEIvEFgIueHsqPy+qNUFmWs9ls9i5jbCmbzd6VZTn7Js+/xiU0Kwk65isAAAAASUVORK5CYII=',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (16, 'DIRECTORIO_ENTIDADES', 'Directory where the entities files will be saved (do not include the final bar)', 'CARACTER', 'archivos',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (17, 'COMANDO_BACKUP', 'Command that will be used to generate the backups', 'CARACTER', './proc/backup.sh',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (18, 'DIRECTORIO_BACKUP', 'Directory where backup files will be saved (do not include the final bar)', 'CARACTER', 'backups',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (19, 'FILTRO_ARCHIVOS_BACKUP', 'File filter used to identify backup files', 'CARACTER', 'backup_*.tar.gz.gpg',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (20, 'ARCHIVO_CONFIG_BACKUP', 'Address of the backup procedure configuration file', 'CARACTER', 'config/backup.config.sh',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (21, 'COMANDO_PROCEDIMIENTOS', 'Command used to execute the Analysis Procedures', 'CARACTER', './proc/ejecutar_procedimiento.sh',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (22, 'USUARIO_PROCEDIMIENTOS', 'User used for the execution of the Analysis Procedures', 'CARACTER', '',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (23, 'CLAVE_USUARIO_PROCEDIMIENTOS', 'User password used for the execution of Analysis Procedures', 'CARACTER', '',false);

INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (24, 'EXP_PROC_SEP_ALELOS', 'Character separator of alleles used in the export of genotypes to the Procedures', 'CARACTER', '/',false);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (25, 'EXP_PROC_VALOR_NULO', 'Null value of the alleles used in the export of genotypes to the Procedures (the expression must be numerical)', 'CARACTER', '-9',false);

INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (26, 'LENGUAJE_DEFECTO', 'Default language [es: spanish, en: english]', 'CARACTER', 'en',true);
INSERT INTO parametros_tega (id,codigo,detalle,tipo,valor,publico) VALUES (27, 'ROL_USUARIO_DEFECTO', 'Default user role [ANONIMO, INVITADO]', 'CARACTER', 'INVITADO',false);

SELECT pg_catalog.setval('parametros_tega_id_seq', 27, true);

INSERT INTO jhi_authority VALUES ('ROLE_ABM');
INSERT INTO jhi_authority VALUES ('ROLE_ADMIN');
INSERT INTO jhi_authority VALUES ('ROLE_MANAGER');
INSERT INTO jhi_authority VALUES ('ROLE_USER');
INSERT INTO jhi_authority VALUES ('ROLE_USER_EXTENDED');

INSERT INTO jhi_user VALUES (1, 'system', '2017-02-19 22:49:28', NULL, NULL, true, NULL, 'anonymoususer@localhost', 'Anonymous', 'en', 'User', 'anonymoususer', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', NULL, NULL, NULL);
INSERT INTO jhi_user VALUES (2, 'system', '2017-02-19 22:49:28', NULL, NULL, true, NULL, 'admin@localhost', 'Admin', 'en', 'Admin', 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', NULL, NULL, 'IMPLEMENTADOR');
INSERT INTO jhi_user VALUES (3,'anonymousUser','2017-03-01 15:43:28.815','anonymous','2017-03-01 15:47:01',true,NULL,'anonymous@localhost',NULL,'en',NULL,'anonymous','$2a$10$NHF6s56Y8CJ1lccwcEoW5OI9Dy5jnHjIXDs8dpBJLVJRitadVlsD.',NULL,NULL,'ANONIMO');
INSERT INTO jhi_user VALUES (4,'anonymousUser','2017-03-01 16:51:59.303','manager','2017-11-26 13:29:40',true,NULL,'manager@localhost',NULL,'en',NULL,'manager','$2a$10$F5FiFeH0A213hhVSjB1qZulgXYYzc60SeqIr/PyYTpiu1Voec3KO.',NULL,NULL,'ADMINISTRADOR');
INSERT INTO jhi_user VALUES (5,'anonymousUser','2017-03-01 16:54:13.422','researcher','2017-11-26 13:30:04',true,NULL,'researcher@localhost',NULL,'en',NULL,'researcher','$2a$10$2d5RZPYtYIw7VW06svPrJOx.51C8y2/1jARxcY3H2fJDa0yNLEvKS',NULL,NULL,'INVESTIGADOR');
INSERT INTO jhi_user VALUES (6,'anonymousUser','2017-03-02 12:15:54.685','invited','2017-11-26 13:30:45',true,NULL,'invited@localhost',NULL,'en',NULL,'invited','$2a$10$RLE7p5X4paat0Fv0UG7RhuHMXrNCXx23xBoO.aI9gAijy7MjIgujm',NULL,NULL,'INVITADO');

SELECT pg_catalog.setval('jhi_user_id_seq', 6, true);

INSERT INTO jhi_user_authority VALUES (2, 'ROLE_ABM');
INSERT INTO jhi_user_authority VALUES (2, 'ROLE_ADMIN');
INSERT INTO jhi_user_authority VALUES (2, 'ROLE_MANAGER');
INSERT INTO jhi_user_authority VALUES (2, 'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (2, 'ROLE_USER_EXTENDED');
INSERT INTO jhi_user_authority VALUES (3,'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (4,'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (4,'ROLE_ABM');
INSERT INTO jhi_user_authority VALUES (4,'ROLE_USER_EXTENDED');
INSERT INTO jhi_user_authority VALUES (4,'ROLE_MANAGER');
INSERT INTO jhi_user_authority VALUES (5,'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (5,'ROLE_ABM');
INSERT INTO jhi_user_authority VALUES (5,'ROLE_USER_EXTENDED');
INSERT INTO jhi_user_authority VALUES (6,'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (6,'ROLE_USER_EXTENDED');
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


INSERT INTO procedimientos (id,codigo,nombre,comando_eje,comando_log,exportar_genotipos_structure,exportar_cantidades,exportar_conjuntos_muestras) VALUES (1, 'structure', 'Structure','./structure.sh','./log.sh',true,true,true);
INSERT INTO procedimientos (id,codigo,nombre,comando_eje,comando_log,exportar_genotipos_genepop) VALUES (2, 'genepop', 'Genepop','./genepop.sh','./log.sh',true);
INSERT INTO procedimientos (id,codigo,nombre,comando_eje,comando_log,exportar_genotipos) VALUES (3, 'dapc', 'DAPC','./dapc.sh','./log.sh',true);
INSERT INTO procedimientos (id,codigo,nombre,comando_eje,comando_log,exportar_genotipos) VALUES (4, 'ind_val', 'Indexes and Validations','./ind_val.sh','./log.sh',true);

SELECT pg_catalog.setval('procedimientos_id_seq', 4, true);


INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (1, 'PARAMETRO', 'General parameters');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (2, 'ADICIONAL', 'Additional parameters');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (3, 'STRUCTURE_MAIN', 'Structure: Main parameters');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (4, 'STRUCTURE_EXTRA', 'Structure: Extra parameters');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (5, 'DISTRUCT', 'Distruct parameters');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (6, 'CLUMPP', 'CLUMPP parameters');
	
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (7, 'GENEPOP_OPCIONES', 'Genepop options');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (8, 'GENEPOP_SETTINGS', 'Genepop parameters');
	
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (9, 'DAPC_FC', 'Determination of K');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (10, 'DAPC_CV', 'Determination of PCs');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (11, 'DAPC_SP', 'Scatter plot');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (12, 'DAPC_LP', 'Variable contribution chart');
	
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (13, 'IND_VAL_GEN_CURV', 'Genotype curve');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (14, 'IND_VAL_DIVERSITY', 'Diversity at Sample Sets level');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (15, 'IND_VAL_HW', 'Hardy - Weinberg');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (16, 'IND_VAL_LOCUS_TABLE', 'Diversity at Loci level');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (17, 'IND_VAL_ALELOS', 'Allele frequency and average');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (18, 'IND_VAL_DIST_POP', 'Distance between Samples Sets');
	
SELECT pg_catalog.setval('categorias_parametros_procedimientos_id_seq', 18, true);	
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (2,1,3, 'STRUCTURE_BURNIN', 'Length of burnin period', false, 'ENTERO', '10000');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (3,1,3, 'STRUCTURE_NUMREPS', 'Number of MCMC reps after burnin', false, 'ENTERO', '20000');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (4,1,3, 'STRUCTURE_PLOIDY', 'Ploidy of data', false, 'ENTERO', '2');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (5,1,3, 'STRUCTURE_MISSING', 'Value given to missing genotype data', true, 'ENTERO', '-9');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (6,1,3, 'STRUCTURE_ONEROWPERIND', 'Store data for individuals in a single line', true, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (7,1,3, 'STRUCTURE_LABEL', 'Input file contains individual labels', true, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (8,1,3, 'STRUCTURE_POPDATA', 'Input file contains a population identifier', true, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (9,1,3, 'STRUCTURE_POPFLAG', 'Input file contains a flag which says whether to use popinfo when USEPOPINFO==1', true, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (10,1,3, 'STRUCTURE_LOCDATA', 'Input file contains a location identifier', true, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (11,1,3, 'STRUCTURE_PHENOTYPE', 'Input file contains phenotype information', true, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (12,1,3, 'STRUCTURE_EXTRACOLS', 'Number of additional columns of data before the genotype data start.', true, 'ENTERO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (13,1,3, 'STRUCTURE_MARKERNAMES', 'Data file contains row of marker names', true, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (14,1,3, 'STRUCTURE_RECESSIVEALLELES', 'Data file contains dominant markers (eg AFLPs) and a row to indicate which alleles are recessive', true, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (15,1,3, 'STRUCTURE_MAPDISTANCES', 'Data file contains row of map distances between loci', true, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (16,1,3, 'STRUCTURE_PHASED', 'Data are in correct phase (relevant for linkage model only)', true, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (17,1,3, 'STRUCTURE_PHASEINFO', 'The data for each individual contains a line indicating phase (linkage model)', true, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (18,1,3, 'STRUCTURE_MARKOVPHASE', 'The phase info follows a Markov model.', true, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (19,1,3, 'STRUCTURE_NOTAMBIGUOUS', 'For use in some analyses of polyploid data', true, 'ENTERO', '-9');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (20,1,4, 'STRUCTURE_STRUCTURE_NOADMIX', 'Use no admixture model (0=admixture model, 1=no-admix)', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (21,1,4, 'STRUCTURE_LINKAGE', 'Use the linkage model', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (22,1,4, 'STRUCTURE_USEPOPINFO', 'Use prior population information to pre-assign individuals to clusters', true, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (23,1,4, 'STRUCTURE_LOCPRIOR', 'Use location information to improve weak data', true, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (24,1,4, 'STRUCTURE_FREQSCORR', 'Allele frequencies are correlated among pops', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (25,1,4, 'STRUCTURE_ONEFST', 'Assume same value of Fst for all subpopulations', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (26,1,4, 'STRUCTURE_INFERALPHA', 'Infer ALPHA (the admixture parameter)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (27,1,4, 'STRUCTURE_POPALPHAS', 'Individual alpha for each population', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (28,1,4, 'STRUCTURE_ALPHA', 'Dirichlet parameter for degree of admixture (this is the initial value if INFERALPHA==1).', false, 'NUMERICO', '1.0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (29,1,4, 'STRUCTURE_INFERLAMBDA', 'Infer LAMBDA (the allele frequencies parameter)', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (30,1,4, 'STRUCTURE_POPSPECIFICLAMBDA', 'Infer a separate lambda for each pop (only if INFERLAMBDA=1)', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (31,1,4, 'STRUCTURE_LAMBDA', 'Dirichlet parameter for allele frequencies', false, 'NUMERICO', '1.0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (32,1,4, 'STRUCTURE_FPRIORMEAN', 'Prior mean and SD of Fst for pops.', false, 'NUMERICO', '0.01');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (33,1,4, 'STRUCTURE_FPRIORSD', 'The prior is a Gamma distribution with these parameters', false, 'NUMERICO', '0.05');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (34,1,4, 'STRUCTURE_UNIFPRIORALPHA', 'Use a uniform prior for alpha otherwise gamma prior', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (35,1,4, 'STRUCTURE_ALPHAMAX', 'Max value of alpha if uniform prior', false, 'NUMERICO', '10.0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (36,1,4, 'STRUCTURE_ALPHAPRIORA', '(only if UNIFPRIORALPHA==0): alpha has a gamma prior with mean A*B, and variance A*B^2.', false, 'NUMERICO', '1.0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (37,1,4, 'STRUCTURE_ALPHAPRIORB', '(only if UNIFPRIORALPHA==0): alpha has a gamma prior with mean A*B, and variance A*B^2.', false, 'NUMERICO', '2.0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (38,1,4, 'STRUCTURE_LOG10RMIN', 'Log10 of minimum allowed value of r under linkage model', false, 'NUMERICO', '-4.0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (39,1,4, 'STRUCTURE_LOG10RMAX', 'Log10 of maximum allowed value of r', false, 'NUMERICO', '1.0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (40,1,4, 'STRUCTURE_LOG10RPROPSD', 'Standard deviation of log r in update', false, 'NUMERICO', '0.1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (41,1,4, 'STRUCTURE_LOG10RSTART', 'Initial value of log10 r', false, 'NUMERICO', '-2.0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (42,1,4, 'STRUCTURE_GENSBACK', 'For use when inferring whether an individual is an immigrant, or has an immigrant an-GENSBACK==2, it tests for immigrant ancestry back to grandparents.', false, 'ENTERO', '2');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (43,1,4, 'STRUCTURE_MIGRPRIOR', 'Prior prob that an individual is a migrant (used only when USEPOPINFO==1).  This should be small, eg 0.01 or 0.1.', false, 'NUMERICO', '0.01');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (44,1,4, 'STRUCTURE_PFROMPOPFLAGONLY', 'Only use individuals with POPFLAG=1 to update	P. This is to enable use of a reference set of  individuals for clustering additional \"test\" individuals.', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (45,1,4, 'STRUCTURE_LOCISPOP', 'Use POPDATA for location information', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (46,1,4, 'STRUCTURE_LOCPRIORINIT', 'Initial value for r, the location prior', false, 'NUMERICO', '1.0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (47,1,4, 'STRUCTURE_MAXLOCPRIOR', 'Max allowed value for r', false, 'NUMERICO', '20.0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (48,1,4, 'STRUCTURE_PRINTNET', 'Print the \"net nucleotide distance\" to screen during the run', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (49,1,4, 'STRUCTURE_PRINTLAMBDA', 'Print current value(s) of lambda to screen', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (50,1,4, 'STRUCTURE_PRINTQSUM', 'Print summary of current population membership to screen', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (51,1,4, 'STRUCTURE_SITEBYSITE', 'Whether or not to print site by site results (Linkage model only)', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (52,1,4, 'STRUCTURE_PRINTQHAT', 'Q-hat printed to a separate file.  Turn this on before using STRAT.', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (53,1,4, 'STRUCTURE_UPDATEFREQ', 'Frequency of printing update on the screen. Set automatically if this is 0.', false, 'ENTERO', '100');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (54,1,4, 'STRUCTURE_PRINTLIKES', 'Print current likelihood to screen every rep', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (55,1,4, 'STRUCTURE_INTERMEDSAVE', 'Number of saves to file during run', false, 'ENTERO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (56,1,4, 'STRUCTURE_ECHODATA', 'Print some of data file to screen to check that the data entry is correct.', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (57,1,4, 'STRUCTURE_ANCESTDIST', 'Collect data about the distribution of ancestry coefficients (Q) for each individual', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (58,1,4, 'STRUCTURE_NUMBOXES', 'The distribution of Q values is stored as a histogram with this number of boxes.', false, 'ENTERO', '1000');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (59,1,4, 'STRUCTURE_ANCESTPINT', 'The size of the displayed probability interval on Q (values between 0.0--1.0)', false, 'NUMERICO', '0.90');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (60,1,4, 'STRUCTURE_COMPUTEPROB', 'Estimate the probability of the Data under  the model.  This is used when choosing the best number of subpopulations.', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (61,1,4, 'STRUCTURE_ADMBURNIN', '[only relevant for linkage model]:  Initial period of burnin with admixture model', false, 'ENTERO', '500');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (62,1,4, 'STRUCTURE_ALPHAPROPSD', 'SD of proposal for updating alpha', false, 'NUMERICO', '0.025');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (63,1,4, 'STRUCTURE_STARTATPOPINFO', 'Use given populations as the initial condition for population origins.  (Need POPDATA==1).  It                               is assumed that the PopData in the input file are between 1 and k where k<=MAXPOPS.', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (64,1,4, 'STRUCTURE_RANDOMIZE', 'Use new random seed for each run', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (65,1,4, 'STRUCTURE_SEED', 'Seed value for random number generator (must set RANDOMIZE=0)', false, 'ENTERO', '2245');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (66,1,4, 'STRUCTURE_METROFREQ', 'Frequency of using Metropolis step to update Q under admixture model (ie use the metr. move everyi steps).  If this is set to 0, it is never used. (Proposal for each q^(i) sampled from prior.  The goal is to improve mixing for small alpha.)', false, 'ENTERO', '10');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (67,1,4, 'STRUCTURE_REPORTHITRATE', 'report hit rate if using METROFREQ', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (69,1,5, 'DISTRUCT_DISTRUCT_PRINT_LABEL_ATOP', 'Print labels above figure', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (70,1,5, 'DISTRUCT_PRINT_LABEL_BELOW', 'print labels below figure', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (71,1,5, 'DISTRUCT_PRINT_SEP', 'print lines to separate populations', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (72,1,5, 'DISTRUCT_FONTHEIGHT', 'size of font', false, 'NUMERICO', '6');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (73,1,5, 'DISTRUCT_DIST_ABOVE', 'distance above plot to place text', false, 'NUMERICO', '5');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (74,1,5, 'DISTRUCT_DIST_BELOW', 'distance below plot to place text', false, 'NUMERICO', '-7');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (75,1,5, 'DISTRUCT_BOXHEIGHT', 'height of the figure', false, 'NUMERICO', '36');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (76,1,5, 'DISTRUCT_INDIVWIDTH', 'width of an individual', false, 'NUMERICO', '1.5');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (77,1,5, 'DISTRUCT_ORIENTATION', '0 for horizontal orientation (default) , 1 for vertical orientation,  2 for reverse horizontal orientation', false, 'ENTERO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (78,1,5, 'DISTRUCT_XORIGIN', 'lower-left x-coordinate of figure', false, 'NUMERICO', '70');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (79,1,5, 'DISTRUCT_YORIGIN', 'lower-left y-coordinate of figure', false, 'NUMERICO', '10');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (80,1,5, 'DISTRUCT_XSCALE', 'scale for x direction', false, 'NUMERICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (81,1,5, 'DISTRUCT_YSCALE', 'scale for y direction', false, 'NUMERICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (82,1,5, 'DISTRUCT_ANGLE_LABEL_ATOP', 'angle for labels atop figure (in [0,180])', false, 'NUMERICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (83,1,5, 'DISTRUCT_ANGLE_LABEL_BELOW', 'angle for labels below figure (in [0,180])', false, 'NUMERICO', '90');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (84,1,5, 'DISTRUCT_LINEWIDTH_RIM', 'width of \"pen\" for rim of box', false, 'NUMERICO', '3');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (85,1,5, 'DISTRUCT_LINEWIDTH_SEP', 'width of \"pen\" for separators between pops and for tics', false, 'NUMERICO', '0.3');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (86,1,5, 'DISTRUCT_LINEWIDTH_IND', 'width of \"pen\" used for individuals', false, 'NUMERICO', '0.3');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (87,1,5, 'DISTRUCT_GRAYSCALE', 'use grayscale instead of colors', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (88,1,5, 'DISTRUCT_ECHO_DATA', 'print some of the data to the screen', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (89,1,5, 'DISTRUCT_REPRINT_DATA', 'print the data as a comment in the ps file', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (90,1,5, 'DISTRUCT_PRINT_INFILE_NAME', 'print the name of INFILE_POPQ above the figure this option is meant for use only with ORIENTATION=0', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (91,1,5, 'DISTRUCT_PRINT_COLOR_BREWER', 'print ColorBrewer settings in the output file this option adds 1689 lines and 104656 bytes to the output and is required if using ColorBrewer colors', false, 'LOGICO', '1');




INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (105,1,2, 'STRUCTURE_K_DESDE', 'Initial value of K for Structure execution', false, 'ENTERO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (106,1,2, 'STRUCTURE_K_HASTA', 'Final value of K for Structure execution', false, 'ENTERO', '4');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (107,1,2, 'STRUCTURE_REPLICAS', 'Number of replicates per K for Structure execution', false, 'ENTERO', '3');


INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (108,2,7, 'GENEPOP_OP_1.1', 'HW test for each locus in each population: H1 = Heterozygote deficiency', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (109,2,7, 'GENEPOP_OP_1.2', 'HW test for each locus in each population: H1 = Heterozygote excess', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (110,2,7, 'GENEPOP_OP_1.3', 'HW test for each locus in each population: Probability test', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (111,2,7, 'GENEPOP_OP_1.4', 'Global test: H1 = Heterozygote deficiency', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (112,2,7, 'GENEPOP_OP_1.5', 'Global test: H1 = Heterozygote excess', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (113,2,7, 'GENEPOP_OP_2.1', 'Pairwise associations (haploid and genotypic disequilibrium): Test for each pair of loci in each population', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (114,2,7, 'GENEPOP_OP_2.2', 'Pairwise associations (haploid and genotypic disequilibrium): Only create genotypic contingency tables', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (115,2,7, 'GENEPOP_OP_3.1', 'Genic differentiation: for all populations', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (116,2,7, 'GENEPOP_OP_3.2', 'Genic differentiation: for all pairs of populations', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (117,2,7, 'GENEPOP_OP_3.3', 'Genotypic differentiation: for all populations', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (118,2,7, 'GENEPOP_OP_3.4', 'Genotypic differentiation: for all pairs of populations', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (119,2,7, 'GENEPOP_OP_5.1', 'Allele and genotype frequencies per locus and per sample', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (120,2,7, 'GENEPOP_OP_5.2', 'Gene diversities & Fis: Using allele identity', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (121,2,7, 'GENEPOP_OP_5.3', 'Gene diversities & Fis: Using allele size', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (122,2,7, 'GENEPOP_OP_6.1', 'Estimating spatial structure: Allele identity (F-statistics) for all populations', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (123,2,7, 'GENEPOP_OP_6.2', 'Estimating spatial structure: Allele identity (F-statistics) for all population pairs', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (124,2,7, 'GENEPOP_OP_6.3', 'Estimating spatial structure: Allele size (Rho-statistics) for all populations', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (125,2,7, 'GENEPOP_OP_6.4', 'Estimating spatial structure: Allele size (Rho-statistics) for all population pairs', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (126,2,7, 'GENEPOP_OP_8.1', 'Null allele: estimates of allele frequencies', false, 'LOGICO', '1');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (127,2,8, 'GENEPOP_Dememorisation', 'Dememorisation', false, 'ENTERO', '10000');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (128,2,8, 'GENEPOP_BatchLength', 'BatchLength', false, 'ENTERO', '5000');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (129,2,8, 'GENEPOP_BatchNumber', 'BatchNumber', false, 'ENTERO', '20');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (130,2,8, 'GENEPOP_HWtests', 'HWtests', false, 'CARACTER', 'Enumeration');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (131,2,8, 'GENEPOP_Mode', 'Mode', true, 'CARACTER', 'Batch');


INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (132,1,6, 'CLUMPP_M', 'Method to be used (1 = FullSearch, 2 = Greedy, 3 = LargeKGreedy).', false, 'ENTERO', '3');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (133,1,6, 'CLUMPP_W', 'Weight by the number of individuals in each population as specified in the datafile.', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (134,1,6, 'CLUMPP_S', 'Pairwise matrix similarity statistic to be used. 1 = G, 2 = G'' ', false, 'ENTERO', '2');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (135,1,6, 'CLUMPP_GREEDY_OPTION', '(Only options 1 and 2 are supported) 1 = All possible input orders, 2 = random input orders, 3 = pre-specified input orders.', false, 'ENTERO', '2');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (136,1,6, 'CLUMPP_REPEATS', 'If GREEDY_OPTION = 2, then REPEATS determines the number of random input orders to be tested. If GREEDY_OPTION = 3, then REPEATS is the number of input orders in PERMUTATIONFILE. ', false, 'ENTERO', '1000');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (137,1,6, 'CLUMPP_PRINT_PERMUTED_DATA', 'Print the permuted data (clusters) in INDFILE or POPFILE to PERMUTED_DATAFILE (0 = don''t print, 1 = print into one file, 2 = print into separate files for each run).', false, 'ENTERO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (138,1,6, 'CLUMPP_PRINT_EVERY_PERM', 'Print every tested permutation of the runs and the corresponding value of SSC to a file specified by EVERY_PERMFILE (0 = don''t print, 1 = print). Note that printing may result in a very large file.', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (139,1,6, 'CLUMPP_PRINT_RANDOM_INPUTORDER', 'Print random input orders of runs to RANDOM_INPUTORDER (0 = don''t print, 1 = print). This option is only available if GREEDY_OPTION = 2.', false, 'LOGICO', '0');


INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (140,1,2, 'STRUCTURE_HILOS', 'Number of execution threads for Structure. (0 indicates all available)', false, 'ENTERO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (141,1,2, 'STRUCTURE_COLOR_EVANNO_DELTA_K', 'Color of the lines and points of the Evanno chart, Delta K vs K', false, 'CARACTER', '#0099FF');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (142,3,1, 'DAPC_LEYENDA_CONJUNTO', 'Attribute of Sample Sets to be used as a legend [detalle, codigo, id]', false, 'CARACTER', 'detalle');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (143,3,1, 'DAPC_SCALE', 'Indicates if the variables should be scaled', false, 'LOGICO', '0');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (144,3,9, 'DAPC_FC_PERC_PCA', 'Minimum percentage of the total variance. If DAPC_FC_N_PCA is different from 0 this parameter must be 0. (function adegenet/find.clusters:perc.pca)', false, 'NUMERICO', '95');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (145,3,9, 'DAPC_FC_N_PCA', 'Number of axes retained in the PCA. If DAPC_FC_PERC_PCA is different from 0 this parameter must be 0. (function adegenet/find.clusters:n.pca)', false, 'ENTERO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (146,3,9, 'DAPC_FC_EJECUTAR', 'Indicates whether the number of groups (K) will be determined (function adegenet/find.clusters)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (147,3,9, 'DAPC_FC_METHOD', 'Clustering method [kmeans, ward] (function adegenet/find.clusters:method)', false, 'CARACTER', 'kmeans');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (148,3,9, 'DAPC_FC_STAT', 'Statistic to use [BIC, AIC, WSS] (function adegenet/find.clusters:stat)', false, 'CARACTER', 'BIC');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (149,3,9, 'DAPC_FC_CRITERION', 'Selection criteria of K [diffNgroup, min, goesup, smoothNgoesup, goodfit] (function adegenet/find.clusters:criterion)', false, 'CARACTER', 'diffNgroup');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (150,3,9, 'DAPC_FC_N_ITER', 'Number of iterations used with kmeans (function adegenet/find.clusters:n.iter)', false, 'ENTERO', '10000');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (151,3,9, 'DAPC_FC_N_STAR', 'Initial number of kmeans centroids (function adegenet/find.clusters:n.start)', false, 'ENTERO', '10');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (152,3,9, 'DAPC_FC_PLOT_COL_BASE', 'Base color used in the graph of the statistic vs K', false, 'CARACTER', '#0099FF');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (153,3,9, 'DAPC_FC_PLOT_COL_SEL', 'Color of K selected in the graph of the statistic vs K', false, 'CARACTER', 'red');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (154,3,9, 'DAPC_FC_K_MAX', 'Maximum value of K to analyze. If it is 0, it will be calculated as the integer value of the number of samples over ten. (function adegenet/find.clusters:max.n.clust)', false, 'ENTERO', '0');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (155,3,10, 'DAPC_CV_NCPUS', 'Number of execution threads (if OMP is installed, leave at 0) (function adegenet/xvalDapc:ncpus)', false, 'ENTERO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (156,3,10, 'DAPC_CV_NA_METHOD', 'Method used to replace null values [mean, zero] (function adegenet/tab:NA.method)', false, 'CARACTER', 'mean');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (157,3,10, 'DAPC_CV_REP', 'Number of repetitions of cross-validation (function adegenet/xvalDapc:n.rep)', false, 'ENTERO', '30');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (158,3,10, 'DAPC_CV_EXP', 'Value that will be used to determine the range of PCs to be evaluated in the second cross validation (VC) (rango a evaluar = mejor PC de la VC 1 +- DAPC_CV_EXP)', false, 'ENTERO', '5');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (159,3,10, 'DAPC_CV_TRAINING_SET', 'Percentage of samples that will be used for the training set (function adegenet/xvalDapc:training.set)', false, 'NUMERICO', '0.9');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (160,3,10, 'DAPC_CV_RESULT', 'Prediction method [groupMean, overall] (function adegenet/xvalDapc:result)', false, 'CARACTER', 'groupMean');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (161,3,11, 'DAPC_SP_LEGEND', 'Indicates if the legends are displayed (function adegenet/scatter:legend)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (162,3,11, 'DAPC_SP_CLABEL', 'Indicates whether the labels are displayed (function adegenet/scatter:legend)', false, 'LOGICO', '0');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (163,3,11, 'DAPC_SP_SCREE_PCA', 'Indicates whether PCAs are displayed (function adegenet/scatter:scree.pca)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (164,3,11, 'DAPC_SP_SCREE_DA', 'Indicates if the DAs are displayed (function adegenet/scatter:scree.da)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (165,3,11, 'DAPC_SP_POSI_LEG', 'Position of the legends [bottomleft, bottomright, topleft, topright] (function adegenet/scatter:posi.leg)', false, 'CARACTER', 'bottomleft');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (166,3,11, 'DAPC_SP_POSI_PCA', 'Position of the PCA [bottomleft, bottomright, topleft, topright] (function adegenet/scatter:posi.pca)', false, 'CARACTER', 'topleft');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (167,3,11, 'DAPC_SP_POSI_DA', 'Position of the DA [bottomleft, bottomright, topleft, topright] (function adegenet/scatter:posi.da)', false, 'CARACTER', 'bottomright');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (168,3,11, 'DAPC_SP_POSI_CLEG', 'Font size of the legends (function adegenet/scatter:cleg)', false, 'ENTERO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (169,3,11, 'DAPC_SP_P_IND_IMP_MUESTRAS', 'Indicates whether the Sample IDs are printed on the sample-level assignment chart', false, 'LOGICO', '1');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (170,3,12, 'DAPC_LP_UMBRAL', 'Value of the quantile over which the names of the variables will be displayed (function adegenet/loadingplot:thres)', false, 'NUMERICO', '0.95');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (171,3,1, 'DAPC_TIPO_LOCI', 'Type of loci to study [codom, PA] (function adegenet/df2genind:type)', false, 'CARACTER', 'codom');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (172,4,1, 'IND_VAL_LEYENDA_CONJUNTO', 'Attribute of Sample Sets to be used as a legend [detalle, codigo, id]', false, 'CARACTER', 'detalle');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (173,4,1, 'IND_VAL_TIPO_LOCI', 'Type of loci to study [codom, PA] (function adegenet/df2genind:type)', false, 'CARACTER', 'codom');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (174,4,13, 'IND_VAL_GEN_CURV', 'Indicates whether the genotype curve is generated (function poppr/genotype_curve)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (175,4,13, 'IND_VAL_GEN_CURV_SAMPLE', 'Number of samples for the generation of the genotype curve (function poppr/genotype_curve:sample)', false, 'ENTERO', '1000');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (176,4,1, 'IND_VAL_PLOIDY', 'Indicates whether the ploidy graph is generated (function poppr/info_table:type=ploidy)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (177,4,1, 'IND_VAL_MISSING', 'Indicates if the graph of lost alleles is generated (function poppr/info_table:type=missing)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (178,4,14, 'IND_VAL_DIVERSITY', 'Indicates whether the table with diversity indexes is generated (function poppr/poppr)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (179,4,14, 'IND_VAL_DIVERSITY_SAMPLE', 'Number of samples for the generation of the diversity index table (function poppr/poppr:sample)', false, 'ENTERO', '1000');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (180,4,14, 'IND_VAL_DIVERSITY_INDEX', 'Index that will be used for the generation of the histogram [rbarD, Ia] (function poppr/poppr:index)', false, 'CARACTER', 'rbarD');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (181,4,1, 'IND_VAL_MLG', 'Indicates whether the MLG (Multilocus Genotypes) table and graphics are generated (function poppr/mlg.table)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (182,4,15, 'IND_VAL_HW', 'Indicates whether Hardy - Weinberg tables and charts are generated (for now only available for Loci diploides) (function pegas/hw.test)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (183,4,15, 'IND_VAL_HW_STAT', 'Method used to determine the P-Value in the Hardy chart - Weinberg (Loci) [chi2: Chi-Cuadrado, MC: Monte Carlo] (function pegas/hw.test)', false, 'CARACTER', 'chi2');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (184,4,15, 'IND_VAL_HW_STAT_MC_SAMPLE', 'Number of replicas used in the Monte Carlo method in Hardy - Weinberg (function pegas/hw.test)', false, 'ENTERO', '1000');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (185,4,15, 'IND_VAL_HW_ALFA', 'Threshold on the P-Value used in the Hardy chart - Weinberg (Loci)', false, 'NUMERICO', '0.05');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (186,4,16, 'IND_VAL_LOCUS_TABLE', 'Indicates whether the table with the diversity indices is generated by Loci (function poppr/locus_table)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (187,4,16, 'IND_VAL_LOCUS_TABLE_INDEX', 'Index to be used in the diversity table by Loci [simpson, shannon, invsimpson] (function poppr/locus_table:index)', false, 'CARACTER', 'simpson');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (188,4,16, 'IND_VAL_LOCUS_TABLE_LEV', 'Level to which the diversity table will be generated by Loci [allele, genotype] (function poppr/locus_table:lev)', false, 'CARACTER', 'allele');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (189,4,17, 'IND_VAL_ALELOS_EJECUTAR', 'Indicates if the graphics and tables will be generated at the level of Alleles (function poppr/tab)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (190,4,17, 'IND_VAL_ALELOS_FREC', 'Indicates if the Alelos frequency charts and tables will be generated', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (191,4,17, 'IND_VAL_ALELOS_PROM', 'Indicate if the graphs and tables of the average amount of Alleles will be generated', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (192,4,17, 'IND_VAL_ALELOS_NA_METHOD', 'Method with which the null values will be treated in the Alelos charts and tables [mean, zero] (function poppr/tab:NA.method)', false, 'CARACTER', 'mean');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (193,4,1, 'IND_VAL_BASIC_STATS', 'Indicates whether the table generates basic statistical values (Fit, Fst, Fis, etc) (function hierfstat/basic.stats)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (194,4,1, 'IND_VAL_ALLELIC_RICHNESS', 'Indicates if the allelic richness table is generated (only available for diploids) (function hierfstat/allelic.richness)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (196,4,18, 'IND_VAL_DIST_POP', 'Indicates whether the distance table is generated between Samples Sets (function adegenet/dist.genpop)', false, 'LOGICO', '1');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (197,4,18, 'IND_VAL_DIST_POP_METHOD', 'Distance function to use [1:Nei, 2:Edwards, 3:Reynolds, 4:Rogers, 5:Provesti] (function adegenet/dist.genpop:method)', false, 'ENTERO', '1');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (198,3,9, 'DAPC_FC_K', 'K value to analyze. If it is 0, it will be determined automatically based on the statistic and criterion indicated. (function adegenet/find.clusters:n.clust)', false, 'ENTERO', '0');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (199,3,11, 'DAPC_SP_P_IND_XLEN', 'Assignment graph at sample level: text size of the X axis', false, 'ENTERO', '7');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (200,3,11, 'DAPC_SP_P_IND_YLEN', 'Assignment graph at sample level: text size of the Y axis', false, 'ENTERO', '12');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (201,3,11, 'DAPC_SP_P_IND_TLEN', 'Assignment graph at sample level: text size of the legends', false, 'ENTERO', '15');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (202,3,11, 'DAPC_SP_P_IND_NCOL', 'Graph of assignment at sample level: number of columns', false, 'ENTERO', '1');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (203,3,11, 'DAPC_SP_P_BOX_XLEN', 'Assignment chart at sample set level: text size of the X axis', false, 'ENTERO', '12');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (204,3,11, 'DAPC_SP_P_BOX_YLEN', 'Assignment chart at sample set level: text size of the Y axis', false, 'ENTERO', '12');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (205,3,11, 'DAPC_SP_P_BOX_TLEN', 'Assignment chart at the sample set level: text size of the legends', false, 'ENTERO', '15');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (206,3,11, 'DAPC_SP_P_BOX_NCOL', 'Assignment chart at sample set level: number of columns', false, 'ENTERO', '1');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (207,3,9, 'DAPC_FC_PLOT_XLEN', 'Graph of the statistic vs K: text size of the X axis', false, 'ENTERO', '18');
INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (208,3,9, 'DAPC_FC_PLOT_YLEN', 'Graph of the statistic vs K: text size of the Y axis', false, 'ENTERO', '18');

INSERT INTO parametros_procedimientos (id, procedimiento_id,categoria_id,codigo,detalle,no_editable,tipo,valor) VALUES (209,1,2, 'STRUCTURE_GEN_LOGS', 'Indicates if the log files for the execution of Structure are generated', false, 'LOGICO', '0');

SELECT pg_catalog.setval('parametros_procedimientos_id_seq', 209, true);

INSERT INTO archivos (id,detalle,direccion) VALUES (1, 'structure.sh', 'archivos/procedimientos/1/structure.sh');
INSERT INTO archivos (id,detalle,direccion) VALUES (2, 'structure', 'archivos/procedimientos/1/structure');
INSERT INTO archivos (id,detalle,direccion) VALUES (3, 'CLUMPP', 'archivos/procedimientos/1/CLUMPP');
INSERT INTO archivos (id,detalle,direccion) VALUES (4, 'distructLinux1.1', 'archivos/procedimientos/1/distructLinux1.1');
INSERT INTO archivos (id,detalle,direccion) VALUES (5, 'structureHarvester.py', 'archivos/procedimientos/1/structureHarvester.py');
INSERT INTO archivos (id,detalle,direccion) VALUES (6, 'harvesterCore.py', 'archivos/procedimientos/1/harvesterCore.py');
INSERT INTO archivos (id,detalle,direccion) VALUES (7, 'procesarEvanno.r', 'archivos/procedimientos/1/procesarEvanno.r');
INSERT INTO archivos (id,detalle,direccion) VALUES (8, 'clumpp.ind.params', 'archivos/procedimientos/1/clumpp.ind.params');
INSERT INTO archivos (id,detalle,direccion) VALUES (9, 'clumpp.pop.params', 'archivos/procedimientos/1/clumpp.pop.params');
INSERT INTO archivos (id,detalle,direccion) VALUES (10, 'distruct.ind.params', 'archivos/procedimientos/1/distruct.ind.params');
INSERT INTO archivos (id,detalle,direccion) VALUES (11, 'distruct.pop.params', 'archivos/procedimientos/1/distruct.pop.params');
INSERT INTO archivos (id,detalle,direccion) VALUES (12, 'log.sh', 'archivos/procedimientos/1/log.sh');

INSERT INTO archivos (id,detalle,direccion) VALUES (13, 'genepop.sh', 'archivos/procedimientos/2/genepop.sh');
INSERT INTO archivos (id,detalle,direccion) VALUES (14, 'Genepop', 'archivos/procedimientos/2/Genepop');
INSERT INTO archivos (id,detalle,direccion) VALUES (15, 'log.sh', 'archivos/procedimientos/2/log.sh');

INSERT INTO archivos (id,detalle,direccion) VALUES (16, 'dapc.sh', 'archivos/procedimientos/3/dapc.sh');
INSERT INTO archivos (id,detalle,direccion) VALUES (17, 'dapc.r', 'archivos/procedimientos/3/dapc.r');
INSERT INTO archivos (id,detalle,direccion) VALUES (18, 'log.sh', 'archivos/procedimientos/3/log.sh');

INSERT INTO archivos (id,detalle,direccion) VALUES (19, 'ind_val.sh', 'archivos/procedimientos/4/ind_val.sh');
INSERT INTO archivos (id,detalle,direccion) VALUES (20, 'ind_val.r', 'archivos/procedimientos/4/ind_val.r');
INSERT INTO archivos (id,detalle,direccion) VALUES (21, 'log.sh', 'archivos/procedimientos/4/log.sh');

SELECT pg_catalog.setval('archivos_id_seq', 21, true);


INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,1);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,2);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,3);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,4);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,5);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,6);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,7);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,8);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,9);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,10);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,11);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (1,12);

INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (2,13);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (2,14);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (2,15);

INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (3,16);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (3,17);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (3,18);

INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (4,19);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (4,20);
INSERT INTO procedimientos_archivos (procedimiento_id,archivo_id) VALUES (4,21);


