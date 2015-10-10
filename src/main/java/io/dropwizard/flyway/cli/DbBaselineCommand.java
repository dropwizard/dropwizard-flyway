package io.dropwizard.flyway.cli;

import io.dropwizard.flyway.FlywayCommand;
import io.dropwizard.flyway.FlywayConfiguration;
import org.flywaydb.core.Flyway;
import io.dropwizard.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;

public class DbBaselineCommand<T extends Configuration> extends AbstractFlywayCommand<T> {
    public DbBaselineCommand(final DatabaseConfiguration<T> databaseConfiguration,
                             final FlywayConfiguration<T> flywayConfiguration,
                             final Class<T> configurationClass) {
        super(FlywayCommand.BASELINE, databaseConfiguration, flywayConfiguration, configurationClass);
    }

    @Override
    protected void run(final Namespace namespace, final Flyway flyway) throws Exception {
        flyway.baseline();
    }
}
