-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 16 mars 2023 à 08:52
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bddgraph`
--

-- --------------------------------------------------------

--
-- Structure de la table `bac`
--

CREATE TABLE `bac` (
  `idBac` int(11) NOT NULL,
  `libelle` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `bac`
--

INSERT INTO `bac` (`idBac`, `libelle`) VALUES
(1, 'Bac1'),
(2, 'Bac2'),
(3, 'Bac3'),
(4, 'Bac4');

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

CREATE TABLE `etudiant` (
  `idEtud` int(11) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `prenom` varchar(20) NOT NULL,
  `dateNaiss` date NOT NULL,
  `LieuNaiss` varchar(20) NOT NULL,
  `sexe` varchar(10) NOT NULL,
  `nationalite` varchar(25) NOT NULL,
  `rue` varchar(25) NOT NULL,
  `cp` int(11) NOT NULL,
  `ville` varchar(25) NOT NULL,
  `telephone` varchar(25) NOT NULL,
  `mail` varchar(25) NOT NULL,
  `niveau` varchar(10) NOT NULL,
  `idFil` int(11) NOT NULL,
  `idBac` int(11) NOT NULL,
  `loisir` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`idEtud`, `nom`, `prenom`, `dateNaiss`, `LieuNaiss`, `sexe`, `nationalite`, `rue`, `cp`, `ville`, `telephone`, `mail`, `niveau`, `idFil`, `idBac`, `loisir`) VALUES
(1, 'Michel', 'Augusti', '2000-12-21', 'Pairs', 'Homme', 'Francais', 'Rue des coco', 75013, 'Paris', '0661511', 'gauthier@gmail.com', 'Confirmé', 2, 4, ' Sports Voyages Musiques'),
(2, 'eddede', 'dedede', '2000-12-17', 'dede', 'Femme', 'dedede', 'dededed', 75013, 'dededede', 'ddedededddd', 'd084151512', 'Débutant', 1, 1, ' Voyages Musiques');

-- --------------------------------------------------------

--
-- Structure de la table `filiere`
--

CREATE TABLE `filiere` (
  `idFil` int(11) NOT NULL,
  `nom` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `filiere`
--

INSERT INTO `filiere` (`idFil`, `nom`) VALUES
(1, 'Fil1'),
(2, 'Fil2');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `mail` varchar(35) NOT NULL,
  `login` varchar(20) NOT NULL,
  `password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `mail`, `login`, `password`) VALUES
(1, 'test@test.com', 'test', 'test');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bac`
--
ALTER TABLE `bac`
  ADD PRIMARY KEY (`idBac`);

--
-- Index pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`idEtud`),
  ADD KEY `idFil` (`idFil`);

--
-- Index pour la table `filiere`
--
ALTER TABLE `filiere`
  ADD PRIMARY KEY (`idFil`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `bac`
--
ALTER TABLE `bac`
  MODIFY `idBac` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `etudiant`
--
ALTER TABLE `etudiant`
  MODIFY `idEtud` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `filiere`
--
ALTER TABLE `filiere`
  MODIFY `idFil` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD CONSTRAINT `etudiant_ibfk_1` FOREIGN KEY (`idFil`) REFERENCES `filiere` (`idFil`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
