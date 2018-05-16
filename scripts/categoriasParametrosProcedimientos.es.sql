INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (1, 'PARAMETRO', 'Parámetros generales');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (2, 'ADICIONAL', 'Parámetros adicionales');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (3, 'STRUCTURE_MAIN', 'Parámetros principales de Structure');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (4, 'STRUCTURE_EXTRA', 'Parámetros extras de Structure');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (5, 'DISTRUCT', 'Parámetros de Distruct');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (6, 'CLUMPP', 'Parámetros de CLUMPP');

INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (7, 'GENEPOP_OPCIONES', 'Opciones de Genepop');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (8, 'GENEPOP_SETTINGS', 'Parámetros de Genepop');

INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (9, 'DAPC_FC', 'Determinación de K');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (10, 'DAPC_CV', 'Determinación de PCs');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (11, 'DAPC_SP', 'Gráfico de dispersión');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (12, 'DAPC_LP', 'Gráfico de contribución de variables');

INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (13, 'IND_VAL_GEN_CURV', 'Curva de Genotipos');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (14, 'IND_VAL_DIVERSITY', 'Diversidad a nivel de Conjuntos de Muestras');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (15, 'IND_VAL_HW', 'Hardy - Weinberg');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (16, 'IND_VAL_LOCUS_TABLE', 'Diversidad a nivel de Loci');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (17, 'IND_VAL_ALELOS', 'Frecuencia y promedio de Alelos');
INSERT INTO categorias_parametros_procedimientos (id, codigo, nombre) VALUES (18, 'IND_VAL_DIST_POP', 'Distancia entre Conjuntos de Muestras');

SELECT pg_catalog.setval('categorias_parametros_procedimientos_id_seq', 18, true);
