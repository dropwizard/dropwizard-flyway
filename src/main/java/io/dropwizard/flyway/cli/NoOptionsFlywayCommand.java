package io.dropwizard.flyway.cli;

import io.dropwizard.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.flyway.FlywayConfiguration;
import io.dropwizard.flyway.FlywayFactory;
import net.sourceforge.argparse4j.inf.Namespace;

public abstract class NoOptionsFlywayCommand<T extends Configuration> extends AbstractFlywayCommand<T> {

    NoOptionsFlywayCommand(String name, String description, DatabaseConfiguration<T> databaseConfiguration,
                           FlywayConfiguration<T> flywayConfiguration, Class<T> configurationClass) {
        super(name, description, databaseConfiguration, flywayConfiguration, configurationClass);
    }

    @Override
    protected void setAdditionalOptions(FlywayFactory flywayFactory, Namespace namespace) {
        // Empty on purpose
    }
}
