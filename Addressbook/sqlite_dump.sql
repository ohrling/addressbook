PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE ContactsList(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,firstName varchar(40) COLLATE NOCASE,lastName varchar(40) COLLATE NOCASE, email varchar(100) COLLATE NOCASE,phoneNumber varchar(100) COLLATE NOCASE, company varchar(100) COLLATE NOCASE, isDeleted tinyint(1), lastUpdated DATETIME NOT NULL DEFAULT (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')));
INSERT INTO ContactsList VALUES(1,'Kalle','Anka','kalle@ankeborg.se','0398920292','Egmont',0,'2019-12-11 20:20:19.169');
INSERT INTO ContactsList VALUES(64,'Marlon','Bayer','jerry.rice@yahoo.com','432.122.9818 x639','Parisian, Fahey and Donnelly',0,'2019-12-10 21:26:21.345');
INSERT INTO ContactsList VALUES(65,'Darren','Wintheiser','ernie.klein@gmail.com','760-577-3624 x850','Ledner and Sons',0,'2019-12-10 21:26:21.442');
INSERT INTO ContactsList VALUES(66,'Keesha','Braun','brinda.bailey@gmail.com','(437) 555-6335 x79903','Thompson Group',0,'2019-12-10 21:26:21.501');
INSERT INTO ContactsList VALUES(67,'Fausto','D''Amore','forrest.lang@yahoo.com','138-078-0819 x321','Shanahan-Senger',0,'2019-12-10 21:26:21.557');
INSERT INTO ContactsList VALUES(68,'Gustavo','D''Amore','maragret.gerlach@yahoo.com','214.471.6091','Ledner LLC',0,'2019-12-10 21:26:21.608');
INSERT INTO ContactsList VALUES(69,'Wendi','Macejkovic','mose.vandervort@yahoo.com','984-808-1224','Anderson-Halvorson',0,'2019-12-10 21:26:21.676');
INSERT INTO ContactsList VALUES(70,'Randolph','Runolfsdottir','velda.effertz@hotmail.com','1-998-828-6130','Walter LLC',0,'2019-12-10 21:26:21.723');
INSERT INTO ContactsList VALUES(71,'Mandy','Mraz','benton.hegmann@gmail.com','283.575.9037 x31583','Jacobson-Huels',0,'2019-12-10 21:26:21.766');
INSERT INTO ContactsList VALUES(72,'Jonah','Gulgowski','shayne.schoen@hotmail.com','017-332-7061 x011','Schoen Group',0,'2019-12-10 21:26:21.804');
INSERT INTO ContactsList VALUES(73,'Dexter','Schmidt','caren.fritsch@gmail.com','961-202-7248','Kunde Inc',0,'2019-12-10 21:26:21.839');
INSERT INTO ContactsList VALUES(74,'Gilma','Rohan','mozella.rempel@yahoo.com','(339) 236-7655 x855','Champlin, McCullough and Hettinger',0,'2019-12-10 21:26:21.879');
INSERT INTO ContactsList VALUES(75,'Lennie','Osinski','evonne.barrows@hotmail.com','1-628-112-6274 x665','Wilderman-Greenholt',0,'2019-12-10 21:26:21.917');
INSERT INTO ContactsList VALUES(76,'Marguerite','O''Connell','zoila.conn@hotmail.com','(298) 587-0164 x808','Abbott LLC',0,'2019-12-11 20:20:20.484');
INSERT INTO ContactsList VALUES(77,'Esperanza','Harber','clement.kulas@gmail.com','(869) 362-8446','Herman Inc',0,'2019-12-10 21:26:21.987');
INSERT INTO ContactsList VALUES(78,'Eldon','Ratke','tamra.champlin@yahoo.com','(283) 783-3635 x944','Abshire-Treutel',0,'2019-12-10 21:26:22.017');
INSERT INTO ContactsList VALUES(79,'Lesa','Bogan','bradley.weissnat@yahoo.com','(089) 646-6302 x5133','Davis-Schiller',0,'2019-12-10 21:26:22.056');
INSERT INTO ContactsList VALUES(80,'Jacinto','Little','pasty.larkin@hotmail.com','906.354.6277 x421','White-Streich',0,'2019-12-10 21:26:22.093');
INSERT INTO ContactsList VALUES(81,'Claude','Bogisich','belle.kihn@gmail.com','1-346-748-2858 x319','Gutmann-Moore',0,'2019-12-11 20:20:19.774');
INSERT INTO ContactsList VALUES(82,'Gracia','Zulauf','trinidad.prosacco@gmail.com','1-238-083-9667 x93411','Jacobson-Johnston',0,'2019-12-10 21:26:22.161');
INSERT INTO ContactsList VALUES(83,'Denis','Hammes','allyson.ondricka@gmail.com','(470) 037-8119 x858','Funk-Crona',0,'2019-12-10 21:26:22.191');
INSERT INTO ContactsList VALUES(84,'Lisandra','Emmerich','numbers.runte@yahoo.com','043-422-8494 x3746','Connelly-Mayer',0,'2019-12-10 21:26:26.424');
INSERT INTO ContactsList VALUES(85,'Mitch','Ankunding','gaylene.ankunding@yahoo.com','1-532-443-6987 x7067','Renner and Sons',0,'2019-12-10 21:26:26.452');
INSERT INTO ContactsList VALUES(86,'Chi','Rempel','edgar.simonis@gmail.com','971.203.6222','Huels-Kuhlman',0,'2019-12-10 21:26:26.472');
INSERT INTO ContactsList VALUES(87,'Stacia','Turcotte','norbert.schroeder@hotmail.com','165-428-5522 x6357','Miller, Schultz and Hilll',0,'2019-12-10 21:26:26.494');
INSERT INTO ContactsList VALUES(88,'Adolph','Block','julieann.sawayn@yahoo.com','568-194-1819','Goyette Inc',0,'2019-12-10 21:26:26.522');
INSERT INTO ContactsList VALUES(89,'Cameron','Heller','juliana.homenick@hotmail.com','357-617-0478','Schmeler-Rowe',0,'2019-12-10 21:26:26.543');
INSERT INTO ContactsList VALUES(90,'Steve','Heaney','greg.hilll@gmail.com','(536) 498-9973','Kulas Inc',0,'2019-12-10 21:26:26.568');
INSERT INTO ContactsList VALUES(91,'Arnita','Lockman','ike.rolfson@yahoo.com','423-833-1647','Waelchi-Bartell',0,'2019-12-10 21:26:26.590');
INSERT INTO ContactsList VALUES(92,'Deanna','McKenzie','andre.harris@gmail.com','1-055-284-4616','Dicki LLC',0,'2019-12-10 21:26:26.615');
INSERT INTO ContactsList VALUES(93,'Dedra','VonRueden','prudence.sipes@gmail.com','(214) 333-2512 x29365','Fritsch-Lang',0,'2019-12-10 21:26:26.636');
INSERT INTO ContactsList VALUES(94,'Deanne','D''Amore','eric.pfeffer@gmail.com','076-279-5220 x7573','Quitzon Inc',0,'2019-12-10 21:26:26.659');
INSERT INTO ContactsList VALUES(95,'Sammie','Schaefer','susanne.witting@yahoo.com','045-912-7693 x9907','King, Stiedemann and Dare',0,'2019-12-11 20:20:21.216');
INSERT INTO ContactsList VALUES(96,'Emmy','Schmidt','bret.dicki@gmail.com','728.930.7750 x656','Mohr-Rodriguez',0,'2019-12-10 21:26:26.723');
INSERT INTO ContactsList VALUES(97,'Russel','Mohr','lindsey.metz@hotmail.com','971.734.9309 x46896','Braun, Bauch and Toy',0,'2019-12-10 21:26:26.754');
INSERT INTO ContactsList VALUES(98,'Vickie','Bauch','mariel.weimann@gmail.com','762-313-3050','Collier-Strosin',0,'2019-12-10 21:26:26.774');
INSERT INTO ContactsList VALUES(99,'Rhonda','Barrows','hester.ondricka@yahoo.com','003-474-5895 x9652','Glover Group',0,'2019-12-10 21:26:26.795');
INSERT INTO ContactsList VALUES(100,'Juan','Botsford','jon.hartmann@yahoo.com','(550) 286-7983 x20908','Huels, Hodkiewicz and Treutel',0,'2019-12-10 21:26:26.821');
INSERT INTO ContactsList VALUES(101,'Maurine','Baumbach','lillia.gutmann@yahoo.com','(337) 388-6206 x548','Metz-Becker',0,'2019-12-10 21:26:26.843');
INSERT INTO ContactsList VALUES(102,'Julienne','Strosin','bernard.von@yahoo.com','160.665.4586 x02901','Cummerata, Zieme and Larson',0,'2019-12-10 21:26:26.870');
INSERT INTO ContactsList VALUES(103,'Tracey','Jakubowski','fredric.carter@gmail.com','(913) 744-7034 x809','Hoeger-Beer',0,'2019-12-10 21:26:26.894');
INSERT INTO ContactsList VALUES(104,'Fnatte','Anka','fnatte@ankeborg.se','','Egmont',0,'2019-12-11 21:22:56.041');
INSERT INTO ContactsList VALUES(105,'Tjatte','Anka','','','',0,'2019-12-11 21:22:56.041');
INSERT INTO ContactsList VALUES(106,'Tommy','Ohrling','vitt_lejon@gmail.com','','Egmont',0,'2019-12-11 21:22:56.041');
INSERT INTO ContactsList VALUES(107,'Knatte','Anka','knatte@ankeborg.se','0702223574','Egmont',0,'2019-12-11 19:54:24.796');
INSERT INTO ContactsList VALUES(108,'Joakim','von Anka','joakim@ankeborg.se','+46702223574','Egmont',0,'2019-12-11 19:53:19.146');
INSERT INTO ContactsList VALUES(109,'Tommy','Pettersson','','','',0,'2019-12-11 21:22:56.041');
INSERT INTO ContactsList VALUES(110,'Alexander','Anka','alexander@ankeborg.se','+46702223574','',0,'2019-12-11 20:17:41.612');
INSERT INTO ContactsList VALUES(111,'Tomas','Kålen','tomas@kalen.se','','',0,'2019-12-11 21:22:56.041');
INSERT INTO ContactsList VALUES(112,'Kajsa','','','','',1,'2019-12-11 21:22:56.041');
INSERT INTO ContactsList VALUES(113,'Kajsa','Anka','','','',1,'2019-12-11 21:22:56.041');
INSERT INTO ContactsList VALUES(114,'Kajsa','Anka','','','',1,'2019-12-11 21:22:56.041');
INSERT INTO ContactsList VALUES(115,'Kajsa','Anka','kajsa@ankeborg.se','','Egmont',0,'2019-12-11 21:22:56.041');
DELETE FROM sqlite_sequence;
INSERT INTO sqlite_sequence VALUES('ContactsList',115);
CREATE TRIGGER change_lastUpdated AFTER UPDATE On ContactsList BEGIN UPDATE ContactsList SET lastUpdated = (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')) WHERE phoneNumber = NEW.phoneNumber; END;
COMMIT;
