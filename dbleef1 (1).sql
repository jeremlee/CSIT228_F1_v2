-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 13, 2024 at 07:10 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbleef1`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblpost`
--

CREATE TABLE `tblpost` (
  `post_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `time_created` datetime(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tblpost`
--

INSERT INTO `tblpost` (`post_id`, `title`, `content`, `time_created`) VALUES
(1, 'minecraft stream', 'building castle #69', '2024-04-14 00:34:26.623508'),
(3, 'mobile legends', 'lose streak again and again', '2024-04-14 00:44:21.228219'),
(4, 'ETS 2', 'crazy sqrl driving', '2024-04-14 01:01:44.620593');

-- --------------------------------------------------------

--
-- Table structure for table `tbluser`
--

CREATE TABLE `tbluser` (
  `uid` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `accessLevel` int(255) NOT NULL,
  `time_created` datetime(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbluser`
--

INSERT INTO `tbluser` (`uid`, `username`, `firstname`, `lastname`, `password`, `email`, `accessLevel`, `time_created`) VALUES
(1, 'kobo', 'kobo', 'kanaeru', 'kanaeru', 'kobo@gmail.com', 0, '2024-04-14 00:09:52.109403'),
(3, 'K69', 'kaela', 'kovalskia', 'lemao', 'kaela@gmail.com', 0, '2024-04-14 00:12:33.807603'),
(4, 'risu', 'risu', 'ayunda', 'tupai', 'risu@gmail.com', 0, '2024-04-14 01:01:25.908250');

-- --------------------------------------------------------

--
-- Table structure for table `tbluserpost`
--

CREATE TABLE `tbluserpost` (
  `userpost_id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `post_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbluserpost`
--

INSERT INTO `tbluserpost` (`userpost_id`, `uid`, `post_id`) VALUES
(1, 3, 1),
(3, 1, 3),
(4, 4, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblpost`
--
ALTER TABLE `tblpost`
  ADD PRIMARY KEY (`post_id`);

--
-- Indexes for table `tbluser`
--
ALTER TABLE `tbluser`
  ADD PRIMARY KEY (`uid`);

--
-- Indexes for table `tbluserpost`
--
ALTER TABLE `tbluserpost`
  ADD PRIMARY KEY (`userpost_id`),
  ADD KEY `userFK` (`uid`),
  ADD KEY `postFK` (`post_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblpost`
--
ALTER TABLE `tblpost`
  MODIFY `post_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbluser`
--
ALTER TABLE `tbluser`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbluserpost`
--
ALTER TABLE `tbluserpost`
  MODIFY `userpost_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbluserpost`
--
ALTER TABLE `tbluserpost`
  ADD CONSTRAINT `postFK` FOREIGN KEY (`post_id`) REFERENCES `tblpost` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `userFK` FOREIGN KEY (`uid`) REFERENCES `tbluser` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
