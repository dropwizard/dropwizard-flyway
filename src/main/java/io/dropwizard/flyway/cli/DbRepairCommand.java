package io.dropwizard.flyway.cli;

import io.dropwizard.flyway.FlywayConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;
import org.flywaydb.core.Flyway;

public class DbRepairCommand<T extends Configuration> extends NoOptionsFlywayCommand<T> {
    public DbRepairCommand(final DatabaseConfiguration<T> databaseConfiguration,
                           final FlywayConfiguration<T> flywayConfiguration,
                           final Class<T> configurationClass) {
        super("repair", "Repairs the metadata table.",
                databaseConfiguration, flywayConfiguration, configurationClass);
    }

    @Override
    public void run(final Namespace namespace, final Flyway flyway) throws Exception {
        flyway.repair();
    }
}
