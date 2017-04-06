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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id`        BIGINT(20) NOT NULL AUTO_INCREMENT,
  `apartment` INT(11)             DEFAULT NULL,
  `building`  VARCHAR(10)         DEFAULT NULL,
  `city`      VARCHAR(30)         DEFAULT NULL,
  `street`    VARCHAR(30)         DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id`             BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `email`          VARCHAR(30)          DEFAULT NULL,
  `home_phone`     VARCHAR(17)          DEFAULT NULL,
  `mobile_phone`   VARCHAR(17) NOT NULL,
  `address_id`     BIGINT(20)           DEFAULT NULL,
  `person_id`      BIGINT(20)  NOT NULL,
  `user_person_id` BIGINT(20)           DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl0sju2uai8cgdtic18wy5hlfr` (`address_id`),
  KEY `FKjbcdaayhsa4dhcuc5q0kkw8et` (`person_id`),
  KEY `FKghphi6t7rshqt4x7cts8hqys4` (`user_person_id`),
  CONSTRAINT `FKghphi6t7rshqt4x7cts8hqys4` FOREIGN KEY (`user_person_id`) REFERENCES `user` (`person_id`),
  CONSTRAINT `FKjbcdaayhsa4dhcuc5q0kkw8et` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FKl0sju2uai8cgdtic18wy5hlfr` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id`         BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `last_name`  VARCHAR(30) NOT NULL,
  `name`       VARCHAR(30) NOT NULL,
  `patronymic` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `login`     VARCHAR(30)  NOT NULL,
  `password`  VARCHAR(255) NOT NULL,
  `person_id` BIGINT(20)   NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `UK_ew1hvam8uwaknuaellwhqchhb` (`login`),
  CONSTRAINT `FKir5g7yucydevmmc84i788jp79` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;