package com.wojciechdm.rest.notes;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.testcontainers.containers.MySQLContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Configuration
@Profile("test")
public class SpringConfiguration {
  private final MySQLContainer CONTAINER =
      (MySQLContainer) new MySQLContainer().withInitScript("database_model.sql");

  @PostConstruct
  public void start() {
    CONTAINER.start();
  }

  @PreDestroy
  public void stop() {
    CONTAINER.stop();
  }

  @Bean
  public DataSource dataSource() {

    return DataSourceBuilder.create()
        .url(CONTAINER.getJdbcUrl() + "?serverTimezone=Europe/Warsaw&useSSL=false")
        .username(CONTAINER.getUsername())
        .password(CONTAINER.getPassword())
        .type(HikariDataSource.class)
        .build();
  }
}
