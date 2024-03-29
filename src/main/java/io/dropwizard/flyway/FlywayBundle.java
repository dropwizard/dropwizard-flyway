package io.dropwizard.flyway;

import io.dropwizard.core.Configuration;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.flyway.cli.DbCommand;
import io.dropwizard.util.Generics;

public abstract class FlywayBundle<T extends Configuration>
        implements ConfiguredBundle<T>, DatabaseConfiguration<T>, FlywayConfiguration<T> {
    @Override
    public final void initialize(final Bootstrap<?> bootstrap) {
        final Class<T> klass = Generics.getTypeParameter(getClass(), Configuration.class);
        bootstrap.addCommand(new DbCommand<T>(name(), this, this, klass));
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
