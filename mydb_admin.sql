DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(25) NOT NULL,
  `password` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


LOCK TABLES `admin` WRITE;

INSERT INTO `admin` VALUES ('admin','1234'),('admin2','0000');

UNLOCK TABLES;

