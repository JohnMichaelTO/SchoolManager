--
-- Base de données: `schoolmanager`
--
CREATE DATABASE IF NOT EXISTS `schoolmanager` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `schoolmanager`;

-- --------------------------------------------------------

--
-- Structure de la table `admins`
--

CREATE TABLE IF NOT EXISTS `admins` (
  `AID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`AID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `admins`
--

INSERT INTO `admins` (`AID`) VALUES
(4);

-- --------------------------------------------------------

--
-- Structure de la table `assists`
--

CREATE TABLE IF NOT EXISTS `assists` (
  `CID` int(11) NOT NULL DEFAULT '0',
  `UID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`CID`,`UID`),
  KEY `UID` (`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `assists`
--

INSERT INTO `assists` (`CID`, `UID`) VALUES
(14, 6);

-- --------------------------------------------------------

--
-- Structure de la table `courses`
--

CREATE TABLE IF NOT EXISTS `courses` (
  `CID` int(11) NOT NULL DEFAULT '0',
  `CNAME` varchar(100) NOT NULL,
  `CYEAR` int(11) DEFAULT NULL,
  `COMPULSORY` tinyint(1) NOT NULL,
  `DEPTID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CID`),
  KEY `DEPTID` (`DEPTID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `courses`
--

INSERT INTO `courses` (`CID`, `CNAME`, `CYEAR`, `COMPULSORY`, `DEPTID`) VALUES
(1, 'Stucturer un écrit, prendre la parole', 1, 1, 4),
(2, 'Le monde anglophone - 1', 1, 1, 5),
(3, 'Lv2 chinois', 1, 1, 5),
(4, 'Fonctions et variations', 1, 1, 0),
(5, 'Nombres et structures', 1, 1, 0),
(6, 'De l''atome à la puce', 1, 1, 1),
(7, 'Electricité générale', 1, 1, 3),
(8, 'Information numérique', 1, 1, 2),
(9, 'Algorithmie et programmation en c', 1, 1, 2),
(10, 'Introduction à la micro-informatique', 1, 1, 2),
(11, 'Optimisation et complexité', 3, 1, 0),
(12, 'Aide à la décision', 3, 1, 0),
(13, 'Algorithme numérique', 3, 1, 2),
(14, 'Programmation java 2', 3, 1, 2),
(15, 'Programmation java 1', 3, 1, 2),
(16, 'Base de données', 3, 1, 2),
(17, 'Physique quantique', 3, 1, 1),
(18, 'Canaux de transmission', 3, 1, 3),
(19, 'Analyse financière', 3, 1, 6),
(20, 'Droit des sociétés et des contrats', 3, 1, 6),
(21, 'Communication et culture générale', 3, 1, 4),
(22, 'Anglais - 6', 3, 1, 5),
(23, 'Anglais renforcé - 6', 3, 1, 5);

--
-- Déclencheurs `courses`
--
DROP TRIGGER IF EXISTS `ADDCOURSE`;
DELIMITER //
CREATE TRIGGER `ADDCOURSE` AFTER INSERT ON `courses`
 FOR EACH ROW begin
	declare done int default 0;
	declare new_SID integer;
	declare cur cursor for select SID from STUDENTS where PROMO = new.CYEAR;
	declare continue handler for not found set done = 1;
	
	if new.COMPULSORY = 1 then
		open cur;
			iloop : loop
				fetch cur into new_SID;
				if done = 1 then
					leave iloop;
				end if;
				insert into ENROLLMENTS values(new.CID, new_SID);
			end loop;
		close cur;
	end if;
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `departments`
--

CREATE TABLE IF NOT EXISTS `departments` (
  `DID` int(11) NOT NULL DEFAULT '0',
  `DNAME` varchar(100) NOT NULL,
  PRIMARY KEY (`DID`),
  UNIQUE KEY `DEPTNAME` (`DNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `departments`
--

INSERT INTO `departments` (`DID`, `DNAME`) VALUES
(4, 'communication'),
(3, 'électronique'),
(6, 'Formation générale'),
(2, 'informatique'),
(5, 'langues'),
(0, 'mathématiques'),
(1, 'physiques');

-- --------------------------------------------------------

--
-- Structure de la table `enrollments`
--

CREATE TABLE IF NOT EXISTS `enrollments` (
  `CID` int(11) NOT NULL DEFAULT '0',
  `SID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`CID`,`SID`),
  KEY `SID` (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `enrollments`
--

INSERT INTO `enrollments` (`CID`, `SID`) VALUES
(11, 0),
(12, 0),
(13, 0),
(14, 0),
(15, 0),
(16, 0),
(17, 0),
(18, 0),
(19, 0),
(20, 0),
(21, 0),
(22, 0),
(23, 0),
(11, 3),
(12, 3),
(13, 3),
(14, 3),
(15, 3),
(16, 3),
(17, 3),
(18, 3),
(19, 3),
(20, 3),
(21, 3),
(22, 3),
(23, 3),
(11, 5),
(12, 5),
(13, 5),
(14, 5),
(15, 5),
(16, 5),
(17, 5),
(18, 5),
(19, 5),
(20, 5),
(21, 5),
(22, 5),
(23, 5);

--
-- Déclencheurs `enrollments`
--
DROP TRIGGER IF EXISTS `ADDENROLLMENT`;
DELIMITER //
CREATE TRIGGER `ADDENROLLMENT` AFTER INSERT ON `enrollments`
 FOR EACH ROW begin
	declare done int default 0;
	declare new_GID integer;
	declare cur cursor for select GID from GRADES where CID = new.CID;
	declare continue handler for not found set done = 1;
	
	open cur;
		iloop : loop
			fetch cur into new_GID;
			if done = 1 then
				leave iloop;
			end if;
			insert into STUDENTGRADES values(new.SID, new_GID, null);
		end loop;
	close cur;
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `grades`
--

CREATE TABLE IF NOT EXISTS `grades` (
  `GID` int(11) NOT NULL DEFAULT '0',
  `CID` int(11) DEFAULT NULL,
  `COEF` decimal(10,2) NOT NULL,
  `GTYPE` varchar(20) NOT NULL,
  PRIMARY KEY (`GID`),
  KEY `CID` (`CID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `grades`
--

INSERT INTO `grades` (`GID`, `CID`, `COEF`, `GTYPE`) VALUES
(1, 12, '0.50', 'DE'),
(2, 12, '0.20', 'CE'),
(3, 12, '0.30', 'PROJET'),
(4, 11, '0.50', 'DE'),
(5, 11, '0.20', 'CE'),
(6, 11, '0.30', 'TAI'),
(7, 13, '0.50', 'DE'),
(8, 13, '0.20', 'CE'),
(9, 13, '0.30', 'TP'),
(10, 14, '0.50', 'DE'),
(11, 14, '0.50', 'PROJET'),
(12, 16, '0.40', 'DE'),
(13, 16, '0.20', 'CE'),
(14, 16, '0.10', 'TP'),
(15, 16, '0.30', 'PROJET'),
(16, 17, '0.60', 'DE'),
(17, 17, '0.40', 'TAI'),
(18, 18, '0.50', 'DE'),
(19, 18, '0.30', 'CE'),
(20, 18, '0.20', 'TP'),
(21, 19, '0.80', 'DE'),
(22, 19, '0.20', 'TD'),
(23, 20, '1.00', 'DE'),
(24, 21, '0.40', 'DE'),
(25, 21, '0.20', 'PRESENTATION'),
(26, 21, '0.20', 'CE'),
(27, 21, '0.20', 'NOUVELLE'),
(28, 22, '0.50', 'DE'),
(29, 22, '0.50', 'TD'),
(30, 23, '0.50', 'DE'),
(31, 23, '0.50', 'TD');

--
-- Déclencheurs `grades`
--
DROP TRIGGER IF EXISTS `ADDGRADE`;
DELIMITER //
CREATE TRIGGER `ADDGRADE` AFTER INSERT ON `grades`
 FOR EACH ROW begin
	declare done int default 0;
	declare new_SID integer;
	declare cur cursor for select SID from GRADES natural join ENROLLMENTS where GID = new.GID;
	declare continue handler for not found set done = 1;
	
	open cur;
		iloop : loop
			fetch cur into new_SID;
			if done = 1 then
				leave iloop;
			end if;
			insert into STUDENTGRADES values(new_SID, new.GID, null);
		end loop;
	close cur;
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `studentgrades`
--

CREATE TABLE IF NOT EXISTS `studentgrades` (
  `SID` int(11) NOT NULL DEFAULT '0',
  `GID` int(11) NOT NULL DEFAULT '0',
  `MARK` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`SID`,`GID`),
  KEY `GID` (`GID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `studentgrades`
--

INSERT INTO `studentgrades` (`SID`, `GID`, `MARK`) VALUES
(0, 1, '14.10'),
(0, 2, '5.80'),
(0, 3, '13.80'),
(0, 4, NULL),
(0, 5, '15.00'),
(0, 6, NULL),
(0, 7, '14.30'),
(0, 8, '14.40'),
(0, 9, '17.50'),
(0, 10, NULL),
(0, 11, NULL),
(0, 12, NULL),
(0, 13, '14.00'),
(0, 14, NULL),
(0, 15, NULL),
(0, 16, NULL),
(0, 17, NULL),
(0, 18, NULL),
(0, 19, '18.00'),
(0, 20, NULL),
(0, 21, NULL),
(0, 22, '10.00'),
(0, 23, NULL),
(0, 24, '8.00'),
(0, 25, '13.00'),
(0, 26, '8.00'),
(0, 27, NULL),
(0, 28, NULL),
(0, 29, '14.70'),
(0, 30, NULL),
(0, 31, '15.40'),
(3, 1, NULL),
(3, 2, NULL),
(3, 3, NULL),
(3, 4, NULL),
(3, 5, NULL),
(3, 6, NULL),
(3, 7, NULL),
(3, 8, NULL),
(3, 9, NULL),
(3, 10, NULL),
(3, 11, NULL),
(3, 12, NULL),
(3, 13, NULL),
(3, 14, NULL),
(3, 15, NULL),
(3, 16, NULL),
(3, 17, NULL),
(3, 18, NULL),
(3, 19, NULL),
(3, 20, NULL),
(3, 21, NULL),
(3, 22, NULL),
(3, 23, NULL),
(3, 24, NULL),
(3, 25, NULL),
(3, 26, NULL),
(3, 27, NULL),
(3, 28, NULL),
(3, 29, NULL),
(3, 30, NULL),
(3, 31, NULL),
(5, 1, NULL),
(5, 2, NULL),
(5, 3, NULL),
(5, 4, NULL),
(5, 5, NULL),
(5, 6, NULL),
(5, 7, NULL),
(5, 8, NULL),
(5, 9, NULL),
(5, 10, NULL),
(5, 11, NULL),
(5, 12, NULL),
(5, 13, NULL),
(5, 14, NULL),
(5, 15, NULL),
(5, 16, NULL),
(5, 17, NULL),
(5, 18, NULL),
(5, 19, NULL),
(5, 20, NULL),
(5, 21, NULL),
(5, 22, NULL),
(5, 23, NULL),
(5, 24, NULL),
(5, 25, NULL),
(5, 26, NULL),
(5, 27, NULL),
(5, 28, NULL),
(5, 29, NULL),
(5, 30, NULL),
(5, 31, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `students`
--

CREATE TABLE IF NOT EXISTS `students` (
  `SID` int(11) NOT NULL DEFAULT '0',
  `PROMO` int(11) NOT NULL,
  `TUTOR` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`),
  KEY `TUTOR` (`TUTOR`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `students`
--

INSERT INTO `students` (`SID`, `PROMO`, `TUTOR`) VALUES
(0, 3, 1),
(3, 3, 1),
(5, 3, 1);

--
-- Déclencheurs `students`
--
DROP TRIGGER IF EXISTS `ADDSTUDENT`;
DELIMITER //
CREATE TRIGGER `ADDSTUDENT` AFTER INSERT ON `students`
 FOR EACH ROW begin
	declare done int default 0;
	declare new_CID integer;
	declare cur cursor for select CID from COURSES where CYEAR = new.PROMO and COMPULSORY = 1;
	declare continue handler for not found set done = 1;
	
	open cur;
		iloop : loop
			fetch cur into new_CID;
			if done = 1 then
				leave iloop;
			end if;
			insert into ENROLLMENTS values(new_CID, new.SID);
		end loop;
	close cur;
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `teachers`
--

CREATE TABLE IF NOT EXISTS `teachers` (
  `TID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `teachers`
--

INSERT INTO `teachers` (`TID`) VALUES
(1),
(6),
(7),
(8),
(9),
(10),
(11),
(12),
(13),
(14),
(15),
(16),
(17);

-- --------------------------------------------------------

--
-- Structure de la table `teaches`
--

CREATE TABLE IF NOT EXISTS `teaches` (
  `CID` int(11) NOT NULL DEFAULT '0',
  `TID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`CID`,`TID`),
  KEY `TID` (`TID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `teaches`
--

INSERT INTO `teaches` (`CID`, `TID`) VALUES
(14, 1),
(15, 6),
(16, 6),
(9, 7),
(5, 8),
(1, 9),
(1, 10),
(1, 11),
(21, 11),
(18, 12),
(7, 13),
(18, 13),
(13, 14),
(4, 15),
(11, 15),
(2, 16);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `UID` int(11) NOT NULL DEFAULT '0',
  `LASTNAME` varchar(100) NOT NULL,
  `FIRSTNAME` varchar(100) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `UTYPE` varchar(50) NOT NULL,
  PRIMARY KEY (`UID`),
  UNIQUE KEY `LOGIN` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`UID`, `LASTNAME`, `FIRSTNAME`, `EMAIL`, `PASSWORD`, `UTYPE`) VALUES
(0, 'to', 'john-michaël', 'john-michael.to@efrei.net', 'test', 'STUDENT'),
(1, 'lahlou', 'abdelkrim', 'abdelkrim.lahlou@efrei.net', 'test', 'TEACHER'),
(3, 'mora', 'benjamin', 'benjamin.mora@efrei.net', 'test', 'STUDENT'),
(4, 'admin', 'ADMIN', 'admin', 'admin', 'ADMIN'),
(5, 'kherroub', 'ryadh', 'ryadh.kherroub@efrei.net', 'mecreant', 'STUDENT'),
(6, 'busca', 'jean-michel', 'jean-michel.busca@efrei.net', 'test', 'TEACHER'),
(7, 'Flasque', 'Nicolas', 'nicolas.flasque@efrei.fr', 'test', 'TEACHER'),
(8, 'Gualino', 'Jacques', 'jacques.gualino@efrei.fr', 'test', 'TEACHER'),
(9, 'Soma', 'Jean', 'soma@efrei.fr', 'test', 'TEACHER'),
(10, 'Soma', 'Henri', 'henri.soma@efrei.fr', 'test', 'TEACHER'),
(11, 'Bourlier', 'Sylvie', 'sylvie.bourlier@efrei.fr', 'test', 'TEACHER'),
(12, 'Ossonce', 'Maxime', 'ossonce@efrei.fr', 'test', 'TEACHER'),
(13, 'Sirot', 'Isabelle', 'sirot@efrei.fr', 'test', 'TEACHER'),
(14, 'Sicard', 'Nicolas', 'sicard@efrei.fr', 'test', 'TEACHER'),
(15, 'Teller', 'Patrick', 'teller@efrei.fr', 'test', 'TEACHER'),
(16, 'Aftab', 'Afroze', 'afroze@efrei.fr', 'test', 'TEACHER'),
(17, 'Lepoivre', 'Franck', 'franck.lepoivre@gmail.com', 'test', 'TEACHER');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `admins`
--
ALTER TABLE `admins`
  ADD CONSTRAINT `admins_ibfk_1` FOREIGN KEY (`AID`) REFERENCES `users` (`UID`);

--
-- Contraintes pour la table `assists`
--
ALTER TABLE `assists`
  ADD CONSTRAINT `assists_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `courses` (`CID`),
  ADD CONSTRAINT `assists_ibfk_2` FOREIGN KEY (`UID`) REFERENCES `users` (`UID`);

--
-- Contraintes pour la table `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`DEPTID`) REFERENCES `departments` (`DID`);

--
-- Contraintes pour la table `enrollments`
--
ALTER TABLE `enrollments`
  ADD CONSTRAINT `enrollments_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `courses` (`CID`),
  ADD CONSTRAINT `enrollments_ibfk_2` FOREIGN KEY (`SID`) REFERENCES `students` (`SID`);

--
-- Contraintes pour la table `grades`
--
ALTER TABLE `grades`
  ADD CONSTRAINT `grades_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `courses` (`CID`);

--
-- Contraintes pour la table `studentgrades`
--
ALTER TABLE `studentgrades`
  ADD CONSTRAINT `studentgrades_ibfk_1` FOREIGN KEY (`SID`) REFERENCES `students` (`SID`),
  ADD CONSTRAINT `studentgrades_ibfk_2` FOREIGN KEY (`GID`) REFERENCES `grades` (`GID`);

--
-- Contraintes pour la table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`SID`) REFERENCES `users` (`UID`),
  ADD CONSTRAINT `students_ibfk_2` FOREIGN KEY (`TUTOR`) REFERENCES `teachers` (`TID`);

--
-- Contraintes pour la table `teachers`
--
ALTER TABLE `teachers`
  ADD CONSTRAINT `teachers_ibfk_1` FOREIGN KEY (`TID`) REFERENCES `users` (`UID`);

--
-- Contraintes pour la table `teaches`
--
ALTER TABLE `teaches`
  ADD CONSTRAINT `teaches_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `courses` (`CID`),
  ADD CONSTRAINT `teaches_ibfk_2` FOREIGN KEY (`TID`) REFERENCES `teachers` (`TID`);