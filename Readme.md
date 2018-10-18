# Notes

Simple RESTful API webservice for managing and storing simple text notes. Single note contains title, content, date of creation, date of last modification and number of version. There is also stored history of note modifactions.

### Getting started

Service was tested at machine with:

- Windows 10 Home 64-bit buid 17134
- Java build 1.8.0_181
- Maven 3.5.3
- Tomcat 8.5
- MySQL 8.0

### Installing

1. Go to `../notes-app/notes-webapp/src/main/webapp/WEB-INF/applicationContext.xml` and set "dataSource" bean. Default setup is:

   ```
   "jdbcUrl"="mysql://localhost:3306"
   "user"="user"
   "password"="password"
   ```

2. Go to `../notes-app/`  and use `mvn install` command to build a package and install it in local repository.

3. Deploy `../notes-app/notes-webapp/target/notes-webapp-0.0.1-SNAPSHOT.war` file at server or servlet container.

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

### Testing

1. Go to

   ```
   ../notes-app-test/notes-app-rest-test/src/test/java/com/polsource/assignment/backend/test/NoteRestApiServiceTest.java
   ```

   and set url field with URL application runned at server. Default setup is: 

   ```
   "http://localhost:8080/notes-webapp/notes"
   ```

2. Go to `../notes-app-test/`  and use `mvn test` command to run integration tests.

Tests use real database. Be aware it's going to delete existings records.

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
  curl -H "Content-Type: application/json" http://localhost:8080/notes-webapp/notes/100-history -X GET
  ```

- Get a list of notes

  ```
  curl -H "Content-Type: application/json" http://localhost:8080/notes-webapp/notes -X GET
  ```

