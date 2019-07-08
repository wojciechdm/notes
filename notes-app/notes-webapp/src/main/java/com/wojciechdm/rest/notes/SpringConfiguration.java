package com.wojciechdm.rest.notes;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
public class SpringConfiguration {

  @Bean
  @ConfigurationProperties("app.datasource")
  DataSource dataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }
}
