-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Ven 09 Janvier 2015 à 20:44
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `if26`
--

-- --------------------------------------------------------

--
-- Structure de la table `contact`
--

CREATE TABLE IF NOT EXISTS `contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `initiator` int(11) NOT NULL,
  `contact` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Cont` (`initiator`,`contact`),
  KEY `CONTACT_USER` (`contact`),
  KEY `INITIATOR_USER` (`initiator`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=29 ;

-- --------------------------------------------------------

--
-- Structure de la table `friend_request`
--

CREATE TABLE IF NOT EXISTS `friend_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asker` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `asker` (`asker`),
  KEY `receiver` (`receiver`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=12 ;

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact` int(11) NOT NULL,
  `message` text COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime NOT NULL,
  `user` int(11) NOT NULL,
  `hasBeenRead` tinyint(1) NOT NULL,
  `hasBeenReceived` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `MESSAGE_INIATOR` (`user`),
  KEY `MESSAGE_RECEIVER` (`contact`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `message` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `MESSAGE` (`message`),
  KEY `USER` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pseudo` text COLLATE utf8_unicode_ci NOT NULL,
  `email` text COLLATE utf8_unicode_ci NOT NULL,
  `password` text COLLATE utf8_unicode_ci NOT NULL,
  `token` text COLLATE utf8_unicode_ci NOT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `password` (`password`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=29 ;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `contact`
--
ALTER TABLE `contact`
  ADD CONSTRAINT `contact_ibfk_1` FOREIGN KEY (`initiator`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `contact_ibfk_2` FOREIGN KEY (`contact`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `friend_request`
--
ALTER TABLE `friend_request`
  ADD CONSTRAINT `friend_request_ibfk_1` FOREIGN KEY (`asker`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `friend_request_ibfk_2` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `message_ibfk_3` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `notification_ibfk_3` FOREIGN KEY (`message`) REFERENCES `message` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
