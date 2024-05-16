-- MariaDB dump 10.19-11.3.2-MariaDB, for osx10.18 (x86_64)
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
CREATE DATABASE debate_tournament_manager;

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
  `id_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`dni`),
  KEY `id_team` (`id_team`),
  KEY `fk_id_user` (`id_user`),
  CONSTRAINT `fk_id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `participant_ibfk_1` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participant`
--

LOCK TABLES `participant` WRITE;
/*!40000 ALTER TABLE `participant` DISABLE KEYS */;
INSERT INTO `participant` VALUES
('11111111a','REBUTTAL_1','MALE','a','uuuuu',4,79,30),
('12345678l','REBUTTAL_1','FEMALE','jlk','ñlñk',4,80,30),
('77777777a','REBUTTAL_2','MALE','jkl','paoiwjeoiqjq',4,79,30),
('77777777w','REBUTTAL_1','FEMALE','poji','lkmn',4,82,30),
('99999999w','REBUTTAL_1','FEMALE','lñkñlk','lkj',4,79,30);
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
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participation`
--

LOCK TABLES `participation` WRITE;
/*!40000 ALTER TABLE `participation` DISABLE KEYS */;
INSERT INTO `participation` VALUES
(160,79,42),
(161,80,42),
(163,82,42);
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
  `id_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `team_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES
(79,'a','a','a',30),
(80,'a','faf','a',30),
(82,'ojpjº','ijop','opj',30);
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
  `id_user` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `tournament_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tournament`
--

LOCK TABLES `tournament` WRITE;
/*!40000 ALTER TABLE `tournament` DISABLE KEYS */;
INSERT INTO `tournament` VALUES
(42,'a','a','a','2024-05-08',30);
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
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
(22,'---------','-','-','-','-'),
(23,'+++++++++','+','+','+','+'),
(24,'77432765e','ines','gonzalez','ines','a'),
(25,'20954233','juanma','ariza','juanma','3/luu3Rae43jMv0WYembfcsNr1Zq8cot2WQb39lICBA='),
(26,'20954233e','juan','juan','juan','73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8='),
(27,'99999999o','roberto','roberto','roberto','3/luu3Rae43jMv0WYembfcsNr1Zq8cot2WQb39lICBA='),
(28,'20954233r','juanma','ariza','juanma1','3/luu3Rae43jMv0WYembfcsNr1Zq8cot2WQb39lICBA='),
(29,'66666666a','juan','juan','manu','b/zVH6BFs1qjizz7xKfAyn13my7crlLgQChwbUZl6EY='),
(30,'12345675v','aaaa','aaaa','aaaa','3/luu3Rae43jMv0WYembfcsNr1Zq8cot2WQb39lICBA='),
(31,'66666666u','dasf','fdas','ssss','3/luu3Rae43jMv0WYembfcsNr1Zq8cot2WQb39lICBA=');
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

-- Dump completed on 2024-05-16 12:21:41
