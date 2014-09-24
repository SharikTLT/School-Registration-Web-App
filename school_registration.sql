-- MySQL dump 10.13  Distrib 5.5.38, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: school_registration
-- ------------------------------------------------------
-- Server version	5.5.38-0ubuntu0.12.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `enrollment_sections`
--

DROP TABLE IF EXISTS `enrollment_sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrollment_sections` (
  `pk` int(11) NOT NULL,
  `fk_enrollment` int(11) DEFAULT NULL,
  `fk_sections` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  KEY `fk_enrollment` (`fk_enrollment`),
  KEY `fk_sections` (`fk_sections`),
  CONSTRAINT `enrollment_sections_ibfk_1` FOREIGN KEY (`fk_enrollment`) REFERENCES `enrollments` (`pk`),
  CONSTRAINT `enrollment_sections_ibfk_2` FOREIGN KEY (`fk_sections`) REFERENCES `sections` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollment_sections`
--

LOCK TABLES `enrollment_sections` WRITE;
/*!40000 ALTER TABLE `enrollment_sections` DISABLE KEYS */;
/*!40000 ALTER TABLE `enrollment_sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollments`
--

DROP TABLE IF EXISTS `enrollments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrollments` (
  `pk` int(11) NOT NULL,
  `enrollment_number` int(11) DEFAULT NULL,
  `fk_students` int(11) DEFAULT NULL,
  `term` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `enrollment_number` (`enrollment_number`),
  KEY `fk_students` (`fk_students`),
  CONSTRAINT `enrollments_ibfk_1` FOREIGN KEY (`fk_students`) REFERENCES `students` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollments`
--

LOCK TABLES `enrollments` WRITE;
/*!40000 ALTER TABLE `enrollments` DISABLE KEYS */;
/*!40000 ALTER TABLE `enrollments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faculty` (
  `pk` int(11) NOT NULL,
  `faculty_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `faculty_number` (`faculty_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (0,0);
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sections`
--

DROP TABLE IF EXISTS `sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sections` (
  `pk` int(11) NOT NULL,
  `section_number` varchar(20) DEFAULT NULL,
  `fk_subject` int(11) DEFAULT NULL,
  `fk_faculty` int(11) DEFAULT NULL,
  `schedule` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `section_number` (`section_number`),
  KEY `fk_faculty` (`fk_faculty`),
  KEY `fk_subject` (`fk_subject`),
  CONSTRAINT `sections_ibfk_1` FOREIGN KEY (`fk_faculty`) REFERENCES `faculty` (`pk`),
  CONSTRAINT `sections_ibfk_2` FOREIGN KEY (`fk_subject`) REFERENCES `subjects` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sections`
--

LOCK TABLES `sections` WRITE;
/*!40000 ALTER TABLE `sections` DISABLE KEYS */;
INSERT INTO `sections` VALUES (1,'AAA111',3,0,'MTH AM10'),(2,'BBB222',6,0,'TBA'),(3,'CCC333',5,0,'TBA'),(4,'DDD444',7,0,'TF PM4'),(5,'EEE555',5,0,'TBA'),(6,'GGG777',1,0,'TBA'),(7,'ZZZ000',4,0,'TF PM4');
/*!40000 ALTER TABLE `sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_enrollments`
--

DROP TABLE IF EXISTS `student_enrollments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_enrollments` (
  `pk` int(11) NOT NULL,
  `fk_student` int(11) DEFAULT NULL,
  `fk_enrollment` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  KEY `fk_student` (`fk_student`),
  KEY `fk_enrollment` (`fk_enrollment`),
  CONSTRAINT `student_enrollments_ibfk_1` FOREIGN KEY (`fk_student`) REFERENCES `students` (`pk`),
  CONSTRAINT `student_enrollments_ibfk_2` FOREIGN KEY (`fk_enrollment`) REFERENCES `enrollments` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_enrollments`
--

LOCK TABLES `student_enrollments` WRITE;
/*!40000 ALTER TABLE `student_enrollments` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_enrollments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students` (
  `pk` int(11) NOT NULL,
  `student_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `student_number` (`student_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject_prerequisites`
--

DROP TABLE IF EXISTS `subject_prerequisites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject_prerequisites` (
  `pk` int(11) NOT NULL,
  `fk_subject` int(11) DEFAULT NULL,
  `fk_prerequisite` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  KEY `fk_subject` (`fk_subject`),
  KEY `fk_prerequisite` (`fk_prerequisite`),
  CONSTRAINT `subject_prerequisites_ibfk_1` FOREIGN KEY (`fk_subject`) REFERENCES `subjects` (`pk`),
  CONSTRAINT `subject_prerequisites_ibfk_2` FOREIGN KEY (`fk_prerequisite`) REFERENCES `subjects` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject_prerequisites`
--

LOCK TABLES `subject_prerequisites` WRITE;
/*!40000 ALTER TABLE `subject_prerequisites` DISABLE KEYS */;
INSERT INTO `subject_prerequisites` VALUES (1,3,1),(2,3,2);
/*!40000 ALTER TABLE `subject_prerequisites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subjects` (
  `pk` int(11) NOT NULL,
  `subject_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `subject_id` (`subject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (4,'CHEM11'),(6,'COM1'),(5,'CS11'),(1,'MATH11'),(2,'MATH14'),(3,'MATH53'),(7,'PHILO1');
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-24 10:11:53
