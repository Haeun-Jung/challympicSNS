-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: challympic
-- ------------------------------------------------------
-- Server version	5.5.5-10.6.5-MariaDB

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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `activity_no` int(11) NOT NULL AUTO_INCREMENT,
  `post_no` int(11) NOT NULL,
  `user_no` int(11) NOT NULL,
  PRIMARY KEY (`activity_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert` (
  `alert_no` int(11) NOT NULL AUTO_INCREMENT,
  `alert_confirm` bit(1) NOT NULL,
  `alert_content` varchar(255) DEFAULT NULL,
  `alert_reg_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`alert_no`),
  KEY `FKns4t2wgs21lxce3hranlp0tlw` (`user_no`),
  CONSTRAINT `FKns4t2wgs21lxce3hranlp0tlw` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `challenge`
--

DROP TABLE IF EXISTS `challenge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `challenge` (
  `challenge_no` int(11) NOT NULL AUTO_INCREMENT,
  `challenge_access` varchar(10) DEFAULT 'PUBLIC',
  `challenge_content` varchar(255) NOT NULL,
  `challenge_end` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `challenge_official` bit(1) DEFAULT NULL,
  `challenge_report` int(11) NOT NULL DEFAULT 0,
  `challenge_start` timestamp NOT NULL DEFAULT current_timestamp(),
  `challenge_title` varchar(255) NOT NULL,
  `challenge_type` int(11) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`challenge_no`),
  KEY `FKf7j9aiw5s3txnrv8ibjd8774u` (`user_no`),
  CONSTRAINT `FKf7j9aiw5s3txnrv8ibjd8774u` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenge`
--

LOCK TABLES `challenge` WRITE;
/*!40000 ALTER TABLE `challenge` DISABLE KEYS */;
INSERT INTO `challenge` VALUES (1,'PUBLIC','요즘 #배달_음식 이 비싸서 요리를 자주 하고 계시지 않나요? 같이 #레시피 도 공유하고 본인이 만든 음식을 #자랑 해봐요!\n#요리 #방콕요리사','2022-02-24 00:00:00','\0',0,'2022-02-16 15:01:15','요리_챌린지',0,3),(2,'PUBLIC','야경 사진의 #마스터 를 찾자! 내가 제일 #야경 을 잘 찍는 것 같다. 내가 찍은 이 사진이 레전드다. 전국에 계신 #금손들 나와주세요~~\n#야경사진 #사진 #야경맛집','2022-02-24 00:00:00','\0',0,'2022-02-16 15:03:57','야경사진_챌린지',0,4),(3,'PUBLIC','#이른_아침 에 일어나 자신의 일과를 시작해보세요. 남들보다 하루를 일찍 시작하여 본인만의 시간도 가질 뿐만 아니라 #자기계발 에도 도움이 됩니다. #미라클모닝 으로 눈부시게 달라질 당신의 일상을 기대합니다!\n#아침형인간 #시간관리','2022-02-24 00:00:00','\0',0,'2022-02-16 15:07:53','미라클 모닝_챌린지',0,5),(4,'PUBLIC','지나가는 강아지를 봐도 #우리집_강아지 가 제일 이뻐보이지 않나요 ㅎㅎ? #당신의_반려견 을 소개해주세요! \n#강아지 #강쥐 #반려견 #왕귀요미','2022-02-24 00:00:00','\0',0,'2022-02-16 15:14:11','천하제일 반려견 자랑_챌린지',0,6);
/*!40000 ALTER TABLE `challenge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `challenge_tag`
--

DROP TABLE IF EXISTS `challenge_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `challenge_tag` (
  `challenge_tag_no` int(11) NOT NULL AUTO_INCREMENT,
  `challenge_no` int(11) DEFAULT NULL,
  `tag_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`challenge_tag_no`),
  KEY `FKsip5es41kp96xyfumx1c6fow5` (`challenge_no`),
  KEY `FKmm3e7k731s4w8se9h4ycflegx` (`tag_no`),
  CONSTRAINT `FKmm3e7k731s4w8se9h4ycflegx` FOREIGN KEY (`tag_no`) REFERENCES `tag` (`tag_no`),
  CONSTRAINT `FKsip5es41kp96xyfumx1c6fow5` FOREIGN KEY (`challenge_no`) REFERENCES `challenge` (`challenge_no`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenge_tag`
--

LOCK TABLES `challenge_tag` WRITE;
/*!40000 ALTER TABLE `challenge_tag` DISABLE KEYS */;
INSERT INTO `challenge_tag` VALUES (1,1,NULL),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,2,NULL),(8,2,12),(9,2,13),(10,2,14),(11,2,15),(12,2,16),(13,2,17),(14,3,NULL),(15,3,23),(16,3,24),(17,3,25),(18,3,26),(19,3,27),(20,4,NULL),(21,4,36),(22,4,37),(23,4,38),(24,4,39),(25,4,40),(26,4,41);
/*!40000 ALTER TABLE `challenge_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `challenger`
--

DROP TABLE IF EXISTS `challenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `challenger` (
  `challenger_no` int(11) NOT NULL AUTO_INCREMENT,
  `challenge_no` int(11) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`challenger_no`),
  KEY `FKjbn18tllfdox88bospmiy8hvn` (`challenge_no`),
  KEY `FKncix0xwd7y7pebmyli09pyvd9` (`user_no`),
  CONSTRAINT `FKjbn18tllfdox88bospmiy8hvn` FOREIGN KEY (`challenge_no`) REFERENCES `challenge` (`challenge_no`),
  CONSTRAINT `FKncix0xwd7y7pebmyli09pyvd9` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenger`
--

LOCK TABLES `challenger` WRITE;
/*!40000 ALTER TABLE `challenger` DISABLE KEYS */;
/*!40000 ALTER TABLE `challenger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `comment_no` int(11) NOT NULL AUTO_INCREMENT,
  `comment_content` varchar(255) NOT NULL,
  `comment_regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `comment_report` int(11) NOT NULL,
  `comment_update` timestamp NOT NULL DEFAULT current_timestamp(),
  `post_no` int(11) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`comment_no`),
  KEY `FKdl3cujtfyaoyubq2c2ldp1kxc` (`post_no`),
  KEY `FKq0ekodjkru4a87f99qeyg28l0` (`user_no`),
  CONSTRAINT `FKdl3cujtfyaoyubq2c2ldp1kxc` FOREIGN KEY (`post_no`) REFERENCES `post` (`post_no`),
  CONSTRAINT `FKq0ekodjkru4a87f99qeyg28l0` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_like`
--

DROP TABLE IF EXISTS `comment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_like` (
  `commentlike_no` int(11) NOT NULL AUTO_INCREMENT,
  `comment_no` int(11) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`commentlike_no`),
  KEY `FKqgrsb78cyuonudxg8o7hbg9xr` (`comment_no`),
  KEY `FK1w5e3luovyjah55ovupvewmq1` (`user_no`),
  CONSTRAINT `FK1w5e3luovyjah55ovupvewmq1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`),
  CONSTRAINT `FKqgrsb78cyuonudxg8o7hbg9xr` FOREIGN KEY (`comment_no`) REFERENCES `comment` (`comment_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_like`
--

LOCK TABLES `comment_like` WRITE;
/*!40000 ALTER TABLE `comment_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follow` (
  `follow_no` int(11) NOT NULL AUTO_INCREMENT,
  `follow_follower_no` int(11) DEFAULT NULL,
  `follow_following_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`follow_no`),
  KEY `FKkiesp55c4j0nwdamkpihtt4o4` (`follow_follower_no`),
  KEY `FKa22de1a9o9oga4scynvomh0to` (`follow_following_no`),
  CONSTRAINT `FKa22de1a9o9oga4scynvomh0to` FOREIGN KEY (`follow_following_no`) REFERENCES `user` (`user_no`),
  CONSTRAINT `FKkiesp55c4j0nwdamkpihtt4o4` FOREIGN KEY (`follow_follower_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interest` (
  `interest_no` int(11) NOT NULL AUTO_INCREMENT,
  `tag_no` int(11) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`interest_no`),
  KEY `FK4yy1m96gv9vvv00fasn2y8ts4` (`tag_no`),
  KEY `FKt5r8iuupapv4j046nwir4ajny` (`user_no`),
  CONSTRAINT `FK4yy1m96gv9vvv00fasn2y8ts4` FOREIGN KEY (`tag_no`) REFERENCES `tag` (`tag_no`),
  CONSTRAINT `FKt5r8iuupapv4j046nwir4ajny` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interest`
--

LOCK TABLES `interest` WRITE;
/*!40000 ALTER TABLE `interest` DISABLE KEYS */;
INSERT INTO `interest` VALUES (1,6,4),(2,9,4),(3,17,5),(4,5,5),(5,7,5),(6,31,6),(7,24,6),(8,2,6),(9,23,7),(10,32,7),(11,15,7),(12,9,7),(13,44,8),(14,36,8),(15,17,8),(16,6,8);
/*!40000 ALTER TABLE `interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media` (
  `file_no` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `file_savedname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`file_no`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
INSERT INTO `media` VALUES (1,'dduck_pmj.png','output/media/20220217/000116','a70e89e629fcbd940b6f6328b497fbf1.png'),(2,'사진1.png','output/profile/20220217/000132','b310a3747ca568b86d1e803a5c10ead3.png'),(3,'han_pmj.png','output/media/20220217/000357','4204eeffadbd4bbc5aba9aed8cc78313.png'),(4,'f1_yj.png','output/media/20220217/000502','56d241ed6d96388dd412ac50ef9f0c4c.png'),(5,'사진2.png','output/profile/20220217/000519','ebbbf597c73b33f6153c7c75380125b9.png'),(6,'time_he.png','output/media/20220217/000753','27abc276ecf0fe38c64630f5ca5678a7.png'),(7,'f2_he.png','output/media/20220217/000917','400189bb4b89316635cebb44559e339e.png'),(8,'n1_he.png','output/media/20220217/001029','27a381546291a5018f1a8572f55a5322.png'),(9,'사진3.png','output/profile/20220217/001043','f20119d4b8a5e6c7c47868b368cfd524.png'),(10,'사진4.png','output/profile/20220217/001140','b3d8a778d272a65aba609813f8f2ed27.png'),(11,'사진6.png','output/profile/20220217/001154','f68fe79d3eabed2151f484abcef37329.png'),(12,'pu_hj.png','output/media/20220217/001411','de1c8c7ddba0d1d45f840817d7a0f7b4.png'),(13,'jin_ob.png','output/media/20220217/001605','8a89ba55ba2acaa25db59e0345765e7a.png'),(14,'사진4.png','output/profile/20220217/001628','b3d8a778d272a65aba609813f8f2ed27.png'),(15,'d1_pmj.png','output/media/20220217/001728','8a5a3ecdeea3161849324a9d4a3f48cf.png'),(16,'t1_mj.png','output/media/20220217/001845','d209ff41ee1f3f56b4b070e7c8daa25c.png'),(17,'n2_mj.png','output/media/20220217/002014','3ca2026cdb6960a2ed3ee41bf229e817.png'),(18,'n3_hj.png','output/media/20220217/002423','a348a71212d1b364b2dea164254182f2.png'),(19,'f3_hj.png','output/media/20220217/002538','bfb16629734c580de7575dbf6b3603a4.png'),(20,'m1_yj.jpg','output/media/20220217/002811','e75abec346f2780ecd3ce6b6ee2c7609.jpg'),(21,'d2_yj.png','output/media/20220217/002920','0f1625812c6acf1fda8483a97daac4b7.png'),(22,'n4_ob.png','output/media/20220217/003143','211cf00a78e0e83fec6f30f43ca0d753.png'),(23,'f4_ob.png','output/media/20220217/003313','79e568dd458317a5b91c5b0ced2b4ea7.png');
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `post_no` int(11) NOT NULL AUTO_INCREMENT,
  `challenge_no` int(11) NOT NULL,
  `post_content` varchar(255) DEFAULT NULL,
  `post_regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `post_report` int(11) NOT NULL,
  `post_update` timestamp NOT NULL DEFAULT current_timestamp(),
  `file_no` int(11) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`post_no`),
  KEY `FKahkigmtksapy14v27pawejw9l` (`file_no`),
  KEY `FKsc38tg1hw5vac8j0nl1ux0oqc` (`user_no`),
  CONSTRAINT `FKahkigmtksapy14v27pawejw9l` FOREIGN KEY (`file_no`) REFERENCES `media` (`file_no`),
  CONSTRAINT `FKsc38tg1hw5vac8j0nl1ux0oqc` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,1,'떡볶이 JMT #떡볶이 #맛있다 #JMT #또_먹고싶다','2022-02-16 15:01:16',0,'2022-02-16 15:01:16',1,3),(2,2,'한강에서 찍은 사진 올려봅니다~ #한강 #야경 #너무_예뻐','2022-02-16 15:03:57',0,'2022-02-16 15:03:57',3,4),(3,1,'아침은 브런치 국룰 #브런치 #팬케이크 맞습니다~!~!','2022-02-16 15:05:03',0,'2022-02-16 15:05:03',4,4),(4,3,'#미라클모닝이 요즘 핫하다 해서 해봤습니다. #새벽5시30분기상  #피곤','2022-02-16 15:07:54',0,'2022-02-16 15:07:54',6,5),(5,1,'쿠...쿠키..맞다구요..!!! #쿠키 #요리 #모양은_이래도_맛은_일품 #JMT','2022-02-16 15:09:17',0,'2022-02-16 15:09:17',7,5),(6,2,'#여수 #야경 #여수밤바다 #야경맛집','2022-02-16 15:10:29',0,'2022-02-16 15:10:29',8,5),(7,4,'아롱이를 소개합니다 #푸들 #화난거아님 #애교쟁이 #반려견 #왕귀요미','2022-02-16 15:14:11',0,'2022-02-16 15:14:11',12,6),(8,4,'#복1 이와 #복3  #시골 #똥강아지 #반려견','2022-02-16 15:16:05',0,'2022-02-16 15:16:05',13,7),(9,4,'#가을이 보다 의자에 잘 앉는 사람 본 적 없음 #강아지 #강쥐','2022-02-16 15:17:29',0,'2022-02-16 15:17:29',15,3),(10,3,'#주말에 #일찍일어나다니 #어렵다 #미라클모닝 #아침형인간 #아니고 #저녁형인간 #시간관리 는 좋다','2022-02-16 15:18:45',0,'2022-02-16 15:18:45',16,3),(11,2,'인천 송도입니다! #인천 #송도 #야경 #야경명소 #센트럴파크 #사진','2022-02-16 15:20:14',0,'2022-02-16 15:20:14',17,3),(12,2,'#부산 #광안리 #야경 #야경맛집','2022-02-16 15:24:24',0,'2022-02-16 15:24:24',18,8),(13,1,'ㄴr는 가끔 ㄴrㅂㅔ를 만든다....⭐\r\n#청경채 #5인분같은1인분 #다이어트 #건강 #방콕요리사','2022-02-16 15:25:39',0,'2022-02-16 15:25:39',19,8),(14,3,'#동기부여도 되고 하루를 일찍 시작할 수 있어서 좋아요. 다들 #미라클모닝 하세요!! #자기계발 #기분좋은하루','2022-02-16 15:28:12',0,'2022-02-16 15:28:12',20,4),(15,4,'#후추 우승각 #강아지 #왕귀요미 #위너','2022-02-16 15:29:20',0,'2022-02-16 15:29:20',21,4),(16,2,'한강에서 산책하다가 찍었습니다.~ #한강 #롯데타워 #월드타워 #야경 #야경맛집','2022-02-16 15:31:43',0,'2022-02-16 15:31:43',22,7),(17,1,'다들 이렇게는 챙겨드시죠?? 집밥최고^_^ #집밥 #소소하게 #요리 #요리왕 #방콕요리사','2022-02-16 15:33:13',0,'2022-02-16 15:33:13',23,7);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_like`
--

DROP TABLE IF EXISTS `post_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_like` (
  `like_no` int(11) NOT NULL AUTO_INCREMENT,
  `post_no` int(11) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`like_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_like`
--

LOCK TABLES `post_like` WRITE;
/*!40000 ALTER TABLE `post_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_tag`
--

DROP TABLE IF EXISTS `post_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_tag` (
  `post_tag_no` int(11) NOT NULL AUTO_INCREMENT,
  `post_no` int(11) DEFAULT NULL,
  `tag_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`post_tag_no`),
  KEY `FKajvcbw21l5dcmgp9w6770rdw2` (`post_no`),
  KEY `FKbn960hsgcebl4skl6a57shu4u` (`tag_no`),
  CONSTRAINT `FKajvcbw21l5dcmgp9w6770rdw2` FOREIGN KEY (`post_no`) REFERENCES `post` (`post_no`),
  CONSTRAINT `FKbn960hsgcebl4skl6a57shu4u` FOREIGN KEY (`tag_no`) REFERENCES `tag` (`tag_no`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_tag`
--

LOCK TABLES `post_tag` WRITE;
/*!40000 ALTER TABLE `post_tag` DISABLE KEYS */;
INSERT INTO `post_tag` VALUES (1,1,7),(2,1,8),(3,1,9),(4,1,10),(5,2,18),(6,2,13),(7,2,19),(8,3,20),(9,3,21),(10,4,28),(11,4,29),(12,4,30),(13,5,31),(14,5,5),(15,5,32),(16,5,9),(17,6,33),(18,6,13),(19,6,34),(20,6,17),(21,7,42),(22,7,43),(23,7,44),(24,7,40),(25,7,41),(26,8,45),(27,8,46),(28,8,47),(29,8,48),(30,8,40),(31,9,49),(32,9,38),(33,9,39),(34,10,50),(35,10,51),(36,10,52),(37,10,25),(38,10,26),(39,10,53),(40,10,54),(41,10,27),(42,11,55),(43,11,56),(44,11,13),(45,11,57),(46,11,58),(47,11,16),(48,12,59),(49,12,60),(50,12,13),(51,12,17),(52,13,61),(53,13,62),(54,13,63),(55,13,6),(56,14,64),(57,14,25),(58,14,24),(59,14,65),(60,15,66),(61,15,38),(62,15,41),(63,15,67),(64,16,18),(65,16,68),(66,16,69),(67,16,13),(68,16,17),(69,17,70),(70,17,71),(71,17,5),(72,17,72),(73,17,6);
/*!40000 ALTER TABLE `post_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qna`
--

DROP TABLE IF EXISTS `qna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qna` (
  `qna_no` int(11) NOT NULL AUTO_INCREMENT,
  `qna_answer` varchar(255) DEFAULT NULL,
  `qna_answer_regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `qna_question` varchar(255) NOT NULL,
  `qna_question_regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `qna_title` varchar(255) NOT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`qna_no`),
  KEY `FK31ghchh0m0etr9lk7qe45bgs` (`user_no`),
  CONSTRAINT `FK31ghchh0m0etr9lk7qe45bgs` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qna`
--

LOCK TABLES `qna` WRITE;
/*!40000 ALTER TABLE `qna` DISABLE KEYS */;
/*!40000 ALTER TABLE `qna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `search`
--

DROP TABLE IF EXISTS `search`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `search` (
  `search_no` int(11) NOT NULL AUTO_INCREMENT,
  `search_content` varchar(255) DEFAULT NULL,
  `search_regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `tag_content` varchar(255) DEFAULT NULL,
  `tag_no` int(11) NOT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`search_no`),
  KEY `FKrsyw61ybabudu5xxybtqrsd4h` (`user_no`),
  CONSTRAINT `FKrsyw61ybabudu5xxybtqrsd4h` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search`
--

LOCK TABLES `search` WRITE;
/*!40000 ALTER TABLE `search` DISABLE KEYS */;
/*!40000 ALTER TABLE `search` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `search_challenge`
--

DROP TABLE IF EXISTS `search_challenge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `search_challenge` (
  `search_challenge_no` int(11) NOT NULL AUTO_INCREMENT,
  `search_regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `challenge_no` int(11) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`search_challenge_no`),
  KEY `FK5ps1tbtpg3sqgaj0lc2qu8dgb` (`challenge_no`),
  KEY `FK63nelr42s5nd5vm65unbf9n8s` (`user_no`),
  CONSTRAINT `FK5ps1tbtpg3sqgaj0lc2qu8dgb` FOREIGN KEY (`challenge_no`) REFERENCES `challenge` (`challenge_no`),
  CONSTRAINT `FK63nelr42s5nd5vm65unbf9n8s` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search_challenge`
--

LOCK TABLES `search_challenge` WRITE;
/*!40000 ALTER TABLE `search_challenge` DISABLE KEYS */;
/*!40000 ALTER TABLE `search_challenge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscription` (
  `subscription_no` int(11) NOT NULL AUTO_INCREMENT,
  `challenge_no` int(11) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`subscription_no`),
  KEY `FKplh7h2l6djewe7xnbdjtt0dd1` (`challenge_no`),
  KEY `FKtck8ocxya3nh7jbd0intp0usx` (`user_no`),
  CONSTRAINT `FKplh7h2l6djewe7xnbdjtt0dd1` FOREIGN KEY (`challenge_no`) REFERENCES `challenge` (`challenge_no`),
  CONSTRAINT `FKtck8ocxya3nh7jbd0intp0usx` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `tag_no` int(11) NOT NULL AUTO_INCREMENT,
  `is_challenge` varchar(255) DEFAULT NULL,
  `tag_content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tag_no`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'challenge','#요리_챌린지'),(2,NULL,'#배달_음식'),(3,NULL,'#레시피'),(4,NULL,'#자랑'),(5,NULL,'#요리'),(6,NULL,'#방콕요리사'),(7,NULL,'#떡볶이'),(8,NULL,'#맛있다'),(9,NULL,'#JMT'),(10,NULL,'#또_먹고싶다'),(11,'challenge','#야경사진_챌린지'),(12,NULL,'#마스터'),(13,NULL,'#야경'),(14,NULL,'#금손들'),(15,NULL,'#야경사진'),(16,NULL,'#사진'),(17,NULL,'#야경맛집'),(18,NULL,'#한강'),(19,NULL,'#너무_예뻐'),(20,NULL,'#브런치'),(21,NULL,'#팬케이크'),(22,'challenge','#미라클 모닝_챌린지'),(23,NULL,'#이른_아침'),(24,NULL,'#자기계발'),(25,NULL,'#미라클모닝'),(26,NULL,'#아침형인간'),(27,NULL,'#시간관리'),(28,NULL,'#미라클모닝이'),(29,NULL,'#새벽5시30분기상'),(30,NULL,'#피곤'),(31,NULL,'#쿠키'),(32,NULL,'#모양은_이래도_맛은_일품'),(33,NULL,'#여수'),(34,NULL,'#여수밤바다'),(35,'challenge','#천하제일 반려견 자랑_챌린지'),(36,NULL,'#우리집_강아지'),(37,NULL,'#당신의_반려견'),(38,NULL,'#강아지'),(39,NULL,'#강쥐'),(40,NULL,'#반려견'),(41,NULL,'#왕귀요미'),(42,NULL,'#푸들'),(43,NULL,'#화난거아님'),(44,NULL,'#애교쟁이'),(45,NULL,'#복1'),(46,NULL,'#복3'),(47,NULL,'#시골'),(48,NULL,'#똥강아지'),(49,NULL,'#가을이'),(50,NULL,'#주말에'),(51,NULL,'#일찍일어나다니'),(52,NULL,'#어렵다'),(53,NULL,'#아니고'),(54,NULL,'#저녁형인간'),(55,NULL,'#인천'),(56,NULL,'#송도'),(57,NULL,'#야경명소'),(58,NULL,'#센트럴파크'),(59,NULL,'#부산'),(60,NULL,'#광안리'),(61,NULL,'#5인분같은1인분'),(62,NULL,'#다이어트'),(63,NULL,'#건강'),(64,NULL,'#동기부여도'),(65,NULL,'#기분좋은하루'),(66,NULL,'#후추'),(67,NULL,'#위너'),(68,NULL,'#롯데타워'),(69,NULL,'#월드타워'),(70,NULL,'#집밥'),(71,NULL,'#소소하게'),(72,NULL,'#요리왕');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `title`
--

DROP TABLE IF EXISTS `title`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `title` (
  `title_no` int(11) NOT NULL AUTO_INCREMENT,
  `title_name` varchar(255) DEFAULT NULL,
  `challenge_no` int(11) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`title_no`),
  KEY `FKsbliti0ldk42d51mhf3ioxv4g` (`challenge_no`),
  KEY `FK3xf50yglvrk47yilu2r48krwi` (`user_no`),
  CONSTRAINT `FK3xf50yglvrk47yilu2r48krwi` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`),
  CONSTRAINT `FKsbliti0ldk42d51mhf3ioxv4g` FOREIGN KEY (`challenge_no`) REFERENCES `challenge` (`challenge_no`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `title`
--

LOCK TABLES `title` WRITE;
/*!40000 ALTER TABLE `title` DISABLE KEYS */;
INSERT INTO `title` VALUES (1,'나는야 방콕요리사',1,NULL),(2,'야경 맛집 사장',2,NULL),(3,'일찍 일어나는',3,NULL),(4,'최강 귀요미 강쥐를 가진',4,NULL);
/*!40000 ALTER TABLE `title` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_no` int(11) NOT NULL AUTO_INCREMENT,
  `user_active` varchar(100) DEFAULT 'ACTIVE',
  `user_auth` varchar(100) DEFAULT 'USER',
  `user_email` varchar(255) NOT NULL,
  `user_inactivedate` datetime(6) DEFAULT NULL,
  `user_nickname` varchar(255) NOT NULL,
  `user_pwd` varchar(255) NOT NULL,
  `user_regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_title` varchar(50) DEFAULT '도전자',
  `file_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_no`),
  UNIQUE KEY `email_nickname_unique` (`user_email`,`user_nickname`),
  KEY `FK2623cyfk9p1gya2w121gocseu` (`file_no`),
  CONSTRAINT `FK2623cyfk9p1gya2w121gocseu` FOREIGN KEY (`file_no`) REFERENCES `media` (`file_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ACTIVE','ADMIN','admin@ssafy.com',NULL,'ADMIN','$2a$10$0yIS7kEt5MEG8M9lCu8WeuAvxmCg3s2rGc6LnS2OAwE951JG7fwxO','2022-02-16 14:58:42',NULL,NULL),(2,'ACTIVE','USER','test01@ssafy.com',NULL,'test','$2a$10$7KTj8D58p/VeFhrmbW/OMuGigyg9d4YADLfxubtsoaBnAGVMpIn3i','2022-02-16 14:58:43',NULL,NULL),(3,'ACTIVE','USER','mij@ssafy.com',NULL,'민주','$2a$10$mN9NUBQfrRTONNkwP9Rrf.sxsoNZpG.8.2gUVbYNZudlvUaGCpt4C','2022-02-16 14:59:29','도전자',2),(4,'ACTIVE','USER','w1@ssafy.com',NULL,'유진','$2a$10$fU6Qrc04MO148AreGPNyOesc.bp79gK7QXOKv.kv5D.jJi6l2glvS','2022-02-16 15:02:21','도전자',5),(5,'ACTIVE','USER','haeun@ssafy.com',NULL,'하은','$2a$10$sAHjlLW/5wgwm3r95IyXPOeVJkiI4X39V4kj.kUmx3bo6MpFguuvG','2022-02-16 15:05:52','도전자',9),(6,'ACTIVE','USER','kwak0568@naver.com',NULL,'현준','$2a$10$aOtZF3gxClhO2TreRdhpb.8nZQaq/cLeE6HMMEpclAaj22hrJw/ra','2022-02-16 15:11:12','도전자',11),(7,'ACTIVE','USER','kwon@ssafy.com',NULL,'오범','$2a$10$oGBF1sFD.xbXGiRuj2Nry.KieR9glsQYJygGBorP/LhAzZYqjXoXe','2022-02-16 15:14:54','도전자',14),(8,'ACTIVE','USER','hp001@ssafy.com',NULL,'혜준','$2a$10$4kvfr/gYdUvt06OgIWoPuOL5X8rMHHc2bsW6/lJGpeUY9pT.xnQsu','2022-02-16 15:23:14','도전자',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'challympic'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-17  0:41:01
