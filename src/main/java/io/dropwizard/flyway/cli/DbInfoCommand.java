package io.dropwizard.flyway.cli;

import io.dropwizard.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.flyway.FlywayCommand;
import io.dropwizard.flyway.FlywayCommands;
import io.dropwizard.flyway.FlywayConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;
import org.flywaydb.core.Flyway;

public class DbInfoCommand<T extends Configuration> extends AbstractFlywayCommand<T> {

    private static final FlywayCommand COMMAND = FlywayCommand.INFO;

    public DbInfoCommand(final DatabaseConfiguration<T> databaseConfiguration,
                         final FlywayConfiguration<T> flywayConfiguration,
                         final Class<T> configurationClass) {
        super(COMMAND, databaseConfiguration, flywayConfiguration, configurationClass);
    }

    @Override
    public void run(final Namespace namespace, final Flyway flyway) throws Exception {
        FlywayCommands.execute(flyway, COMMAND);
    }
}
