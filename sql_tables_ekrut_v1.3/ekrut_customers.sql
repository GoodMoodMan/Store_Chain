-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: ekrut
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `customerID` int NOT NULL,
  `customer_number` int DEFAULT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `email_address` varchar(50) DEFAULT NULL,
  `credit_card_number` varchar(50) DEFAULT NULL,
  `CustomersStatus` enum('Active','Frozen','pending','blocked') DEFAULT 'Active',
  `isSubscriber` tinyint(1) DEFAULT '0',
  `isOnline` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`customerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES ('Geff','bozos',223837198,3,'053055220','noleviw255@dmonies.com','5555-6666-9993','Active',0,1),('Mary','Jane',285529100,5,'052236852','faweyen717@dmonies.com','5555-6666-9995','Active',0,0),('Kim','Wexler',298325273,4,'050395384','layijev401@edinel.com','5555-6666-9994','Active',0,0),('Ellen','Replay',303146766,9,'054099361','sayopip387@edinel.com','5555-6666-9999','Frozen',0,0),('Rick','Sanchez',304842442,2,'050476235','fopone3928@dmonies.com','5555-6666-9992','Active',1,0),('Hugh','Hefner',314761065,7,'050224913','hughefner11@diratu.com','5555-6666-9997','Active',0,0),('Salsa','Stark',322379646,8,'050366735','xatiye7325@eilnews.com','5555-6666-9998','blocked',0,0),('Joe','Mama',343092928,1,'054011054','woreyo7857@edinel.com','5555-6666-9991','Active',1,1),('Gill','Bates',354053895,6,'050950823','rasori9949@diratu.com','5555-6666-9996','Active',0,0);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-17 15:07:06
