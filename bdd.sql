-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: banco
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `cpf` varchar(15) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `sobrenome` varchar(50) NOT NULL,
  `idade` int NOT NULL,
  `dtNascimento` date NOT NULL,
  `email` varchar(80) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `senha` varchar(20) NOT NULL,
  `endereco` varchar(200) NOT NULL,
  PRIMARY KEY (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('54497481751','Fulsano','Deutrano',22,'2000-09-01','sicrano@gmail.com','43502789','12345678','86300000, 45, Avenidade Nosa Senhora do Rocio, Estoril, Cornelio Procopio, Pr, Casa'),('60708153518','Deutrano','Sicrano',20,'2002-12-06','deutranosicrano@gmail.com','000000','12345678','Rua dos Bobos, 0, 00000368, Casa muito engraçada, Osasco, SP, Centro');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-22 10:27:42



-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: banco
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `conta`
--

DROP TABLE IF EXISTS `conta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conta` (
  `numAgencia` int NOT NULL DEFAULT '5520',
  `numConta` varchar(10) NOT NULL,
  `saldo` double NOT NULL,
  `tipoConta` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Ativa',
  `dtAbertura` date DEFAULT NULL,
  `limite` double DEFAULT NULL,
  `aniversario` int DEFAULT NULL,
  `dtUltimAtu` date DEFAULT NULL,
  `clienteCpf` varchar(15) NOT NULL,
  PRIMARY KEY (`numConta`,`clienteCpf`),
  KEY `fk_Conta_Cliente` (`clienteCpf`),
  CONSTRAINT `fk_Conta_Cliente` FOREIGN KEY (`clienteCpf`) REFERENCES `cliente` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conta`
--

LOCK TABLES `conta` WRITE;
/*!40000 ALTER TABLE `conta` DISABLE KEYS */;
INSERT INTO `conta` VALUES (5520,'18850-8',100,'Corrente','Ativa','2022-12-08',500,NULL,NULL,'54497481751'),(5520,'21280-3',80,'Poupança','Ativa','2022-12-07',NULL,8,'2022-12-08','60708153518');
/*!40000 ALTER TABLE `conta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-22 10:27:42

-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: banco
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `senha` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionario`
--

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` VALUES (2,'JoiceKelly','1234');
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-22 10:27:42


-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: banco
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `movimentacao`
--

DROP TABLE IF EXISTS `movimentacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimentacao` (
  `idMovimentacao` int NOT NULL AUTO_INCREMENT,
  `contaNumContaOrigem` varchar(10) NOT NULL,
  `contaNumContaDestino` varchar(10) NOT NULL,
  `montante` double NOT NULL,
  `dtMovimentacao` date NOT NULL,
  `tipoMovimentacao` varchar(20) NOT NULL,
  `mensagem` tinytext,
  PRIMARY KEY (`idMovimentacao`,`contaNumContaOrigem`,`contaNumContaDestino`),
  KEY `fk_Conta_Movimentacao_NumeroContaOrigem` (`contaNumContaOrigem`),
  KEY `fk_Conta_Movimentacao_NumeroContaDestino` (`contaNumContaDestino`),
  CONSTRAINT `fk_Conta_Movimentacao_NumeroContaDestino` FOREIGN KEY (`contaNumContaDestino`) REFERENCES `conta` (`numConta`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Conta_Movimentacao_NumeroContaOrigem` FOREIGN KEY (`contaNumContaOrigem`) REFERENCES `conta` (`numConta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=245 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimentacao`
--

LOCK TABLES `movimentacao` WRITE;
/*!40000 ALTER TABLE `movimentacao` DISABLE KEYS */;
INSERT INTO `movimentacao` VALUES (229,'52414-10','52414-10',100,'2022-12-06','Depósito',''),(231,'87540-1','87540-1',200,'2022-12-06','Depósito','null'),(232,'87540-1','52414-10',30,'2022-12-06','Transferência','oi amor'),(233,'52414-10','87540-1',10,'2022-12-07','Transferência',''),(234,'52414-10','52414-10',10,'2022-12-07','Transferência',''),(235,'52414-10','52414-10',10,'2022-12-07','Transferência',''),(236,'52414-10','87540-1',10,'2022-12-07','Transferência',''),(237,'81340-2','81340-2',100,'2022-12-07','Depósito','null'),(238,'21280-3','21280-3',100,'2022-12-07','Depósito',''),(239,'21280-3','52414-10',100,'2022-12-07','Transferência','oi'),(240,'81340-2','87540-1',100,'2022-12-07','Transferência',''),(241,'18850-8','18850-8',100,'2022-12-08','Depósito','null'),(242,'50127-10','50127-10',100,'2022-12-08','Depósito',''),(243,'50127-10','21280-3',80,'2022-12-08','Transferência','ola'),(244,'37601-2','37601-2',50000000000,'2023-01-01','Depósito','');
/*!40000 ALTER TABLE `movimentacao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-22 10:27:42



CREATE USER 'root'@'localhost' IDENTIFIED BY 'utfpr';
GRANT ALL PRIVILEGES ON * . * TO 'root'@'localhost';
FLUSH PRIVILEGES;



CREATE EVENT aplica
	ON SCHEDULE
		EVERY 1 month
        STARTS (curdate())
        on completion preserve
	DO
		UPDATE banco.Conta
        SET dtUltimAtu = curdate()
		WHERE saldo < 0 and month(curdate()) <> (1 + month(dtUltimAtu)) % 12;




CREATE EVENT teste
	ON SCHEDULE
		EVERY 1 month
        STARTS '2022-12-01'
        on completion preserve
	DO
		UPDATE banco.Conta
        SET saldo = round(saldo * 1.05, 2), dtUltimAtu = curdate()
		WHERE saldo < 0 and month(curdate()) = (1 + month(dtUltimAtu)) % 12;
		
		
		
		
CREATE EVENT juros
  ON SCHEDULE
    EVERY 1 day
    STARTS (curdate())
    on completion preserve
  DO
	UPDATE banco.Conta 
    SET saldo = round(saldo * 1.05, 2), dtUltimAtu = curdate()
    WHERE aniversario = day(curdate()) and numConta <> 0;
