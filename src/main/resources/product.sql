CREATE TABLE `diangetai_music` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `uploader` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `played` int(8) NOT NULL DEFAULT 0,
  `star` int(8) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;