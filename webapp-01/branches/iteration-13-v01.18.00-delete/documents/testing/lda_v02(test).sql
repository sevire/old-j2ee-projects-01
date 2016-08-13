-- MySQL dump 10.13  Distrib 5.6.13, for osx10.7 (i386)
--
-- Host: localhost    Database: lda_v02
-- ------------------------------------------------------
-- Server version	5.6.13

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
-- Table structure for table `Configuration`
--

DROP TABLE IF EXISTS `Configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Configuration` (
  `name` varchar(45) NOT NULL,
  `value` varchar(45) DEFAULT NULL,
  `id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Configuration`
--

LOCK TABLES `Configuration` WRITE;
/*!40000 ALTER TABLE `Configuration` DISABLE KEYS */;
INSERT INTO `Configuration` VALUES ('blogEnabled','true',0),('forceGallery','true',0),('forceThumbnails','true',0),('galleryRoot','gallery',0),('homePage','home',0),('loggingLevel','debug',0),('maxImageWidth','800',0),('maxThumbnailHeight','100',0),('maxThumbnailWidth','100',0),('numGalleryColumns','4',0),('thumbnailRelPath','thumbnails',0);
/*!40000 ALTER TABLE `Configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Screen_type`
--

DROP TABLE IF EXISTS `Screen_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Screen_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Screen_type`
--

LOCK TABLES `Screen_type` WRITE;
/*!40000 ALTER TABLE `Screen_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `Screen_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Screens`
--

DROP TABLE IF EXISTS `Screens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Screens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `name` char(20) DEFAULT NULL,
  `ScreenTitleLong` char(50) DEFAULT NULL,
  `ScreenTitleShort` char(20) DEFAULT NULL,
  `ScreenContents` text,
  `MetaDescription` text,
  `EnabledFlag` tinyint(1) DEFAULT NULL,
  `GalleryFlag` tinyint(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `ScreenType` varchar(45) NOT NULL,
  `SortKey` int(11) DEFAULT NULL,
  `ScreenDisplayType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `BK_unique_name` (`name`),
  KEY `FK_index01` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=105 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Screens`
--

LOCK TABLES `Screens` WRITE;
/*!40000 ALTER TABLE `Screens` DISABLE KEYS */;
INSERT INTO `Screens` VALUES (91,0,'testpage-01','','Test Page 01','This is test page 01','',1,0,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(92,0,'testpage-02','','Test Page 02','This is test page 02','',1,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(94,0,'testpage-03','','Test Page 03','This is test page 03','',1,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(93,0,'mistresses','','Dummy (Mistresses)','','',1,0,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(95,0,'testpage-04','','Test Page 04','This is test page 04','',1,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(96,0,'testpage-05','','Test Page 05','This is test page 05','',1,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(97,0,'testpage-06','','Test Page 06','This is test page 06','',1,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(98,0,'testpage-07','','Test Page 07','This is test page 07','',1,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(99,0,'testpage-08','','Test Page 08','This is test page 08','',0,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(100,0,'testpage-09','','Test Page 09','This is test page 09','',0,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(101,0,'testpage-10','','Test Page 10','This is test page 10','',0,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(102,0,'testpage-11','','Test Page 11','This is test page 11','',0,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(103,0,'testpage-12','','Test Page 12','This is test page 12','',0,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03'),(104,0,'testpage-13','','Test Page 13','This is test page 13','',0,1,'2015-06-21 13:04:52','2015-06-21 13:04:52','Mistress',100,'mistress-03');
/*!40000 ALTER TABLE `Screens` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-21 15:12:01
