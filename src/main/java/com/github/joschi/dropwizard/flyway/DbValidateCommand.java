package com.github.joschi.dropwizard.flyway;

import org.flywaydb.core.Flyway;
import io.dropwizard.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;

public class DbValidateCommand<T extends Configuration> extends AbstractFlywayCommand<T> {
    public DbValidateCommand(final DatabaseConfiguration<T> databaseConfiguration, final Class<T> configurationClass) {
        super("validate", "Validates the applied migrations against the ones available on the classpath.", databaseConfiguration, configurationClass);
    }

    @Override
    protected void run(final Namespace namespace, final Flyway flyway) throws Exception {
        flyway.validate();
    }
}
