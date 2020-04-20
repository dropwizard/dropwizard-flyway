package io.dropwizard.flyway.cli;

import io.dropwizard.flyway.FlywayConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.flyway.FlywayFactory;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.flywaydb.core.Flyway;

import java.util.SortedMap;
import java.util.TreeMap;

public class DbCommand<T extends Configuration> extends AbstractFlywayCommand<T> {
    private static final String COMMAND_NAME_ATTR = "subCommand";
    private final SortedMap<String, AbstractFlywayCommand<T>> subCommands = new TreeMap<>();

    public DbCommand(final String name, final DatabaseConfiguration<T> databaseConfiguration,
                     final FlywayConfiguration<T> flywayConfiguration,
                     final Class<T> configurationClass) {
        super(name, "Run database migration tasks",
                databaseConfiguration, flywayConfiguration, configurationClass);

        addSubCommand(new DbMigrateCommand<>(databaseConfiguration, flywayConfiguration, configurationClass));
        addSubCommand(new DbCleanCommand<>(databaseConfiguration, flywayConfiguration, configurationClass));
        addSubCommand(new DbInitCommand<>(databaseConfiguration, flywayConfiguration, configurationClass));
        addSubCommand(new DbValidateCommand<>(databaseConfiguration, flywayConfiguration, configurationClass));
        addSubCommand(new DbInfoCommand<>(databaseConfiguration, flywayConfiguration, configurationClass));
        addSubCommand(new DbRepairCommand<>(databaseConfiguration, flywayConfiguration, configurationClass));
    }

    private void addSubCommand(final AbstractFlywayCommand<T> subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
    }

    @Override
    public void configure(final Subparser subparser) {
        for (AbstractFlywayCommand<T> subCommand : subCommands.values()) {
            final Subparser cmdParser = subparser.addSubparsers()
                    .addParser(subCommand.getName())
                    .setDefault(COMMAND_NAME_ATTR, subCommand.getName())
                    .description(subCommand.getDescription());
            subCommand.configure(cmdParser);
        }
    }

    @Override
    protected void setAdditionalOptions(FlywayFactory flywayFactory, Namespace namespace) {
        subCommands.get(namespace.getString(COMMAND_NAME_ATTR)).setAdditionalOptions(flywayFactory, namespace);
    }

    @Override
    public void run(final Namespace namespace, final Flyway flyway) throws Exception {
        subCommands.get(namespace.getString(COMMAND_NAME_ATTR)).run(namespace, flyway);
    }
}