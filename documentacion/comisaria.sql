CREATE DATABASE  IF NOT EXISTS `comisaria` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `comisaria`;

--
-- Table structure for table `multastipo`
--


CREATE TABLE `multastipo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `importe` double DEFAULT NULL,
  `tipo` enum('L','G','MG') DEFAULT 'L',
  `carnetpuntos` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `multastipo`
--

LOCK TABLES `multastipo` WRITE;
/*!40000 ALTER TABLE `multastipo` DISABLE KEYS */;
INSERT INTO `multastipo` VALUES (1,'Exceso de velocidad',100,'L',1),(2,'Positivo Alcoholemia',600,'G',3),(3,'Semaforo en ambar',150,'L',2),(4,'Seguro caducado',300,'G',3),(5,'ITV caducada',150,'L',2),(6,'Duplicar velocidad permitida',1500,'MG',5),(7,'Triplicar tasa Alcoholemia',2000,'MG',6),(8,'Stop',150,'L',2),(9,'Ceda el paso',150,'L',2),(10,'Seguro inexistente',300,'G',3),(11,'Semaforo en rojo',333,'G',3),(12,'direccion sentido contrario',253,'MG',4),(13,'cambio de sentido prohibido',253,'MG',4),(14,'direccion sentido contrario',50,'G',2);
/*!40000 ALTER TABLE `multastipo` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `policia`
--



CREATE TABLE `policia` (
  `idPolicia` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `numplaca` varchar(45) unique NULL,
  `edad` int(11) DEFAULT NULL,
  `departamento` varchar(45) DEFAULT NULL,
  `foto` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPolicia`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `policia`
--

LOCK TABLES `policia` WRITE;
/*!40000 ALTER TABLE `policia` DISABLE KEYS */;
INSERT INTO `policia` VALUES (1,'Juan Sanchez','1234AS',56,'A3',NULL),(2,'Laura Montiel','2314RT',40,'A3',NULL),(3,'Pepe Gonzalez','7655CD',57,'B2',NULL),(4,'Luis Lopez','7765AS',32,'B1',NULL),(5,'Matias Prats','3221DF',29,'A2',NULL);
/*!40000 ALTER TABLE `policia` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `multas`
--



CREATE TABLE `multas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `fecha` timestamp ,
  `importe` double DEFAULT NULL,
  `idpolicia` int(11) NOT NULL,
  `nifinfractor` varchar(45) DEFAULT NULL,
  `idtipo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
   CONSTRAINT `policiafk` FOREIGN KEY (`idpolicia`) REFERENCES `policia` (`idPolicia`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tipofk` FOREIGN KEY (`idtipo`) REFERENCES `multastipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `multas`
--

LOCK TABLES `multas` WRITE;
/*!40000 ALTER TABLE `multas` DISABLE KEYS */;
INSERT INTO `multas` VALUES (1,'Exceso de velocidad','2018-01-02 00:00:00',100,1,NULL,1),(2,'Positivo Alcoholemia','2015-01-02 00:00:00',600,1,'22345678',2),(3,'Stop','2017-01-02 07:40:00',150,2,'11678456',3),(4,'Seguro caducado','2016-04-02 19:05:00',300,3,NULL,4),(5,'ITV caducada','2017-05-02 20:15:00',150,4,'19442678',5),(6,'Duplicar velocidad permitida','2015-07-02 00:00:00',1500,4,'19442678',6),(7,'Triplicar tasa Alcoholemia','2015-07-02 00:00:00',2000,4,'21456234',7),(8,'Stop','2015-08-02 04:00:00',150,2,NULL,3),(9,'Stop','2018-01-03 22:00:00',150,1,NULL,3),(10,'Seguro caducado','2015-03-03 00:00:00',300,2,'78543123',4),(11,'cambio de direccion incorrecto','2015-05-29 23:20:00',333,3,NULL,1),(12,'direccion sentido contrario','2016-06-02 14:00:00',253,4,'78543123',1),(13,'direccion sentido contrario','2017-06-02 00:00:00',253,1,NULL,1),(14,'direccion sentido contrario','2015-04-02 11:30:00',50,1,'12345678',1);
/*!40000 ALTER TABLE `multas` ENABLE KEYS */;
UNLOCK TABLES;



