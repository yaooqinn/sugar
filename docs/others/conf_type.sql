-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: 2018-05-29 02:40:18
-- 服务器版本： 5.7.18
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sparkconf`
--

-- --------------------------------------------------------

--
-- 表的结构 `conf_type`
--

CREATE TABLE `conf_type` (
  `type_id` int(11) NOT NULL COMMENT 'type序号',
  `type_name` text CHARACTER SET ascii COLLATE ascii_bin COMMENT 'type名称'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `conf_type`
--

INSERT INTO `conf_type` (`type_id`, `type_name`) VALUES
(0, '<unknown>'),
(1, 'common'),
(2, 'runtime environment'),
(3, 'shuffle behavior'),
(4, 'ui'),
(5, 'serialization'),
(6, 'memory'),
(7, 'execution behavior'),
(8, 'networking'),
(9, 'scheduling'),
(10, 'dynamic allocation'),
(11, 'security'),
(12, 'TLS / SSL'),
(13, 'sql'),
(14, 'streaming'),
(15, 'r'),
(16, 'graphx'),
(17, 'deploy'),
(18, 'yarn'),
(19, 'mesos'),
(20, 'k8s'),
(21, 'standalone'),
(22, 'logging'),
(23, 'hadoop'),
(24, 'hive'),
(25, 'env');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `conf_type`
--
ALTER TABLE `conf_type`
  ADD PRIMARY KEY (`type_id`) USING BTREE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
