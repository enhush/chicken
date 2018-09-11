-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Sep 11, 2018 at 10:01 PM
-- Server version: 5.7.21
-- PHP Version: 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chicken`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_accountsetting`
--

DROP TABLE IF EXISTS `tb_accountsetting`;
CREATE TABLE IF NOT EXISTS `tb_accountsetting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu` varchar(255) DEFAULT NULL,
  `menuNext` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1sumusrs9oy4hxf5cscqkiisf` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_address`
--

DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE IF NOT EXISTS `tb_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `detail` varchar(255) DEFAULT NULL,
  `door` varchar(255) DEFAULT NULL,
  `doorCode` varchar(255) DEFAULT NULL,
  `floor` varchar(255) DEFAULT NULL,
  `orts` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `apartment_id` bigint(20) DEFAULT NULL,
  `district_id` bigint(20) DEFAULT NULL,
  `khoroo_id` bigint(20) DEFAULT NULL,
  `street_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_vt8ix1nts8el8jr83ny6uvf8` (`apartment_id`),
  KEY `FK_g8k3qoc1s5h2quox9wrx1ghh9` (`district_id`),
  KEY `FK_tqeyalh84fpy5g02nn42dxa51` (`khoroo_id`),
  KEY `FK_nrb0s7oyrf1arf5ap6vsc7d1v` (`street_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_address`
--

INSERT INTO `tb_address` (`id`, `detail`, `door`, `doorCode`, `floor`, `orts`, `phoneNumber`, `apartment_id`, `district_id`, `khoroo_id`, `street_id`) VALUES
(1, NULL, '182', '2356#', '1', '6', '91100321', 3, 344, 104, NULL),
(2, 'jk', '36', '8463', '3', '3', '99694080', 4, 343, NULL, 1),
(3, '', '23', '8456', '5', '2', '99719579', 5, 345, 6, 3),
(4, '', '45', '7654', '1', '5', '88765656', 6, 347, 157, 3),
(5, '', '', '', '', '', '99694080', 7, NULL, NULL, 3),
(6, '', '', '', '', '', '99694080', 8, NULL, NULL, 3),
(7, '', '', '', '', '', '', 9, 348, 80, 3),
(8, '', '', '', '', '', '91100321', 10, NULL, NULL, 3),
(9, 'Маркетингийн зардалд', '', '', '', '', '', 11, NULL, NULL, 3);

-- --------------------------------------------------------

--
-- Table structure for table `tb_apartment`
--

DROP TABLE IF EXISTS `tb_apartment`;
CREATE TABLE IF NOT EXISTS `tb_apartment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `khoroo_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_oygxpdyt3g6gak4wg44g9eixy` (`khoroo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_apartment`
--

INSERT INTO `tb_apartment` (`id`, `name`, `khoroo_id`) VALUES
(1, '32', 104),
(2, '33', 104),
(3, '34б', 104),
(4, '15', 26),
(5, '20', 6),
(6, '26', 157),
(7, '', NULL),
(8, '', NULL),
(9, '', 80),
(10, '', NULL),
(11, '', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tb_car`
--

DROP TABLE IF EXISTS `tb_car`;
CREATE TABLE IF NOT EXISTS `tb_car` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `carNumber` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_car_schedule`
--

DROP TABLE IF EXISTS `tb_car_schedule`;
CREATE TABLE IF NOT EXISTS `tb_car_schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `driverName` varchar(255) DEFAULT NULL,
  `fineInfo` varchar(255) DEFAULT NULL,
  `fuelInfo` varchar(255) DEFAULT NULL,
  `giveKm` varchar(255) DEFAULT NULL,
  `receiveKm` varchar(255) DEFAULT NULL,
  `wentKm` varchar(255) DEFAULT NULL,
  `car_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_smf1xpkw3r88i7hit4kn5rs95` (`car_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_chat_message`
--

DROP TABLE IF EXISTS `tb_chat_message`;
CREATE TABLE IF NOT EXISTS `tb_chat_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `date` datetime DEFAULT NULL,
  `readed` tinyint(1) NOT NULL,
  `attach_id` bigint(20) DEFAULT NULL,
  `msgReceiver_id` bigint(20) DEFAULT NULL,
  `msgSender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_j5iiko2fosru0bvabpbhshay1` (`attach_id`),
  KEY `FK_laaf3f49eju26xsfla1pj3b4p` (`msgReceiver_id`),
  KEY `FK_vaintfqmecso1dkiphe6ewu3` (`msgSender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_chat_message`
--

INSERT INTO `tb_chat_message` (`id`, `content`, `date`, `readed`, `attach_id`, `msgReceiver_id`, `msgSender_id`) VALUES
(1, 'быйбйб', '2018-06-26 14:23:06', 1, NULL, 2, 1),
(2, NULL, '2018-06-26 14:23:15', 1, 1, 2, 1),
(3, 'хор', '2018-06-28 12:40:29', 0, NULL, 2, 1),
(4, 'sda', '2018-07-03 11:20:49', 0, NULL, 2, 1),
(5, 'erw', '2018-07-03 11:20:52', 0, NULL, 2, 1),
(6, 'sds', '2018-07-18 16:32:13', 0, NULL, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_chat_message_attach`
--

DROP TABLE IF EXISTS `tb_chat_message_attach`;
CREATE TABLE IF NOT EXISTS `tb_chat_message_attach` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `filesize` float DEFAULT NULL,
  `dir` varchar(255) DEFAULT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `chatMessage_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pka58x0de5140ecp7xtx5e56w` (`chatMessage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_chat_message_attach`
--

INSERT INTO `tb_chat_message_attach` (`id`, `createdDate`, `filesize`, `dir`, `extension`, `name`, `chatMessage_id`) VALUES
(1, '2018-06-26 14:23:15', 7.36, '/FileCenter/ChatPath/2018/6/26/1529994194918', 'mp3', '01 Rollin`', 2);

-- --------------------------------------------------------

--
-- Table structure for table `tb_dashboard_management`
--

DROP TABLE IF EXISTS `tb_dashboard_management`;
CREATE TABLE IF NOT EXISTS `tb_dashboard_management` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `imageDir` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_dashboard_management`
--

INSERT INTO `tb_dashboard_management` (`id`, `imageDir`) VALUES
(1, '/FileCenter/Dashboard/cover/2018/7/3/1530613226244.png');

-- --------------------------------------------------------

--
-- Table structure for table `tb_district`
--

DROP TABLE IF EXISTS `tb_district`;
CREATE TABLE IF NOT EXISTS `tb_district` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=351 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_district`
--

INSERT INTO `tb_district` (`id`, `name`) VALUES
(342, 'Баянгол'),
(343, 'Баянзүрх'),
(344, 'Сонгинохайрхан'),
(345, 'Сүхбаатар'),
(346, 'Хан-Уул'),
(347, 'Чингэлтэй'),
(348, 'Налайх'),
(349, 'Багануур'),
(350, 'Багахангай');

-- --------------------------------------------------------

--
-- Table structure for table `tb_fileshare`
--

DROP TABLE IF EXISTS `tb_fileshare`;
CREATE TABLE IF NOT EXISTS `tb_fileshare` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `deleteFlag` tinyint(1) DEFAULT NULL,
  `updatedDate` datetime DEFAULT NULL,
  `description` longtext,
  `dir` varchar(255) DEFAULT NULL,
  `downloadCount` bigint(20) DEFAULT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `fileSize` varchar(255) DEFAULT NULL,
  `fileSizeM` float DEFAULT NULL,
  `ftype` bigint(20) DEFAULT NULL,
  `name` longtext,
  `sendUserList` longtext,
  `folderStructure_id` bigint(20) DEFAULT NULL,
  `uploader_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1uw1kn9kbcbhuw7dubi0vad7w` (`folderStructure_id`),
  KEY `FK_rbnt8yjiihwbwa7lsf5c8kguq` (`uploader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_fileshare_received_user`
--

DROP TABLE IF EXISTS `tb_fileshare_received_user`;
CREATE TABLE IF NOT EXISTS `tb_fileshare_received_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `fileShare_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_flvno5q82s5j117ow5lllafry` (`fileShare_id`),
  KEY `FK_ebpglaa778u3cflldjmjsitcm` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_foldercolor`
--

DROP TABLE IF EXISTS `tb_foldercolor`;
CREATE TABLE IF NOT EXISTS `tb_foldercolor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_folderstructure`
--

DROP TABLE IF EXISTS `tb_folderstructure`;
CREATE TABLE IF NOT EXISTS `tb_folderstructure` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `extension` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `queue` bigint(20) DEFAULT NULL,
  `systemFolder` tinyint(1) NOT NULL,
  `folderColor_id` bigint(20) DEFAULT NULL,
  `folderStructure_id` bigint(20) DEFAULT NULL,
  `folderType_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dajhww8h6uyphbl22qhqu7ll0` (`folderColor_id`),
  KEY `FK_b8fukbk83h8v2qppb5mcaoe6v` (`folderStructure_id`),
  KEY `FK_aj3ofqfoua98jfiwli2x14cmy` (`folderType_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_foldertype`
--

DROP TABLE IF EXISTS `tb_foldertype`;
CREATE TABLE IF NOT EXISTS `tb_foldertype` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `val` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_ingredient`
--

DROP TABLE IF EXISTS `tb_ingredient`;
CREATE TABLE IF NOT EXISTS `tb_ingredient` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remain` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_ingredient`
--

INSERT INTO `tb_ingredient` (`id`, `name`, `remain`) VALUES
(1, 'Мах', 15000),
(2, 'Гурил', 25000),
(3, 'Будаа', 13000),
(4, 'Хан боргоцой', 1),
(5, 'Хиам', 3000);

-- --------------------------------------------------------

--
-- Table structure for table `tb_khoroo`
--

DROP TABLE IF EXISTS `tb_khoroo`;
CREATE TABLE IF NOT EXISTS `tb_khoroo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `district_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_69gmegqnm5cm56e82flx899hs` (`district_id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_khoroo`
--

INSERT INTO `tb_khoroo` (`id`, `name`, `district_id`) VALUES
(1, '01', 345),
(2, '02', 345),
(3, '03', 345),
(4, '04', 345),
(5, '05', 345),
(6, '06', 345),
(7, '07', 345),
(8, '08', 345),
(9, '09', 345),
(10, '10', 345),
(11, '11', 345),
(12, '12', 345),
(13, '13', 345),
(14, '14', 345),
(15, '15', 345),
(16, '16', 345),
(17, '17', 345),
(18, '18', 345),
(19, '19', 345),
(20, '20', 345),
(23, '01', 343),
(24, '02', 343),
(25, '03', 343),
(26, '04', 343),
(27, '05', 343),
(28, '06', 343),
(30, '07', 343),
(31, '08', 343),
(32, '09', 343),
(34, '10', 343),
(35, '11', 343),
(37, '12', 343),
(39, '13', 343),
(40, '14', 343),
(42, '15', 343),
(44, '16', 343),
(46, '17', 343),
(47, '18', 343),
(48, '19', 343),
(49, '20', 343),
(50, '21', 343),
(51, '22', 343),
(52, '23', 343),
(53, '24', 343),
(54, '25', 343),
(55, '26', 343),
(56, '27', 343),
(57, '28', 343),
(58, '01', 342),
(59, '02', 342),
(60, '03', 342),
(61, '04', 342),
(62, '05', 342),
(63, '06', 342),
(64, '07', 342),
(65, '08', 342),
(66, '09', 342),
(67, '10', 342),
(68, '11', 342),
(69, '12', 342),
(70, '13', 342),
(71, '14', 342),
(72, '15', 342),
(73, '16', 342),
(74, '17', 342),
(75, '18', 342),
(76, '19', 342),
(77, '20', 342),
(78, '21', 342),
(79, '01', 344),
(80, '01', 348),
(81, '02', 344),
(82, '02', 348),
(83, '03', 344),
(84, '04', 344),
(85, '03', 348),
(86, '05', 344),
(87, '06', 344),
(88, '04', 348),
(89, '07', 344),
(90, '08', 344),
(91, '05', 348),
(92, '09', 344),
(93, '10', 344),
(94, '06', 348),
(95, '11', 344),
(96, '07', 348),
(97, '12', 344),
(98, '13', 344),
(99, '14', 344),
(100, '01', 349),
(101, '15', 344),
(102, '02', 349),
(103, '16', 344),
(104, '17', 344),
(105, '03', 349),
(106, '18', 344),
(107, '19', 344),
(108, '04', 349),
(109, '20', 344),
(110, '21', 344),
(111, '05', 349),
(112, '22', 344),
(113, '23', 344),
(114, '24', 344),
(115, '25', 344),
(116, '06', 349),
(117, '26', 344),
(118, '27', 344),
(119, '07', 349),
(120, '28', 344),
(121, '01', 350),
(122, '29', 344),
(123, '30', 344),
(124, '02', 350),
(125, '01', 346),
(126, '03', 350),
(127, '02', 346),
(128, '04', 350),
(129, '03', 346),
(130, '05', 350),
(131, '04', 346),
(132, '05', 346),
(133, '06', 346),
(134, '07', 346),
(135, '08', 346),
(136, '09', 346),
(137, '06', 350),
(138, '10', 346),
(139, '11', 346),
(140, '12', 346),
(141, '13', 346),
(142, '07', 350),
(143, '14', 346),
(144, '15', 346),
(145, '16', 346),
(146, '17', 346),
(147, '18', 346),
(148, '19', 346),
(149, '20', 346),
(150, '21', 346),
(151, '22', 346),
(152, '23', 346),
(153, '24', 346),
(154, '01', 347),
(155, '25', 346),
(156, '02', 347),
(157, '03', 347),
(158, '04', 347),
(159, '05', 347),
(160, '06', 347),
(161, '07', 347),
(162, '08', 347),
(163, '09', 347),
(164, '10', 347),
(165, '11', 347),
(166, '12', 347),
(167, '13', 347),
(168, '14', 347),
(169, '15', 347),
(170, '16', 347),
(171, '17', 347),
(172, '18', 347),
(173, '19', 347),
(174, '20', 347);

-- --------------------------------------------------------

--
-- Table structure for table `tb_notification`
--

DROP TABLE IF EXISTS `tb_notification`;
CREATE TABLE IF NOT EXISTS `tb_notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `seen` tinyint(1) NOT NULL,
  `status` int(11) NOT NULL,
  `acceptor_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_i1tg4b19qh391dkdincrslft3` (`acceptor_id`),
  KEY `FK_6pd228hpa5h99w9hh2tq60h0e` (`post_id`),
  KEY `FK_g0brtkwtgrplmchiugoclgjug` (`sender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_notification`
--

INSERT INTO `tb_notification` (`id`, `date`, `message`, `seen`, `status`, `acceptor_id`, `post_id`, `sender_id`) VALUES
(1, '2018-06-26 14:34:38', 'Таны нийтлэлд сэтгэгдэл бичлээ', 0, 0, 1, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `tb_notification_message`
--

DROP TABLE IF EXISTS `tb_notification_message`;
CREATE TABLE IF NOT EXISTS `tb_notification_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `seen` tinyint(1) NOT NULL,
  `status` int(11) NOT NULL,
  `acceptor_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_oqx9s9tryy51kkk1elmtpm2f8` (`acceptor_id`),
  KEY `FK_g8iqydnd4cubnrhwt61ldx832` (`sender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_notification_message`
--

INSERT INTO `tb_notification_message` (`id`, `date`, `message`, `seen`, `status`, `acceptor_id`, `sender_id`) VALUES
(1, '2018-06-26 14:34:38', 'Таны нийтлэлд сэтгэгдэл бичлээ', 0, 0, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `tb_order`
--

DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE IF NOT EXISTS `tb_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer` int(11) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `other` int(11) DEFAULT NULL,
  `payType` int(11) DEFAULT NULL,
  `payment` int(11) DEFAULT NULL,
  `paymentOther` int(11) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `takeDate` datetime DEFAULT NULL,
  `totalAmount` bigint(20) DEFAULT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `chief_id` bigint(20) DEFAULT NULL,
  `driver_id` bigint(20) DEFAULT NULL,
  `takeUser_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2lv5enunh6tjen9euqonnunle` (`address_id`),
  KEY `FK_3qmijfk1g595m4uehseecjld5` (`chief_id`),
  KEY `FK_la6j75dgpul5da4nne8nvh0lw` (`driver_id`),
  KEY `FK_omta6ygsct0yiwsurg3bwy7bc` (`takeUser_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_order`
--

INSERT INTO `tb_order` (`id`, `customer`, `endDate`, `name`, `other`, `payType`, `payment`, `paymentOther`, `phoneNumber`, `status`, `takeDate`, `totalAmount`, `address_id`, `chief_id`, `driver_id`, `takeUser_id`) VALUES
(1, 0, NULL, '1807181156', 0, NULL, 0, NULL, '99694080', 0, '2018-07-18 00:00:00', 38900, 2, 2, NULL, 1),
(2, 0, NULL, '1807181217', 0, NULL, 1, NULL, '91100321', 1, '2018-07-18 00:00:00', 29900, 1, NULL, NULL, 1),
(3, 0, NULL, '1807181434', 0, NULL, 0, NULL, '99719579', 1, '2018-07-18 00:00:00', 32900, 3, NULL, NULL, 1),
(6, 0, '2018-07-19 18:07:24', '1807181507', 0, NULL, 3, NULL, '99694080', 1, '2018-07-08 15:07:17', 38900, 2, 2, 1, 1),
(7, 0, NULL, '1807192318', 0, NULL, 1, NULL, '88765656', 1, '2018-07-19 23:18:50', 38900, 4, NULL, NULL, 1),
(8, 0, '2018-07-21 16:07:42', '1807211626', 0, NULL, 0, NULL, '99694080', 1, '2018-07-21 16:26:57', 0, 5, NULL, NULL, 1),
(9, 0, '2018-07-21 16:07:35', '1807211631', 0, NULL, 0, NULL, '99694080', 1, '2018-07-21 16:31:18', 0, 6, NULL, NULL, 1),
(10, 0, '2018-07-25 17:07:23', '1807251716', 0, NULL, 2, NULL, '', 1, '2018-07-25 17:16:07', 143600, 7, NULL, NULL, 1),
(11, 0, NULL, '1807251720', 0, NULL, 0, NULL, '91100321', 1, '2018-07-25 17:20:37', 0, 8, NULL, NULL, 1),
(12, 0, NULL, '1807251721', 0, NULL, 0, NULL, '', 1, '2018-07-25 17:21:11', 0, 9, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_order_product_rel`
--

DROP TABLE IF EXISTS `tb_order_product_rel`;
CREATE TABLE IF NOT EXISTS `tb_order_product_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `count` int(11) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pgalvh2ke66r431l68vou1r02` (`order_id`),
  KEY `FK_tb6y0u3x4ypj9i3f1bw9wo9kj` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_order_product_rel`
--

INSERT INTO `tb_order_product_rel` (`id`, `count`, `price`, `order_id`, `product_id`) VALUES
(1, 1, 3000, 1, 1),
(2, 1, 35900, 1, 4),
(3, 1, 29900, 2, 11),
(4, 1, 3000, 3, 1),
(5, 1, 29900, 3, 11),
(12, 1, 3000, 6, 1),
(13, 1, 35900, 6, 4),
(14, 1, 3000, 7, 1),
(15, 1, 35900, 7, 4),
(16, 0, 0, 8, 1),
(17, 0, 0, 9, 1),
(18, 2, 71800, 10, 4),
(19, 2, 59800, 10, 11),
(20, 4, 12000, 10, 1),
(21, 0, 0, 11, 1),
(22, 0, 0, 12, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_permission_rel`
--

DROP TABLE IF EXISTS `tb_permission_rel`;
CREATE TABLE IF NOT EXISTS `tb_permission_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permissionType_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7cx81agxrel36s51g0ba3p3ni` (`permissionType_id`),
  KEY `FK_175kdlum0cstlnpl3930fo24p` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_permission_rel`
--

INSERT INTO `tb_permission_rel` (`id`, `permissionType_id`, `user_id`) VALUES
(1, 1, 1),
(2, 3, 1),
(3, 5, 1),
(4, 2, 2),
(5, 4, 2),
(6, 5, 2),
(7, 6, 1),
(8, 8, 1),
(9, 10, 1),
(15, 2, 4),
(16, 4, 4),
(17, 5, 4),
(18, 7, 4),
(19, 9, 4),
(20, 11, 4),
(21, 1, 5),
(22, 3, 5),
(23, 5, 5),
(24, 6, 5),
(25, 8, 5),
(26, 10, 5),
(27, 4, 6),
(28, 5, 6),
(29, 7, 6),
(30, 8, 6),
(31, 10, 6),
(32, 12, 1),
(33, 14, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_post`
--

DROP TABLE IF EXISTS `tb_post`;
CREATE TABLE IF NOT EXISTS `tb_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activeDate` datetime DEFAULT NULL,
  `content` longtext,
  `createdDate` datetime DEFAULT NULL,
  `fromNotification` tinyint(1) DEFAULT NULL,
  `likeUsers` varchar(255) DEFAULT NULL,
  `likes` bigint(20) DEFAULT NULL,
  `pin` tinyint(1) DEFAULT NULL,
  `seeAll` tinyint(1) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `typeIconName` varchar(255) DEFAULT NULL,
  `typeModelId` bigint(20) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rfjy9c5u870h7l86ivs8edhto` (`owner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_post`
--

INSERT INTO `tb_post` (`id`, `activeDate`, `content`, `createdDate`, `fromNotification`, `likeUsers`, `likes`, `pin`, `seeAll`, `type`, `typeIconName`, `typeModelId`, `owner_id`) VALUES
(1, '2018-06-26 14:34:38', 'Сайнуу миний хөөрхнүүдээ Сайн ажиллаарай :)', '2018-06-26 14:20:45', 0, 'A.Admin\n', 1, 0, 1, 'post', NULL, NULL, 1),
(2, '2018-06-26 14:34:25', 'jknlkmlmkl', '2018-06-26 14:34:25', 0, '', 0, 0, 1, 'question', NULL, NULL, 2),
(4, '2018-07-20 13:07:43', 'Hello guys', '2018-07-20 13:07:43', 0, '', 0, 0, 1, 'post', NULL, NULL, 1),
(5, '2018-07-20 13:08:54', 'Хэзээ зугаалгаар явах вэ?', '2018-07-20 13:08:54', 0, '', 0, 0, 1, 'question', NULL, NULL, 1),
(6, '2018-07-20 13:10:35', '', '2018-07-20 13:10:35', 0, '', 0, 0, 1, 'post', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_post_attach`
--

DROP TABLE IF EXISTS `tb_post_attach`;
CREATE TABLE IF NOT EXISTS `tb_post_attach` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `filesize` float DEFAULT NULL,
  `dir` varchar(255) DEFAULT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ceit8tuwpebtxgdrowcqj5w57` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_post_attach`
--

INSERT INTO `tb_post_attach` (`id`, `createdDate`, `filesize`, `dir`, `extension`, `name`, `post_id`) VALUES
(1, '2018-06-26 14:34:25', NULL, '/FileCenter/PostPath/2018/6/26/1529994863444', 'jpg', '46077', 2),
(2, '2018-07-20 13:10:35', NULL, '/FileCenter/PostPath/2018/7/20/1532117429141', 'png', 'download', 6);

-- --------------------------------------------------------

--
-- Table structure for table `tb_post_choice`
--

DROP TABLE IF EXISTS `tb_post_choice`;
CREATE TABLE IF NOT EXISTS `tb_post_choice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `choice` varchar(255) DEFAULT NULL,
  `procent` int(11) NOT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6hf4hsv283ao41ebwbbsn1fke` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_post_choice`
--

INSERT INTO `tb_post_choice` (`id`, `choice`, `procent`, `post_id`) VALUES
(1, 'vffgfd', 0, 2),
(2, 'dfgdgdg', 0, 2),
(3, 'Өнөөдөр', 100, 5),
(4, 'Маргааш', 0, 5),
(5, 'Дараа жил', 0, 5);

-- --------------------------------------------------------

--
-- Table structure for table `tb_post_choice_vote`
--

DROP TABLE IF EXISTS `tb_post_choice_vote`;
CREATE TABLE IF NOT EXISTS `tb_post_choice_vote` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `choice_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_eb13dmufauwxxhaer1pfwkdkq` (`choice_id`),
  KEY `FK_6s14y9ufutuae3oobm0ic2sbu` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_post_choice_vote`
--

INSERT INTO `tb_post_choice_vote` (`id`, `choice_id`, `user_id`) VALUES
(1, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_post_comment`
--

DROP TABLE IF EXISTS `tb_post_comment`;
CREATE TABLE IF NOT EXISTS `tb_post_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` longtext,
  `createdDate` datetime DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_o71k6cjqj19ydumtwt1unqyds` (`owner_id`),
  KEY `FK_lq4agysnm1grisywfyo527ivy` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_post_comment`
--

INSERT INTO `tb_post_comment` (`id`, `comment`, `createdDate`, `owner_id`, `post_id`) VALUES
(1, 'k;l;;', '2018-06-26 14:33:31', 1, 1),
(2, 'dwdsadsd', '2018-06-26 14:34:38', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_post_see_user`
--

DROP TABLE IF EXISTS `tb_post_see_user`;
CREATE TABLE IF NOT EXISTS `tb_post_see_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `likeThisPost` tinyint(1) NOT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5g6rex2wqk9nvmjafd17yejkf` (`post_id`),
  KEY `FK_fjs8c3jkot13vo3cp138g107w` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_product`
--

DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE IF NOT EXISTS `tb_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `size` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `priceEmployee` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_product`
--

INSERT INTO `tb_product` (`id`, `name`, `price`, `size`, `type`, `priceEmployee`) VALUES
(1, 'Coca Cola Бидний', 3000, '1.25L', 1, NULL),
(4, 'Oh chicken том', 35900, '1 кг', 0, NULL),
(11, 'Салями пицца том', 29900, '15  инч', 0, NULL),
(12, 'Big pizza', 30000, '18 инч', 0, 25000);

-- --------------------------------------------------------

--
-- Table structure for table `tb_product_ingredient_rel`
--

DROP TABLE IF EXISTS `tb_product_ingredient_rel`;
CREATE TABLE IF NOT EXISTS `tb_product_ingredient_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `portion` int(11) DEFAULT NULL,
  `ingredient_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dnwrab25pvhhi3kptdclcwl3i` (`ingredient_id`),
  KEY `FK_g5gq7ok2ri6fr5pq1evvsjkas` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_product_ingredient_rel`
--

INSERT INTO `tb_product_ingredient_rel` (`id`, `portion`, `ingredient_id`, `product_id`) VALUES
(15, 500, 1, 11),
(16, 400, 2, 11),
(17, 500, 1, 4),
(18, 300, 2, 4),
(19, 200, 3, 4),
(25, 500, 1, 12),
(26, 600, 2, 12),
(27, 300, 3, 12),
(28, 250, 4, 12),
(29, 400, 5, 12);

-- --------------------------------------------------------

--
-- Table structure for table `tb_sample_result`
--

DROP TABLE IF EXISTS `tb_sample_result`;
CREATE TABLE IF NOT EXISTS `tb_sample_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `deleteFlag` tinyint(1) DEFAULT NULL,
  `updatedDate` datetime DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `pdfUrl` varchar(255) DEFAULT NULL,
  `purpose` varchar(255) DEFAULT NULL,
  `recieve_date` datetime DEFAULT NULL,
  `result_date` datetime DEFAULT NULL,
  `sample_id` bigint(20) DEFAULT NULL,
  `type` tinyint(1) NOT NULL,
  `vet_sum_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_stock`
--

DROP TABLE IF EXISTS `tb_stock`;
CREATE TABLE IF NOT EXISTS `tb_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `allPrice` bigint(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `state` int(11) NOT NULL,
  `unitPrice` bigint(20) DEFAULT NULL,
  `respondent_id` bigint(20) DEFAULT NULL,
  `respondentTeam_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_nmqogn6nrn9rt5slbd31xch96` (`respondent_id`),
  KEY `FK_tbooinoic8uxbmq52fd2o61ir` (`respondentTeam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_stock_file`
--

DROP TABLE IF EXISTS `tb_stock_file`;
CREATE TABLE IF NOT EXISTS `tb_stock_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `extension` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `stock_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_aketdt4bnsu7mn4vmra2j31jo` (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tb_street`
--

DROP TABLE IF EXISTS `tb_street`;
CREATE TABLE IF NOT EXISTS `tb_street` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_street`
--

INSERT INTO `tb_street` (`id`, `name`) VALUES
(1, 'Эхтайвны гудамж'),
(2, 'Өнөр хорооллын гудамж'),
(3, '');

-- --------------------------------------------------------

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE IF NOT EXISTS `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `address3` varchar(255) DEFAULT NULL,
  `address4` varchar(255) DEFAULT NULL,
  `attempt` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `employmentDate` datetime DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `h` int(11) NOT NULL,
  `isMen` tinyint(1) DEFAULT NULL,
  `lastAttempt` datetime DEFAULT NULL,
  `lastLogOutDate` datetime DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `lastNameLength` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone1` varchar(255) DEFAULT NULL,
  `phone2` varchar(255) DEFAULT NULL,
  `profilePicture` varchar(255) DEFAULT NULL,
  `registerNumber` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `w` int(11) NOT NULL,
  `webFireBasetoken` varchar(255) DEFAULT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `userPosition_id` bigint(20) DEFAULT NULL,
  `userTeam_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7au85bh0x85ehds73tfqs1wb9` (`userPosition_id`),
  KEY `FK_qecwqv3i8603fxtnkbtj02fqn` (`userTeam_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_user`
--

INSERT INTO `tb_user` (`id`, `active`, `address`, `address1`, `address2`, `address3`, `address4`, `attempt`, `code`, `createdDate`, `email`, `employmentDate`, `firstName`, `h`, `isMen`, `lastAttempt`, `lastLogOutDate`, `lastName`, `lastNameLength`, `password`, `phone1`, `phone2`, `profilePicture`, `registerNumber`, `username`, `w`, `webFireBasetoken`, `x`, `y`, `userPosition_id`, `userTeam_id`) VALUES
(1, 1, 'UB', 'Улаанбаатар', 'БЗД', '2-р хороо', 'Их тойруу 106-1', 1, '6000', '2018-06-26 00:00:00', 't@yahoo.com', '2016-06-25 00:00:00', 'Баярмаа', 100, 0, '2018-07-18 16:31:51', '2018-09-11 13:25:20', 'Дондов', 1, '40bd001563085fc35165329ea1ff5c5ecbdbbeef', '88149977', '88869977', '/public/userImages/1532067507201.png', 'ЦД77102768', 'MAYA', 100, '', 0, 0, 1, 1),
(2, 0, NULL, 'УБ', 'СБД', '11-р хороо', '', 0, '1245', '2018-06-26 14:22:47', 'bold@yahoo.com', '2018-06-26 00:00:00', 'Болд', 160, 1, NULL, '2018-06-26 14:35:11', 'Бат', 1, 'c9b76ae79584aabfa8a92e1e354d651804860118', '95522233', '99694080', '/public/userImages/1532002810014.png', 'УФ89120421', 'BOLD', 160, '', 28, 16, 1, 1),
(4, 1, NULL, 'Улаанбаатар', 'БЗД', '2-р хороо', 'Их тойруу 106-1', 0, '6001', '2018-07-25 16:34:33', 't@yahoo.com', '2018-07-25 00:00:00', 'Түвшинбат', 188, 1, NULL, '2018-07-25 16:34:33', 'Алтанцэцэг', 1, '40bd001563085fc35165329ea1ff5c5ecbdbbeef', '88869977', '88149977', '/assets/images/avatars/avatar4_big.png', 'УП00000000', 'TUVSHINBAT', 188, NULL, 0, 0, 2, 1),
(5, 1, NULL, 'Улаанбаатар', 'БГД', '6-р хороо', '20-29 тоот', 0, '6002', '2018-07-25 16:37:18', 't@yahoo.com', '2017-03-15 00:00:00', 'Нинждулам', 198, 0, NULL, '2018-07-25 16:37:18', 'Батсүх', 1, '40bd001563085fc35165329ea1ff5c5ecbdbbeef', '99441855', '99132342', '/assets/images/avatars/avatar12_big.png', 'ЙР90062809', 'NINJDULAM', 198, NULL, 0, 0, 6, 1),
(6, 1, NULL, '55555', '555555', '5555', '555', 0, 'hhhh', '2018-07-25 16:42:29', 'e@yyahoo.com', '2018-07-25 00:00:00', '888', 100, 1, NULL, '2018-07-25 16:42:29', '888', 1, '1a7bfb30d2cdbca973bc19f93c399c28d6276616', '66666666', '55555555', '/assets/images/avatars/avatar3_big.png', '8888888888', '888', 100, NULL, 0, 0, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_userpermission`
--

DROP TABLE IF EXISTS `tb_userpermission`;
CREATE TABLE IF NOT EXISTS `tb_userpermission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `queue` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_userpermission`
--

INSERT INTO `tb_userpermission` (`id`, `alias`, `name`, `queue`) VALUES
(1, 'account', 'Хэрэглэгчид', 1),
(2, 'file', 'Файл', 2),
(3, 'dashboard', 'Миний самбар', 3),
(4, 'ingredient', 'Орц', 4),
(5, 'product', 'Бүтээгдэхүүн', 5),
(6, 'order', 'Захиалга', 6),
(7, 'car', 'Автомашин ашиглалт', 7),
(8, 'stock', 'Хөрөнгийн бүртгэл', 8);

-- --------------------------------------------------------

--
-- Table structure for table `tb_userpermission_type`
--

DROP TABLE IF EXISTS `tb_userpermission_type`;
CREATE TABLE IF NOT EXISTS `tb_userpermission_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `value` int(11) NOT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8jjbo4yithismvvx8p0t8ps9b` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_userpermission_type`
--

INSERT INTO `tb_userpermission_type` (`id`, `description`, `value`, `permission_id`) VALUES
(1, 'Нэмэх, засах, устгах', 3, 1),
(2, 'Харах', 2, 1),
(3, 'Нэмэх, засах, устгах', 3, 2),
(4, 'Харах', 2, 2),
(5, 'Ашиглана', 2, 3),
(6, 'Нэмэх, засах, устгах', 3, 4),
(7, 'Харах', 2, 4),
(8, 'Нэмэх, засах, устгах', 3, 5),
(9, 'Харах', 2, 5),
(10, 'Нэмэх, засах, устгах', 3, 6),
(11, 'Харах', 2, 6),
(12, 'Ашиглана', 2, 7),
(13, 'Харах', 2, 8),
(14, 'Нэмэх, засах, устгах', 3, 8);

-- --------------------------------------------------------

--
-- Table structure for table `tb_userposition`
--

DROP TABLE IF EXISTS `tb_userposition`;
CREATE TABLE IF NOT EXISTS `tb_userposition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `rate` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_userposition`
--

INSERT INTO `tb_userposition` (`id`, `name`, `rate`) VALUES
(1, 'Ерөнхий захирал', 1),
(2, 'Гүйцэтгэх захирал', 2),
(3, 'Ерөнхий менежер', 3),
(4, 'Нягтлан', 4),
(5, 'Маркетингийн менежер', 5),
(6, 'Ахлах менежер', 5),
(7, 'Менежер', 5),
(8, 'Ахлах тогооч', 6),
(9, 'Нярав', 5),
(10, 'Оператор', 6),
(11, 'Салбарын ЗБ', 5),
(12, 'Цагийн ажилтан', 8),
(13, 'Тогооч', 7);

-- --------------------------------------------------------

--
-- Table structure for table `tb_userteam`
--

DROP TABLE IF EXISTS `tb_userteam`;
CREATE TABLE IF NOT EXISTS `tb_userteam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `nameMin` varchar(255) DEFAULT NULL,
  `queue` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_userteam`
--

INSERT INTO `tb_userteam` (`id`, `name`, `nameMin`, `queue`) VALUES
(1, 'Finance', 'СУА', 1),
(2, 'Гал тогоо', 'kITCHEN', 2),
(3, 'Хүргэлтийн алба', 'Delivery', 3),
(4, 'Маркетингийн алба', 'Marketing team', 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_accountsetting`
--
ALTER TABLE `tb_accountsetting`
  ADD CONSTRAINT `FK_1sumusrs9oy4hxf5cscqkiisf` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`);

--
-- Constraints for table `tb_address`
--
ALTER TABLE `tb_address`
  ADD CONSTRAINT `FK_g8k3qoc1s5h2quox9wrx1ghh9` FOREIGN KEY (`district_id`) REFERENCES `tb_district` (`id`),
  ADD CONSTRAINT `FK_nrb0s7oyrf1arf5ap6vsc7d1v` FOREIGN KEY (`street_id`) REFERENCES `tb_street` (`id`),
  ADD CONSTRAINT `FK_tqeyalh84fpy5g02nn42dxa51` FOREIGN KEY (`khoroo_id`) REFERENCES `tb_khoroo` (`id`),
  ADD CONSTRAINT `FK_vt8ix1nts8el8jr83ny6uvf8` FOREIGN KEY (`apartment_id`) REFERENCES `tb_apartment` (`id`);

--
-- Constraints for table `tb_apartment`
--
ALTER TABLE `tb_apartment`
  ADD CONSTRAINT `FK_oygxpdyt3g6gak4wg44g9eixy` FOREIGN KEY (`khoroo_id`) REFERENCES `tb_khoroo` (`id`);

--
-- Constraints for table `tb_car_schedule`
--
ALTER TABLE `tb_car_schedule`
  ADD CONSTRAINT `FK_smf1xpkw3r88i7hit4kn5rs95` FOREIGN KEY (`car_id`) REFERENCES `tb_car` (`id`);

--
-- Constraints for table `tb_chat_message`
--
ALTER TABLE `tb_chat_message`
  ADD CONSTRAINT `FK_j5iiko2fosru0bvabpbhshay1` FOREIGN KEY (`attach_id`) REFERENCES `tb_chat_message_attach` (`id`),
  ADD CONSTRAINT `FK_laaf3f49eju26xsfla1pj3b4p` FOREIGN KEY (`msgReceiver_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `FK_vaintfqmecso1dkiphe6ewu3` FOREIGN KEY (`msgSender_id`) REFERENCES `tb_user` (`id`);

--
-- Constraints for table `tb_chat_message_attach`
--
ALTER TABLE `tb_chat_message_attach`
  ADD CONSTRAINT `FK_pka58x0de5140ecp7xtx5e56w` FOREIGN KEY (`chatMessage_id`) REFERENCES `tb_chat_message` (`id`);

--
-- Constraints for table `tb_fileshare`
--
ALTER TABLE `tb_fileshare`
  ADD CONSTRAINT `FK_1uw1kn9kbcbhuw7dubi0vad7w` FOREIGN KEY (`folderStructure_id`) REFERENCES `tb_folderstructure` (`id`),
  ADD CONSTRAINT `FK_rbnt8yjiihwbwa7lsf5c8kguq` FOREIGN KEY (`uploader_id`) REFERENCES `tb_user` (`id`);

--
-- Constraints for table `tb_fileshare_received_user`
--
ALTER TABLE `tb_fileshare_received_user`
  ADD CONSTRAINT `FK_ebpglaa778u3cflldjmjsitcm` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `FK_flvno5q82s5j117ow5lllafry` FOREIGN KEY (`fileShare_id`) REFERENCES `tb_fileshare` (`id`);

--
-- Constraints for table `tb_folderstructure`
--
ALTER TABLE `tb_folderstructure`
  ADD CONSTRAINT `FK_aj3ofqfoua98jfiwli2x14cmy` FOREIGN KEY (`folderType_id`) REFERENCES `tb_foldertype` (`id`),
  ADD CONSTRAINT `FK_b8fukbk83h8v2qppb5mcaoe6v` FOREIGN KEY (`folderStructure_id`) REFERENCES `tb_folderstructure` (`id`),
  ADD CONSTRAINT `FK_dajhww8h6uyphbl22qhqu7ll0` FOREIGN KEY (`folderColor_id`) REFERENCES `tb_foldercolor` (`id`);

--
-- Constraints for table `tb_khoroo`
--
ALTER TABLE `tb_khoroo`
  ADD CONSTRAINT `FK_69gmegqnm5cm56e82flx899hs` FOREIGN KEY (`district_id`) REFERENCES `tb_district` (`id`);

--
-- Constraints for table `tb_notification`
--
ALTER TABLE `tb_notification`
  ADD CONSTRAINT `FK_6pd228hpa5h99w9hh2tq60h0e` FOREIGN KEY (`post_id`) REFERENCES `tb_post` (`id`),
  ADD CONSTRAINT `FK_g0brtkwtgrplmchiugoclgjug` FOREIGN KEY (`sender_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `FK_i1tg4b19qh391dkdincrslft3` FOREIGN KEY (`acceptor_id`) REFERENCES `tb_user` (`id`);

--
-- Constraints for table `tb_notification_message`
--
ALTER TABLE `tb_notification_message`
  ADD CONSTRAINT `FK_g8iqydnd4cubnrhwt61ldx832` FOREIGN KEY (`sender_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `FK_oqx9s9tryy51kkk1elmtpm2f8` FOREIGN KEY (`acceptor_id`) REFERENCES `tb_user` (`id`);

--
-- Constraints for table `tb_order`
--
ALTER TABLE `tb_order`
  ADD CONSTRAINT `FK_2lv5enunh6tjen9euqonnunle` FOREIGN KEY (`address_id`) REFERENCES `tb_address` (`id`),
  ADD CONSTRAINT `FK_3qmijfk1g595m4uehseecjld5` FOREIGN KEY (`chief_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `FK_la6j75dgpul5da4nne8nvh0lw` FOREIGN KEY (`driver_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `FK_omta6ygsct0yiwsurg3bwy7bc` FOREIGN KEY (`takeUser_id`) REFERENCES `tb_user` (`id`);

--
-- Constraints for table `tb_order_product_rel`
--
ALTER TABLE `tb_order_product_rel`
  ADD CONSTRAINT `FK_pgalvh2ke66r431l68vou1r02` FOREIGN KEY (`order_id`) REFERENCES `tb_order` (`id`),
  ADD CONSTRAINT `FK_tb6y0u3x4ypj9i3f1bw9wo9kj` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`id`);

--
-- Constraints for table `tb_permission_rel`
--
ALTER TABLE `tb_permission_rel`
  ADD CONSTRAINT `FK_175kdlum0cstlnpl3930fo24p` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `FK_7cx81agxrel36s51g0ba3p3ni` FOREIGN KEY (`permissionType_id`) REFERENCES `tb_userpermission_type` (`id`);

--
-- Constraints for table `tb_post`
--
ALTER TABLE `tb_post`
  ADD CONSTRAINT `FK_rfjy9c5u870h7l86ivs8edhto` FOREIGN KEY (`owner_id`) REFERENCES `tb_user` (`id`);

--
-- Constraints for table `tb_post_attach`
--
ALTER TABLE `tb_post_attach`
  ADD CONSTRAINT `FK_ceit8tuwpebtxgdrowcqj5w57` FOREIGN KEY (`post_id`) REFERENCES `tb_post` (`id`);

--
-- Constraints for table `tb_post_choice`
--
ALTER TABLE `tb_post_choice`
  ADD CONSTRAINT `FK_6hf4hsv283ao41ebwbbsn1fke` FOREIGN KEY (`post_id`) REFERENCES `tb_post` (`id`);

--
-- Constraints for table `tb_post_choice_vote`
--
ALTER TABLE `tb_post_choice_vote`
  ADD CONSTRAINT `FK_6s14y9ufutuae3oobm0ic2sbu` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `FK_eb13dmufauwxxhaer1pfwkdkq` FOREIGN KEY (`choice_id`) REFERENCES `tb_post_choice` (`id`);

--
-- Constraints for table `tb_post_comment`
--
ALTER TABLE `tb_post_comment`
  ADD CONSTRAINT `FK_lq4agysnm1grisywfyo527ivy` FOREIGN KEY (`post_id`) REFERENCES `tb_post` (`id`),
  ADD CONSTRAINT `FK_o71k6cjqj19ydumtwt1unqyds` FOREIGN KEY (`owner_id`) REFERENCES `tb_user` (`id`);

--
-- Constraints for table `tb_post_see_user`
--
ALTER TABLE `tb_post_see_user`
  ADD CONSTRAINT `FK_5g6rex2wqk9nvmjafd17yejkf` FOREIGN KEY (`post_id`) REFERENCES `tb_post` (`id`),
  ADD CONSTRAINT `FK_fjs8c3jkot13vo3cp138g107w` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`);

--
-- Constraints for table `tb_product_ingredient_rel`
--
ALTER TABLE `tb_product_ingredient_rel`
  ADD CONSTRAINT `FK_dnwrab25pvhhi3kptdclcwl3i` FOREIGN KEY (`ingredient_id`) REFERENCES `tb_ingredient` (`id`),
  ADD CONSTRAINT `FK_g5gq7ok2ri6fr5pq1evvsjkas` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`id`);

--
-- Constraints for table `tb_stock`
--
ALTER TABLE `tb_stock`
  ADD CONSTRAINT `FK_nmqogn6nrn9rt5slbd31xch96` FOREIGN KEY (`respondent_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `FK_tbooinoic8uxbmq52fd2o61ir` FOREIGN KEY (`respondentTeam_id`) REFERENCES `tb_userteam` (`id`);

--
-- Constraints for table `tb_stock_file`
--
ALTER TABLE `tb_stock_file`
  ADD CONSTRAINT `FK_aketdt4bnsu7mn4vmra2j31jo` FOREIGN KEY (`stock_id`) REFERENCES `tb_stock` (`id`);

--
-- Constraints for table `tb_user`
--
ALTER TABLE `tb_user`
  ADD CONSTRAINT `FK_7au85bh0x85ehds73tfqs1wb9` FOREIGN KEY (`userPosition_id`) REFERENCES `tb_userposition` (`id`),
  ADD CONSTRAINT `FK_qecwqv3i8603fxtnkbtj02fqn` FOREIGN KEY (`userTeam_id`) REFERENCES `tb_userteam` (`id`);

--
-- Constraints for table `tb_userpermission_type`
--
ALTER TABLE `tb_userpermission_type`
  ADD CONSTRAINT `FK_8jjbo4yithismvvx8p0t8ps9b` FOREIGN KEY (`permission_id`) REFERENCES `tb_userpermission` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
