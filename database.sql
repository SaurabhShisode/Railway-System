-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: RailwaySystem
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `train_id` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  `class` enum('Sleeper','AC1','AC2','AC3','General') DEFAULT NULL,
  `type` enum('Online','Offline','Tatkal') DEFAULT NULL,
  `seat_number` varchar(10) DEFAULT NULL,
  `status` enum('Booked','Canceled') DEFAULT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `user_id` (`user_id`),
  KEY `train_id` (`train_id`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`train_id`) REFERENCES `trains` (`train_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (11,1,1,'2024-12-10','AC2','Online','A1','Booked'),(12,2,2,'2024-12-11','AC3','Offline','B3','Booked'),(13,6,3,'2024-12-12','AC1','Tatkal','C5','Booked'),(14,7,4,'2024-12-13','Sleeper','Online','D2','Canceled'),(15,1,5,'2024-12-14','General','Offline','E8','Booked'),(16,6,9,'2024-12-11','AC1','Online','A12','Canceled'),(17,8,10,'2025-01-18','AC3','Tatkal','B51','Booked'),(18,8,5,'2025-01-11','AC3','Online','C7','Canceled'),(19,1,19,'2024-12-10','Sleeper','Online','C15','Booked'),(20,1,29,'2024-12-10','Sleeper','Online','B12','Booked'),(21,1,19,'2024-12-10','Sleeper','Online','C56','Booked'),(22,10,9,'2025-03-12','AC1','Online','C53','Booked'),(23,8,39,'2024-12-10','Sleeper','Online','Z14','Canceled'),(24,8,2,'2024-12-10','AC2','Offline','N11','Booked');
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cancellations`
--

DROP TABLE IF EXISTS `cancellations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cancellations` (
  `cancellation_id` int NOT NULL AUTO_INCREMENT,
  `booking_id` int DEFAULT NULL,
  `refund_amount` decimal(10,2) DEFAULT NULL,
  `cancellation_date` date DEFAULT NULL,
  PRIMARY KEY (`cancellation_id`),
  KEY `booking_id` (`booking_id`),
  CONSTRAINT `cancellations_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cancellations`
--

LOCK TABLES `cancellations` WRITE;
/*!40000 ALTER TABLE `cancellations` DISABLE KEYS */;
/*!40000 ALTER TABLE `cancellations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `ticket_id` int NOT NULL AUTO_INCREMENT,
  `booking_id` int DEFAULT NULL,
  `passenger_name` varchar(50) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` enum('Male','Female','Other') DEFAULT NULL,
  `seat_number` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `booking_id` (`booking_id`),
  CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (6,11,'John Doe',30,'Male','A1'),(7,12,'Saurabh',22,'Male','B3'),(8,13,'Jane Smith',25,'Female','C5'),(9,14,'Alice Johnson',28,'Female','D2'),(10,15,'John Doe',30,'Male','E8');
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trains`
--

DROP TABLE IF EXISTS `trains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trains` (
  `train_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` enum('Express','Passenger','Rajdhani','Shatabdi','Tatkal') DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `destination` varchar(50) DEFAULT NULL,
  `departure_time` time DEFAULT NULL,
  `arrival_time` time DEFAULT NULL,
  `availability` int DEFAULT NULL,
  PRIMARY KEY (`train_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trains`
--

LOCK TABLES `trains` WRITE;
/*!40000 ALTER TABLE `trains` DISABLE KEYS */;
INSERT INTO `trains` VALUES (1,'Express 101','Express','Delhi','Mumbai','06:00:00','14:00:00',100),(2,'Rajdhani 202','Rajdhani','Kolkata','Chennai','07:00:00','15:30:00',80),(3,'Shatabdi 303','Shatabdi','Bangalore','Hyderabad','08:30:00','12:30:00',120),(4,'Tatkal 404','Tatkal','Jaipur','Agra','10:00:00','12:45:00',50),(5,'Passenger 505','Passenger','Chandigarh','Amritsar','09:00:00','11:30:00',150),(6,'Shatabdi Express','Shatabdi','New Delhi','Mumbai','08:00:00','16:00:00',100),(7,'Rajdhani Express','Rajdhani','Kolkata','Chennai','15:00:00','22:30:00',80),(8,'Duronto Express','Express','Bangalore','Hyderabad','06:30:00','11:00:00',120),(9,'Superfast Express','Express','Mumbai','Goa','09:00:00','12:30:00',150),(10,'Tatkal Special','Tatkal','Pune','Nagpur','17:00:00','23:00:00',50),(11,'Morning Express','Express','Delhi','Mumbai','05:00:00','13:00:00',90),(12,'Super Rajdhani','Rajdhani','Kolkata','Chennai','18:00:00','02:30:00',85),(13,'Evening Shatabdi','Shatabdi','Bangalore','Hyderabad','17:30:00','21:30:00',100),(14,'Tatkal Morning','Tatkal','Jaipur','Agra','08:00:00','10:45:00',55),(15,'Weekend Passenger','Passenger','Chandigarh','Amritsar','10:00:00','12:30:00',140),(16,'Duronto Midnight','Express','Delhi','Mumbai','23:00:00','07:00:00',110),(17,'Luxury Rajdhani','Rajdhani','Kolkata','Chennai','20:00:00','05:00:00',75),(18,'Citylink Shatabdi','Shatabdi','Bangalore','Hyderabad','10:00:00','14:00:00',125),(19,'Expressway Special','Express','Mumbai','Goa','11:00:00','14:30:00',160),(20,'Tatkal Express','Tatkal','Pune','Nagpur','06:00:00','12:00:00',60),(21,'Morning Express','Express','Delhi','Mumbai','05:00:00','13:00:00',90),(22,'Super Rajdhani','Rajdhani','Kolkata','Chennai','18:00:00','02:30:00',85),(23,'Evening Shatabdi','Shatabdi','Bangalore','Hyderabad','17:30:00','21:30:00',100),(24,'Tatkal Morning','Tatkal','Jaipur','Agra','08:00:00','10:45:00',55),(25,'Weekend Passenger','Passenger','Chandigarh','Amritsar','10:00:00','12:30:00',140),(26,'Duronto Midnight','Express','Delhi','Mumbai','23:00:00','07:00:00',110),(27,'Luxury Rajdhani','Rajdhani','Kolkata','Chennai','20:00:00','05:00:00',75),(28,'Citylink Shatabdi','Shatabdi','Bangalore','Hyderabad','10:00:00','14:00:00',125),(29,'Expressway Special','Express','Mumbai','Goa','11:00:00','14:30:00',160),(30,'Tatkal Express','Tatkal','Pune','Nagpur','06:00:00','12:00:00',60),(31,'Morning Express','Express','Delhi','Mumbai','05:00:00','13:00:00',90),(32,'Super Rajdhani','Rajdhani','Kolkata','Chennai','18:00:00','02:30:00',85),(33,'Evening Shatabdi','Shatabdi','Bangalore','Hyderabad','17:30:00','21:30:00',100),(34,'Tatkal Morning','Tatkal','Jaipur','Agra','08:00:00','10:45:00',55),(35,'Weekend Passenger','Passenger','Chandigarh','Amritsar','10:00:00','12:30:00',140),(36,'Duronto Midnight','Express','Delhi','Mumbai','23:00:00','07:00:00',110),(37,'Luxury Rajdhani','Rajdhani','Kolkata','Chennai','20:00:00','05:00:00',75),(38,'Citylink Shatabdi','Shatabdi','Bangalore','Hyderabad','10:00:00','14:00:00',125),(39,'Expressway Special','Express','Mumbai','Goa','11:00:00','14:30:00',160),(40,'Tatkal Express','Tatkal','Pune','Nagpur','06:00:00','12:00:00',60),(41,'Fast Express','Express','Delhi','Mumbai','05:30:00','14:15:00',120),(42,'Raj Express','Rajdhani','Delhi','Mumbai','08:00:00','16:30:00',80),(43,'Shatabdi Star','Shatabdi','Delhi','Mumbai','10:00:00','18:00:00',90),(44,'Duronto 1','Express','Kolkata','Chennai','06:00:00','14:30:00',85),(45,'Super Rajdhani','Rajdhani','Kolkata','Chennai','08:00:00','16:45:00',75),(46,'Passenger Train','Passenger','Kolkata','Chennai','10:30:00','20:00:00',200),(47,'Express King','Express','Bangalore','Hyderabad','07:00:00','12:00:00',100),(48,'Shatabdi Deluxe','Shatabdi','Bangalore','Hyderabad','09:15:00','13:45:00',110),(49,'Fast Passenger','Passenger','Bangalore','Hyderabad','11:30:00','16:30:00',150),(50,'Tatkal King','Tatkal','Jaipur','Agra','09:00:00','11:45:00',60),(51,'Superfast Express','Express','Jaipur','Agra','11:30:00','14:00:00',70),(52,'Luxury Rajdhani','Rajdhani','Jaipur','Agra','15:00:00','17:45:00',50),(53,'Passenger Max','Passenger','Chandigarh','Amritsar','08:00:00','10:30:00',180),(54,'Super Passenger','Passenger','Chandigarh','Amritsar','12:00:00','14:45:00',160),(55,'Duronto Special','Express','Chandigarh','Amritsar','16:00:00','18:30:00',140),(56,'Fast Express','Express','Delhi','Mumbai','05:30:00','14:15:00',120),(57,'Raj Express','Rajdhani','Delhi','Mumbai','08:00:00','16:30:00',80),(58,'Shatabdi Star','Shatabdi','Delhi','Mumbai','10:00:00','18:00:00',90),(59,'Duronto 1','Express','Kolkata','Chennai','06:00:00','14:30:00',85),(60,'Super Rajdhani','Rajdhani','Kolkata','Chennai','08:00:00','16:45:00',75),(61,'Passenger Train','Passenger','Kolkata','Chennai','10:30:00','20:00:00',200),(62,'Express King','Express','Bangalore','Hyderabad','07:00:00','12:00:00',100),(63,'Shatabdi Deluxe','Shatabdi','Bangalore','Hyderabad','09:15:00','13:45:00',110),(64,'Fast Passenger','Passenger','Bangalore','Hyderabad','11:30:00','16:30:00',150),(65,'Tatkal King','Tatkal','Jaipur','Agra','09:00:00','11:45:00',60),(66,'Superfast Express','Express','Jaipur','Agra','11:30:00','14:00:00',70),(67,'Luxury Rajdhani','Rajdhani','Jaipur','Agra','15:00:00','17:45:00',50),(68,'Passenger Max','Passenger','Chandigarh','Amritsar','08:00:00','10:30:00',180),(69,'Super Passenger','Passenger','Chandigarh','Amritsar','12:00:00','14:45:00',160),(70,'Duronto Special','Express','Chandigarh','Amritsar','16:00:00','18:30:00',140);
/*!40000 ALTER TABLE `trains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'John Doe','john.doe@example.com','1234567890','password123'),(2,'saurabh','saurabh@gmail.com','2342342432','1234'),(6,'Jane Smith','jane.smith@example.com','9123456789','password456'),(7,'Alice Johnson','alice.johnson@example.com','9988776655','password789'),(8,'venus','venus@gmail.com','2131231231','venus'),(9,'','','',''),(10,'siddhi','siddhi@gmail.com','1234567893','siddhi');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-10 11:44:58
