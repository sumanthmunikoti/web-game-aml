CREATE DATABASE `aml` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `aml`;

CREATE TABLE `useraccount` (
  `userid` varchar(45) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `entity_value` varchar(45) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `lob` varchar(100) DEFAULT NULL,
  `bank` varchar(90) DEFAULT NULL,
  `clean_money` double DEFAULT NULL,
  `dirty_money` float DEFAULT NULL,
  `preciousMetals` int(11) DEFAULT NULL,
  `preciousMetalsPrice` double DEFAULT NULL,
  `property` int(11) DEFAULT NULL,
  `propertyPrice` double DEFAULT NULL,
  `artWorks` int(11) DEFAULT NULL,
  `artPrice` double DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `transactionstatus` (
  `receiver` varchar(45) NOT NULL,
  `sender` varchar(45) DEFAULT NULL,
  `commodity` varchar(45) DEFAULT NULL,
  `commPrice` double DEFAULT NULL,
  `commQty` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `network` (
  `userid1` varchar(45) NOT NULL,
  `userid2` varchar(45) NOT NULL,
  PRIMARY KEY (`userid1`,`userid2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `friendrequesttable` (
  `sender` varchar(45) NOT NULL,
  `receiver` varchar(45) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`receiver`,`sender`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table to contain the status of friend requests';

CREATE TABLE `detailedtransactions` (
  `transactionid` double NOT NULL,
  `date` varchar(45) DEFAULT NULL,
  `commPrice` double DEFAULT NULL,
  `sender` varchar(45) DEFAULT NULL,
  `senderAddress` varchar(80) DEFAULT NULL,
  `senderlob` varchar(45) DEFAULT NULL,
  `commodity` varchar(45) DEFAULT NULL,
  `receiver` varchar(45) DEFAULT NULL,
  `receiverAddress` varchar(80) DEFAULT NULL,
  `receiverlob` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `reported` varchar(45) DEFAULT NULL,
  `whoReported` varchar(45) DEFAULT 'No One',
  PRIMARY KEY (`transactionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `comowndetails` (
  `userid` varchar(45) DEFAULT NULL,
  `companyName` varchar(45) DEFAULT NULL,
  `entityType` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `chat_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(100) DEFAULT NULL,
  `chat` longtext,
  `timestamp` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

