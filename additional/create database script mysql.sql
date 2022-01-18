CREATE DATABASE `caesar_crypt`;
CREATE TABLE `crypted_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` DATETIME NOT NULL,
  `source_text` longtext NOT NULL,
  `crypted_text` longtext,
	`key` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;