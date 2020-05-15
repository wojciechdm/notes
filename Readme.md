# Notes

RESTful API webservice for managing and storing simple text notes. Single note contains title, content, date of creation, date of last modification and number of version. There is also stored history of note modifactions.

Used technologies: Java 11, Maven, MySQL, Spring Boot, Hibernate, Lombok, Spock, Testcontainers.

### Installing

1. Set envoirenment variables with database credencials and url. Names of variables:

   ```
   NOTES_DB_URL
   NOTES_DB_USERNAME
   NOTES_DB_PASSWORD
   ```

2. Go to `../notes-app/`  and use `mvn package` command to build a package and run executable jar file.

### Setup database

To create needed tables use scripts:

```
CREATE TABLE `notes`.`notes` (
  `note_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `created` date NOT NULL,
  `modified` date NOT NULL,
  `version` bigint NOT NULL,  
  PRIMARY KEY (`note_id`)
) ENGINE=InnoDB DEFAULT CHARSET= utf8mb4

CREATE TABLE `notes`.`notes_history` (
  `note_history_id` bigint NOT NULL AUTO_INCREMENT,
  `note_id` bigint NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `created` date NOT NULL,
  `modified` date NOT NULL,
  `version` bigint NOT NULL,  
  PRIMARY KEY (`note_history_id`)
) ENGINE=InnoDB DEFAULT CHARSET= utf8mb4
```

### Example usages

Some example curl commands to use:

- Add a new note

  ```
  curl -H "Content-Type: application/json" http://localhost:8080/notes-webapp/notes -X POST -d '{"title":"example title", "content":"example content"}'
  ```

  Command above works with Unix shell. Windows command prompt needs to replace every single quotes with double quotes. Below it's example command for Windows.

  ```
  curl -H "Content-Type: application/json" http://localhost:8080/notes-webapp/notes -X POST -d "{\"title\":\"example title\", \"content\":\"example content\"}"
  ```

- Edit a note (100 is example note id)

  ```
  curl -H "Content-Type: application/json" http://localhost:8080/notes-webapp/notes/100 -X PUT -d '{"title":"edited title", "content":"edited content"}'
  ```

- Delete a note (100 is example note id)

  ```
  curl -H "Content-Type: application/json" http://localhost:8080/notes-webapp/notes/100 -X DELETE
  ```

- Get a note (100 is example note id)

  ```
  curl -H "Content-Type: application/json" http://localhost:8080/notes-webapp/notes/100 -X GET
  ```

- Get a note changes history (100 is example note id)

  ```
  curl -H "Content-Type: application/json" http://localhost:8080/notes-webapp/notes/100/history -X GET
  ```

- Get a list of notes

  ```
  curl -H "Content-Type: application/json" http://localhost:8080/notes-webapp/notes -X GET
  ```

