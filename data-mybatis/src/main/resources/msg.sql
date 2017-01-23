-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: msg
-- ------------------------------------------------------
-- Server version	5.5.47

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
-- Table structure for table `schema_version`
--

DROP TABLE IF EXISTS `schema_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_version` (
  `version_rank` int(11) NOT NULL,
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`version`),
  KEY `schema_version_vr_idx` (`version_rank`),
  KEY `schema_version_ir_idx` (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schema_version`
--

LOCK TABLES `schema_version` WRITE;
/*!40000 ALTER TABLE `schema_version` DISABLE KEYS */;
INSERT INTO `schema_version` VALUES (1,1,'20161227143223','TRANS CREATE','SQL','DDL/V20161227143223__TRANS_CREATE.sql',-512926420,'root','2016-12-28 02:46:38',21,1),(2,2,'20161227143323','TRANS DETAILS CREATE','SQL','DDL/V20161227143323__TRANS_DETAILS_CREATE.sql',-400908869,'root','2016-12-28 02:46:38',26,1);
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trans`
--

DROP TABLE IF EXISTS `trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trans` (
  `rawKey` varchar(128) NOT NULL COMMENT '转码原文件key',
  `message` varchar(200) DEFAULT NULL COMMENT '转码失败信息',
  `type` tinyint(1) DEFAULT NULL COMMENT '转码文件类型：0:MP3,1:MP4',
  `coverKey` varchar(128) DEFAULT NULL COMMENT '视频文件截图key',
  `state` tinyint(1) DEFAULT NULL COMMENT '转码状态：0:失败,1:成功',
  PRIMARY KEY (`rawKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='转码信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trans`
--

LOCK TABLES `trans` WRITE;
/*!40000 ALTER TABLE `trans` DISABLE KEYS */;
INSERT INTO `trans` VALUES ('local/audio/group6/a','格式不正确，无法进行转码',NULL,NULL,0),('local/audio/group6/b.mp4','InvalidParameter.ResourceContentBad',1,NULL,0),('local/audio/group6/c.mp4','123',1,'local/audio/group6/c.jpg',NULL),('local/audio/group6/d.mp4',NULL,1,'local/audio/group6/d.jpg',NULL),('local/audio/group6/e.mp3',NULL,0,NULL,NULL),('local/audio/group6/f.mp3',NULL,0,NULL,NULL),('local/audio/group6/g.mp3',NULL,0,NULL,NULL);
/*!40000 ALTER TABLE `trans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trans_details`
--

DROP TABLE IF EXISTS `trans_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trans_details` (
  `rawKey` varchar(128) NOT NULL COMMENT '转码原文件key',
  `transKey` varchar(128) NOT NULL COMMENT '转码文件key',
  `clarity` tinyint(1) DEFAULT NULL COMMENT '视频清晰度,0:LD,,1:SD,2:HD,3:FHD',
  `quality` tinyint(1) DEFAULT NULL COMMENT '音频清晰度,0:MP3320,1:MP3192,2:MP3160,3:MP3128,4:MP364',
  `transMsg` varchar(128) DEFAULT NULL COMMENT '转码失败的信息',
  KEY `fk_rawKey_idx` (`rawKey`),
  CONSTRAINT `fk_rawKey` FOREIGN KEY (`rawKey`) REFERENCES `trans` (`rawKey`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='转码详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trans_details`
--

LOCK TABLES `trans_details` WRITE;
/*!40000 ALTER TABLE `trans_details` DISABLE KEYS */;
INSERT INTO `trans_details` VALUES ('local/audio/group6/e.mp3','local/audio/group6/e.mp3',NULL,1,'InvalidParameter.ResourceContentBad'),('local/audio/group6/g.mp3','local/audio/group6/g.mp3',NULL,1,NULL),('local/audio/group6/f.mp3','local/audio/group6/f.mp3',NULL,1,NULL),('local/audio/group6/d.mp4','local/audio/group6/d.mp4',1,NULL,NULL),('local/audio/group6/c.mp4','local/audio/group6/c.mp4',1,NULL,'InvalidParameter.TemplateNotSupported'),('local/audio/group6/c.mp4','local/audio/group6/c-ld.mp4',0,NULL,NULL);
/*!40000 ALTER TABLE `trans_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-04 13:18:30
