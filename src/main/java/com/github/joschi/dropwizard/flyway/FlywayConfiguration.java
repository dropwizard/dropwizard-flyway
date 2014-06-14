package com.github.joschi.dropwizard.flyway;

import io.dropwizard.Configuration;

public interface FlywayConfiguration<T extends Configuration> {
    FlywayFactory getFlywayFactory(T configuration);
}
