
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
