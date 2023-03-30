package io.dropwizard.flyway;

import io.dropwizard.core.Configuration;

public interface FlywayConfiguration<T extends Configuration> {
    FlywayFactory getFlywayFactory(T configuration);
}
