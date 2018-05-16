
INSERT INTO jhi_authority VALUES ('ROLE_ABM');
INSERT INTO jhi_authority VALUES ('ROLE_ADMIN');
INSERT INTO jhi_authority VALUES ('ROLE_MANAGER');
INSERT INTO jhi_authority VALUES ('ROLE_USER');
INSERT INTO jhi_authority VALUES ('ROLE_USER_EXTENDED');

INSERT INTO jhi_user VALUES (1, 'system', '2017-02-19 22:49:28', NULL, NULL, true, NULL, 'anonymoususer@localhost', 'Anonymous', 'es', 'User', 'anonymoususer', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', NULL, NULL, NULL);
INSERT INTO jhi_user VALUES (2, 'system', '2017-02-19 22:49:28', NULL, NULL, true, NULL, 'admin@localhost', 'Admin', 'es', 'Admin', 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', NULL, NULL, 'IMPLEMENTADOR');
INSERT INTO jhi_user VALUES (3,'anonymousUser','2017-03-01 15:43:28.815','admin','2017-03-01 15:47:01',true,NULL,'anonimo@localhost',NULL,'es',NULL,'anonimo','$2a$10$TjcBe7/dVVzH1wvAsRNT3uy4hgsDpOqwVd0GKOtctITVv4RKwWeNi',NULL,NULL,'ANONIMO');
INSERT INTO jhi_user VALUES (4,'anonymousUser','2017-03-01 16:51:59.303','administrador','2017-11-26 13:29:40',true,NULL,'administrador@localhost',NULL,'es',NULL,'administrador','$2a$10$iVZdIWRSPB4gWQkvNAAtlu39U86KmL.bF2Cp2BBFoooFCZXSe52S.',NULL,NULL,'ADMINISTRADOR');
INSERT INTO jhi_user VALUES (5,'anonymousUser','2017-03-01 16:54:13.422','investigador','2017-11-26 13:30:04',true,NULL,'investigador@localhost',NULL,'es',NULL,'investigador','$2a$10$N4fQcgLXdIUGebzhz/Uzg.1ec/UV7f9KJAwfr2kCO7JHZOA0L2G1q',NULL,NULL,'INVESTIGADOR');
INSERT INTO jhi_user VALUES (6,'anonymousUser','2017-03-02 12:15:54.685','invitado','2017-11-26 13:30:45',true,NULL,'invitado@localhost',NULL,'es',NULL,'invitado','$2a$10$P3ECJtkCCDya69gDajYVyeGSgs4joDYaENA.jYjjJ1vR4VMZv0mli',NULL,NULL,'INVITADO');

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
