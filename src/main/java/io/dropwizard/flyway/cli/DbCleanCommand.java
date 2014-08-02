package io.dropwizard.flyway.cli;

import io.dropwizard.flyway.FlywayConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;
import org.flywaydb.core.Flyway;

public class DbCleanCommand<T extends Configuration> extends AbstractFlywayCommand<T> {
    public DbCleanCommand(final DatabaseConfiguration<T> databaseConfiguration,
                          final FlywayConfiguration<T> flywayConfiguration,
                          final Class<T> configurationClass) {
        super("clean", "Drops all objects in the configured schemas.",
                databaseConfiguration, flywayConfiguration, configurationClass);
    }

    @Override
    protected void run(final Namespace namespace, final Flyway flyway) throws Exception {
        flyway.clean();
    }
}
