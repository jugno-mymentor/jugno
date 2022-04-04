package com.jawad.myspringauth;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class PropertyNameBoundColumnNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {

  @Override
  public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return new Identifier(identifier.getText(), true);
  }

}
