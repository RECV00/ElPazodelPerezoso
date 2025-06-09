-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-06-2025 a las 06:31:11
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

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
-- Estructura de tabla para la tabla `tb_admin`
--

CREATE TABLE `tb_admin` (
  `id_admin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_admin`
--

INSERT INTO `tb_admin` (`id_admin`) VALUES
(3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_article`
--

CREATE TABLE `tb_article` (
  `id_article` int(11) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `expiration_date` date DEFAULT NULL,
  `product_name` varchar(100) NOT NULL,
  `product_quantity` int(11) NOT NULL,
  `supplier` varchar(100) DEFAULT NULL,
  `unit_of_measurement` varchar(50) DEFAULT NULL,
  `unit_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_article`
--

INSERT INTO `tb_article` (`id_article`, `description`, `expiration_date`, `product_name`, `product_quantity`, `supplier`, `unit_of_measurement`, `unit_price`) VALUES
(2001, 'Paquete de leña de roble, 10 kg', NULL, 'Leña seca', 15, 'Maderera del Norte', 'paquetes', 12500),
(2002, 'Tanque de gas 20 lb', NULL, 'Gas propano', 8, 'GasExpress', 'unidades', 18500),
(2003, 'Linterna LED 1000 lúmenes', NULL, 'Linterna recargable', 5, 'TodoCamping', 'unidades', 22400),
(2004, 'Kit básico con vendas y medicamentos', '2025-12-31', 'Botiquín primeros auxilios', 3, 'Farmacia Rural', 'unidades', 18750),
(2005, 'Saco térmico para clima frío', NULL, 'Saco de dormir -5°C', 4, 'Aventura Outdoor', 'unidades', 45600),
(2006, 'Botella 1 litro, paquete de 12', '2024-06-30', 'Agua embotellada', 10, 'Agua Pura', 'paquetes', 5600),
(2007, 'Spray 200 ml', '2024-08-15', 'Repelente de insectos', 12, 'Farmacia Rural', 'unidades', 3250),
(2008, 'Bolsa 5 kg', NULL, 'Carbón para asar', 7, 'SuperSuministros', 'bolsas', 8500),
(2009, 'Cuerda nylon 10m x 8mm', NULL, 'Cuerda resistente', 6, 'TodoCamping', 'unidades', 11200),
(2010, 'Paquete de 4 unidades', '2026-03-31', 'Baterías AA', 20, 'ElectroTodo', 'paquetes', 4900),
(2011, 'Juego de cerradura con 2 llaves', NULL, 'Cerradura para cabaña', 3, 'Seguridad Total', 'juegos', 18700),
(2012, 'Manta de emergencia 2x1.5m', NULL, 'Manta térmica', 8, 'Aventura Outdoor', 'unidades', 8500),
(2013, 'Incluye martillo, destornilladores y alicate', NULL, 'Kit de herramientas básico', 2, 'Ferretería del Campo', 'kits', 32400),
(2015, 'Extintor 2.5 kg', '2025-09-30', 'Extintor ABC', 3, 'Seguridad Total', 'unidades', 38500),
(2016, 'Paquete de leña de roble, 10 kg', NULL, 'Leña seca', 15, 'Maderera del Norte', 'paquetes', 12500),
(2017, 'Tanque de gas 20 lb', NULL, 'Gas propano', 8, 'GasExpress', 'unidades', 18500),
(2018, 'Linterna LED 1000 lúmenes', NULL, 'Linterna recargable', 5, 'TodoCamping', 'unidades', 22400),
(2019, 'Kit básico con vendas y medicamentos', '2025-12-31', 'Botiquín primeros auxilios', 3, 'Farmacia Rural', 'unidades', 18750),
(2020, 'Saco térmico para clima frío', NULL, 'Saco de dormir -5°C', 4, 'Aventura Outdoor', 'unidades', 45600),
(2021, 'Botella 1 litro, paquete de 12', '2024-06-30', 'Agua embotellada', 10, 'Agua Pura', 'paquetes', 5600),
(2022, 'Spray 200 ml', '2024-08-15', 'Repelente de insectos', 12, 'Farmacia Rural', 'unidades', 3250),
(2023, 'Bolsa 5 kg', NULL, 'Carbón para asar', 7, 'SuperSuministros', 'bolsas', 8500),
(2024, 'Cuerda nylon 10m x 8mm', NULL, 'Cuerda resistente', 6, 'TodoCamping', 'unidades', 11200),
(2025, 'Paquete de 4 unidades', '2026-03-31', 'Baterías AA', 20, 'ElectroTodo', 'paquetes', 4900),
(2026, 'Juego de cerradura con 2 llaves', NULL, 'Cerradura para cabaña', 3, 'Seguridad Total', 'juegos', 18700),
(2027, 'Manta de emergencia 2x1.5m', NULL, 'Manta térmica', 8, 'Aventura Outdoor', 'unidades', 8500),
(2028, 'Incluye martillo, destornilladores y alicate', NULL, 'Kit de herramientas básico', 2, 'Ferretería del Campo', 'kits', 32400),
(2029, 'Extintor 2.5 kg', '2025-09-30', 'Extintor ABC', 3, 'Seguridad Total', 'unidades', 38500);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_booking`
--

CREATE TABLE `tb_booking` (
  `id_booking` int(11) NOT NULL,
  `additional_services` bit(1) NOT NULL,
  `booking_type` varchar(255) NOT NULL,
  `check_in_date` date NOT NULL,
  `check_out_date` date NOT NULL,
  `number_guest` int(11) NOT NULL,
  `promotion_code` varchar(20) DEFAULT NULL,
  `reserve_status` varchar(20) NOT NULL,
  `special_requirements` varchar(500) DEFAULT NULL,
  `id_cabin` int(11) DEFAULT NULL,
  `id_client` int(11) NOT NULL,
  `id_dishe` int(11) DEFAULT NULL,
  `id_payment` int(11) DEFAULT NULL,
  `id_tour` int(11) DEFAULT NULL,
  `id_vehicle` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_booking`
--

INSERT INTO `tb_booking` (`id_booking`, `additional_services`, `booking_type`, `check_in_date`, `check_out_date`, `number_guest`, `promotion_code`, `reserve_status`, `special_requirements`, `id_cabin`, `id_client`, `id_dishe`, `id_payment`, `id_tour`, `id_vehicle`) VALUES
(10, b'1', 'Hospedaje', '2025-06-11', '2025-06-20', 12, 'GDHD65', 'Confirmada', 'Piscina', 1, 1, 4, 10, 13, 3),
(11, b'1', 'Hospedaje', '2025-06-14', '2025-06-30', 20, '', 'Pendiente', 'Piscina, Abanicos', 14, 1, 12, 12, 23, 3),
(12, b'1', 'Hospedaje', '2025-06-10', '2025-06-26', 15, 'FGHJK567', 'cancelada', '', 14, 2, NULL, 14, 18, NULL),
(13, b'0', 'Alquiler Temporal', '2025-06-11', '2025-07-31', 5, '', 'confirmada', '', 7, 2, NULL, 9, NULL, NULL),
(14, b'1', 'Alquiler Temporal', '2025-07-30', '2025-08-15', 11, 'DFGHJK45', 'confirmada', 'Piscina', 1, 1, 4, 15, 14, 2),
(15, b'1', 'Alquiler Temporal', '2025-06-11', '2025-06-18', 8, 'GHJK678', 'confirmada', 'Piscina', 1, 2, 2, 16, 13, 6),
(16, b'1', 'Hospedaje', '2025-06-21', '2025-07-01', 7, 'FGHJ678', 'cancelada', 'Abanicos', 11, 6, 2, 17, 18, 3),
(17, b'1', 'Hospedaje', '2025-06-13', '2025-06-26', 4, 'FGHJK5678', 'confirmada', '', 1, 2, 2, 18, 14, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_cabin`
--

CREATE TABLE `tb_cabin` (
  `cabinid` int(11) NOT NULL,
  `capacity` int(11) NOT NULL,
  `description` varchar(500) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `included_services` varchar(500) DEFAULT NULL,
  `location` varchar(200) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price_per_night` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_cabin`
--

INSERT INTO `tb_cabin` (`cabinid`, `capacity`, `description`, `image`, `included_services`, `location`, `name`, `price_per_night`) VALUES
(1, 26, 'Hermosa cabaña en medio del bosque con  vista al volcán turrialba.', 'img/imagen1.jpg', 'Cocina equipada,toldos,parqueo gratis', 'Limón,Pococí,Cariari,CostaRica', 'Cabaña las luciérnagasa', 50000),
(2, 5, 'Cabaña acogedora rodeada de montaña y vista al mar', 'img/imagen2.jpg', 'Cocina equipada, abanicos, ropa cama,WiFi', 'Limón,Pococí,Tortugero,CostaRica', 'Cabaña rústico tortuguero', 15000),
(5, 4, 'Una acogedora cabaña rodeada de árboles y naturaleza, ideal para relajarse.', 'img/787a_imagen4.jpg', 'Desayuno incluido, Wi-Fi gratis, Estacionamiento privado', 'Limón,Pococí,Cariari,CostaRica', 'El bosque', 50000),
(6, 8, 'Amplia cabaña con espacio para toda la familia, cerca de atracciones turísticas.', 'img/934a_imagen6.jpg', 'Parque infantil, Cocina totalmente equipada, Servicio de limpieza diario', 'Guanacaste,Santa Cruz,CostaRica', 'Focus', 20000),
(7, 5, 'Cabaña de lujo con todas las comodidades modernas, piscina privada y spa.', 'img/7aaa_imagen5.jpg', 'Piscina privada, Spa, Servicio de conserjería 24/7', 'Guanacaste,Santa Cruz,CostaRica', 'Cabaña de Lujo', 30000),
(10, 26, 'Hermosa cabaña en medio del bosque con  vista al volcán turrialba.', 'img/imagen1.jpg', 'Cocina equipada,toldos,parqueo gratis', 'Limón,Pococí,Cariari,CostaRica', 'Cabaña las luciérnagasa', 12000),
(11, 5, 'Cabaña acogedora rodeada de montaña y vista al mar', 'img/imagen2.jpg', 'Cocina equipada, abanicos, ropa cama,WiFi', 'Limón,Pococí,Tortugero,CostaRica', 'Cabaña rústico tortuguero', 15000),
(12, 4, 'Una acogedora cabaña rodeada de árboles y naturaleza, ideal para relajarse.', 'img/787a_imagen4.jpg', 'Desayuno incluido, Wi-Fi gratis, Estacionamiento privado', 'Limón,Pococí,Cariari,CostaRica', 'El bosque', 50450),
(13, 8, 'Amplia cabaña con espacio para toda la familia, cerca de atracciones turísticas.', 'img/934a_imagen6.jpg', 'Parque infantil, Cocina totalmente equipada, Servicio de limpieza diario', 'Guanacaste,Santa Cruz,CostaRica', 'Focus', 200000),
(14, 5, 'Cabaña de lujo con todas las comodidades modernas, piscina privada y spa.', 'img/7aaa_imagen5.jpg', 'Piscina privada, Spa, Servicio de conserjería 24/7', 'Guanacaste,Santa Cruz,CostaRica', 'Cabaña de Lujo', 300000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_client`
--

CREATE TABLE `tb_client` (
  `id_client` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_client`
--

INSERT INTO `tb_client` (`id_client`) VALUES
(1),
(2),
(6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_dishe`
--

CREATE TABLE `tb_dishe` (
  `disheid` int(11) NOT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `preparation_time` time(6) DEFAULT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_dishe`
--

INSERT INTO `tb_dishe` (`disheid`, `is_available`, `category`, `description`, `image_url`, `name`, `preparation_time`, `price`) VALUES
(1, b'1', 'Pastas', 'Deliciosa pasta con salsa cremosa de huevo, queso parmesano, panceta y pimienta negra.', 'img/carbonara.jpg', 'Pasta Carbonara', '00:25:00.000000', 1100),
(2, b'1', 'Ensaladas', 'Ensalada fresca con lechuga romana, croutones, queso parmesano y aderezo césar clásico.', 'img/cesar.jpg', 'Ensalada César', '00:10:00.000000', 8500),
(3, b'1', 'Carnes', 'Jugosa hamburguesa de carne 100% res con queso, lechuga, tomate y salsa especial.', 'img/burger.jpg', 'Hamburguesa Clásica', '00:15:00.000000', 9000),
(4, b'1', 'Japonés', 'Combinación de 12 piezas de sushi incluyendo salmón, atún y camarón.', 'img/sushi.jpg', 'Sushi Variado', '00:20:00.000000', 1800),
(5, b'1', 'Mexicano', 'Tortillas de maíz con carne marinada, piña, cebolla y cilantro.', 'img/tacos.jpg', 'Tacos al Pastor', '00:12:00.000000', 7000),
(6, b'1', 'Postres', 'Postre italiano con capas de bizcocho empapado en café y crema de mascarpone.', 'img/tiramisu.jpg', 'Tiramisú', '00:00:00.000000', 6000),
(8, b'1', 'Pastas', 'Deliciosa pasta con salsa cremosa de huevo, queso parmesano, panceta y pimienta negra.', 'img/carbonara.jpg', 'Pasta Carbonara', '00:25:00.000000', 1100),
(9, b'1', 'Ensaladas', 'Ensalada fresca con lechuga romana, croutones, queso parmesano y aderezo césar clásico.', 'img/cesar.jpg', 'Ensalada César', '00:10:00.000000', 8000),
(10, b'1', 'Carnes', 'Jugosa hamburguesa de carne 100% res con queso, lechuga, tomate y salsa especial.', 'img/burger.jpg', 'Hamburguesa Clásica', '00:15:00.000000', 9500),
(11, b'1', 'Japonés', 'Combinación de 12 piezas de sushi incluyendo salmón, atún y camarón.', 'img/sushi.jpg', 'Sushi Variado', '00:20:00.000000', 18000),
(12, b'1', 'Mexicano', 'Tortillas de maíz con carne marinada, piña, cebolla y cilantro.', 'img/tacos.jpg', 'Tacos al Pastor', '00:12:00.000000', 7500),
(13, b'1', 'Postres', 'Postre italiano con capas de bizcocho empapado en café y crema de mascarpone.', 'img/tiramisu.jpg', 'Tiramisú', '00:00:00.000000', 6538);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_employee`
--

CREATE TABLE `tb_employee` (
  `account_number` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `workstation` varchar(255) DEFAULT NULL,
  `id_employee` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_employee`
--

INSERT INTO `tb_employee` (`account_number`, `salary`, `workstation`, `id_employee`) VALUES
('CR123456789', 1500, 'Recepcionista', 4),
('CR362871292378', 52000, 'Gerente', 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_maintenance`
--

CREATE TABLE `tb_maintenance` (
  `id` int(11) NOT NULL,
  `maintenance_date` date DEFAULT NULL,
  `maintenance_type` varchar(20) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `priorities` enum('alta','media','baja') DEFAULT NULL,
  `state` enum('Pendiente','En proceso','Terminada') DEFAULT NULL,
  `assigned_personal` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_maintenance`
--

INSERT INTO `tb_maintenance` (`id`, `maintenance_date`, `maintenance_type`, `description`, `priorities`, `state`, `assigned_personal`, `location`) VALUES
(8, '2025-06-07', 'Preventivo', 'Revisión del sistema contra incendios', 'alta', 'Terminada', 'Técnico', 'Pasillo central'),
(9, '2025-06-04', 'Correctivo', 'Cambio de foco en pasillo', 'baja', 'Pendiente', 'usuario', 'Pasillo lateral'),
(10, '2025-06-05', 'Preventivo', 'Inspección de tuberías de gas', 'alta', 'En proceso', 'tecnico', 'Cocina 2'),
(11, '2025-06-06', 'Correctivo', 'Reparación de cerradura dañada en oficina', 'media', 'Pendiente', 'usuario', 'Oficina de gerencia'),
(12, '2025-06-07', 'Preventivo', 'Mantenimiento de aire acondicionado', 'media', 'Terminada', 'tecnico', 'Sala de empleados'),
(13, '2025-06-08', 'Correctivo', 'Reparación de fuga de agua', 'alta', 'En proceso', 'tecnico', 'Baño 1'),
(14, '2025-06-09', 'Preventivo', 'Verificación de luces de emergencia', 'baja', 'Pendiente', 'usuario', 'Escaleras de salida'),
(15, '2025-06-10', 'Correctivo', 'Sustitución de interruptor dañado', 'media', 'Terminada', 'tecnico', 'Área de comensales');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_payment_management`
--

CREATE TABLE `tb_payment_management` (
  `id_payment` int(11) NOT NULL,
  `date_transfer` date DEFAULT NULL,
  `direction` varchar(255) DEFAULT NULL,
  `identification_fiscal` varchar(255) DEFAULT NULL,
  `method_payment` varchar(255) DEFAULT NULL,
  `name_client` varchar(255) DEFAULT NULL,
  `number_reference` varchar(255) DEFAULT NULL,
  `proof` varchar(255) DEFAULT NULL,
  `state_payment` varchar(255) DEFAULT NULL,
  `transaction_amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_payment_management`
--

INSERT INTO `tb_payment_management` (`id_payment`, `date_transfer`, `direction`, `identification_fiscal`, `method_payment`, `name_client`, `number_reference`, `proof`, `state_payment`, `transaction_amount`) VALUES
(9, '2025-05-07', 'Moravia', '325648452', 'Targeta', 'Lucia Gonzales', 'REF003', 'Laboratorio Ajax_1749153865378.pdf', 'Completado', 4500001),
(10, '2025-05-08', 'Guanacaste', '654895320', 'Efectivo', 'Gustavo Hernandez', 'REF004', NULL, 'Parcial', 754800),
(11, '2025-05-13', 'Sarapiqui, Heredia Costa Rica', '742032516', 'Transferencia Digital', 'Rodrigo Fernandez', 'REF006', NULL, 'Parcial', 145000),
(12, '2025-06-05', 'Guanacaste', '145236985', 'Transferencia Digital', 'Sofia Torres Torres', 'REF008', 'Laboratorio Ajax.pdf', 'Pendiente', 14569876),
(13, '2025-06-05', 'Moravia, San Jose', '456986325', 'Targeta', 'Karla Gutierrez Fernandez', 'REF009', 'Laboratorio -Hospital.pdf', 'Pendiente', 456321956),
(14, '2025-06-05', 'Santo Domingo de Heredia', '120356986', 'Transferencia Digital', 'Carlos Gomez Fonseca', 'REF010', 'Laboratorio Estadio WS.pdf', 'Parcial', 145623596),
(15, '2025-06-08', 'San José', '123456789', 'Transferencia', 'Juan Casanova', 'REF012', 'Proyecto parte 1 (1).pdf', 'Completado', 1976),
(16, '2025-06-08', 'Heredia', '987654321', 'Transferencia', 'María Gómez', 'REF015', 'Tarea 1 (2).pdf', 'Completado', 3352),
(17, '2025-06-08', 'San José', '304560789', 'Efectivo', 'Rosa Mora', 'REF019', 'Investigación II-Parte.pdf', 'Completado', 1556),
(18, '2025-06-08', 'Heredia', '987654321', 'Transferencia', 'María Gómez', 'FER015', 'Investigación 2.pdf', 'Parcial', 661500);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_review`
--

CREATE TABLE `tb_review` (
  `id_review` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `review_date` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `id_client` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_tour`
--

CREATE TABLE `tb_tour` (
  `id_tour` int(11) NOT NULL,
  `tour_date` date DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `duration` time(6) DEFAULT NULL,
  `multimedia` varchar(255) DEFAULT NULL,
  `name_tour` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `start_time` time(6) DEFAULT NULL,
  `starting_point` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_tour`
--

INSERT INTO `tb_tour` (`id_tour`, `tour_date`, `description`, `duration`, `multimedia`, `name_tour`, `price`, `start_time`, `starting_point`) VALUES
(13, '2025-04-08', 'Recorridos guiados que permiten conocer la vida en la granja, interactuar con animales y aprender sobre la producción sostenible.', '03:00:00.000000', 'img/118f_granja.jpg', 'Tour de la Granja', 2500, '03:00:00.000000', 'Entrada Principal de la Granja'),
(14, '2025-03-30', 'Experiencia educativa sobre el cultivo, cosecha y procesamiento de la caña de azúcar.', '02:30:00.000000', 'img/3c04_trapiche.jpg', 'Cultivo de Caña de Azúcar', 3000, '02:30:00.000000', 'Campo de Caña de Azúcar'),
(15, '2025-03-25', 'Los visitantes podrán ser parte de la producción del cacao desde la cosecha hasta la creación de chocolate.', '04:00:00.000000', 'img/54c5_cacao.jpg', 'Recolección y Procesamiento del Cacao', 3500, '04:00:00.000000', 'Plantación de Cacao'),
(16, '2025-03-31', 'Los turistas podrán presenciar y formar parte del proceso de elaboración artesanal de la jalea de guayaba.', '02:00:00.000000', 'img/3412_jaleaguayaba.jpg', 'Elaboración de Jalea de Guayaba', 2000, '02:00:00.000000', 'Cocina Artesanal'),
(17, '2025-03-31', 'Excursiones guiadas para descubrir la biodiversidad nocturna en los alrededores de las cabañas.', '02:00:00.000000', 'img/1dc0_senderismo.jpg', 'Senderismo Nocturno', 15000, '02:00:00.000000', 'Cabañas del Bosque'),
(18, '2025-03-22', 'Cocina interactiva con chefs locales, preparando platillos típicos con ingredientes frescos y tradicionales.', '03:30:00.000000', 'img/4b6a_gastronomia.jpg', 'Tour Gastronómico', 4000, '03:30:00.000000', 'Cocina Central'),
(19, '2025-03-29', 'Ordeño', '03:24:00.000000', 'img/a0bf_vaca perfil.jpeg', 'Vacas', 6754, '02:22:00.000000', 'Corral'),
(20, '2025-04-08', 'Recorridos guiados que permiten conocer la vida en la granja, interactuar con animales y aprender sobre la producción sostenible.', '03:00:00.000000', 'img/118f_granja.jpg', 'Tour de la Granja', 2500, '03:00:00.000000', 'Entrada Principal de la Granja'),
(21, '2025-03-30', 'Experiencia educativa sobre el cultivo, cosecha y procesamiento de la caña de azúcar.', '02:30:00.000000', 'img/3c04_trapiche.jpg', 'Cultivo de Caña de Azúcar', 3050, '02:30:00.000000', 'Campo de Caña de Azúcar'),
(22, '2025-03-25', 'Los visitantes podrán ser parte de la producción del cacao desde la cosecha hasta la creación de chocolate.', '04:00:00.000000', 'img/54c5_cacao.jpg', 'Recolección y Procesamiento del Cacao', 3500, '04:00:00.000000', 'Plantación de Cacao'),
(23, '2025-03-31', 'Los turistas podrán presenciar y formar parte del proceso de elaboración artesanal de la jalea de guayaba.', '02:00:00.000000', 'img/3412_jaleaguayaba.jpg', 'Elaboración de Jalea de Guayaba', 2000, '02:00:00.000000', 'Cocina Artesanal'),
(24, '2025-03-31', 'Excursiones guiadas para descubrir la biodiversidad nocturna en los alrededores de las cabañas.', '02:00:00.000000', 'img/1dc0_senderismo.jpg', 'Senderismo Nocturno', 1500, '02:00:00.000000', 'Cabañas del Bosque'),
(25, '2025-03-22', 'Cocina interactiva con chefs locales, preparando platillos típicos con ingredientes frescos y tradicionales.', '03:30:00.000000', 'img/4b6a_gastronomia.jpg', 'Tour Gastronómico', 2420, '03:30:00.000000', 'Cocina Central'),
(26, '2025-03-29', 'Ordeño', '03:24:00.000000', 'img/a0bf_vaca perfil.jpeg', 'Vacas', 6754, '02:22:00.000000', 'Corral');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_user`
--

CREATE TABLE `tb_user` (
  `user_type` varchar(31) NOT NULL,
  `id_user` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `identification` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` int(11) DEFAULT NULL,
  `profile_picture` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_user`
--

INSERT INTO `tb_user` (`user_type`, `id_user`, `address`, `birthdate`, `email`, `identification`, `last_name`, `name`, `password`, `phone`, `profile_picture`) VALUES
('CLIENT', 1, 'San José', '1985-05-15', 'juan@example.com', '123456789', 'Casanova', 'Juan', '$2a$10$zVwXFhvfFRypnFCAsDlykexYXxc8IlM2HjZNzrVH4S/C/HDpxHKf2', 87654321, '/img/image.jpg'),
('CLIENT', 2, 'Heredia', '1990-08-22', 'maria@example.com', '987654321', 'Gómez', 'María', '$2a$10$EUfYuk2dJ1v3DNeosvc4reJfoB6EwdsHu1DyrBqMmDqtF4ejD/Z/e', 12345678, '/img/image.jpg'),
('ADMIN', 3, 'Limón', '1960-01-14', 'admin@example.com', 'admin', 'Castro', 'David', '$2a$10$2OjdbY1ij33D8GjBnPqKTew3beM65DB6TRTK9Jw7Pm6VXL1Ojsk5m', 8883439, '/img/image.jpg'),
('EMPLOYEE', 4, 'Cartago', '1992-03-25', 'empleado@example.com', '444555666', 'Empleada', 'Ana', '$2a$10$RdAb2TJdWvRx1lUMKJ6uDO1kesP0DlXeHeXvx8vNYeJyZgGIGnDP6', 66666666, '/img/image.jpg'),
('CLIENT', 6, 'San José', '1998-02-14', 'mora@example.com', '304560789', 'Mora', 'Rosa', '$2a$10$z/vIiuluT/kNcCoYOCrcQ.rpEksGrDqMVpOXlKum1mP.XHqWDyCPe', 89765433, 'img/b5f8_burger.jpg'),
('EMPLOYEE', 7, 'Sarapiquí, Tangamandapio', '2000-08-30', 'corralesreychell@gmail.com', '402480420', 'Corrales', 'Reichel', '$2a$10$f1/NoH3odId6BjMYCmm.JOcJdFH4aNjy67pXTHaK4U3pGuIjfBuFG', 62310662, 'img/eef7_perezoso.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_vehicle`
--

CREATE TABLE `tb_vehicle` (
  `id_vehicle` int(11) NOT NULL,
  `date_time_service` datetime(6) NOT NULL,
  `driver` varchar(100) NOT NULL,
  `final_location` varchar(200) NOT NULL,
  `initial_location` varchar(200) NOT NULL,
  `plate` varchar(50) NOT NULL,
  `service_duration` int(11) NOT NULL,
  `service_status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_vehicle`
--

INSERT INTO `tb_vehicle` (`id_vehicle`, `date_time_service`, `driver`, `final_location`, `initial_location`, `plate`, `service_duration`, `service_status`) VALUES
(1, '2023-11-15 08:00:00.000000', 'Juan Pérez', 'Centro Comercial Plaza Mayor', 'Almacén Central, Calle 10 #20-30', 'ABC123', 45, 'Programado'),
(2, '2023-11-14 14:30:00.000000', 'María Gómez', 'Aeropuerto Internacional', 'Terminal Norte', 'XYZ789', 60, 'En curso'),
(3, '2023-11-13 10:00:00.000000', 'Carlos Rodríguez', 'Calle 80 #23-45, Casa 12', 'Carrera 45 #12-34, Apartamento 302', 'DEF456', 120, 'Completado'),
(4, '2023-11-16 09:15:00.000000', 'Ana López', 'Sede Industrial Zona Franca', 'Oficinas Principal, Avenida 30', 'GHI789', 90, 'Cancelado'),
(5, '2023-11-17 07:00:00.000000', 'Pedro Martínez', 'Obra Nueva Torre Verde', 'Depósito de Materiales, Kilómetro 5', 'JKL012', 75, 'Programado'),
(6, '2023-11-15 16:45:00.000000', 'Luisa Fernández', 'Centro de Convenciones', 'Hotel Dann Carlton', 'MNO345', 30, 'Programado'),
(7, '2023-11-14 22:00:00.000000', 'Jorge Ramírez', 'Barrio El Poblado, Carrera 43', 'Discoteca La 70', 'PQR678', 40, 'Completado');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tb_admin`
--
ALTER TABLE `tb_admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indices de la tabla `tb_article`
--
ALTER TABLE `tb_article`
  ADD PRIMARY KEY (`id_article`);

--
-- Indices de la tabla `tb_booking`
--
ALTER TABLE `tb_booking`
  ADD PRIMARY KEY (`id_booking`),
  ADD UNIQUE KEY `UKpv54f04octj1jyn7mn62d5amv` (`id_payment`),
  ADD KEY `FKgonxgn6b4oqjr2xywb14sow90` (`id_cabin`),
  ADD KEY `FKfwc8wvs9hicrf1g6iw28fj3t0` (`id_client`),
  ADD KEY `FKdwmhrissy6tuer4yt6cqwd5p3` (`id_dishe`),
  ADD KEY `FKhk6ybab9g2hl5pjc6hjjh1loe` (`id_tour`),
  ADD KEY `FK40no492pxj74jsm3x609epgx4` (`id_vehicle`);

--
-- Indices de la tabla `tb_cabin`
--
ALTER TABLE `tb_cabin`
  ADD PRIMARY KEY (`cabinid`);

--
-- Indices de la tabla `tb_client`
--
ALTER TABLE `tb_client`
  ADD PRIMARY KEY (`id_client`);

--
-- Indices de la tabla `tb_dishe`
--
ALTER TABLE `tb_dishe`
  ADD PRIMARY KEY (`disheid`);

--
-- Indices de la tabla `tb_employee`
--
ALTER TABLE `tb_employee`
  ADD PRIMARY KEY (`id_employee`);

--
-- Indices de la tabla `tb_maintenance`
--
ALTER TABLE `tb_maintenance`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tb_payment_management`
--
ALTER TABLE `tb_payment_management`
  ADD PRIMARY KEY (`id_payment`);

--
-- Indices de la tabla `tb_review`
--
ALTER TABLE `tb_review`
  ADD PRIMARY KEY (`id_review`),
  ADD KEY `FKk7i2kxj56kdj15ea935vxoq35` (`id_client`),
  ADD KEY `FKrjlh0qxfgyf8ac9cstt3w0nmp` (`parent_id`);

--
-- Indices de la tabla `tb_tour`
--
ALTER TABLE `tb_tour`
  ADD PRIMARY KEY (`id_tour`);

--
-- Indices de la tabla `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `UKf5u2wxmworuim1or539rlh65l` (`identification`),
  ADD UNIQUE KEY `UK4vih17mube9j7cqyjlfbcrk4m` (`email`);

--
-- Indices de la tabla `tb_vehicle`
--
ALTER TABLE `tb_vehicle`
  ADD PRIMARY KEY (`id_vehicle`),
  ADD UNIQUE KEY `UKsyvoqfeu2w84aioie90tohmh8` (`plate`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tb_article`
--
ALTER TABLE `tb_article`
  MODIFY `id_article` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2030;

--
-- AUTO_INCREMENT de la tabla `tb_booking`
--
ALTER TABLE `tb_booking`
  MODIFY `id_booking` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `tb_cabin`
--
ALTER TABLE `tb_cabin`
  MODIFY `cabinid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `tb_dishe`
--
ALTER TABLE `tb_dishe`
  MODIFY `disheid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `tb_maintenance`
--
ALTER TABLE `tb_maintenance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `tb_payment_management`
--
ALTER TABLE `tb_payment_management`
  MODIFY `id_payment` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `tb_review`
--
ALTER TABLE `tb_review`
  MODIFY `id_review` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tb_tour`
--
ALTER TABLE `tb_tour`
  MODIFY `id_tour` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `tb_user`
--
ALTER TABLE `tb_user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tb_vehicle`
--
ALTER TABLE `tb_vehicle`
  MODIFY `id_vehicle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tb_admin`
--
ALTER TABLE `tb_admin`
  ADD CONSTRAINT `FK3hejdryb2682xr35p7bnkboce` FOREIGN KEY (`id_admin`) REFERENCES `tb_user` (`id_user`);

--
-- Filtros para la tabla `tb_booking`
--
ALTER TABLE `tb_booking`
  ADD CONSTRAINT `FK40no492pxj74jsm3x609epgx4` FOREIGN KEY (`id_vehicle`) REFERENCES `tb_vehicle` (`id_vehicle`),
  ADD CONSTRAINT `FK9aibau0ljmobwa7bfj311rjcq` FOREIGN KEY (`id_payment`) REFERENCES `tb_payment_management` (`id_payment`),
  ADD CONSTRAINT `FKdwmhrissy6tuer4yt6cqwd5p3` FOREIGN KEY (`id_dishe`) REFERENCES `tb_dishe` (`disheid`),
  ADD CONSTRAINT `FKfwc8wvs9hicrf1g6iw28fj3t0` FOREIGN KEY (`id_client`) REFERENCES `tb_client` (`id_client`),
  ADD CONSTRAINT `FKgonxgn6b4oqjr2xywb14sow90` FOREIGN KEY (`id_cabin`) REFERENCES `tb_cabin` (`cabinid`),
  ADD CONSTRAINT `FKhk6ybab9g2hl5pjc6hjjh1loe` FOREIGN KEY (`id_tour`) REFERENCES `tb_tour` (`id_tour`);

--
-- Filtros para la tabla `tb_client`
--
ALTER TABLE `tb_client`
  ADD CONSTRAINT `FKgc2xm8emimc202o86cthf5h1h` FOREIGN KEY (`id_client`) REFERENCES `tb_user` (`id_user`);

--
-- Filtros para la tabla `tb_employee`
--
ALTER TABLE `tb_employee`
  ADD CONSTRAINT `FKry8l05xid44aa0751csrb9uql` FOREIGN KEY (`id_employee`) REFERENCES `tb_user` (`id_user`);

--
-- Filtros para la tabla `tb_review`
--
ALTER TABLE `tb_review`
  ADD CONSTRAINT `FKk7i2kxj56kdj15ea935vxoq35` FOREIGN KEY (`id_client`) REFERENCES `tb_client` (`id_client`),
  ADD CONSTRAINT `FKrjlh0qxfgyf8ac9cstt3w0nmp` FOREIGN KEY (`parent_id`) REFERENCES `tb_review` (`id_review`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
