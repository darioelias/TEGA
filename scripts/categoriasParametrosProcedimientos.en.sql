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
