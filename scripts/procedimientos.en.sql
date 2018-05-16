INSERT INTO procedimientos (id,codigo,nombre,comando_eje,comando_log,exportar_genotipos_structure,exportar_cantidades,exportar_conjuntos_muestras) VALUES (1, 'structure', 'Structure','./structure.sh','./log.sh',true,true,true);
INSERT INTO procedimientos (id,codigo,nombre,comando_eje,comando_log,exportar_genotipos_genepop) VALUES (2, 'genepop', 'Genepop','./genepop.sh','./log.sh',true);
INSERT INTO procedimientos (id,codigo,nombre,comando_eje,comando_log,exportar_genotipos) VALUES (3, 'dapc', 'DAPC','./dapc.sh','./log.sh',true);
INSERT INTO procedimientos (id,codigo,nombre,comando_eje,comando_log,exportar_genotipos) VALUES (4, 'ind_val', 'Indexes and Validations','./ind_val.sh','./log.sh',true);

SELECT pg_catalog.setval('procedimientos_id_seq', 4, true);


