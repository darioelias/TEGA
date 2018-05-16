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
