-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Mer 19 Juin 2019 à 23:13
-- Version du serveur :  5.7.26-0ubuntu0.16.04.1
-- Version de PHP :  7.0.33-8+ubuntu16.04.1+deb.sury.org+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `esp8266`
--

-- --------------------------------------------------------

--
-- Structure de la table `adress_ip`
--

CREATE TABLE `adress_ip` (
  `id` int(11) NOT NULL,
  `ip` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `adress_ip`
--

INSERT INTO `adress_ip` (`id`, `ip`) VALUES
(1, '192.168.43.169');

-- --------------------------------------------------------

--
-- Structure de la table `data`
--

CREATE TABLE `data` (
  `led` varchar(8) NOT NULL,
  `led1` varchar(8) NOT NULL,
  `door` varchar(8) NOT NULL,
  `door1` varchar(8) NOT NULL,
  `person` int(11) NOT NULL,
  `temp` int(11) NOT NULL,
  `hum` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `data`
--

INSERT INTO `data` (`led`, `led1`, `door`, `door1`, `person`, `temp`, `hum`) VALUES
('LOW', 'LOW', 'open', 'close', 10, 28, 33);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `adress_ip`
--
ALTER TABLE `adress_ip`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `adress_ip`
--
ALTER TABLE `adress_ip`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
