Dropwizard Flyway
=================
[![Build Status](https://travis-ci.org/dropwizard/dropwizard-flyway.svg?branch=master)](https://travis-ci.org/dropwizard/dropwizard-flyway)
[![Coverage Status](https://img.shields.io/coveralls/dropwizard/dropwizard-flyway.svg)](https://coveralls.io/r/dropwizard/dropwizard-flyway)

`dropwizard-flyway` is a set of commands using [Flyway](http://www.flywaydb.org/) for database migrations in [Dropwizard](http://dropwizard.io/) applications.


Usage
-----

Just add the `FlywayBundle` to your Dropwizard application inside the [`Application#initialize`](http://dropwizard.io/0.7.1/dropwizard-core/apidocs/io/dropwizard/Application.html#initialize%28io.dropwizard.setup.Bootstrap%29) method.

    @Override
    public void initialize(Bootstrap<MyConfiguration> bootstrap) {
        // ...
        bootstrap.addBundle(new FlywayBundle<MyConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(MyConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
            
            @Override
            public FlywayFactory getFlywayFactory(MyConfiguration configuration) {
                return configuration.getFlywayFactory();
            }
        });
    }


After that you can use one of the following Flyway commands:

| Command       | Description                                                                  |
| ------------- | ---------------------------------------------------------------------------- |
| `db migrate`  | Migrates the database                                                        |
| `db clean`    | Drops all objects in the configured schemas                                  |
| `db info`     | Prints the details and status information about all the migrations           |
| `db validate` | Validates the applied migrations against the ones available on the classpath |
| `db baseline` | Creates and initializes the metadata table (existing database)               |
| `db repair`   | Repairs the metadata table                                                   |

The [Flyway migrations](http://flywaydb.org/documentation/migration/) must be accessible in the classpath under `db/migration` (or any other path configured with the `locations` parameter, see [FlywayFactory](https://dropwizard.github.io/dropwizard-flyway/0.7.0-1/apidocs/io/dropwizard/flyway/FlywayFactory.html)).


Configuration
-------------

`dropwizard-flyway` is using the standard [DataSourceFactory](http://dropwizard.io/0.7.1/dropwizard-db/apidocs/io/dropwizard/db/DataSourceFactory.html) from [`dropwizard-db`](http://dropwizard.io/0.7.1/dropwizard-db/) for configuring its [DataSource](http://docs.oracle.com/javase/7/docs/api/javax/sql/DataSource.html).

Additionally you can override the following configuration settings of Flyway using [FlywayFactory](https://dropwizard.github.io/dropwizard-flyway/0.7.0-1/apidocs/io/dropwizard/flyway/FlywayFactory.html):

    flyway:
      # The encoding of SQL migrations. (default: UTF-8) 
      encoding: UTF-8
      # The schemas managed by Flyway. (default: default schema of the connection)
      schemas:
      # The fully qualified class names of the callbacks for lifecycle notifications. (default: empty list)
      callbacks:
      # The name of the schema metadata table that will be used by Flyway. (default: schema_version)
      metaDataTableName: schema_version
      # The file name prefix for sql migrations (default: V)
      sqlMigrationPrefix: V
      # The file name separator for sql migrations (default: __)
      sqlMigrationSeparator: __
      # The file name suffix for sql migrations (default: .sql)
      sqlMigrationSuffix: .sql
      # The prefix of every placeholder. (default: ${ )
      placeholderPrefix: ${
      # The suffix of every placeholder. (default: } )
      placeholderSuffix: }
      # The map of <placeholder, replacementValue> to apply to sql migration scripts. (default: empty map)
      placeholders:
      # Locations to scan recursively for migrations. (default: db/migration)
      locations:
        - db/migration
      # The fully qualified class names of the custom MigrationResolvers to be used in addition to the built-in ones for resolving Migrations to apply. (default: empty list)
      resolvers:


Maven Artifacts
---------------

This project is available on Maven Central. To add it to your project simply add the following dependencies to your `pom.xml`:

    <dependency>
      <groupId>io.dropwizard.modules</groupId>
      <artifactId>dropwizard-flyway</artifactId>
      <version>0.8.0-2</version>
    </dependency>


Support
-------

Please file bug reports and feature requests in [GitHub issues](https://github.com/dropwizard/dropwizard-flyway/issues).


License
-------

Copyright (c) 2014-2015 Jochen Schalanda

This library is licensed under the Apache License, Version 2.0.

See http://www.apache.org/licenses/LICENSE-2.0.html or the LICENSE file in this repository for the full license text.
