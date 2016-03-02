-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-03-2016 a las 13:44:12
-- Versión del servidor: 5.6.26
-- Versión de PHP: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdkeep`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `keep`
--

CREATE TABLE IF NOT EXISTS `keep` (
  `id` int(11) NOT NULL,
  `login` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `idAndroid` int(11) DEFAULT NULL,
  `contenido` text COLLATE utf8_unicode_ci,
  `ruta` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `estado` enum('borrado','estable','inestable') COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=561 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `keep`
--

INSERT INTO `keep` (`id`, `login`, `idAndroid`, `contenido`, `ruta`, `estado`) VALUES
(1, 'pepe', NULL, 'Nota 1', NULL, 'inestable'),
(556, 'invitado', -1364517375, 'Hhg----hfj--dj--dj--', 'no', 'estable'),
(558, 'invitado', -1273099120, 'kuidhbalwdbja', 'no', 'estable'),
(559, 'invitado', 1228944311, 'kuidh balw dbja', 'no', 'estable'),
(560, 'invitado', 1178048461, 'Juguemos--kdoekf', 'no', 'estable');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `login` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `pass` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`login`, `pass`) VALUES
('bernardo', 'bernardo'),
('invitado', 'invitado'),
('koko', 'koko'),
('pepe', 'pepe');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `keep`
--
ALTER TABLE `keep`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`,`idAndroid`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`login`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `keep`
--
ALTER TABLE `keep`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=561;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `keep`
--
ALTER TABLE `keep`
  ADD CONSTRAINT `keep_ibfk_1` FOREIGN KEY (`login`) REFERENCES `usuario` (`login`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
