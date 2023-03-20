DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(25) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `phoneNum` varchar(13) DEFAULT NULL,
  `email` varchar(25) DEFAULT NULL,
  `designation` varchar(20) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


LOCK TABLES `employee` WRITE;

INSERT INTO `employee` VALUES (123,'name','Male','0000000','name@gmail.com','software developer',30000);

UNLOCK TABLES;

