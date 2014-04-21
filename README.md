Dropwizard Flyway
=================
[![Build Status](https://travis-ci.org/joschi/dropwizard-flyway.svg?branch=master)](https://travis-ci.org/joschi/dropwizard-flyway)

`dropwizard-flyway` is a set of commands using [Flyway](http://www.flyway.org/) for database migrations in [Dropwizard](http://www.dropwizard.io/) applications.


Usage
-----

Just add the `FlywayBundle` to your Dropwizard application inside the [`Application#initialize`](https://dropwizard.github.io/dropwizard/0.7.0/dropwizard-core/apidocs/io/dropwizard/Application.html#initialize-io.dropwizard.setup.Bootstrap-) method. 

    @Override
    public void initialize(Bootstrap<MyConfiguration> bootstrap) {
        // ...
        bootstrap.addBundle(new FlywayBundle<MyConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(MyConfiguration configuration) {
                return configuration.getDataSourceFactory();
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

The [Flyway migrations](http://flywaydb.org/documentation/migration/) must be accessible in the classpath under `db/migration`.


Configuration
-------------

`dropwizard-flyway` is using the standard [DataSourceFactory](https://dropwizard.github.io/dropwizard/0.7.0/dropwizard-db/apidocs/io/dropwizard/db/DataSourceFactory.html) configuration from [`dropwizard-db`](https://dropwizard.github.io/dropwizard/0.7.0/dropwizard-db/).


Maven Artifacts
---------------

This project is available on Maven Central. To add it to your project simply add the following dependencies to your `pom.xml`:

    <dependency>
      <groupId>com.github.joschi</groupId>
      <artifactId>dropwizard-flyway</artifactId>
      <version>0.1.0</version>
    </dependency>


Support
-------

Please file bug reports and feature requests in [GitHub issues](https://github.com/joschi/dropwizard-flyway/issues).


License
-------

Copyright (c) 2014 Jochen Schalanda

This library is licensed under the Apache License, Version 2.0.

See http://www.apache.org/licenses/LICENSE-2.0.html or the LICENSE file in this repository for the full license text.