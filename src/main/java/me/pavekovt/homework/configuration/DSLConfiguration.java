package me.pavekovt.homework.configuration;


import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DSLConfiguration {

  final DataSource ds;

  public DSLConfiguration(DataSource ds) {
    this.ds = ds;
  }

  @Bean
  public DSLContext dsl() {
    return DSL.using(ds, SQLDialect.H2);
  }
}
