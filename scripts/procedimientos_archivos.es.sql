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


