-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 15, 2023 at 11:46 AM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 8.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rizky12rplapemesananhotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `fasilitas_kamar`
--

CREATE TABLE `fasilitas_kamar` (
  `id` varchar(10) NOT NULL,
  `tipe_kamar` varchar(255) NOT NULL,
  `harga` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fasilitas_kamar`
--

INSERT INTO `fasilitas_kamar` (`id`, `tipe_kamar`, `harga`) VALUES
('FK1', 'Standard', '200000'),
('FK2', 'Deluxe', '600000'),
('FK3', 'Superior gold', '300000'),
('FK4', 'Superior', '300000'),
('FK5', 'Golden VIP', '500000');

-- --------------------------------------------------------

--
-- Table structure for table `fasilitas_umum`
--

CREATE TABLE `fasilitas_umum` (
  `id_fasilitas` varchar(10) NOT NULL,
  `nama_fasilitas` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fasilitas_umum`
--

INSERT INTO `fasilitas_umum` (`id_fasilitas`, `nama_fasilitas`) VALUES
('FU3', 'Kolam Renang'),
('FU1', 'Restoran Biasa'),
('FU2', 'Free WIFI');

-- --------------------------------------------------------

--
-- Table structure for table `pemesanan`
--

CREATE TABLE `pemesanan` (
  `id` varchar(20) NOT NULL,
  `nama_pemesan` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `no_hp` varchar(255) NOT NULL,
  `tipe_kamar` varchar(255) NOT NULL,
  `jumlah_kamar` varchar(6) NOT NULL,
  `tgl_checkin` varchar(255) NOT NULL,
  `tgl_checkout` varchar(255) NOT NULL,
  `harga` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pemesanan`
--

INSERT INTO `pemesanan` (`id`, `nama_pemesan`, `email`, `no_hp`, `tipe_kamar`, `jumlah_kamar`, `tgl_checkin`, `tgl_checkout`, `harga`) VALUES
('00010022023095938', 'adi', 'adi@gmail.com', '0891112223333', 'Deluxe', '1', '08/02/2023', '10/02/2023', '1000000'),
('00010022023100003', 'adi', 'adi@gmail.com', '0891112223333', 'Standard', '3', '08/02/2023', '10/02/2023', '1200000'),
('00010022023100019', 'adi', 'adi@gmail.com', '0891112223333', 'Superior', '2', '08/02/2023', '10/02/2023', '1400000'),
('00010022023100055', 'firman', 'firmani@gmail.com', '0891112223388', 'Deluxe', '1', '09/02/2023', '10/02/2023', '500000'),
('00010022023100136', 'sahrul ramadani', 'sahrul.ramadani@gmail.com', '0891112223366', 'Superior', '1', '06/02/2023', '10/02/2023', '1400000'),
('00011022023035455', 'ridwan', 'ridwan@gmail.com', '08300012123', 'Standard', '2', '09/02/2023', '11/02/2023', '800000'),
('00011022023090704', 'jefri', 'jefri@gmail.com', '08925550012', 'VIP SUPER ', '2', '09/02/2023', '10/02/2023', '1200000'),
('00011022023140152', 'almustofa', 'al@gmail.com', '089334121433', 'Superior', '2', '07/02/2023', '08/02/2023', '600000'),
('00011022023174508', 'adiyan', 'adiyan@gmail.com', '089324322332', 'Superior', '3', '09/02/2023', '10/02/2023', '900000'),
('00011022023214010', 'yosi', 'yosi@gmail.com', '0893242342332', 'Standard', '2', '02/02/2023', '03/02/2023', '400000'),
('00011022023223032', 'himas', 'himas@gmail.com', '08988801012', 'Standard', '2', '10/02/2023', '11/02/2023', '400000'),
('00012022023081503', 'agus', 'agus@gmail.com', '0895550001111', 'GOLDEN', '2', '08/02/2023', '09/02/2023', '700000'),
('00012022023154931', 'yuada', 'yuda@email.com', '08293434123123', 'Superior gold', '1', '08/02/2023', '09/02/2023', '300000'),
('00012022023161421', 'adani', 'adani@gmail.com', '089231231231', 'THUNDER', '2', '08/02/2023', '09/02/2023', '620000'),
('00013022023152423', 'adit', 'adit@gmail.com', '089000448428', 'Superior gold', '1', '07/02/2023', '10/02/2023', '900000'),
('00015022023055034', 'adudu', 'adu@gmail.com', '08934341412312', 'THUNDER', '2', '02/02/2023', '03/02/2023', '620000'),
('00015022023165042', 'surya susena', 'suryasusena@gmail.com', '089771231122', 'THUNDER', '2', '08/02/2023', '09/02/2023', '620000'),
('00015022023171756', 'dafatamu', 'dafatamu@gmail.com', '08977713123', 'Standard', '2', '08/02/2023', '09/02/2023', '400000');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(10) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `level` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `level`) VALUES
(1, 'admin', 'admin', 'Administrator'),
(2, 'tamu', 'tamu', 'Tamu'),
(3, 'resepsionis', 'resepsionis', 'Resepsionis'),
(4, 'ridwn', 'ridwan', 'Administrator'),
(5, 'ridwan', 'ridwan', 'Administrator'),
(6, 'sahruladmin', 'sahruladmin', 'Administrator'),
(7, 'dafatamu', 'dafatamu', 'Tamu'),
(8, 'adit', 'adit', 'Tamu'),
(9, 'dani', 'dani', 'Resepsionis'),
(10, 'asri', 'asri', 'Resepsionis'),
(11, 'stella', 'stella', 'Tamu'),
(12, 'rendi', 'rendi', 'Tamu'),
(13, 'upin', 'upin', 'Resepsionis'),
(14, 'ipin', 'ipin', 'Tamu'),
(15, 'ros', 'ros', 'Tamu'),
(16, 'surya', 'surya', 'Tamu'),
(17, 'dafaresepsionis', 'dafaresepsionis', 'Resepsionis'),
(18, 'rizkyadmin', 'rizkyadmin', 'Administrator');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `fasilitas_kamar`
--
ALTER TABLE `fasilitas_kamar`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
