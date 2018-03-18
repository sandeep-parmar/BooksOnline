-- MySQL dump 10.13  Distrib 5.5.50, for Win32 (x86)
--
-- Host: localhost    Database: bookdb
-- ------------------------------------------------------
-- Server version	5.5.50

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
-- Table structure for table `book_user`
--

DROP TABLE IF EXISTS `book_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_user` (
  `uid` varchar(64) NOT NULL DEFAULT '',
  `bookid` varchar(64) NOT NULL DEFAULT '',
  `pin` int(6) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `price` int(8) DEFAULT NULL,
  `soldstatus` int(1) DEFAULT NULL,
  PRIMARY KEY (`uid`,`bookid`),
  KEY `pin` (`pin`),
  CONSTRAINT `book_user_ibfk_1` FOREIGN KEY (`pin`) REFERENCES `locality` (`pin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_user`
--

LOCK TABLES `book_user` WRITE;
/*!40000 ALTER TABLE `book_user` DISABLE KEYS */;
INSERT INTO `book_user` VALUES ('123','9780062515674',411015,'sandeep parmar','9011916677',150,0),('123','9789352770250',411015,'sandeep parmar','9011916677',200,1),('123','9789386057587',452145,'sandeep parmar','9011916677',75,0);
/*!40000 ALTER TABLE `book_user` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `books` (
  `bookid` varchar(13) NOT NULL DEFAULT '',
  `booktitle` varchar(64) NOT NULL,
  `bookauthor` varchar(1024) NOT NULL,
  `bookdesc` varchar(2048) DEFAULT NULL,
  `thumbnail` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`bookid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES ('9780062515674','The Monk Who Sold His Ferrari','Robin Sharma ','Wisdom to Create a Life of Passion, Purpose, and Peace This inspiring tale provides a step-by-step approach to living with greater courage, balance, abundance, and joy. A wonderfully crafted fable, The Monk Who Sold His Ferrari tells the extraordinary story of Julian Mantle, a lawyer forced to confront the spiritual crisis of his out-of-balance life. On a life-changing odyssey to an ancient culture, he discovers powerful, wise, and practical lessons that teach us to: Develop Joyful Thoughts, Follow Our Life\'s Mission and Calling, Cultivate Self-Discipline and Act Courageously, Value Time as Our Most Important Commodity, Nourish Our Relationships, and Live Fully, One Day at a Time.','http://books.google.com/books/content?id=aKJAQGed_-MC&printsec=frontcover&img=1&zoom=1&source=gbs_api'),('9789352770250','And Then There Were None','Agatha Christie ','Ten strangers, apparently with little in common, are lured to an island mansion by the mysterious U.N.Owen. Over dinner, a record begins to play, accusing each of hiding a guilty secret. Then former reckless driver Tony Marston is found murdered by a deadly dose of cyanide. The tension escalates as the survivors realize that the killer is not only among them but is preparing to strike again... and again...','http://books.google.com/books/content?id=BOw_DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),('9789386057587','Inner Engineering','Sadhguru ','‘Inner Engineering is a fascinating read, rich with Sadhguru’s insights and his teachings. If you are ready, it is a tool to help awaken your own inner intelligence, the ultimate and supreme genius that mirrors the wisdom of the cosmos’—Deepak Chopra In his revolutionary new book, visionary, mystic and yogi Sadhguru distils his own experiences with spirituality and yoga and introduces the transformational concept of Inner Engineering. Developed by him over several years, this powerful practice serves to align the mind and the body with energies around and within, creating a world of limitless power and possibilities. Inner Engineering is your own software for joy and well-being.','http://books.google.com/books/content?id=cqCiDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locality`
--

DROP TABLE IF EXISTS `locality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `locality` (
  `pin` int(6) NOT NULL,
  `city` varchar(64) DEFAULT NULL,
  `area` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`pin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locality`
--

LOCK TABLES `locality` WRITE;
/*!40000 ALTER TABLE `locality` DISABLE KEYS */;
INSERT INTO `locality` VALUES (411015,'PUNE','vishrantwadi'),(452145,'chennai','manda'),(654124,'chennai','manda');
/*!40000 ALTER TABLE `locality` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `mobile` varchar(13) NOT NULL DEFAULT '',
  `email` varchar(64) NOT NULL,
  `salt` varchar(256) DEFAULT NULL,
  `uuid` varchar(256) DEFAULT NULL,
  `active` int(11) DEFAULT NULL,
  PRIMARY KEY (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('sandeep','cwvnwe0d2vWzru5m6re64lEje53h9lZq56DCjoOQQI4=','123','sandeepparmar20@gmail.com','wLM8EHTQrjeaXfkWbHu8Ug==','4c1bd10f-540a-4436-a2b6-bbfa2e19ed4c',1);
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

-- Dump completed on 2018-02-26 20:15:21
