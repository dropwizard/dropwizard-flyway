package io.dropwizard.flyway;

import io.dropwizard.flyway.cli.DbCommand;
import io.dropwizard.Bundle;
import io.dropwizard.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.util.Generics;

public abstract class FlywayBundle<T extends Configuration>
        implements Bundle, DatabaseConfiguration<T>, FlywayConfiguration<T> {
    @Override
    public final void initialize(final Bootstrap<?> bootstrap) {
        final Class<T> klass = Generics.getTypeParameter(getClass(), Configuration.class);
        bootstrap.addCommand(new DbCommand<T>(name(), this, this, klass));
    }

    @Override
    public final void run(final Environment environment) {
        // nothing doing
    }

    @Override
    public FlywayFactory getFlywayFactory(T configuration) {
        // Default Flyway configuration
        return new FlywayFactory();
    }

    protected String name() {
        return "db";
    }
}
