-- MariaDB dump 10.19-11.3.2-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: debate_tournament_manager
-- ------------------------------------------------------
-- Server version	11.3.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `participant`
--

DROP TABLE IF EXISTS `participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participant` (
  `dni` varchar(9) NOT NULL,
  `role` varchar(20) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `age` int(11) NOT NULL,
  `id_team` int(11) NOT NULL,
  PRIMARY KEY (`dni`),
  KEY `id_team` (`id_team`),
  CONSTRAINT `participant_ibfk_1` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participant`
--

LOCK TABLES `participant` WRITE;
/*!40000 ALTER TABLE `participant` DISABLE KEYS */;
INSERT INTO `participant` VALUES
('12345678A','INTRODUCTION','MALE','Juan','P├®rez',30,3);
/*!40000 ALTER TABLE `participant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participation`
--

DROP TABLE IF EXISTS `participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_team` int(11) NOT NULL,
  `id_tournament` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_team` (`id_team`),
  KEY `id_tournament` (`id_tournament`),
  CONSTRAINT `participation_ibfk_1` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`) ON DELETE CASCADE,
  CONSTRAINT `participation_ibfk_2` FOREIGN KEY (`id_tournament`) REFERENCES `tournament` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participation`
--

LOCK TABLES `participation` WRITE;
/*!40000 ALTER TABLE `participation` DISABLE KEYS */;
/*!40000 ALTER TABLE `participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `institution` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES
(2,'Equipo 2','Ciudad 2','Instituci├│n 2'),
(3,'Nuevo Nombre del Equipo','Nueva Ciudad del Equipo','Nueva Instituci├│n del Equipo'),
(4,'Equipo 2','Ciudad 2','Instituci├│n 2'),
(5,'Equipo 3','Ciudad 3','Instituci├│n 3'),
(6,'Equipo 4','Ciudad 4','Instituci├│n 4'),
(7,'Equipo 5','Ciudad 5','Instituci├│n 5'),
(8,'Equipo 6','Ciudad 6','Instituci├│n 6'),
(9,'Equipo 7','Ciudad 7','Instituci├│n 7'),
(10,'Equipo 8','Ciudad 8','Instituci├│n 8'),
(11,'Equipo 1','Ciudad 1','Instituci├│n 1'),
(12,'Equipo 2','Ciudad 2','Instituci├│n 2'),
(13,'Equipo 3','Ciudad 3','Instituci├│n 3'),
(14,'Equipo 4','Ciudad 4','Instituci├│n 4'),
(15,'Equipo 5','Ciudad 5','Instituci├│n 5'),
(16,'Equipo 6','Ciudad 6','Instituci├│n 6'),
(17,'Equipo 7','Ciudad 7','Instituci├│n 7'),
(18,'Equipo 8','Ciudad 8','Instituci├│n 8'),
(19,'Equipo 9','Ciudad 9','Instituci├│n 9'),
(20,'Equipo 10','Ciudad 10','Instituci├│n 10'),
(21,'Equipo 11','Ciudad 11','Instituci├│n 11'),
(22,'Equipo 12','Ciudad 12','Instituci├│n 12'),
(23,'Equipo 13','Ciudad 13','Instituci├│n 13'),
(24,'Equipo 14','Ciudad 14','Instituci├│n 14'),
(25,'Equipo 15','Ciudad 15','Instituci├│n 15'),
(26,'Equipo 16','Ciudad 16','Instituci├│n 16'),
(27,'Equipo 17','Ciudad 17','Instituci├│n 17'),
(28,'Equipo 18','Ciudad 18','Instituci├│n 18'),
(29,'Equipo 19','Ciudad 19','Instituci├│n 19'),
(30,'Equipo 20','Ciudad 20','Instituci├│n 20');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tournament`
--

DROP TABLE IF EXISTS `tournament`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tournament` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `location` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `date` date DEFAULT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `tournament_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tournament`
--

LOCK TABLES `tournament` WRITE;
/*!40000 ALTER TABLE `tournament` DISABLE KEYS */;
INSERT INTO `tournament` VALUES
(1,'Tournament Name 1','Tournament Location 1','Tournament City 1','2024-05-31',1),
(2,'Tournament Name 2','Tournament Location 2','Tournament City 2','2024-06-15',2),
(3,'Tournament Name 3','Tournament Location 3','Tournament City 3',NULL,3);
/*!40000 ALTER TABLE `tournament` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dni` varchar(9) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
(1,'20954233z','asfd','afsd','afsd','246ff2fb7de1c997b08e161ba4663ce1'),
(2,'twer','wt','wte','twer','6a314a69e64b7295b440a69a193d311b'),
(3,'','','','','d41d8cd98f00b204e9800998ecf8427e'),
(7,'asfd','fsda','fasd','asdfafsd','912ec803b2ce49e4a541068d495ab570'),
(8,'juanmasdk','asdf','asdfasdf','qergzsr','82512e9f526c2735bacdbaa29ea202da'),
(9,'20954345s','asdf','utfgcd','fd56kg','e816f7018f6f94f57163c3f8d84bdc4e'),
(10,'987654321','asdf','fad','nssacv43','9db818e88e95efa221194308349b5051'),
(11,'123456789','sadf','asdf','afsddsafasd4w','afe9a1acac4cfe143286c39a3da3c9b1'),
(12,'777777777','adsf','asdf','afsdds333','79dc550a68840cce0f3f2dcef6fb657190b8e75e5282ccf1c95908418dfa5cca'),
(13,'111111111','a','a','c','c8623e984cf78e0f3a959827b540a5cdbe4da246f5302ec8de4270300cf724c1'),
(14,'000000000','z','z','z','de1ae14cda5fa4d154340fc2e38dfe8895d8bbb6262cec26d518bf45fbae40db'),
(15,'97645678a','juan maria ','ariza serrano','arizaser','15d32dcce56eaafcf5a926646a46ed378ce308b6456c97df1d557db8f8932b6a'),
(16,'bbbbbbbbb','b','b','b','dba1de6de88c058e5e0922171e0bd97e79e20e9fc6c1d2737a91146765527305'),
(17,'ggggggggg','g','g','g','233c608f3436627a02ea3d3a0377ba4a383e9f9878a52196221a6923ae531367'),
(19,'qqqqqqqqq','q','q','q','q'),
(20,'.........','.','.','.','.'),
(21,'uuuuuuuuu','u','u','u','u'),
(22,'---------','-','-','-','-');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-02 17:55:06
