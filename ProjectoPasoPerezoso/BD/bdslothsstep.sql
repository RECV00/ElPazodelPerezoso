-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-04-2025 a las 22:58:45
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdslothsstep`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_cabin`
--

CREATE TABLE `tb_cabin` (
  `cabinID` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `capacity` int(11) NOT NULL,
  `pricePerNight` double NOT NULL,
  `location` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `includedServices` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_cabin`
--

INSERT INTO `tb_cabin` (`cabinID`, `name`, `description`, `capacity`, `pricePerNight`, `location`, `image`, `includedServices`) VALUES
(1, 'Cabaña las luciérnagasa', 'Hermosa cabaña en medio del bosque con  vista al volcán turrialba.', 30, 120.5, 'Limón,Pococí,Cariari,CostaRica', 'img/imagen1.jpg', 'Cocina equipada,toldos,parqueo gratis'),
(2, 'Cabaña rústico tortuguero', 'Cabaña acogedora rodeada de montaña y vista al mar', 5, 150.75, 'Limón,Pococí,Tortugero,CostaRica', 'img/imagen2.jpg', 'Cocina equipada, abanicos, ropa cama,WiFi'),
(5, 'Cabaña en el bosque', 'Una acogedora cabaña rodeada de árboles y naturaleza, ideal para relajarse.', 4, 50, 'Limón,Pococí,Cariari,CostaRica', 'img/787a_imagen4.jpg', 'Desayuno incluido, Wi-Fi gratis, Estacionamiento privado'),
(6, 'Cabaña Familiar', 'Amplia cabaña con espacio para toda la familia, cerca de atracciones turísticas.', 8, 200, 'Guanacaste,Santa Cruz,CostaRica', 'img/934a_imagen6.jpg', 'Parque infantil, Cocina totalmente equipada, Servicio de limpieza diario'),
(7, 'Cabaña de Lujo', 'Cabaña de lujo con todas las comodidades modernas, piscina privada y spa.', 5, 300, 'Guanacaste,Santa Cruz,CostaRica', 'img/7aaa_imagen5.jpg', 'Piscina privada, Spa, Servicio de conserjería 24/7');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_tour`
--

CREATE TABLE `tb_tour` (
  `id_Tour` int(11) NOT NULL,
  `nameTour` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `price` double(10,2) NOT NULL,
  `date` date NOT NULL,
  `startTime` time NOT NULL,
  `duration` time NOT NULL,
  `startingPoint` varchar(255) NOT NULL,
  `multimedia` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_tour`
--

INSERT INTO `tb_tour` (`id_Tour`, `nameTour`, `description`, `price`, `date`, `startTime`, `duration`, `startingPoint`, `multimedia`) VALUES
(13, 'Tour de la Granja', 'Recorridos guiados que permiten conocer la vida en la granja, interactuar con animales y aprender sobre la producción sostenible.', 25.00, '2025-03-27', '03:00:00', '03:00:00', 'Entrada Principal de la Granja', 'img/118f_granja.jpg'),
(14, 'Cultivo de Caña de Azúcar', 'Experiencia educativa sobre el cultivo, cosecha y procesamiento de la caña de azúcar.', 30.00, '2025-03-30', '02:30:00', '02:30:00', 'Campo de Caña de Azúcar', 'img/3c04_trapiche.jpg'),
(15, 'Recolección y Procesamiento del Cacao', 'Los visitantes podrán ser parte de la producción del cacao desde la cosecha hasta la creación de chocolate.', 35.00, '2025-03-25', '04:00:00', '04:00:00', 'Plantación de Cacao', 'img/54c5_cacao.jpg'),
(16, 'Elaboración de Jalea de Guayaba', 'Los turistas podrán presenciar y formar parte del proceso de elaboración artesanal de la jalea de guayaba.', 20.00, '2025-03-31', '02:00:00', '02:00:00', 'Cocina Artesanal', 'img/3412_jaleaguayaba.jpg'),
(17, 'Senderismo Nocturno', 'Excursiones guiadas para descubrir la biodiversidad nocturna en los alrededores de las cabañas.', 15.00, '2025-03-31', '02:00:00', '02:00:00', 'Cabañas del Bosque', 'img/1dc0_senderismo.jpg'),
(18, 'Tour Gastronómico', 'Cocina interactiva con chefs locales, preparando platillos típicos con ingredientes frescos y tradicionales.', 40.00, '2025-03-22', '03:30:00', '03:30:00', 'Cocina Central', 'img/4b6a_gastronomia.jpg'),
(19, 'Vacas', 'Ordeño', 6754.00, '2025-03-29', '02:22:00', '03:24:00', 'Corral', 'img/a0bf_vaca perfil.jpeg');

--
-- Estructura de tabla para la tabla `tb_article`
--

CREATE TABLE `tb_article` (
  `id_article` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `product_quantity` int(11) NOT NULL,
  `unit_of_measurement` varchar(50) NOT NULL,
  `expiration_date` date DEFAULT NULL,
  `supplier` varchar(255) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_article`
--

INSERT INTO `tb_article` (`id_article`, `product_name`, `description`, `product_quantity`, `unit_of_measurement`, `expiration_date`, `supplier`, `unit_price`) VALUES
(2001, 'Leña seca', 'Paquete de leña de roble, 10 kg', 15, 'paquetes', NULL, 'Maderera del Norte', 12500.00),
(2002, 'Gas propano', 'Tanque de gas 20 lb', 8, 'unidades', NULL, 'GasExpress', 18500.00),
(2003, 'Linterna recargable', 'Linterna LED 1000 lúmenes', 5, 'unidades', NULL, 'TodoCamping', 22400.00),
(2004, 'Botiquín primeros auxilios', 'Kit básico con vendas y medicamentos', 3, 'unidades', '2025-12-31', 'Farmacia Rural', 18750.00),
(2005, 'Saco de dormir -5°C', 'Saco térmico para clima frío', 4, 'unidades', NULL, 'Aventura Outdoor', 45600.00),
(2006, 'Agua embotellada', 'Botella 1 litro, paquete de 12', 10, 'paquetes', '2024-06-30', 'Agua Pura', 5600.00),
(2007, 'Repelente de insectos', 'Spray 200 ml', 12, 'unidades', '2024-08-15', 'Farmacia Rural', 3250.00),
(2008, 'Carbón para asar', 'Bolsa 5 kg', 7, 'bolsas', NULL, 'SuperSuministros', 8500.00),
(2009, 'Cuerda resistente', 'Cuerda nylon 10m x 8mm', 6, 'unidades', NULL, 'TodoCamping', 11200.00),
(2010, 'Baterías AA', 'Paquete de 4 unidades', 20, 'paquetes', '2026-03-31', 'ElectroTodo', 4900.00),
(2011, 'Cerradura para cabaña', 'Juego de cerradura con 2 llaves', 3, 'juegos', NULL, 'Seguridad Total', 18700.00),
(2012, 'Manta térmica', 'Manta de emergencia 2x1.5m', 8, 'unidades', NULL, 'Aventura Outdoor', 8500.00),
(2013, 'Kit de herramientas básico', 'Incluye martillo, destornilladores y alicate', 2, 'kits', NULL, 'Ferretería del Campo', 32400.00),
(2014, 'Hielo en bolsa', 'Bolsa 5 kg', 5, 'bolsas', NULL, 'SuperSuministros', 4500.00),
(2015, 'Extintor ABC', 'Extintor 2.5 kg', 3, 'unidades', '2025-09-30', 'Seguridad Total', 38500.00);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tb_cabin`
--
ALTER TABLE `tb_cabin`
  ADD PRIMARY KEY (`cabinID`);

--
-- Indices de la tabla `tb_tour`
--
ALTER TABLE `tb_tour`
  ADD PRIMARY KEY (`id_Tour`);

--
-- Indices de la tabla `tb_article`
--
ALTER TABLE `tb_article`
  ADD PRIMARY KEY (`id_article`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tb_cabin`
--
ALTER TABLE `tb_cabin`
  MODIFY `cabinID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tb_tour`
--
ALTER TABLE `tb_tour`
  MODIFY `id_Tour` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

--
-- AUTO_INCREMENT de la tabla `tb_article`
--
ALTER TABLE `tb_article`
  MODIFY `id_article` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2016;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
