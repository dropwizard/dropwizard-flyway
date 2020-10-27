Dropwizard Flyway
=================

[![Build Status](https://travis-ci.org/dropwizard/dropwizard-flyway.svg?branch=master)](https://travis-ci.org/dropwizard/dropwizard-flyway)
[![Coverage Status](https://img.shields.io/coveralls/dropwizard/dropwizard-flyway.svg)](https://coveralls.io/r/dropwizard/dropwizard-flyway)
[![Maven Central](https://img.shields.io/maven-central/v/io.dropwizard.modules/dropwizard-flyway/1.3)](https://search.maven.org/artifact/io.dropwizard.modules/dropwizard-flyway)

`dropwizard-flyway` is a set of commands using [Flyway](http://www.flywaydb.org/) for database migrations in [Dropwizard](http://dropwizard.io/) applications.


Usage
-----

Just add the `FlywayBundle` to your Dropwizard application inside the [`Application#initialize`](http://dropwizard.io/1.3.0/dropwizard-core/apidocs/io/dropwizard/Application.html#initialize%28io.dropwizard.setup.Bootstrap%29) method.

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
| `db init`     | Creates and initializes the metadata table (existing database)               |
| `db repair`   | Repairs the metadata table                                                   |

The [Flyway migrations](http://flywaydb.org/documentation/migration/) must be accessible in the classpath under `db/migration` (or any other path configured with the `locations` parameter, see [FlywayFactory](https://dropwizard.github.io/dropwizard-flyway/1.3.0-5/apidocs/io/dropwizard/flyway/FlywayFactory.html)).


Configuration
-------------

`dropwizard-flyway` is using the standard [DataSourceFactory](http://dropwizard.io/1.3.0/dropwizard-db/apidocs/io/dropwizard/db/DataSourceFactory.html) from [`dropwizard-db`](http://dropwizard.io/1.3.0/dropwizard-db/) for configuring its [DataSource](http://docs.oracle.com/javase/8/docs/api/javax/sql/DataSource.html).

Additionally you can override the following configuration settings of Flyway using [FlywayFactory](https://dropwizard.github.io/dropwizard-flyway/1.3.0-5/apidocs/io/dropwizard/flyway/FlywayFactory.html):

    flyway:
      # The encoding of SQL migrations. (default: UTF-8) 
      encoding: UTF-8

      # The default schema managed by Flyway. (default: the first schema listed in schemas)
      defaultSchema:
      
      # The schemas managed by Flyway. (default: default schema of the connection)
      schemas:
      
      # The fully qualified class names of the callbacks for lifecycle notifications. (default: empty list)
      callbacks:
      
      # The name of the schema metadata table that will be used by Flyway. (default: flyway_schema_history)
      metaDataTableName: flyway_schema_history
      
      # The file name prefix for sql migrations (default: V)
      sqlMigrationPrefix: V
      
      # The file name separator for sql migrations (default: __)
      sqlMigrationSeparator: __
      
      # The file name suffixes for sql migrations (default: .sql)
      sqlMigrationSuffixes:
        - .sql
      
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
      
      # Allows migrations to be run "out of order". If you already have versions 1 and 3 applied, and now a version 2 is found, it will be applied too instead of being ignored. (default: false)
      outOfOrder: false
      
      # The description to tag an existing schema with when executing baseline. (default: << Flyway Baseline >>)
      baselineDescription: "<< Flyway Baseline >>"
      
      # Whether to automatically call baseline when migrate is executed against a non-empty schema with no metadata table. (default: false)
      # Be careful when enabling this as it removes the safety net that ensures Flyway does not migrate the wrong database in case of a configuration mistake!
      baselineOnMigrate: false
      
      # Whether to automatically call validate or not when running migrate. (default: true)
      validateOnMigrate: true
      
      # The version to tag an existing schema with when executing baseline. (default: 1)
      baselineVersion: 1
      
      # Whether to disabled clean. (default: false)
      # This is especially useful for production environments where running clean can be quite a career limiting move.
      cleanDisabled: false
      
      # Whether to group all pending migrations together in the same transaction when applying them
      # (only recommended for databases with support for DDL transactions).
      # true if migrations should be grouped. false if they should be applied individually instead. (default: false)
      group: false
      
      # Ignore future migrations when reading the schema history table. These are migrations that were performed by a
      # newer deployment of the application that are not yet available in this version.
      # true to continue normally and log a warning, false to fail fast with an exception. (default: true)
      ignoreFutureMigrations: true
      
      # Ignore ignored migrations when reading the schema history table. These are migrations that were added in between
      # already migrated migrations in this version.
      # true to continue normally, false to fail fast with an exception. (default: false)
      ignoreIgnoredMigrations: false
      
      # Ignore missing migrations when reading the schema history table. These are migrations that were performed by an
      # older deployment of the application that are no longer available in this version.
      # true to continue normally and log a warning, false to fail fast with an exception. (default: false)
      ignoreMissingMigrations: false
      
      # The username that will be recorded in the schema history table as having applied the migration.
      # <<blank>> for the current database user of the connection. (default: <<blank>>).
      installedBy:
      
      # Whether to allow mixing transactional and non-transactional statements within the same migration.
      # true if mixed migrations should be allowed. false if an error should be thrown instead. (default: false)
      mixed: false
      
      # Whether placeholders should be replaced. (default: true)
      placeholderReplacement: true
      
      # If set to true, default built-in callbacks (sql) are skipped and only custom callback as
      # defined by 'callbacks' are used. (default: false)
      skipDefaultCallbacks: false
      
      # If set to true, default built-in resolvers (jdbc, spring-jdbc and sql) are skipped and only custom resolvers as
      # defined by 'resolvers' are used. (default: false)
      skipDefaultResolvers: false
      
      #### COMMERCIAL FEATURES
      # (Flyway Pro and Flyway Enterprise only)
      
      # Whether to batch SQL statements when executing them. (default: false)
      batch: false
      
      # The file where to output the SQL statements of a migration dry run. If the file specified is in a non-existent
      # directory, Flyway will create all directories and parent directories as needed.
      # <<blank>> to execute the SQL statements directly against the database. (default: <<blank>>)
      #dryRunOutputFile:
      
      # Comma-separated list of the fully qualified class names of handlers for errors and warnings that occur during a
      # migration. This can be used to customize Flyway's behavior by for example throwing another runtime exception,
      # outputting a warning or suppressing the error instead of throwing a FlywayException.
      # ErrorHandlers are invoked in order until one reports to have successfully handled the errors or warnings.
      # If none do, or if none are present, Flyway falls back to its default handling of errors and warnings.
      # <<blank>> to use the default internal handler (default: <<blank>>)
      #errorHandlers:

      # Rules for the built-in error handler that lets you override specific SQL states and errors codes from error to
      # warning or from warning to error.
      # Each error override has the following format: STATE:12345:W. It is a 5 character SQL state, a colon, the SQL 
      # error code, a colon and finally the desired behavior that should override the initial one. The following 
      # behaviors are accepted: W to force a warning and E to force an error.
      # For example, to force Oracle stored procedure compilation issues to produce errors instead of warnings,
      # the following errorOverride can be used: 99999:17110:E
      #errorOverrides:
      
      # Whether to Flyway's support for Oracle SQL*Plus commands should be activated. (default: false)
      oracleSqlPlus: false
      
      # Whether to stream SQL migrations when executing them. (default: false)
      stream: false
      
      # Target version up to which Flyway should consider migrations.
      # The special value 'current' designates the current version of the schema. (default: <<latest version>>)
      #target:
      
      # The file name prefix for undo SQL migrations. (default: U)
      # Undo SQL migrations are responsible for undoing the effects of the versioned migration with the same version.
      # They have the following file name structure: prefixVERSIONseparatorDESCRIPTIONsuffix ,
      # which using the defaults translates to U1.1__My_description.sql
      undoSqlMigrationPrefix: U



Maven Artifacts
---------------

[![Maven Central](https://img.shields.io/maven-central/v/io.dropwizard.modules/dropwizard-flyway/1.3)](https://search.maven.org/artifact/io.dropwizard.modules/dropwizard-flyway)
[![Maven Central](https://img.shields.io/maven-central/v/io.dropwizard.modules/dropwizard-flyway/2.0)](https://search.maven.org/artifact/io.dropwizard.modules/dropwizard-flyway)

This project is available on Maven Central. To add it to your project simply add the following dependencies to your `pom.xml`:

    <dependency>
      <groupId>io.dropwizard.modules</groupId>
      <artifactId>dropwizard-flyway</artifactId>
      <version>1.3.0-{release-version}</version>
    </dependency>


Support
-------

Please file bug reports and feature requests in [GitHub issues](https://github.com/dropwizard/dropwizard-flyway/issues).


License
-------

Copyright (c) 2014-2020 Jochen Schalanda, Dropwizard Team

This library is licensed under the Apache License, Version 2.0.

See http://www.apache.org/licenses/LICENSE-2.0.html or the LICENSE file in this repository for the full license text.
