package io.dropwizard.flyway.cli;

import io.dropwizard.flyway.FlywayConfiguration;
import org.flywaydb.core.Flyway;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;

public class DbInitCommand<T extends Configuration> extends NoOptionsFlywayCommand<T> {
    public DbInitCommand(final DatabaseConfiguration<T> databaseConfiguration,
                         final FlywayConfiguration<T> flywayConfiguration,
                         final Class<T> configurationClass) {
        super("init", "Creates and initializes the metadata table (existing database).",
                databaseConfiguration, flywayConfiguration, configurationClass);
    }

    @Override
    protected void run(final Namespace namespace, final Flyway flyway) throws Exception {
        flyway.baseline();
    }
}
