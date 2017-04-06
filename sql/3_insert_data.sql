-- Host: localhost    Database: phone_book
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address`
  DISABLE KEYS */;
INSERT INTO `address` VALUES (1, 45, '14/7', 'Kiev', 'Sagaudachnogo');
/*!40000 ALTER TABLE `address`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact`
  DISABLE KEYS */;
INSERT INTO `contact` VALUES (1, 'dos@gmail.net', '+380(44)1112255', '+380(97)7894561', 1, 3, 2);
/*!40000 ALTER TABLE `contact`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person`
  DISABLE KEYS */;
INSERT INTO `person` VALUES (1, 'Paton', 'Fedor', 'Feliksovich'), (2, 'Smit', 'Nikola', 'Feliksovich'),
  (3, 'Dostoevskiy', 'Ivan', 'Yakovlevich');
/*!40000 ALTER TABLE `person`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
  DISABLE KEYS */;
INSERT INTO `user` VALUES ('pat', '$2a$10$R60O4Y0ePu9N0eH1YFoA..JZWjgrZ9uzlSIGOgCEw2zJ89lje97Au', 1),
  ('smit', '$2a$10$dyb7IqXSBRRteRmgSiL/A.xKBhm/Z121xBtgDGjnkJruGV1/JlS76', 2);
/*!40000 ALTER TABLE `user`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;