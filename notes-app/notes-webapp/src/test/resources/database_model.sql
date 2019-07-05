create TABLE `notes` (
  `note_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `created` date NOT NULL,
  `modified` date NOT NULL,
  `version` bigint NOT NULL,
  PRIMARY KEY (`note_id`)
) ENGINE=InnoDB DEFAULT CHARSET= utf8mb4;

create TABLE `notes_history` (
  `note_history_id` bigint NOT NULL AUTO_INCREMENT,
  `note_id` bigint NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `created` date NOT NULL,
  `modified` date NOT NULL,
  `version` bigint NOT NULL,
  PRIMARY KEY (`note_history_id`)
) ENGINE=InnoDB DEFAULT CHARSET= utf8mb4;