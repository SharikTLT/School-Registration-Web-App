DROP TABLE IF EXISTS `enrollment_sections`;
CREATE TABLE `enrollment_sections` (
  `fk_enrollment` int(11) NOT NULL DEFAULT '0',
  `fk_sections` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`fk_enrollment`,`fk_sections`),
  KEY `fk_sections` (`fk_sections`),
  CONSTRAINT `enrollment_sections_ibfk_1` FOREIGN KEY (`fk_enrollment`) REFERENCES `enrollments` (`pk`),
  CONSTRAINT `enrollment_sections_ibfk_2` FOREIGN KEY (`fk_sections`) REFERENCES `sections` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';

DROP TABLE IF EXISTS `enrollments`;
CREATE TABLE `enrollments` (
  `pk` int(11) NOT NULL,
  `enrollment_number` int(11) DEFAULT NULL,
  `fk_students` int(11) DEFAULT NULL,
  `term` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `enrollment_number` (`enrollment_number`),
  KEY `fk_students` (`fk_students`),
  KEY `term` (`term`),
  CONSTRAINT `enrollments_ibfk_2` FOREIGN KEY (`term`) REFERENCES `terms` (`term`),
  CONSTRAINT `enrollments_ibfk_1` FOREIGN KEY (`fk_students`) REFERENCES `students` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `enrollments` WRITE;
INSERT INTO `enrollments` VALUES (1,1,2,'2012 1st'),(2,2,2,'2012 2nd'),(3,3,1,'2012 2nd');
UNLOCK TABLES;

DROP TABLE IF EXISTS `faculty`;
CREATE TABLE `faculty` (
  `pk` int(11) NOT NULL,
  `faculty_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `faculty_number` (`faculty_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `faculty` WRITE;
INSERT INTO `faculty` VALUES (0,0);
UNLOCK TABLES;

DROP TABLE IF EXISTS `sections`;
CREATE TABLE `sections` (
  `pk` int(11) NOT NULL,
  `section_number` varchar(20) DEFAULT NULL,
  `fk_subject` int(11) DEFAULT NULL,
  `term` varchar(20) DEFAULT NULL,
  `fk_faculty` int(11) DEFAULT NULL,
  `schedule` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `section_number` (`section_number`),
  KEY `fk_faculty` (`fk_faculty`),
  KEY `fk_subject` (`fk_subject`),
  KEY `term` (`term`),
  CONSTRAINT `sections_ibfk_3` FOREIGN KEY (`term`) REFERENCES `terms` (`term`),
  CONSTRAINT `sections_ibfk_1` FOREIGN KEY (`fk_faculty`) REFERENCES `faculty` (`pk`),
  CONSTRAINT `sections_ibfk_2` FOREIGN KEY (`fk_subject`) REFERENCES `subjects` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `pk` int(11) NOT NULL,
  `student_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `student_number` (`student_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `students` WRITE;
INSERT INTO `students` VALUES (1,1),(2,2),(3,3);
UNLOCK TABLES;

DROP TABLE IF EXISTS `subject_prerequisites`;
CREATE TABLE `subject_prerequisites` (
  `fk_subject` int(11) NOT NULL DEFAULT '0',
  `fk_prerequisite` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`fk_subject`,`fk_prerequisite`),
  KEY `fk_prerequisite` (`fk_prerequisite`),
  CONSTRAINT `subject_prerequisites_ibfk_1` FOREIGN KEY (`fk_subject`) REFERENCES `subjects` (`pk`),
  CONSTRAINT `subject_prerequisites_ibfk_2` FOREIGN KEY (`fk_prerequisite`) REFERENCES `subjects` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `subjects`;
CREATE TABLE `subjects` (
  `pk` int(11) NOT NULL,
  `subject_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `subject_id` (`subject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


LOCK TABLES `subjects` WRITE;
INSERT INTO `subjects` VALUES (4,'CHEM11'),(6,'COM1'),(5,'CS11'),(1,'MATH11'),(2,'MATH14'),(3,'MATH53'),(7,'PHILO1');
UNLOCK TABLES;

DROP TABLE IF EXISTS `terms`;
CREATE TABLE `terms` (
  `term` varchar(20) NOT NULL,
  PRIMARY KEY (`term`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `terms` WRITE;
INSERT INTO `terms` VALUES ('2012 1st'),('2012 2nd'),('2013 1st'),('2013 2nd'),('2014 1st');
UNLOCK TABLES;
